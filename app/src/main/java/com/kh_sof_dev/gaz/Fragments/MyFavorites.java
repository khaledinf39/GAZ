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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFavorites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFavorites extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MyFavorites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFavorites.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFavorites newInstance(String param1, String param2) {
        MyFavorites fragment = new MyFavorites();
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
private ImageView back;
    private RecyclerView favorRV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_my_favorites, container, false);
        favorRV=(RecyclerView)view.findViewById(R.id.my_favorit_RV);
        favorRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DBManager dbManager=new DBManager(getContext());
        dbManager.open();
        favorRV.setAdapter(new MyFavorit_adapter(getContext(),dbManager.fetch_bestProd(),MyFavorites.this));
        dbManager.close();
        back=(ImageView) view.findViewById(R.id.back_btn);
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
