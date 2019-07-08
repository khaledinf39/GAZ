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
import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.Favorit_adapter;
import com.kh_sof_dev.gaz.Adapters.Resrvation_adapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Fragments.Reservations.MyReservations;
import com.kh_sof_dev.gaz.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
CircleImageView user_img;
    TextView name,address,seeallRes,seeallFav;
    RecyclerView Rv_reser,Rv_favor;
    ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_profile, container, false);
        back=(ImageView) view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
//MainNew._goto(Profile.this,new Home(),View.VISIBLE);
            }
        });
user_img=(CircleImageView)view.findViewById(R.id.UserImg);
name=(TextView)view.findViewById(R.id.Name_tv);
address=(TextView)view.findViewById(R.id.address_tv);
seeallFav=(TextView)view.findViewById(R.id.seeAllFavo_tv);
        seeallRes=(TextView)view.findViewById(R.id.seeAllReser_tv);
        Rv_favor=(RecyclerView)view.findViewById(R.id.my_favorit_RV);
        Rv_reser=(RecyclerView)view.findViewById(R.id.my_reservation_RV);
Rv_favor.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));
        Rv_reser.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true));

        DBManager dbManager=new DBManager(getContext());
        dbManager.open();
        Rv_reser.setAdapter(new Resrvation_adapter(getContext(),dbManager.fetch_order()));
        Rv_favor.setAdapter(new Favorit_adapter(getContext(),dbManager.fetch_bestProd()));
        dbManager.close();
        /****************************action***************************/
        user_info user_info=new user_info(getContext());
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
    private void _goto(Fragment frg){
        MainNew.fragmentManager = getFragmentManager();
        MainNew.fragmentTransaction = MainNew.fragmentManager.beginTransaction();
        MainNew.fragment = frg;
        MainNew.fragmentTransaction.replace(R.id.ContentLogin, MainNew.fragment);
        MainNew.fragmentTransaction.commit();
    }
}
