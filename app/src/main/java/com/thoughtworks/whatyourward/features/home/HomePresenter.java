package com.thoughtworks.whatyourward.features.home;

import com.thoughtworks.whatyourward.Constants;
import com.thoughtworks.whatyourward.data.DataManager;
import com.thoughtworks.whatyourward.data.model.ward.Ward;
import com.thoughtworks.whatyourward.features.base.BasePresenter;
import com.thoughtworks.whatyourward.injection.ConfigPersistent;
import com.thoughtworks.whatyourward.interfaces.OnWardSuccess;
import com.thoughtworks.whatyourward.util.StringUtil;

import java.util.ArrayList;

import javax.inject.Inject;

@ConfigPersistent
public class HomePresenter extends BasePresenter<HomeView> {

    private final DataManager dataManager;


    @Inject
    public HomePresenter(DataManager dataManager) {
        this.dataManager = dataManager;

    }


    @Override
    public void attachView(HomeView mvpView) {
        super.attachView(mvpView);


    }


    public void onViewReady() {
        getView().onViewReady();
    }


    public void loadWard() {

        dataManager.loadWard(wardList -> getView().showCategoryList(wardList));
    }

    public void startLoadingAnimation() {

        getView().showLoadingAnimation();
    }


    public void stopLoadingAnimation() {

        getView().hideLoadingAnimation();

    }


//    public void checkAndHandleLocationPermission() {
//
//        getView().onLocationPermission();
//    }

//    public void handleLocationPermission(boolean isGranted) {
//
//        if(isGranted) {
//            getView().getCurrentLocation();
//        }else{
//            getView().showLocationPermissionError();
//        }
//    }

    public void handleWardDetails(String wardNo, ArrayList<Ward> wardList) {

        if(!StringUtil.isEmpty(wardNo)) {
            for (Ward ward : wardList) {
                if (ward != null && wardNo.equalsIgnoreCase(ward.getWardNo())) {
                    getView().showWardDetailsBottomSheet(ward);
                }
            }
        }else{
            getView().showWardDetailsNotFoundError();
        }
    }

    public void handleGpsPermissionState(int resultCode) {

        if (resultCode == Constants.RESULT_CODES.SUCCESS) {
            getView().onGpsPermissionEnabled();
        }else {
            getView().showGpsPermissionError();
        }

    }

    public void getCurrentLocation(){

        getView().getCurrentLocation();
    }

    public void clickNextButton() {

        getView().onNextButtonClicked();
    }

    public void closeScreen(){

        getView().closeScreen();
    }


    public void handleAirplaneModeOnState() {

        getView().showAirplaneModeIsOnError();

    }

    public void showLocationPermissionErrorDialog(){

        getView().showLocationPermissionErrorDialog();
    }

    public void showGpsPermissionDialog(){

        getView().showGpsPermissionDialog();
    }

}
