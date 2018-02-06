package com.thoughtworks.whatyourward.features.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.LongSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.concurrent.atomic.AtomicLong;

import butterknife.ButterKnife;
import com.thoughtworks.whatyourward.WhatYourWardApplication;
import com.thoughtworks.whatyourward.injection.component.ActivityComponent;
import com.thoughtworks.whatyourward.injection.component.ConfigPersistentComponent;
import com.thoughtworks.whatyourward.injection.component.DaggerConfigPersistentComponent;
import com.thoughtworks.whatyourward.injection.module.ActivityModule;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Abstract activity that every other Activity in this application must implement. It provides the
 * following functionality: - Handles creation of Dagger components and makes sure that instances of
 * ConfigPersistentComponent are kept across configuration changes. - Set up and handles a
 * GoogleApiClient instance that can be used to access the Google sign in api. - Handles signing out
 * when an authentication error event is received.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final LongSparseArray<ConfigPersistentComponent> componentsArray =
            new LongSparseArray<>();

    private long activityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);

        // Create the ActivityComponent and reuses cached ConfigPersistentComponent if this is
        // being called after a configuration change.
        activityId =
                savedInstanceState != null
                        ? savedInstanceState.getLong(KEY_ACTIVITY_ID)
                        : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (componentsArray.get(activityId) == null) {
            Timber.i("Creating new ConfigPersistentComponent id=%d", activityId);
            configPersistentComponent =
                    DaggerConfigPersistentComponent.builder()
                            .appComponent(WhatYourWardApplication.get(this).getComponent())
                            .build();
            componentsArray.put(activityId, configPersistentComponent);
        } else {
            Timber.i("Reusing ConfigPersistentComponent id=%d", activityId);
            configPersistentComponent = componentsArray.get(activityId);
        }
        ActivityComponent activityComponent =
                configPersistentComponent.activityComponent(new ActivityModule(this));
        inject(activityComponent);
        attachView();
    }

    protected abstract int getLayout();

    protected abstract void inject(ActivityComponent activityComponent);

    protected abstract void attachView();

    protected abstract void detachPresenter();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, activityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            Timber.i("Clearing ConfigPersistentComponent id=%d", activityId);
            componentsArray.remove(activityId);
        }
        detachPresenter();
        super.onDestroy();
    }

    public void initToolbar(String title, Toolbar toolbar, boolean isBackEnabled) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isBackEnabled);
        getSupportActionBar().setDisplayShowHomeEnabled(isBackEnabled);
        getSupportActionBar().setTitle(title);
    }

}
