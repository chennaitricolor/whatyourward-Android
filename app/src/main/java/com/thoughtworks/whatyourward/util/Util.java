package com.thoughtworks.whatyourward.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by Chandru on 10/11/17.
 */

public class Util {




    public  void showToastShort(Context context,String message){

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }




}
