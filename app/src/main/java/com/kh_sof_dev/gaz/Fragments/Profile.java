package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kh_sof_dev.gaz.Adapters.Favorit_adapter;
import com.kh_sof_dev.gaz.Adapters.Resrvation_adapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Fragments.Reservations.MyReservations;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.MainNew;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    public Profile() {

    }

    CircleImageView user_img;
    TextView name, address, seeallRes, seeallFav;
    RecyclerView Rv_reser, Rv_favor;
    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_profile, container, false);
        back = (ImageView) view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
//MainNew._goto(Profile.this,new Home(),View.VISIBLE);
            }
        });
        user_img = (CircleImageView) view.findViewById(R.id.UserImg);
        name = (TextView) view.findViewById(R.id.Name_tv);
        address = (TextView) view.findViewById(R.id.address_tv);
        seeallFav = (TextView) view.findViewById(R.id.seeAllFavo_tv);
        seeallRes = (TextView) view.findViewById(R.id.seeAllReser_tv);
        Rv_favor = (RecyclerView) view.findViewById(R.id.my_favorit_RV);
        Rv_reser = (RecyclerView) view.findViewById(R.id.my_reservation_RV);
        Rv_favor.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        Rv_reser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        DBManager dbManager = new DBManager(getContext());
        dbManager.open();
        Rv_reser.setAdapter(new Resrvation_adapter(getContext(), dbManager.fetch_order()));
        Rv_favor.setAdapter(new Favorit_adapter(getContext(), dbManager.fetch_bestProd()));
        dbManager.close();
        /****************************action***************************/
        user_info user_info = new user_info(getContext());
        if (!user_info.getImag().isEmpty()) {
            Glide.with(getContext())
                    .load(user_info.getImag())
                    .placeholder(R.drawable.ic_user_img_gray)
                    .into(user_img);
        }
        name.setText(user_info.getName());
        address.setText(user_info.getCity());

        seeallRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _goto(new MyReservations());
            }
        });
        seeallFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _goto(new MyFavorites());
            }
        });
        return view;
    }

    private void _goto(Fragment frg) {
        MainNew.fragmentManager = getFragmentManager();
        MainNew.fragmentTransaction = MainNew.fragmentManager.beginTransaction();
        MainNew.fragment = frg;
        MainNew.fragmentTransaction.replace(R.id.ContentLogin, MainNew.fragment);
        MainNew.fragmentTransaction.commit();
    }
}
