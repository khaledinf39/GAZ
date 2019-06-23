package com.kh_sof_dev.gaz.Fragments.Refill_frg;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Activities.MainNew;
import com.kh_sof_dev.gaz.R;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Tab_home_gaz extends Fragment {

int order_type;
    @SuppressLint("ValidFragment")
    public Tab_home_gaz(int order_type) {
        this.order_type=order_type;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_tab_home_gaz, container, false);
//
        Button refill_btn=view.findViewById(R.id.go_btn);

        refill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    MainNew.goto_(new Refill(4),getContext());

            }
        });
        Button shop_btn=view.findViewById(R.id.shop_btn);

        shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    MainNew.goto_(new Refill(2),getContext());

            }
        });
        return view;
    }

}
