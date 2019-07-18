package com.kh_sof_dev.gaz.activities;

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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Fragments.LoginFrag;
import com.kh_sof_dev.gaz.Fragments.Test_location;
import com.kh_sof_dev.gaz.R;
import com.google.android.gms.maps.model.LatLng;

public class Login extends AppCompatActivity {
    public static FragmentManager fragmentManager;
    public  static Fragment fragment;
    public static FragmentTransaction fragmentTransaction;
    public  static LatLng mLatLng=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_login);



        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = (Fragment) fragmentManager.findFragmentById(R.id.ContentLogin);
        if (fragment == null) {
            fragment = new Test_location();
            fragmentTransaction.add(R.id.ContentLogin, fragment).commit();
            fragmentTransaction.addToBackStack(null);
        }
    }
    @Override
    public void onBackPressed() {

    }
}
