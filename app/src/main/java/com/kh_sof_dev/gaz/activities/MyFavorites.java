package com.kh_sof_dev.gaz.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kh_sof_dev.gaz.Adapters.myFavoriteAdapter;
import com.kh_sof_dev.gaz.Classes.Database.Best;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyFavorites extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_my_favorites);
        RecyclerView favorRV = findViewById(R.id.my_favorit_RV);
        favorRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Best> orderDetailsListBest = realm.where(Best.class).findAll();
        List<Product> productsFav = new ArrayList<>();
        for (Best best : orderDetailsListBest) {
            Product p = new Product();
            p.setID_((int) best.getId());
            p.setId(best.getProductId());
            p.setName(best.getProductName());
            p.setQty(best.getQuantity());
            p.setPrice(best.getPrice());
            p.setImage(best.getImage());
            productsFav.add(p);
        }
        favorRV.setAdapter(new myFavoriteAdapter(this, productsFav));

//        DBManager dbManager = new DBManager(this);
//        dbManager.open();
//        favorRV.setAdapter(new myFavoriteAdapter(this, dbManager.fetch_bestProd()));
//        dbManager.close();
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
