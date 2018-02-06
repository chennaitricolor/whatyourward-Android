package com.thoughtworks.whatyourward.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Chandru on 02/02/18.
 */

public class IntentUtil {


    public static void makeCallWard(Context context,String contactNum) {

        if(!StringUtil.isEmpty(contactNum)) {
            if (contactNum.contains("/"))
                contactNum = contactNum.split("/")[0].trim();

            openNumberInDial(context, contactNum);
        }
    }


    public static void makeCallZone(Context context,String contactNum) {
        if(!StringUtil.isEmpty(contactNum)) {

            if (contactNum.contains("/"))
                contactNum = contactNum.split("/")[0].trim();


            openNumberInDial(context, contactNum);
        }
    }


    public static void sendEmail(Context context,String email) {
        if(!StringUtil.isEmpty(email)) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"+email));
            context.startActivity(emailIntent);

        }
    }



    private static void openNumberInDial(Context context,String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        context.startActivity(intent);
    }


    public static void joinWhatsappGroup(Context context,String linkText) {

        if(!StringUtil.isEmpty(linkText)) {

            if (!linkText.startsWith("http://") && !linkText.startsWith("https://"))
                linkText = "http://" + linkText;

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkText));
            context.startActivity(browserIntent);

        }
    }
}
