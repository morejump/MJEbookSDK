package com.mjebooksdk;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.mjebooksdk.database.HighLight;
import com.mjebooksdk.database.HighlightImpl;
import com.mjebooksdk.database.locators.ReadLocator;
import com.mjebooksdk.database.sqlite.DbAdapter;
import com.mjebooksdk.network.QualifiedTypeConverterFactory;
import com.mjebooksdk.network.R2StreamerApi;
import com.mjebooksdk.ui.activity.FolioActivity;
import com.mjebooksdk.ui.base.OnSaveHighlight;
import com.mjebooksdk.ui.base.SaveReceivedHighlightTask;
import com.mjebooksdk.util.OnHighlightListener;
import com.mjebooksdk.util.SharePreferenceManager;
import com.mjebooksdk.util.Utils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MjEbookReader {

    @SuppressLint("StaticFieldLeak")
    private static MjEbookReader singleton = null;

    public static final String EXTRA_BOOK_ID = "com.folioreader.extra.BOOK_ID";
    public static final String EXTRA_LAST_READ_LOCATOR = "com.folioreader.extra.READ_LAST_LOCATOR";
    public static final String EXTRA_CURRENT_READ_LOCATOR = "com.folioreader.extra.READ_CURRENT_LOCATOR";
    public static final String EXTRA_PORT_NUMBER = "com.folioreader.extra.PORT_NUMBER";
    public static final String ACTION_SAVE_READ_LAST_LOCATOR = "com.folioreader.action.SAVE_READ_LAST_LOCATOR";
    public static final String ACTION_SAVE_READ_CURRENT_LOCATOR = "com.folioreader.action.SAVE_READ_CURRENT_LOCATOR";
    public static final String ACTION_CLOSE_FOLIOREADER = "com.folioreader.action.CLOSE_FOLIOREADER";
    public static final String ACTION_FOLIOREADER_CLOSED = "com.folioreader.action.FOLIOREADER_CLOSED";
    public static final String ASSETS_FOLDER_FILE_PREFIX = "file:///android_asset/";
    public static final String EPUB_EXTENSION_FILE = ".epub";

    private Context context;
    private Config config;
    private boolean overrideConfig;
    private int portNumber = Constants.DEFAULT_PORT_NUMBER;
    private OnHighlightListener onHighlightListener;
    private OnClosedListener onClosedListener;
    private ReadLocator readLocator;
    private String fileNameEpub;
    private boolean isShowLastLocation;
    private String bannerAdsId;
    private String interAdsId;
    private int showInterAdsAfter;
    private String developerId;
    @Nullable
    public Retrofit retrofit;
    @Nullable
    public R2StreamerApi r2StreamerApi;

    public interface OnClosedListener {
        /**
         * You may call {@link MjEbookReader#clear()} in this method, if you wouldn't require to open
         * an epub again from the current activity.
         * Or you may call {@link MjEbookReader#stop()} in this method, if you wouldn't require to open
         * an epub again from your application.
         */
        void onFolioReaderClosed();
    }

    private BroadcastReceiver highlightReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            HighlightImpl highlightImpl = intent.getParcelableExtra(HighlightImpl.INTENT);
            HighLight.HighLightAction action = (HighLight.HighLightAction)
                    intent.getSerializableExtra(HighLight.HighLightAction.class.getName());
            if (onHighlightListener != null && highlightImpl != null && action != null) {
                onHighlightListener.onHighlight(highlightImpl, action);
            }
        }
    };

    private BroadcastReceiver readLastLocatorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReadLocator readLocator = (ReadLocator) intent.getSerializableExtra(MjEbookReader.EXTRA_LAST_READ_LOCATOR);
            SharePreferenceManager.setLastReadLocator(readLocator.toJson());
        }
    };

    private BroadcastReceiver readCurrentLocatorReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ReadLocator readLocator = (ReadLocator) intent.getSerializableExtra(MjEbookReader.EXTRA_CURRENT_READ_LOCATOR);
        }
    };

    private BroadcastReceiver closedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (onClosedListener != null)
                onClosedListener.onFolioReaderClosed();
        }
    };

    public static MjEbookReader get() {
        if (singleton == null) {
            synchronized (MjEbookReader.class) {
                if (singleton == null) {
                    if (AppContext.get() == null) {
                        throw new IllegalStateException("-> context == null");
                    }
                    singleton = new MjEbookReader(AppContext.get());
                }
            }
        }
        return singleton;
    }

    private MjEbookReader() {
    }

    private MjEbookReader(Context context) {
        this.context = context;
        DbAdapter.initialize(context);

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.registerReceiver(highlightReceiver,
                new IntentFilter(HighlightImpl.BROADCAST_EVENT));
        localBroadcastManager.registerReceiver(readLastLocatorReceiver,
                new IntentFilter(ACTION_SAVE_READ_LAST_LOCATOR));
        localBroadcastManager.registerReceiver(readCurrentLocatorReceiver,
                new IntentFilter(ACTION_SAVE_READ_CURRENT_LOCATOR));
        localBroadcastManager.registerReceiver(closedReceiver,
                new IntentFilter(ACTION_FOLIOREADER_CLOSED));
    }


    public MjEbookReader openBook() {
        SharePreferenceManager.init(context);
        if (fileNameEpub != null || !fileNameEpub.isEmpty()) {
            if (isShowLastLocation){
                ReadLocator lastReadLocator = ReadLocator.fromJson(SharePreferenceManager.getLastReadLocator(SharePreferenceManager.LAST_READ_LOCATOR_KEY));
                setReadLocator(lastReadLocator);
            }
            Intent intent = getIntentFromUrl(fileNameEpub, 0);
            context.startActivity(intent);
        }
        return singleton;
    }

