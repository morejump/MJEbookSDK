package com.mjebooksdk.top.reader;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.mjebooksdk.Config;
import com.mjebooksdk.FolioReader;
import com.mjebooksdk.util.AppUtil;
import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private FolioReader folioReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        folioReader = FolioReader.get();

        Config config = AppUtil.getSavedConfig(getApplicationContext());
        if (config == null)
            config = new Config();
        config.setAllowedDirection(Config.AllowedDirection.ONLY_VERTICAL);

        folioReader.setConfig(config, true)
                .setShowLastLocation(true)
                .setInterAdsId("dddddddddd")
                .setBannerAdsId("ca-app-pub-3940256099942544/6300978111")
                .setShowInterAdsAfter(5)
                .setFileNameEpub("tamquocdiennghia")
                .openBook();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FolioReader.clear();
    }
}

