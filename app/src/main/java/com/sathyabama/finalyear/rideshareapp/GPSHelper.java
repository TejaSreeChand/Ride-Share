package com.sathyabama.finalyear.rideshareapp;

import android.content.Context;
import android.os.Handler;

public class GPSHelper {
    GPSTracker gps;
    double latOfSensor = 0, lonOfSensor = 0;
    String latitude = "", longitude = "";
    public GPSHelper(Context context) {

        final Handler ha = new Handler();
        gps = new GPSTracker(context);
        if (gps.canGetLocation() && gps.getLatitude() != 0) {

            latOfSensor = gps.getLatitude();
            lonOfSensor = gps.getLongitude();


        } else {
            gps.showSettingsAlert();
        }

    }



    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
