package com.kh_sof_dev.gaz.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kh_sof_dev.gaz.R;

public class Logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // run() method will be executed when 3 seconds have passed
                //Time to start Intro
                SharedPreferences sp1=getSharedPreferences("first_time", MODE_PRIVATE);
                //startActivity(new Intent(LogoAct.this,Intro.class));


                if (sp1.getBoolean("is_first", true)){
                    /***********/
                    //openIntroPage
                    startActivity(new Intent(Logo.this,Intro.class));
                    finish();

                }else {
                    Intent intent = new Intent(Logo.this, Intro.class);
                    startActivity(intent );
                    finish();
                }

            }
        }, 3000);

    }
}
