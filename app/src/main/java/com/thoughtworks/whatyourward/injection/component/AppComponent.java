package com.thoughtworks.whatyourward.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

import com.thoughtworks.whatyourward.data.DataManager;
import com.thoughtworks.whatyourward.injection.ApplicationContext;
import com.thoughtworks.whatyourward.injection.module.AppModule;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager dataManager();

//    LocalifyClient localifyClient();
}
