package com.kh_sof_dev.gaz.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kh_sof_dev.gaz.Adapters.Tirme_and_condiction_adapter;
import com.kh_sof_dev.gaz.Classes.constant.About_us.cons;
import com.kh_sof_dev.gaz.Classes.constant.About_us.show_s_cus;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

public class UserAgreement extends AppCompatActivity {

    private RecyclerView argemmentRV;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_user_agreement);
        final ProgressBar progressBar = findViewById(R.id.progress);
        argemmentRV = findViewById(R.id.RV);
        argemmentRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Http_get_constant constant = new Http_get_constant();
        constant.getStaticPage(this, new Http_get_constant.staticPageListener() {
            @Override
            public void onSuccess(show_s_cus stticpage) {
                progressBar.setVisibility(View.GONE);
                List<cons> consList;
                List<cons> consList2 = new ArrayList<>();
                consList = stticpage.getItems();
                for (int i = 0; i < consList.size(); i++
                ) {
                    cons c = consList.get(i);
                    if (c.getId().equals("5ca8f84265100a0024f0bb35")) {
                        consList2.add(c);
                    }
                }
                argemmentRV.setAdapter(new Tirme_and_condiction_adapter(UserAgreement.this, consList2));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                MainNew._goto(UserAgreement.this,new Home(),View.VISIBLE);
            }
        });
    }
}
