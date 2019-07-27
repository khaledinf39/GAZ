package com.kh_sof_dev.gaz.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Adapters.Product_adapter;
import com.kh_sof_dev.gaz.Classes.Products.Categories;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Products.show_products;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

public class AllProducts extends AppCompatActivity {
    public static final String category = "category";

    private RecyclerView RV;
    private Categories cat;

    TextView seeMore;
    int limit = 10, page = 0;
    List<Product> productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_all_products);

        if (getIntent().hasExtra(category)) {

            ImageView back = findViewById(R.id.back_btn);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
//                    MainNew.goto_(new com.kh_sof_dev.gaz.activities.Categories(), );
                }
            });

            cat = (Categories) getIntent().getSerializableExtra(category);
            if (cat != null) {
                TextView header = findViewById(R.id.header);
                header.setText(cat.getName());
                RV = findViewById(R.id.RV);
                RV.setLayoutManager(new GridLayoutManager(this, 2));
                productList = new ArrayList<>();
                final Http_products http_products = new Http_products();
                http_products.Post_products_category(this, cat.getId(), 1, limit, page, new Http_products.productsListener() {
                    @Override
                    public void onSuccess(show_products products) {
                        productList.addAll(products.getProducts());
                        if (products.getPagatination().getTotalPages() != products.getPagatination().getPageNumber()) {
                            seeMore.setVisibility(View.VISIBLE);
                            page = page + 1;
                        } else {
                            seeMore.setVisibility(View.GONE);
                        }
                        RV.setAdapter(new Product_adapter(AllProducts.this, productList));
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });


                seeMore = findViewById(R.id.see_more);
                seeMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        http_products.Post_products_category(AllProducts.this, cat.getId(), 1, limit, page, new Http_products.productsListener() {
                            @Override
                            public void onSuccess(show_products products) {
                                productList.addAll(products.getProducts());
                                if (products.getPagatination().getTotalPages() != products.getPagatination().getPageNumber()) {
                                    seeMore.setVisibility(View.VISIBLE);
                                    page = page + 1;
                                } else {
                                    seeMore.setVisibility(View.GONE);
                                }
                                RV.setAdapter(new Product_adapter(AllProducts.this, productList));
                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFailure(String msg) {

                            }
                        });

                    }
                });
            }
        }
    }
}
