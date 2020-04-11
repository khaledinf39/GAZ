package com.kh_sof_dev.gaz.activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.kh_sof_dev.gaz.Fragments.LoginFrag;
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
//            fragment = new TestLocation();
//            fragmentTransaction.add(R.id.ContentLogin, fragment).commit();
//            fragmentTransaction.addToBackStack(null);
//        }
        changeFragment(new LoginFrag());
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
