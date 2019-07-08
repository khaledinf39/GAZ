package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kh_sof_dev.gaz.Adapters.Tirme_and_condiction_adapter;
import com.kh_sof_dev.gaz.Classes.constant.About_us.cons;
import com.kh_sof_dev.gaz.Classes.constant.About_us.show_s_cus;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


public class AboutUs extends Fragment {

    public AboutUs() {
        // Required empty public constructor
    }


    public static AboutUs newInstance(String param1, String param2) {
        AboutUs fragment = new AboutUs();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
private ImageView back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_about_us, container, false);

        final ProgressBar progressBar=view.findViewById(R.id.progress);
         final RecyclerView argemmentRV=(RecyclerView)view.findViewById(R.id.RV);
        argemmentRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        Http_get_constant constant=new Http_get_constant();
        constant.getStaticPage(getContext(), new Http_get_constant.staticPageListener() {
            @Override
            public void onSuccess(show_s_cus stticpage) {
                progressBar.setVisibility(View.GONE);
                List<cons> consList=new ArrayList<>();
                List<cons> consList2=new ArrayList<>();
                       consList= stticpage.getItems();
                for (int i =0;i<consList.size();i++
                ) {
                    cons c=consList.get(i);
                    if (c.getId().equals("5c9219afc4410f17e1c1ac4e")){
                        consList2.add(c);
                    }
                }
                argemmentRV.setAdapter(new Tirme_and_condiction_adapter(getContext(),consList2));
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
        back=view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
//                MainNew._goto(AboutUs.this,new Home(),View.VISIBLE);
            }
        });
        return view;
    }

}
