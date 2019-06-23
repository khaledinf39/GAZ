package com.kh_sof_dev.gaz.Fragments.Reservations;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.kh_sof_dev.gaz.Adapters.Order_adapter;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.show_order;
import com.kh_sof_dev.gaz.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reservation_Canceled_devier#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservation_Canceled_devier extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Reservation_Canceled_devier() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reservation_Canceled.
     */
    // TODO: Rename and change types and number of parameters
    public static Reservation_Canceled_devier newInstance(String param1, String param2) {
        Reservation_Canceled_devier fragment = new Reservation_Canceled_devier();
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

    RecyclerView RV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_reservation_canceled, container, false);
        RV=(RecyclerView)view.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        Http_orders http_orders=new Http_orders();
        final ProgressBar progressBar=(ProgressBar)view.findViewById(R.id.progress);
        http_orders.GetMy_Order(getContext(), "6", 0, 10, new Http_orders.OnOrder_geter_lisennter() {
            @Override
            public void onSuccess(show_order order) {
                RV.setAdapter(new Order_adapter(getContext(),order.getItems(),Reservation_Canceled_devier.this));
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
