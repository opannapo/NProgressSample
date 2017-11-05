package napodev.nprogress;

import android.app.Application;

import napodev.framework.bework.utils.Log;
import napodev.framework.bework.utils.local.SPreference;

/**
 * Created by opannapo on 11/5/17.
 */

public class APP extends Application {
    public String subActivityName;
    private static final String PREF_NAME = "NProgressSample";
    private static final String PREF_KEY = "aA3eG34SQ1W2E3R4";
    private static final String LOG_TAG = "NProgressSample";
    private boolean isLogEnable = true;
    private boolean isWithDetailLine = true;
    private boolean isWithLogCaller = true;

    public APP() {

    }

    public APP(String subActivityName) {
        this.subActivityName = subActivityName;
    }

    public void onCreate() {
        super.onCreate();
        this.setupConfiguration();
    }

    public void onTerminate() {
        super.onTerminate();
        System.gc();
    }

    private void setupConfiguration() {
        SPreference.getConfig().setAppContex(this).setPrefName(PREF_NAME).setAESKey(PREF_KEY).build();
        Log.getConfig().setLogEnable(isLogEnable).setWithDetailLine(isWithDetailLine).setTAG(LOG_TAG).setWithDetailCaller(isWithLogCaller);
    }
}

