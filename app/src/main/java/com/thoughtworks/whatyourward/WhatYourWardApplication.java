package com.thoughtworks.whatyourward;

import android.app.Application;
import android.content.Context;


import com.thoughtworks.whatyourward.injection.component.AppComponent;
import com.thoughtworks.whatyourward.injection.component.DaggerAppComponent;
import com.thoughtworks.whatyourward.injection.module.AppModule;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class WhatYourWardApplication extends Application {

    private AppComponent appComponent;

    public static WhatYourWardApplication get(Context context) {
        return (WhatYourWardApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(Constants.FONT_APP)
                .setFontAttrId(R.attr.fontPath)
                .build());


    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
