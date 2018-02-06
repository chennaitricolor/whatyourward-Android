package com.thoughtworks.whatyourward.features.splash;

import com.thoughtworks.whatyourward.data.DataManager;
import com.thoughtworks.whatyourward.features.base.BasePresenter;
import com.thoughtworks.whatyourward.injection.ConfigPersistent;

import javax.inject.Inject;

@ConfigPersistent
public class SplashScreenPresenter extends BasePresenter<SplashScreenView> {

    private final DataManager dataManager;

    @Inject
    public SplashScreenPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }



    @Override
    public void attachView(SplashScreenView mvpView) {
        super.attachView(mvpView);
    }


    public void onViewReady(){
        getView().onViewReady();

    }


//    public void goToNextScreen(){
//        getView().goToNextScreen();
//    }


    public void checkLocationPermission() {
        getView().onLocationPermission();
    }


    public void handleLocationPermission(boolean isGranted) {

        if(isGranted) {
            getView().goToNextScreen();
        }else{
            getView().showLocationPermissionError();
        }
    }

    public void closeScreen(){

        getView().closeScreen();
    }


}
