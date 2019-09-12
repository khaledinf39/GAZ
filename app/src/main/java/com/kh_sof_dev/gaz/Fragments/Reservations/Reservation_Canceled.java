package com.kh_sof_dev.gaz.Fragments.Reservations;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Adapters.Order_adapter;
import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.Order_getter;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.show_order;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


public class Reservation_Canceled extends Fragment {


    public Reservation_Canceled() {

    }

    TextView seeMore;
    RecyclerView RV;
    int limit = 10, page = 0;
    String req_nb = "5";
    List<Order_getter> order_getterList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_reservation_canceled, container, false);
        RV = view.findViewById(R.id.RV);
        seeMore = view.findViewById(R.id.see_more);
        RV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        final Http_orders http_orders = new Http_orders();
        final ProgressBar progressBar = view.findViewById(R.id.progress);
        order_getterList = new ArrayList<>();
        final Order_adapter adapter = new Order_adapter(getContext(), order_getterList);
        RV.setAdapter(adapter);
        http_orders.GetMy_Order(getContext(), req_nb, limit, page, new Http_orders.OnOrder_geter_lisennter() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(final show_order order) {
                order_getterList.addAll(order.getItems());
                adapter.notifyDataSetChanged();
                if (order.getPagatination().getPageNumber() < order.getPagatination().getTotalPages()) {
                    seeMore.setVisibility(View.VISIBLE);
                    page = page + 1;
                } else {
                    seeMore.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                http_orders.GetMy_Order(getContext(), req_nb, limit, page, new Http_orders.OnOrder_geter_lisennter() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onSuccess(final show_order order) {
                        order_getterList.addAll(order.getItems());
                        adapter.notifyDataSetChanged();
                        if (order.getPagatination().getPageNumber() < order.getPagatination().getTotalPages()) {
                            seeMore.setVisibility(View.VISIBLE);
                            page = page + 1;
                        } else {
                            seeMore.setVisibility(View.GONE);
                        }
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });

            }
        });
        return view;
    }
}
