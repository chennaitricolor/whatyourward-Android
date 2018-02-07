package com.thoughtworks.whatyourward;

/**
 * Created by Chandru on 09/11/17.
 */
public class Constants {

    public static final String PREF_FILE_NAME = "thoughtworks_pref_file";

    public static final String CHENNAI_WARD_INFO_DETAILS = "chennaiwardinfo.txt";

    public static final String DATABASE_NAME =  "thoughtworks_db";


    public static final String FONT_APP = "fonts/Dosis-Medium.ttf";



    public interface SHARED_PREF {
        String SCREEN_ID = "Constants.SHARED_PREF.SCREEN_ID";

    }



    public interface DEFAULT{

       int MAP_ZOOM = 13;

        double LATITUDE = 13.0827;

        double LONGITUDE = 80.2707;

    }

    public interface REQUEST_CODES{

        int CHECK_GPS_PERMISSION = 100;

    }

    public interface RESULT_CODES{

        int SUCCESS = -1;

    }


    public interface ATTRIBUTE{

        String KML_NAME = "name";

    }
    public interface INTERVAL_IN_MS{

        int UPDATE_LOCATION = 10000; // 10 sec
        int FATEST_LOCATION = 5000; // 5 sec

        int SPLASH_TIME_OUT = 2000;

        int KML_LOADING = 5000;

    }


}
