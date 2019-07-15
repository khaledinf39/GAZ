package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kh_sof_dev.gaz.Adapters.MyFavorit_adapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.R;

public class MyFavorites extends Fragment {

    public MyFavorites() {

    }

    private ImageView back;
    private RecyclerView favorRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_my_favorites, container, false);
        favorRV = (RecyclerView) view.findViewById(R.id.my_favorit_RV);
        favorRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DBManager dbManager = new DBManager(getContext());
        dbManager.open();
        favorRV.setAdapter(new MyFavorit_adapter(getContext(), dbManager.fetch_bestProd(), MyFavorites.this));
        dbManager.close();
        back = (ImageView) view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
//                MainNew._goto(MyFavorites.this,new Home(),View.VISIBLE);
            }
        });
        return view;
    }

}
