# MjEbookSDK

MjEbookSDK is a wrapper library. Which is based on FolioReader-Android opensource. It is customized to be used more simply.


# Gradle

Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependency
```
implementation 'com.github.morejump:MJEbookSDK:Tag'
```


# Usage

Just by looking at the sample code, we can know how to use the library:
```
public class HomeActivity extends AppCompatActivity {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    private MjEbookReader mjEbookReader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
```
tamquocdiennghia is the epub file name that we copied into the Assests folder: tamquocdiennghia.epub

Done!!!
