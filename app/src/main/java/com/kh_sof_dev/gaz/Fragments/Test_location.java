package com.kh_sof_dev.gaz.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.supplier.near_supplier;
import com.kh_sof_dev.gaz.Classes.Order.testMyplace;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Fragments.Reservations.MyReservations;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.activities.MainNew;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Test_location extends Fragment {


    public Test_location() {

    }

    LinearLayout no_location, progressBar;

    /************************location*/
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private void Getpermissin() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 15);
                locationManager = (LocationManager)
                        getActivity().getSystemService(Context.LOCATION_SERVICE);

                //if you want to lock screen for always Portrait mode
                getActivity().setRequestedOrientation(ActivityInfo
                        .SCREEN_ORIENTATION_PORTRAIT);
//                get_location();

            } else {
                locationManager = (LocationManager)
                        getActivity().getSystemService(Context.LOCATION_SERVICE);

                //if you want to lock screen for always Portrait mode
                getActivity().setRequestedOrientation(ActivityInfo
                        .SCREEN_ORIENTATION_PORTRAIT);
//                get_location();
            }
        }
    }

    /**********get gps location*********/
    private Boolean flag;

    @SuppressLint("MissingPermission")
    private void get_location() {

        flag = displayGpsStatus();
        if (flag) {
            locationListener = new MyLocationListener();


            try {
                locationManager.requestLocationUpdates(LocationManager
                        .GPS_PROVIDER, 5000, 10, locationListener);

            } catch (Exception ex) {
            }


        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    LatLng mLatLng = null;

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getActivity().getBaseContext()
                .getContentResolver();
        boolean gpsStatus = android.provider.Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_opengps);
        dialog.setCancelable(false);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_btn);
        TextView tex1 = (TextView) dialog.findViewById(R.id.tx1_tv);
        TextView tex2 = (TextView) dialog.findViewById(R.id.tx2_tv);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        dialog.show();
        Button go_sitting = (Button) dialog.findViewById(R.id.open_sitting_btn);
        go_sitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                dialog.dismiss();
            }
        });


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

    /*************************************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_test_location, container, false);
        no_location = view.findViewById(R.id.no_location);
        progressBar = view.findViewById(R.id.progress);
        no_location.setVisibility(View.GONE);
        /***********************Get location***************/
        Getpermissin();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        get_location();
        /*****************************************************/

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onResume() {
        super.onResume();
        /***********************Get location***************/
        Getpermissin();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        get_location();
        /*****************************************************/
    }

//    private void _goto(Fragment frg) {
//        Login.fragmentManager = getFragmentManager();
//        Login.fragmentTransaction = Login.fragmentManager.beginTransaction();
//        Login.fragment = frg;
//        Login.fragmentTransaction.replace(R.id.ContentLogin, Login.fragment);
//        Login.fragmentTransaction.commit();
//    }

    private void test_location() {
        Http_orders http_orders = new Http_orders();
        try {
            http_orders.Get_MyPlaceSupp(getContext(), mLatLng.latitude, mLatLng.longitude, new Http_orders.Onsupplier_place_lisennter() {
                @Override
                public void onSuccess(near_supplier test) {
                    if (test.isStatus()) {
                        new user_info(test.getItems().getId(), getContext());
                        /*******************************Test Login*************/

                        SharedPreferences loginInf = getActivity().getSharedPreferences("Login", MODE_PRIVATE);

                        String id = loginInf.getString("id", null);
                        String token = loginInf.getString("token", null);
                        if (id != null && token != null) {
                            startActivity(new Intent(getContext(), MainNew.class));
                            getActivity().finish();
                        } else {


                            ((Login) getActivity()).changeFragment(new LoginFrag());

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

                }
            });

            firstone=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
