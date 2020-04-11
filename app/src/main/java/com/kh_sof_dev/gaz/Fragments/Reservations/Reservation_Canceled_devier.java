package com.kh_sof_dev.gaz.Fragments.Reservations;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kh_sof_dev.gaz.Adapters.Order_adapter;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.show_order;
import com.kh_sof_dev.gaz.R;

public class Reservation_Canceled_devier extends Fragment {

    public Reservation_Canceled_devier() {
        // Required empty public constructor
    }

    RecyclerView RV;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_reservation_canceled, container, false);
        RV = view.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        Http_orders http_orders = new Http_orders();
        final ProgressBar progressBar = view.findViewById(R.id.progress);
        http_orders.GetMy_Order(getContext(), "6", 0, 10, new Http_orders.OnOrder_geter_lisennter() {
            @Override
            public void onSuccess(show_order order) {
                RV.setAdapter(new Order_adapter(getContext(), order.getItems()));
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });

        return view;
    }

}
