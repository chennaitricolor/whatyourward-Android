package com.thoughtworks.whatyourward.features.home;

import com.thoughtworks.whatyourward.data.model.ward.Ward;
import com.thoughtworks.whatyourward.features.base.MvpView;

import java.util.ArrayList;

public interface HomeView extends MvpView {

    void onViewReady();

    void showCategoryList(ArrayList<Ward> wardArrayList);

    void showLoadingAnimation();

    void hideLoadingAnimation();

    void showWardDetailsBottomSheet(Ward ward);

//    void onLocationPermission();

    void getCurrentLocation();

    void showLocationPermissionErrorDialog();

    void showWardDetailsNotFoundError();

    void onGpsPermissionEnabled();

    void showGpsPermissionError();

    void onNextButtonClicked();

    void closeScreen();

    void showAirplaneModeIsOnError();

//    void hideAnimationAndFinish();

    void showGpsPermissionDialog();

}
