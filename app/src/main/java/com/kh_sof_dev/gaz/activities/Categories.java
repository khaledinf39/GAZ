package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kh_sof_dev.gaz.Adapters.Gategories;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.show_main_catigories;
import com.kh_sof_dev.gaz.R;

public class Categories extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_categories);
        ImageView back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Categories.this, MainNew.class));
//                MainNew._goto(Categories.this,new Home(),View.VISIBLE);
            }
        });
//        TextView header = findViewById(R.id.header);
        final RecyclerView RV = findViewById(R.id.RV);
        RV.setLayoutManager(new GridLayoutManager(this, 2));
        final Http_products http_products = new Http_products();
        http_products.Getcatigories(this, 10, new Http_products.categoriesListener() {
            @Override
            public void onSuccess(final show_main_catigories catigories) {

                RV.setAdapter(new Gategories(Categories.this, catigories.getcatigoriess(), new Gategories.categorie_selected_listenner() {
                    @Override
                    public void onSuccess(com.kh_sof_dev.gaz.Classes.Products.Categories categories1) {
                        Intent intent = new Intent(Categories.this, AllProducts.class);
                        intent.putExtra(AllProducts.category, categories1);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
