package com.thoughtworks.whatyourward.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Chandru on 10/11/17.
 */

public class ImageUtil {


    public static void loadImage(Context context, String imageUrl, ImageView imageView){

        Glide.with(context).load(imageUrl).into(imageView);

    }


    public static void loadImage(Context context, int drawable, ImageView imageView){

        Glide.with(context).load(drawable).into(imageView);

    }

}