//    public MjEbookReader openBook(String assetOrSdcardPath) {
//        Intent intent = getIntentFromUrl(assetOrSdcardPath, 0);
//        context.startActivity(intent);
//        return singleton;
//    }

//    public MjEbookReader openBook(int rawId) {
//        Intent intent = getIntentFromUrl(null, rawId);
//        context.startActivity(intent);
//        return singleton;
//    }
//
//    public MjEbookReader openBook(String assetOrSdcardPath, String bookId) {
//        Intent intent = getIntentFromUrl(assetOrSdcardPath, 0);
//        intent.putExtra(EXTRA_BOOK_ID, bookId);
//        context.startActivity(intent);
//        return singleton;
//    }
//
//    public MjEbookReader openBook(int rawId, String bookId) {
//        Intent intent = getIntentFromUrl(null, rawId);
//        intent.putExtra(EXTRA_BOOK_ID, bookId);
//        context.startActivity(intent);
//        return singleton;
//    }

    private Intent getIntentFromUrl(String assetOrSdcardPath, int rawId) {

        Intent intent = new Intent(context, FolioActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Config.INTENT_CONFIG, config);
        intent.putExtra(Config.EXTRA_OVERRIDE_CONFIG, overrideConfig);
        intent.putExtra(EXTRA_PORT_NUMBER, portNumber);
        intent.putExtra(FolioActivity.EXTRA_READ_LOCATOR, (Parcelable) readLocator);

        if (rawId != 0) {
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_PATH, rawId);
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_TYPE,
                    FolioActivity.EpubSourceType.RAW);
        } else if (assetOrSdcardPath.contains(Constants.ASSET)) {
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_PATH, assetOrSdcardPath);
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_TYPE,
                    FolioActivity.EpubSourceType.ASSETS);
        } else {
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_PATH, assetOrSdcardPath);
            intent.putExtra(FolioActivity.INTENT_EPUB_SOURCE_TYPE,
                    FolioActivity.EpubSourceType.SD_CARD);
        }

        return intent;
    }

    /**
     * Pass your configuration and choose to override it every time or just for first execution.
     *
     * @param config         custom configuration.
     * @param overrideConfig true will override the config, false will use either this
     *                       config if it is null in application context or will fetch previously
     *                       saved one while execution.
     */
    public MjEbookReader setConfig(Config config, boolean overrideConfig) {
        this.config = config;
        this.overrideConfig = overrideConfig;
        return singleton;
    }

    public MjEbookReader setPortNumber(int portNumber) {
        this.portNumber = portNumber;
        return singleton;
    }

    public static void initRetrofit(String streamerUrl) {

        if (singleton == null || singleton.retrofit != null)
            return;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();

        singleton.retrofit = new Retrofit.Builder()
                .baseUrl(streamerUrl)
                .addConverterFactory(new QualifiedTypeConverterFactory(
                        JacksonConverterFactory.create(),
                        GsonConverterFactory.create()))
                .client(client)
                .build();

        singleton.r2StreamerApi = singleton.retrofit.create(R2StreamerApi.class);
    }

    public MjEbookReader setOnHighlightListener(OnHighlightListener onHighlightListener) {
        this.onHighlightListener = onHighlightListener;
        return singleton;
    }

    public MjEbookReader setOnClosedListener(OnClosedListener onClosedListener) {
        this.onClosedListener = onClosedListener;
        return singleton;
    }

    public MjEbookReader setReadLocator(ReadLocator readLocator) {
        this.readLocator = readLocator;
        return singleton;
    }

    public void saveReceivedHighLights(List<HighLight> highlights,
                                       OnSaveHighlight onSaveHighlight) {
        new SaveReceivedHighlightTask(onSaveHighlight, highlights).execute();
    }

    /**
     * Closes all the activities related to MjEbookReader.
     * After closing all the activities of MjEbookReader, callback can be received in
     * {@link OnClosedListener#onFolioReaderClosed()} if implemented.
     * Developer is still bound to call {@link #clear()} or {@link #stop()}
     * for clean up if required.
     */
    public void close() {
        Intent intent = new Intent(MjEbookReader.ACTION_CLOSE_FOLIOREADER);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /**
     * Nullifies readLocator and listeners.
     * This method ideally should be used in onDestroy() of Activity or Fragment.
     * Use this method if you want to use MjEbookReader singleton instance again in the application,
     * else use {@link #stop()} which destruct the MjEbookReader singleton instance.
     */
    public static synchronized void clear() {

        if (singleton != null) {
            singleton.readLocator = null;
            singleton.onHighlightListener = null;
            singleton.onClosedListener = null;
        }
    }

    /**
     * Destructs the MjEbookReader singleton instance.
     * Use this method only if you are sure that you won't need to use
     * MjEbookReader singleton instance again in application, else use {@link #clear()}.
     */
    public static synchronized void stop() {

        if (singleton != null) {
            DbAdapter.terminate();
            singleton.unregisterListeners();
            singleton = null;
        }
    }

    private void unregisterListeners() {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.unregisterReceiver(highlightReceiver);
        localBroadcastManager.unregisterReceiver(readLastLocatorReceiver);
        localBroadcastManager.unregisterReceiver(readCurrentLocatorReceiver);
        localBroadcastManager.unregisterReceiver(closedReceiver);
    }

    public MjEbookReader setFileNameEpub(String fileNameEpub) {
        if (fileNameEpub != null && !fileNameEpub.isEmpty()) {
            this.fileNameEpub = ASSETS_FOLDER_FILE_PREFIX + fileNameEpub + EPUB_EXTENSION_FILE;
        }
        return singleton;
    }

    public String getBannerAdsId() {
        return bannerAdsId;
    }

    public MjEbookReader setBannerAdsId(String bannerAdsId) {
        this.bannerAdsId = bannerAdsId;
        return singleton;
    }

    public String getInterAdsId() {
        return interAdsId;
    }

    public MjEbookReader setInterAdsId(String interAdsId) {
        this.interAdsId = interAdsId;
        return singleton;
    }

    public int getShowInterAdsAfter() {
        return showInterAdsAfter;
    }

    public MjEbookReader setShowInterAdsAfter(int showInterAdsAfter) {
        this.showInterAdsAfter = showInterAdsAfter;
        Utils.TIME_TO_SHOW_INTER = showInterAdsAfter;
        return singleton;
    }

    public boolean isShowLastLocation() {
        return isShowLastLocation;
    }

    public MjEbookReader setShowLastLocation(boolean showLastLocation) {
        isShowLastLocation = showLastLocation;
        return singleton;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public MjEbookReader setDeveloperId(String developerId) {
        this.developerId = developerId;
        if (developerId != null && !developerId.isEmpty()) {
            Utils.DEVELOPER_ID = developerId;
        }
        return singleton;
    }

}
