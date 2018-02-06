package com.thoughtworks.whatyourward.features.splash;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.thoughtworks.whatyourward.Constants;
import com.thoughtworks.whatyourward.R;
import com.thoughtworks.whatyourward.features.base.BaseActivity;
import com.thoughtworks.whatyourward.features.home.HomeActivity;
import com.thoughtworks.whatyourward.injection.component.ActivityComponent;
import com.thoughtworks.whatyourward.util.ImageUtil;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by udhayakumarulaganathan on 03/01/18.
 */

public class SplashScreenActivity extends BaseActivity implements SplashScreenView {


    @Inject
    SplashScreenPresenter splashScreenPresenter;

    @BindView(R.id.img_splash)
    ImageView imgSplash;
    private Handler handler;

    private RxPermissions rxPermissions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashScreenPresenter.onViewReady();

    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        splashScreenPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        splashScreenPresenter.detachView();
    }


    @Override
    public void goToNextScreen() {
        handler =  new Handler();


        handler.postDelayed(() -> {

            startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
            finish();

        }, Constants.INTERVAL_IN_MS.SPLASH_TIME_OUT);


    }

    @Override
    public void onViewReady() {


        ImageUtil.loadImage(this,R.drawable.bg_splash,imgSplash);

//        splashScreenPresenter.goToNextScreen();

        splashScreenPresenter.checkLocationPermission();

    }

    @Override
    public void onBackPressed() {

        handler.removeCallbacksAndMessages(null);
        super.onBackPressed();
    }


    @Override
    public void onLocationPermission() {

        rxPermissions = new RxPermissions(SplashScreenActivity.this); // where this is an Activity instance

        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {

                    splashScreenPresenter.handleLocationPermission(granted);


                });
    }

    @Override
    public void showLocationPermissionError() {
        Toast.makeText(SplashScreenActivity.this,
                R.string.permission_error_location,
                Toast.LENGTH_SHORT).show();


        splashScreenPresenter.closeScreen();
    }

    @Override
    public void closeScreen() {

        finish();
    }

}

