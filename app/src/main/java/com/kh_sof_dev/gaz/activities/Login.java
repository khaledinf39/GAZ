package com.kh_sof_dev.gaz.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.kh_sof_dev.gaz.Fragments.Test_location;
import com.kh_sof_dev.gaz.R;

public class Login extends AppCompatActivity {
    //    public static FragmentManager fragmentManager;
//    public static Fragment fragment;
//    public static FragmentTransaction fragmentTransaction;
    public static LatLng mLatLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_login);


//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragment = fragmentManager.findFragmentById(R.id.ContentLogin);
//        if (fragment == null) {
//            fragment = new Test_location();
//            fragmentTransaction.add(R.id.ContentLogin, fragment).commit();
//            fragmentTransaction.addToBackStack(null);
//        }
        changeFragment(new Test_location());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.ContentLogin, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
