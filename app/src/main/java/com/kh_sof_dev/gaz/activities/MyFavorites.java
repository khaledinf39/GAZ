package com.kh_sof_dev.gaz.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kh_sof_dev.gaz.Adapters.myFavoriteAdapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.R;

public class MyFavorites extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_my_favorites);
        RecyclerView favorRV = findViewById(R.id.my_favorit_RV);
        favorRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DBManager dbManager = new DBManager(this);
        dbManager.open();
        favorRV.setAdapter(new myFavoriteAdapter(this, dbManager.fetch_bestProd()));
        dbManager.close();
        ImageView back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                MainNew._goto(MyFavorites.this,new Home(),View.VISIBLE);
            }
        });
    }

}
