package com.kh_sof_dev.gaz.Activities;

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
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Fragments.LoginFrag;
import com.kh_sof_dev.gaz.R;
import com.google.android.gms.maps.model.LatLng;

public class Login extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public  static Fragment fragment;
    public static FragmentTransaction fragmentTransaction;
    /************************location*/
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private void Getpermissin(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 15);
                locationManager = (LocationManager)
                        this. getSystemService(Context.LOCATION_SERVICE);

                //if you want to lock screen for always Portrait mode
                this.setRequestedOrientation(ActivityInfo
                        .SCREEN_ORIENTATION_PORTRAIT);
                get_location();

            } else {
                locationManager = (LocationManager)
                        this. getSystemService(Context.LOCATION_SERVICE);

                //if you want to lock screen for always Portrait mode
                this.setRequestedOrientation(ActivityInfo
                        .SCREEN_ORIENTATION_PORTRAIT);
                get_location();
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

            }catch (Exception ex){}
        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }
public  static LatLng mLatLng=null;
    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = this.getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final Dialog dialog = new Dialog(this);
        dialog .setContentView(R.layout.popup_opengps);
        dialog.setCancelable(false);
        Button cancel=(Button)dialog.findViewById(R.id.cancel_btn);
        TextView tex1=(TextView)dialog.findViewById(R.id.tx1_tv);
        TextView tex2=(TextView)dialog.findViewById(R.id.tx2_tv);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button go_sitting=(Button)dialog.findViewById(R.id.open_sitting_btn);
        go_sitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                dialog.cancel();
            }
        });
        dialog.show();


    }
    /*---------- Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {


//            Toast.makeText(
//                    getApplicationContext(),
//                    "Location changed: Lat: " + loc.getLatitude() + " Lng: "
//                            + loc.getLongitude(), Toast.LENGTH_SHORT).show();
//            String longitude = "Longitude: " + loc.getLongitude();
//            Log.v("longitude", longitude);
//            String latitude = "Latitude: " + loc.getLatitude();
//            Log.v("latitude", latitude);

            mLatLng=new LatLng(loc.getLatitude(),loc.getLongitude());

//            try {
//                /*------- To get city name from coordinates -------- */
//                String cityName = null;
//                Geocoder gcd = new Geocoder(Login.this, Locale.getDefault());
//                List<Address> addresses;
//
//                addresses = gcd.getFromLocation(loc.getLatitude(),
//                        loc.getLongitude(), 1);
//                if (addresses.size() > 0) {
//                    System.out.println(addresses.get(0).getLocality());
//                    cityName = addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();// addresses.get(0).getLocality();
//                    Log.d("cityName",cityName);
//
//                    Ed.putString("address", cityName);
//                    Ed.commit();
//                }
//
//
//
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}



        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
    /*************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_login);

/*******************************Test Login*************/

        SharedPreferences loginInf=this.getSharedPreferences("Login", MODE_PRIVATE);

        String id=loginInf.getString("id", null);
        String token = loginInf.getString("token", null);
        if(id!=null && token!= null){
            startActivity(new Intent(Login.this,MainNew.class));
            finish();
        }
        /***********************Get location***************/
        Getpermissin();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        get_location();
        /*****************************************************/
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = (Fragment) fragmentManager.findFragmentById(R.id.ContentLogin);
        if (fragment == null) {
            fragment = new LoginFrag();
            fragmentTransaction.add(R.id.ContentLogin, fragment).commit();
            fragmentTransaction.addToBackStack(null);
        }
    }
}
