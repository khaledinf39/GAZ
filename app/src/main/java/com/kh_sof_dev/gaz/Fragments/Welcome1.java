package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.R;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;


public class Welcome1 extends SlideFragment {

    public Welcome1() {
        // Required empty public constructor
    }

    public static Welcome1 newInstance(String param1, String param2) {
        Welcome1 fragment = new Welcome1();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_welcome1, container, false);
        TextView skip=view.findViewById(R.id.skip_tv);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Login.class));
                getActivity().finish();
            }
        });
        return view;
    }

}
