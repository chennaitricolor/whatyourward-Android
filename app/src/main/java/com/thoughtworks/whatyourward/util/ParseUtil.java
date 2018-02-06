package com.thoughtworks.whatyourward.util;

import android.content.res.AssetManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.whatyourward.data.model.ward.Ward;
import com.thoughtworks.whatyourward.data.model.ward.ZoneInfo;

/**
 * Created by udhayakumarulaganathan on 31/01/18.
 */

public class ParseUtil {

    private static Gson gson = new GsonBuilder().create();

    public static String getWardNum (String wardNameFromKml) {
        if (wardNameFromKml == null)
            return null;

        String[] strs = wardNameFromKml.split(" ");

        if (strs.length < 2)
            return null;

        return strs[1];
    }


}
