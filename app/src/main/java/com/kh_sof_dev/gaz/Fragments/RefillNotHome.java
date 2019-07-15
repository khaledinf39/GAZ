package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.R;

public class RefillNotHome extends Fragment {

    public RefillNotHome() {

    }

    private RecyclerView gaztypRV;
    TextView price;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_refill_not_home, container, false);
        gaztypRV = (RecyclerView) view.findViewById(R.id.GaztypeRV);
        price = (TextView) view.findViewById(R.id.price_tv);
        gaztypRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        return view;
    }

}
