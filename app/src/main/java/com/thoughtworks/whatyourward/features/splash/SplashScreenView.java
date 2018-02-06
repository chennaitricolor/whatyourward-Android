package com.thoughtworks.whatyourward.features.splash;

import com.thoughtworks.whatyourward.features.base.MvpView;

public interface SplashScreenView extends MvpView {


    void goToNextScreen();

    void onViewReady();

    void onLocationPermission();

    void showLocationPermissionError();

    void closeScreen();


}
