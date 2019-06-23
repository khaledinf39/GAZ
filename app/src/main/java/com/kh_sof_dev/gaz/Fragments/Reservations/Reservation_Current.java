package com.kh_sof_dev.gaz.Fragments.Reservations;


import android.os.Build;
import android.os.Bundle;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reservation_Current#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservation_Current extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Reservation_Current() {
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
    public static Reservation_Current newInstance(String param1, String param2) {
        Reservation_Current fragment = new Reservation_Current();
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

    TextView seeMore;
    RecyclerView RV;
    int limit=10,page=0;
    String req_nb="2";
    List<Order_getter> order_getterList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_reservation_canceled, container, false);
        RV=(RecyclerView)view.findViewById(R.id.RV);
        seeMore=view.findViewById(R.id.see_more);
        RV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        final Http_orders http_orders=new Http_orders();
        final ProgressBar progressBar=(ProgressBar)view.findViewById(R.id.progress);
        order_getterList=new ArrayList<>();
        final Order_adapter adapter=new Order_adapter(getContext(),order_getterList,Reservation_Current.this);
        RV.setAdapter(adapter);
        http_orders.GetMy_Order(getContext(), req_nb,limit,page, new Http_orders.OnOrder_geter_lisennter() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onSuccess(final show_order order) {
                order_getterList.addAll(order.getItems());
               adapter.notifyDataSetChanged();
                if (order.getPagatination().getPageNumber()!=order.getPagatination().getTotalPages()){
                    seeMore.setVisibility(View.VISIBLE);
                    page=page+1;
                }else {
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
                http_orders.GetMy_Order(getContext(), req_nb,limit,page, new Http_orders.OnOrder_geter_lisennter() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onSuccess(final show_order order) {
                        order_getterList.addAll(order.getItems());
                        adapter.notifyDataSetChanged();
                        if (order.getPagatination().getPageNumber()!=order.getPagatination().getTotalPages()){
                            seeMore.setVisibility(View.VISIBLE);
                            page=page+1;
                        }else {
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
