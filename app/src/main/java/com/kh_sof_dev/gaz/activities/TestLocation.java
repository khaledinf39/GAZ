package com.kh_sof_dev.gaz.activities;


import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.supplier.near_supplier;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;

public class TestLocation extends AppCompatActivity {

    LinearLayout no_location, progressBar;
    View refresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_test_location);
        no_location = findViewById(R.id.no_location);
        progressBar = findViewById(R.id.progress);
        refresh = findViewById(R.id.refresh);
        no_location.setVisibility(View.GONE);
        //***********************Get location***************/
        Getpermissin();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        get_location();
        //*****************************************************/


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                no_location.setVisibility(View.GONE);
                //***********************Get location***************/
                Getpermissin();
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                test_location();
                //*****************************************************/
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        //***********************Get location***************/
        Getpermissin();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        get_location();
        //*****************************************************/
    }

    /************************location*/
    private LocationManager locationManager = null;

    private void Getpermissin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 15);
                locationManager = (LocationManager)
                        getSystemService(Context.LOCATION_SERVICE);

                //if you want to lock screen for always Portrait mode
                setRequestedOrientation(ActivityInfo
                        .SCREEN_ORIENTATION_PORTRAIT);
//                get_location();

            } else {
                locationManager = (LocationManager)
                        getSystemService(Context.LOCATION_SERVICE);

                //if you want to lock screen for always Portrait mode
                setRequestedOrientation(ActivityInfo
                        .SCREEN_ORIENTATION_PORTRAIT);
//                get_location();
            }
        }
    }

    private void get_location() {

        //**********get gps location*********/
        boolean flag = displayGpsStatus();
        if (flag) {
            LocationListener locationListener = new MyLocationListener();

            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else {
            alertbox();
//            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    LatLng mLatLng = null;

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getContentResolver();
        return Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_opengps);
        dialog.setCancelable(false);
        Button cancel = dialog.findViewById(R.id.cancel_btn);
//        TextView tex1 = dialog.findViewById(R.id.tx1_tv);
//        TextView tex2 = dialog.findViewById(R.id.tx2_tv);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button go_sitting = dialog.findViewById(R.id.open_sitting_btn);
        go_sitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    boolean firstone = false;

    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            mLatLng = new LatLng(loc.getLatitude(), loc.getLongitude());
            Login.mLatLng = mLatLng;
            if (!firstone) {
                test_location();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    private void test_location() {
        Http_orders http_orders = new Http_orders();
        try {
            http_orders.Get_MyPlaceSupp(this, mLatLng.latitude, mLatLng.longitude, new Http_orders.Onsupplier_place_lisennter() {
                @Override
                public void onSuccess(near_supplier test) {
                    if (test.isStatus()) {
                        new user_info(test.getItems().getId(), TestLocation.this);
                        //*******************************Test Login*************/

                        SharedPreferences loginInf = getSharedPreferences("Login", MODE_PRIVATE);

                        String id = loginInf.getString("id", null);
                        String token = loginInf.getString("token", null);
                        if (id != null && token != null) {
                            startActivity(new Intent(TestLocation.this, MainNew.class));
                            finish();
                        } else {
                            startActivity(new Intent(TestLocation.this, Login.class));
                            finish();
                        }

                    } else {
                        no_location.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure(String msg) {
                    no_location.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            });

            firstone = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
