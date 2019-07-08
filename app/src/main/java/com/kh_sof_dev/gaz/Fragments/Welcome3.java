package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.R;
import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import static android.content.Context.MODE_PRIVATE;

public class Welcome3 extends SlideFragment {

    public Welcome3() {
        // Required empty public constructor
    }

    @Override
    public boolean canGoForward() {
        return false;
    }

    public static Welcome3 newInstance(String param1, String param2) {
        Welcome3 fragment = new Welcome3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
private Button start;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.layout_f_welcome3, container, false);
start=(Button)view.findViewById(R.id.start_btn);
start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SharedPreferences sp=getContext().getSharedPreferences("Intro", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putBoolean("state", true);
        Ed.commit();
        startActivity(new Intent(getContext(), Login.class));
        getActivity().finish();

    }
});
        return view;
    }
}
