package com.thoughtworks.whatyourward.data;

import android.content.Context;

import com.github.polok.localify.LocalifyClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.whatyourward.Constants;
import com.thoughtworks.whatyourward.data.local.PreferencesHelper;
import com.thoughtworks.whatyourward.data.model.ward.Ward;
import com.thoughtworks.whatyourward.injection.ApplicationContext;
import com.thoughtworks.whatyourward.interfaces.OnWardSuccess;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Chandru on 09/11/17.
 */

@Singleton
public class DataManager {


    private LocalifyClient localifyClient;


    private PreferencesHelper preferencesHelper;


    private Context context;

    @Inject
    public DataManager(@ApplicationContext Context context,LocalifyClient localifyClient, PreferencesHelper preferencesHelper) {

        this.context = context;
        this.localifyClient = localifyClient;
        this.preferencesHelper = preferencesHelper;


    }



    public void loadWard(OnWardSuccess onWardSuccess){

        localifyClient.localify()
                .rx()
                .loadAssetsFile(Constants.CHENNAI_WARD_INFO_DETAILS)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, ArrayList<Ward>>() {
                    @Override
                    public ArrayList<Ward> call(String data) {

                        Gson gson = new GsonBuilder().create();
                        return gson.fromJson(data,  new TypeToken<ArrayList<Ward>>(){}.getType());
                    }
                }).subscribe(new Subscriber<ArrayList<Ward>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ArrayList<Ward> categoryResponse) {

                onWardSuccess.onWardList(categoryResponse);

            }
        });
    }



    public void trackScreen(int screedID){
         preferencesHelper.putInt(Constants.SHARED_PREF.SCREEN_ID, screedID);
    }


    public int loadScreen(int screedID){
        return preferencesHelper.getInt(Constants.SHARED_PREF.SCREEN_ID);
    }




    }

