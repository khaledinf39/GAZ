package com.kh_sof_dev.gaz.Fragments;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Adapters.Gategories;
import com.kh_sof_dev.gaz.Adapters.Product_adapter;
import com.kh_sof_dev.gaz.Adapters.Top20Products;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Products.show_main_catigories;
import com.kh_sof_dev.gaz.Classes.Products.show_products;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.MainNew;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class SearchResults extends Fragment {
    private String mProduct;

    public SearchResults() {

    }

    public SearchResults(String productName) {
        mProduct = productName;
    }

    private RecyclerView searchRV, top_prodRV;
    private ImageView back;
    private TextView resultnb, orderTv, trieTv;
    List<Product> productList;
    Product_adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_search_results, container, false);
        back = (ImageView) view.findViewById(R.id.back_btn);
        resultnb = (TextView) view.findViewById(R.id.resultNB_tv);
        trieTv = (TextView) view.findViewById(R.id.trie_tv);
        orderTv = (TextView) view.findViewById(R.id.order_tv);
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
        searchRV = (RecyclerView) view.findViewById(R.id.search_RV);
        top_prodRV = (RecyclerView) view.findViewById(R.id.topProductRV);
        searchRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productList = new ArrayList<>();
        adapter = new Product_adapter(getContext(), productList, SearchResults.this);
        searchRV.setAdapter(adapter);

        Http_products http_products = new Http_products();
        http_products.Post_shearch_products(getContext(), mProduct, new Http_products.productsListener() {
            @Override
            public void onSuccess(show_products products) {
                productList = products.getProducts();
                adapter = new Product_adapter(getContext(), productList, SearchResults.this);
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
        top_prodRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        http_products.Get_top20_Products(getContext(), new Http_products.productsListener() {
            @Override
            public void onSuccess(show_products products) {

                top_prodRV.setAdapter(new Top20Products(getContext(), products.getProducts(), SearchResults.this));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNew.goto_(new Search(), getContext());
            }
        });
        return view;
    }

    private void orderFunction() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_f_categories);
        final RecyclerView Rv = dialog.findViewById(R.id.RV);
        Rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        dialog.show();
        Http_products http_products = new Http_products();
        http_products.Getcatigories(getContext(), 10, new Http_products.categoriesListener() {
            @Override
            public void onSuccess(show_main_catigories catigories) {
                Rv.setAdapter(new Gategories(getContext(), catigories.getcatigoriess(), new Gategories.categorie_selected_listenner() {
                    @Override
                    public void onSuccess(com.kh_sof_dev.gaz.Classes.Products.catigories catigories) {
                        List<Product> productsTrie = new ArrayList<>();
                        for (Product p : productList
                        ) {
                            if (p.getCategory_id().equals(catigories.getId())) {
                                productsTrie.add(p);
                            }
                        }
                        adapter = new Product_adapter(getContext(), productsTrie, SearchResults.this);
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
            Double price = productList.get(i).getPrice();
            int pos = i;
            for (int j = 0; j < productList.size(); j++
            ) {
                if (productList.get(j).getPrice() > price) {
                    price = productList.get(j).getPrice();
                    pos = j;
                }

            }
            productList_trie.add(productList.get(pos));
            productList.remove(pos);
        }
        productList = productList_trie;
        adapter = new Product_adapter(getContext(), productList_trie, SearchResults.this);
        searchRV.setAdapter(adapter);
        resultnb.setText(productList_trie.size() + "نتيجة البحث ( " + "أصناف)");

    }

}
