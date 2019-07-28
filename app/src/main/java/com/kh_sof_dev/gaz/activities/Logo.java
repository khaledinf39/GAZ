package com.kh_sof_dev.gaz.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kh_sof_dev.gaz.R;

import java.util.Timer;
import java.util.TimerTask;

public class Logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_logo);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Logo.this, Intro.class));
                finish();
//                SharedPreferences sp1 = getSharedPreferences("first_time", MODE_PRIVATE);
//                if (sp1.getBoolean("is_first", true)) {
//                    startActivity(new Intent(Logo.this, Intro.class));
//                    finish();
//                } else {
//                    Intent intent = new Intent(Logo.this, Intro.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        }, 3000);
    }
}
