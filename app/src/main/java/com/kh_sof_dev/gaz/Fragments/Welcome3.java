package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.TestLocation;

import static android.content.Context.MODE_PRIVATE;

public class Welcome3 extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_welcome3, container, false);
        Button start = view.findViewById(R.id.start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("Intro", MODE_PRIVATE);
                SharedPreferences.Editor Ed = sp.edit();
                Ed.putBoolean("state", true);
                Ed.apply();
                startActivity(new Intent(getContext(), TestLocation.class));
                getActivity().finish();

            }
        });
        return view;
    }
}
