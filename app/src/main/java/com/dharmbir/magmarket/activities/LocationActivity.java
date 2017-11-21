package com.dharmbir.magmarket.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;


import com.dharmbir.magmarket.R;

import im.delight.android.location.SimpleLocation;


// chat icon on chat detail page
public class LocationActivity extends FragmentActivity {

    public double latitude = 26.922054;
    public double longitude = 75.778851;

    SimpleLocation simpleLoc;

    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST=1337;
    private static final int LOCATION_REQUEST=INITIAL_REQUEST+3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        simpleLoc = new SimpleLocation(this);

        fetchCurrentLoation();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void fetchCurrentLoation() {
        try {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (canAccessLocation()) {
                    simpleLoc.beginUpdates();
                } else {
                    requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                }
            } else {
                simpleLoc.beginUpdates();
            }

            if (!simpleLoc.hasLocationEnabled()) {
                // ask the user to enable location access
                SimpleLocation.openSettings(this);
                return;
            }

            latitude  = simpleLoc.getLatitude();
            longitude = simpleLoc.getLongitude();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        simpleLoc.endUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // start auto scroll when onResume

        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (canAccessLocation()) {
                    simpleLoc.beginUpdates();
                } else {
                    requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
                }
            } else {
                simpleLoc.beginUpdates();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean canAccessLocation() {
        return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean hasPermission(String perm) {
        return(PackageManager.PERMISSION_GRANTED==checkSelfPermission(perm));
    }
}

