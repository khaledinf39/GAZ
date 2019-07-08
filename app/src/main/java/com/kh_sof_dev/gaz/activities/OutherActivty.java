package com.kh_sof_dev.gaz.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kh_sof_dev.gaz.R;

public class OutherActivty extends AppCompatActivity {
public static Fragment new_frg=null;
public static  FragmentManager fragmentManager;
public static  FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_outher_activty);
          fragmentManager =getSupportFragmentManager();
          fragmentTransaction = fragmentManager.beginTransaction();
      if (new_frg!=null) {
          Fragment fragment = new_frg;
          fragmentTransaction.replace(R.id.ContentLogin, fragment);
          fragmentTransaction.commit();
      }


    }
//    public static void _goto(Fragment frg, Fragment new_frg, int bottomvisibility){
//
//
//
////        fragmentManager =frg.getFragmentManager();
////        fragmentTransaction = fragmentManager.beginTransaction();
//       Fragment fragment = new_frg;
//        fragmentTransaction.replace(R.id.ContentLogin, fragment);
//        fragmentTransaction.commit();
//
//    }
}
