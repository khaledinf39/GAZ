package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.kh_sof_dev.gaz.R;

public class SendRequestSucc extends AppCompatActivity {

    Button cont_shop, cont_req;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_send_request__succ);
        cont_req = findViewById(R.id.continue_order);
        cont_shop = findViewById(R.id.continue_shopping);
        cont_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SendRequestSucc.this, MainNew.class));
                finish();
            }
        });
        cont_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendRequestSucc.this, MyReservations.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
//                MainNew.goto_(new MyReservations(), getContext());

            }
        });

    }

}