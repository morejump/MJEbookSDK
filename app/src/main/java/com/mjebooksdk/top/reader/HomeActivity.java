package com.mjebooksdk.top.reader;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.mjebooksdk.Config;
import com.mjebooksdk.MjEbookReader;
import com.mjebooksdk.util.AppUtil;
import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private MjEbookReader mjEbookReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        mjEbookReader = MjEbookReader.get();

        Config config = AppUtil.getSavedConfig(getApplicationContext());
        if (config == null){
            config = new Config();
        }
        config.setAllowedDirection(Config.AllowedDirection.ONLY_VERTICAL);

        mjEbookReader.setConfig(config, true)
                .setShowLastLocation(true)
                .setInterAdsId("ca-app-pub-3940256099942544/1033173712")
                .setBannerAdsId("ca-app-pub-3940256099942544/6300978111")
                .setShowInterAdsAfter(2)
                .setDeveloperId("morejump")
                .setFileNameEpub("tamquocdiennghia")
                .openBook();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MjEbookReader.clear();
    }
}

