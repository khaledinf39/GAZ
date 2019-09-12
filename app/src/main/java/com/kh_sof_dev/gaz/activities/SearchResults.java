package com.kh_sof_dev.gaz.activities;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Adapters.Gategories;
import com.kh_sof_dev.gaz.Adapters.Product_adapter;
import com.kh_sof_dev.gaz.Adapters.Top20Products;
import com.kh_sof_dev.gaz.Classes.Products.Categories;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Products.show_main_catigories;
import com.kh_sof_dev.gaz.Classes.Products.show_products;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


public class SearchResults extends AppCompatActivity {
    public static final String productName = "product_name";

    private RecyclerView searchRV, top_prodRV;
    private TextView resultnb;
    List<Product> productList;
    Product_adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_search_results);
        ImageView back = findViewById(R.id.back_btn);
        resultnb = findViewById(R.id.resultNB_tv);
        TextView trieTv = findViewById(R.id.trie_tv);
        TextView orderTv = findViewById(R.id.order_tv);
        trieTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderFunction();
            }
        });
        orderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trieFunction();
            }
        });
        searchRV = findViewById(R.id.search_RV);
        top_prodRV = findViewById(R.id.topProductRV);
        searchRV.setLayoutManager(new GridLayoutManager(this, 2));
        productList = new ArrayList<>();
        adapter = new Product_adapter(this, productList);
        searchRV.setAdapter(adapter);

        if (getIntent().hasExtra(productName)) {
            String mProduct = getIntent().getStringExtra(productName);
            if (mProduct != null) {
                Http_products http_products = new Http_products();
                http_products.Post_shearch_products(this, mProduct, new Http_products.productsListener() {
                    @Override
                    public void onSuccess(show_products products) {
                        productList = products.getProducts();
                        adapter = new Product_adapter(SearchResults.this, productList);
                        searchRV.setAdapter(adapter);


                        resultnb.setText(productList.size() + "نتيجة البحث ( " + "أصناف)");
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                top_prodRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
                http_products.Get_top20_Products(this, new Http_products.productsListener() {
                    @Override
                    public void onSuccess(show_products products) {

                        top_prodRV.setAdapter(new Top20Products(SearchResults.this, products.getProducts()));
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void orderFunction() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_f_categories);
        final RecyclerView Rv = dialog.findViewById(R.id.RV);
        Rv.setLayoutManager(new GridLayoutManager(this, 2));
        dialog.show();
        Http_products http_products = new Http_products();
        http_products.Getcatigories(this, 10, new Http_products.categoriesListener() {
            @Override
            public void onSuccess(show_main_catigories catigories) {
                Rv.setAdapter(new Gategories(SearchResults.this, catigories.getcatigoriess(), new Gategories.categorie_selected_listenner() {
                    @Override
                    public void onSuccess(Categories Categories) {
                        List<Product> productsTrie = new ArrayList<>();
                        for (Product p : productList
                        ) {
                            if (p.getCategory_id().equals(Categories.getId())) {
                                productsTrie.add(p);
                            }
                        }
                        adapter = new Product_adapter(SearchResults.this, productsTrie);
                        searchRV.setAdapter(adapter);
                        resultnb.setText(productsTrie.size() + "نتيجة البحث ( " + "أصناف)");
                        dialog.dismiss();

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
        ImageView back = dialog.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void trieFunction() {
        List<Product> productList_trie = new ArrayList<>();
        for (int i = 0; i < productList.size(); ) {
            Double price = productList.get(i).getPrice_buy_new();
            int pos = i;
            for (int j = 0; j < productList.size(); j++
            ) {
                if (productList.get(j).getPrice_buy_new() > price) {
                    price = productList.get(j).getPrice_buy_new();
                    pos = j;
                }

            }
            productList_trie.add(productList.get(pos));
            productList.remove(pos);
        }
        productList = productList_trie;
        adapter = new Product_adapter(this, productList_trie);
        searchRV.setAdapter(adapter);
        resultnb.setText(productList_trie.size() + "نتيجة البحث ( " + "أصناف)");

    }

}
