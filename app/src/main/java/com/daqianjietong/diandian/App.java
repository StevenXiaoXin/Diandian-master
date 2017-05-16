package com.daqianjietong.diandian;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.daqianjietong.diandian.utils.SpUtil;

import org.xutils.x;


/**
 * Created by MuWenlei on 16/10/15.
 */
public class App extends Application {
    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        x.Ext.init(this);
        SpUtil.init(this);
    }


    public static App getmApp() {
        return mApp;
    }

    public static void setmApp(App mApp) {
        App.mApp = mApp;
    }


    public static Context getAppContext() {
        return mApp;
    }

    public static Resources getAppResources() {
        return mApp.getResources();
    }

}
