package com.kh_sof_dev.gaz.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.Product_adapter;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Products.catigories;
import com.kh_sof_dev.gaz.Classes.Products.show_products;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AllProducts extends Fragment {

    private TextView header;
    private RecyclerView RV;
    private catigories cat;
    private ImageView back;
    public AllProducts(catigories cat) {
        // Required empty public constructor
        this.cat=cat;
    }

    TextView seeMore;
    int limit=10,page=0;
    List<Product>productList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_all_products, container, false);
        back=(ImageView)view.findViewById(R.id.back_btn);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        MainNew.goto_(new Categories(),getContext());
    }
});
        header=(TextView)view.findViewById(R.id.header);
        header.setText(cat.getName());
        RV=(RecyclerView)view.findViewById(R.id.RV);
        RV.setLayoutManager(new GridLayoutManager(getContext(),2));
        productList=new ArrayList<>();
        final Http_products http_products=new Http_products();
http_products.Post_products_category(getContext(), cat.getId(),1,limit,page, new Http_products.productsListener() {
    @Override
    public void onSuccess(show_products products) {
        productList.addAll(products.getProducts());
        if (products.getPagatination().getTotalPages()!=products.getPagatination().getPageNumber()){
            seeMore.setVisibility(View.VISIBLE);
            page=page+1;
        }else {
            seeMore.setVisibility(View.GONE);
        }
        RV.setAdapter(new Product_adapter(getContext(),productList,AllProducts.this));
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFailure(String msg) {

    }
});


        seeMore=view.findViewById(R.id.see_more);
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                http_products.Post_products_category(getContext(), cat.getId(),1,limit,page, new Http_products.productsListener() {
                    @Override
                    public void onSuccess(show_products products) {
                        productList.addAll(products.getProducts());
                        if (products.getPagatination().getTotalPages()!=products.getPagatination().getPageNumber()){
                            seeMore.setVisibility(View.VISIBLE);
                            page=page+1;
                        }else {
                            seeMore.setVisibility(View.GONE);
                        }
                        RV.setAdapter(new Product_adapter(getContext(),productList,AllProducts.this));
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
        return view;
    }

}
