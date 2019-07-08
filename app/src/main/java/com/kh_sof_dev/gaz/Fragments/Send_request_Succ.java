package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Fragments.Reservations.MyReservations;
import com.kh_sof_dev.gaz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Send_request_Succ extends Fragment {


    public Send_request_Succ() {
        // Required empty public constructor
    }
Button cont_shop,cont_req;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_send_request__succ, container, false);
        cont_req=view.findViewById(R.id.continue_order);
        cont_shop=view.findViewById(R.id.continue_shopping);
        cont_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        cont_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNew.goto_(new MyReservations(),getContext());

            }
        });

        return view;
    }

}
