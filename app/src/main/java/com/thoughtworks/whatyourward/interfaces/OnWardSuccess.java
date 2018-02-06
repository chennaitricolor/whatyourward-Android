package com.thoughtworks.whatyourward.interfaces;

import com.thoughtworks.whatyourward.data.model.ward.Ward;

import java.util.ArrayList;

public interface OnWardSuccess {

    void onWardList(ArrayList<Ward> wardList);
}
