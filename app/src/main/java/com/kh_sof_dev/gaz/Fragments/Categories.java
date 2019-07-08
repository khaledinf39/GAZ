package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
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
import com.kh_sof_dev.gaz.Adapters.Gategories;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.show_main_catigories;
import com.kh_sof_dev.gaz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Categories extends Fragment {
    TextView seeMore;
    int page=10;
private TextView header;
private RecyclerView RV;
    public Categories() {
        // Required empty public constructor
    }

private ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_categories, container, false);


        back=(ImageView)view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(),MainNew.class));
//                MainNew._goto(Categories.this,new Home(),View.VISIBLE);
            }
        });
        header=(TextView)view.findViewById(R.id.header);
        RV=(RecyclerView)view.findViewById(R.id.RV);
        RV.setLayoutManager(new GridLayoutManager(getContext(),2));
        final Http_products http_products=new Http_products();
        http_products.Getcatigories(getContext(),page, new Http_products.categoriesListener() {
            @Override
            public void onSuccess(final show_main_catigories catigories) {

                RV.setAdapter(new Gategories(getContext(), catigories.getcatigoriess(), new Gategories.categorie_selected_listenner() {
                    @Override
                    public void onSuccess(com.kh_sof_dev.gaz.Classes.Products.catigories catigories1) {
                           MainNew.goto_(new AllProducts(catigories1),getContext());


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


        return view;
    }

}
