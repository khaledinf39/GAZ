package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.Order_getter;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.Follow_order;
import com.kh_sof_dev.gaz.activities.OrderDetails;

import java.util.List;
import java.util.Locale;


/**
 * Created by User on 2/6/2018.
 */

public class Order_adapter extends RecyclerView.Adapter<Order_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private View mView;
    //vars
    private List<Order_getter> mItems;
    public static int Item_selected;
    private Context mContext;

    public Order_adapter(Context context, List<Order_getter> names) {
        mItems = names;
        mContext = context;
        Item_selected = 0;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_reservation_past_main, parent, false);
        mView = view;
        return new ViewHolder(view);
    }


    public static String getdate(String date_) {
        Log.e("date_", date_.substring(0, date_.indexOf("T")));
        return date_.substring(0, date_.indexOf("T"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        if (mItems.get(position).getStatusId() == 5 && mItems.get(position).getStatusId() == 6) {
            holder.price.setText("طلب ملغي");
            holder.price.setTextColor(mContext.getColor(R.color.red_google_opacity_pressed));
        } else {
            holder.price.setText(String.format(Locale.ENGLISH, "%.2f", mItems.get(position).getSubTotal()));
        }
        holder.time.setText(mItems.get(position).getDelivery_time());
        try {
            holder.date.setText(getdate(mItems.get(position).getDelivery_date()));
        } catch (Exception e) {
            holder.date.setText("");
            e.printStackTrace();
        }

        String order_typ = "";
        switch (mItems.get(position).getOrderType()) {
            case 1:
                order_typ = " شراء منتحات ";
                break;
            case 2:
                order_typ = " تعبئة إسطوانة غاز ";
                break;
            case 3:
                order_typ = " تعبئة خزان غاز ";
                break;
        }
        holder.type.setText(order_typ);
        holder.details.setText(mItems.get(position).getAddressDetails());
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItems.get(position).getStatusId() == 2) {
//                    OutherActivty.new_frg = new Follow_order(mItems.get(position));
                    Intent intent = new Intent(mContext, Follow_order.class);
                    intent.putExtra(OrderDetails.REQUEST_DETAILS, mItems.get(position));
                    mContext.startActivity(intent);


                } else {
//                    OutherActivty.new_frg = new OrderDetails(mItems.get(position));
                    Intent intent = new Intent(mContext, OrderDetails.class);
                    intent.putExtra(OrderDetails.REQUEST_DETAILS, mItems.get(position));
                    mContext.startActivity(intent);


                }
//                MainNew._goto(mFragment,new SearchResults(mItems.get(position).getName()),View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, time, price, details, type;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_tv);
            time = itemView.findViewById(R.id.time_tv);
            price = itemView.findViewById(R.id.price_tv);
            details = itemView.findViewById(R.id.details_tv);
            type = itemView.findViewById(R.id.type_tv);


        }
    }
}