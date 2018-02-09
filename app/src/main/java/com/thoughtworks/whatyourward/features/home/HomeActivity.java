package com.thoughtworks.whatyourward.features.home;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.thoughtworks.whatyourward.Constants;
import com.thoughtworks.whatyourward.R;
import com.thoughtworks.whatyourward.data.model.ward.Ward;
import com.thoughtworks.whatyourward.data.model.ward.ZoneInfo;
import com.thoughtworks.whatyourward.features.base.BaseActivity;
import com.thoughtworks.whatyourward.injection.component.ActivityComponent;
import com.thoughtworks.whatyourward.util.IntentUtil;
import com.thoughtworks.whatyourward.util.KmlUtil;
import com.thoughtworks.whatyourward.util.NetworkUtil;
import com.thoughtworks.whatyourward.util.ParseUtil;
import com.thoughtworks.whatyourward.util.StringUtil;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;


public class HomeActivity extends BaseActivity implements HomeView, OnMapReadyCallback
        , GoogleApiClient.OnConnectionFailedListener
        , GoogleApiClient.ConnectionCallbacks
        , ResultCallback<LocationSettingsResult> {

    @Inject
    HomePresenter homePresenter;


    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.img_loading)
    ImageView imgLoading;
    @BindView(R.id.img_map_marker)
    ImageView imgMapMarker;

    @BindView(R.id.view_loading)
    LinearLayout viewLoading;

    @BindView(R.id.ll_footer)
    LinearLayout llFooter;

    private KmlLayer kmlLayer;


    private SupportMapFragment mapFragment;

    private GoogleMap mGoogleMap;
    private ArrayList<Ward> mWardArrayList;
    private AnimationDrawable loadingAnimationDrawable;


    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest locationRequest;
    private Handler handler;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        homePresenter.onViewReady();

    }

    @Override
    public int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        homePresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        homePresenter.detachView();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        Timber.i("on Map ready called");


    }


    private void configureMapAndAddLayer(double latitude, double longitude) {

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), Constants.DEFAULT.MAP_ZOOM));
        mGoogleMap.getUiSettings().setZoomControlsEnabled(false);

        mGoogleMap.setMyLocationEnabled(true);

        handler = new Handler();

        handler.postDelayed(() -> {

            try {

                homePresenter.stopLoadingAnimation();

                Timber.i("Kml loading");

                kmlLayer = new KmlLayer(mGoogleMap, R.raw.chennai_wards, HomeActivity.this);
                kmlLayer.addLayerToMap();

                Timber.i("Kml loaded");

            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
        }, Constants.INTERVAL_IN_MS.KML_LOADING);


    }


    @Override
    public void onViewReady() {

        homePresenter.startLoadingAnimation();

        initGoogleApiClient();

        initGoogleMaps();

        homePresenter.loadWard();
    }


    private void initGoogleMaps() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

    }


    @Override
    public void showCategoryList(ArrayList<Ward> wardArrayList) {

        mWardArrayList = wardArrayList;


    }

    @Override
    public void showLoadingAnimation() {

        viewLoading.setVisibility(View.VISIBLE);
        imgMapMarker.setVisibility(View.GONE);
        llFooter.setVisibility(View.GONE);

        loadingAnimationDrawable
                = (AnimationDrawable) imgLoading.getDrawable();

        imgLoading.post(
                () -> loadingAnimationDrawable.start()

        );


    }

    @Override
    public void hideLoadingAnimation() {

        loadingAnimationDrawable.stop();

        viewLoading.setVisibility(View.GONE);
        llFooter.setVisibility(View.VISIBLE);

        imgMapMarker.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_next)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_next:

                homePresenter.clickNextButton();

                break;
        }
    }


    @Override
    public void showWardDetailsBottomSheet(Ward ward) {

        ZoneInfo zoneInfo = ward.getZoneInfo();

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_ward_details, null);

        TextView txtZoneName = view.findViewById(R.id.txt_zone_name);
        TextView txtZoneAddress = view.findViewById(R.id.txt_zone_address);
        TextView txtZoneNumber = view.findViewById(R.id.txt_zone_number);
        TextView txtZoneMobile = view.findViewById(R.id.txt_zone_mobile);
        TextView txtWardName = view.findViewById(R.id.txt_ward_name);
        TextView txtWardAddress = view.findViewById(R.id.txt_ward_address);
        TextView txtWardId = view.findViewById(R.id.txt_ward_id);
        TextView txtWardMobile = view.findViewById(R.id.txt_ward_mobile);
        TextView txtWardEmail = view.findViewById(R.id.txt_ward_email);
        LinearLayout llWhatsappGroup = view.findViewById(R.id.ll_whatsapp_group);


        setText(getString(R.string.text_ward_name_hint), ward.getWardName(), txtWardName);
        setText(getString(R.string.text_ward_address_hint), ward.getWardOfficeAddress(), txtWardAddress);
        setText("", ward.getWardNo(), txtWardId);
        setText(getString(R.string.text_zone_name_hint), zoneInfo.getZoneName(), txtZoneName);
        setText("", zoneInfo.getZoneNo(), txtZoneNumber);
        setText(getString(R.string.text_zone_address_hint), zoneInfo.getZonalOfficeAddress(), txtZoneAddress);

        setContactDetails(zoneInfo.getZonalOfficePhone(), txtZoneMobile);

        setContactDetails(ward.getWardOfficePhone(), txtWardMobile);

        setEmailDetails(ward.getWardOfficeEmail(), txtWardEmail);


        llWhatsappGroup.setOnClickListener(v -> IntentUtil.joinWhatsappGroup(HomeActivity.this, ward.getWardWhatsappGroupLink()));

        txtWardMobile.setOnClickListener(v -> IntentUtil.makeCallWard(HomeActivity.this, ward.getWardOfficePhone()));

        txtZoneMobile.setOnClickListener(v -> IntentUtil.makeCallZone(HomeActivity.this, zoneInfo.getZonalOfficePhone()));

        txtWardEmail.setOnClickListener(v -> {

            IntentUtil.sendEmail(HomeActivity.this, ward.getWardOfficeEmail());
        });

        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());

        dialog.show();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }


    private void setContactDetails(String number, TextView textView) {


        if (!StringUtil.isEmpty(number)) {
            textView.setText(number);
        } else {
            textView.setText(R.string.info_no_contact);
        }

        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_phone), null, null, null);

    }


    private void setEmailDetails(String email, TextView textView) {


        if (!StringUtil.isEmpty(email)) {
            textView.setText(email);
        } else {
            textView.setText(R.string.info_no_email);
        }

        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.ic_email_variant), null, null, null);

    }


    @Override
    public void getCurrentLocation() {

        Timber.i("Location enabled success");

        Timber.i("onLocation Updated called");


        Location location = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (location != null) {

            Timber.i("Lat lng inside onLocationUpdated");

            configureMapAndAddLayer(location.getLatitude(), location.getLongitude());

            Timber.i("getMapAsync() called in onLocationUpdated");

        } else {


            Toast.makeText(HomeActivity.this, R.string.error_default_location_message,Toast.LENGTH_SHORT).show();

            configureMapAndAddLayer(Constants.DEFAULT.LATITUDE, Constants.DEFAULT.LONGITUDE);

//            homePresenter.showLocationPermissionErrorDialog();
        }

    }

//    @Override
//    public void showLocationPermissionErrorDialog() {
//
//
//        Timber.i("Location object is null.");
//
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
//
//
//        alertDialog
//                .setMessage(R.string.dialog_location_message)
//                .setCancelable(false)
//                .setPositiveButton(R.string.dialog_location_positve_btn, (dialogBox, id) -> {
//
//                    Timber.i("Trying to reconnect");
//
//                    mGoogleApiClient.connect();
//                    dialogBox.dismiss();
//
//                })
//
//                .setNegativeButton(R.string.dialog_location_negative_btn,
//                        (dialogBox, id) -> {
//
//                            homePresenter.stopLoadingAnimation();
//
//                            homePresenter.closeScreen();
//                            dialogBox.dismiss();
//                        })
//
//
//                .setNeutralButton(R.string.dialog_location_neutral_btn,
//                        (dialogBox, id) -> {
//
//                            homePresenter.showGpsPermissionDialog();
//
//                            dialogBox.dismiss();
//                        });
//
//        AlertDialog alertDialogAndroid = alertDialog.create();
//        alertDialogAndroid.show();
//
//    }


    @Override
    public void showWardDetailsNotFoundError() {



        Toast.makeText(this, R.string.error_no_ward_details, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGpsPermissionEnabled() {

        Toast.makeText(getApplicationContext(), R.string.permission_success_gps, Toast.LENGTH_LONG).show();

        homePresenter.getCurrentLocation();

    }

    @Override
    public void showGpsPermissionError() {

        Toast.makeText(getApplicationContext(), R.string.permission_error_gps, Toast.LENGTH_LONG).show();

        homePresenter.stopLoadingAnimation();

        homePresenter.closeScreen();


    }

    @Override
    public void onNextButtonClicked() {
        LatLng centerLatLngPosition = getMapCenterPosition();

        String wardNo = getWardNum(centerLatLngPosition);

        homePresenter.handleWardDetails(wardNo, mWardArrayList);
    }

    @Override
    public void closeScreen() {

        finish();
    }

    @Override
    public void showAirplaneModeIsOnError() {

        Toast.makeText(getApplicationContext(), R.string.permission_error_airplane, Toast.LENGTH_LONG).show();

        homePresenter.stopLoadingAnimation();

        homePresenter.closeScreen();

    }


    private LatLng getMapCenterPosition() {
        if (mGoogleMap != null)
            return mGoogleMap.getCameraPosition().target;

        return new LatLng(120.0, 80.0);
    }


    private String getWardNum(LatLng latLng) {
        KmlPlacemark kmlPlacemark = KmlUtil.containsInAnyPolygon(kmlLayer, latLng);
        if (kmlPlacemark != null) {
            String wardName = kmlPlacemark.getProperty(Constants.ATTRIBUTE.KML_NAME);
            String wardNo = ParseUtil.getWardNum(wardName);
            return wardNo;
        }
        return null;
    }


    private void setText(String appendText, String text, TextView textView) {

        if (TextUtils.isEmpty(text)) {
            textView.setText(appendText + "--");
        } else {
            textView.setText(appendText + text);
        }

    }


    private void initGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();
        mGoogleApiClient.connect();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(Constants.INTERVAL_IN_MS.UPDATE_LOCATION);
        locationRequest.setFastestInterval(Constants.INTERVAL_IN_MS.FATEST_LOCATION);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
            homePresenter.showGpsPermissionDialog();
    }


    @Override
    public void showGpsPermissionDialog(){


        if (!NetworkUtil.isAirplaneModeOn(HomeActivity.this)) {

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(
                            mGoogleApiClient,
                            builder.build()
                    );
            result.setResultCallback(this);

        } else {

            homePresenter.handleAirplaneModeOnState();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

        Timber.i("Google Api client connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Timber.i("Google Api client connection failed");


    }

    @Override
    public void onResult(@NonNull LocationSettingsResult result) {

        final Status status = result.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:

                homePresenter.getCurrentLocation();

                break;

            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                try {

                    status.startResolutionForResult(HomeActivity.this, Constants.REQUEST_CODES.CHECK_GPS_PERMISSION);

                } catch (IntentSender.SendIntentException e) {

                }
                break;

            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                // Location settings are unavailable so not possible to show any dialog now
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODES.CHECK_GPS_PERMISSION) {
            homePresenter.handleGpsPermissionState(resultCode);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onBackPressed() {

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        super.onBackPressed();
    }
}
