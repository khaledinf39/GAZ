package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Ntification.Http_notification;
import com.kh_sof_dev.gaz.Classes.Ntification.notification;
import com.kh_sof_dev.gaz.Classes.Ntification.show_notif;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.OrderDetails;

import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private View mView;
    //vars
    private List<notification> mItems;
    public static int Item_selected;
    private Context mContext;

    public Notification_adapter(Context context, List<notification> names) {
        mItems = names;
        mContext = context;
        Item_selected = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notifications_main, parent, false);

        mView = view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        if (mItems.get(position).getRead()) {
            mItems.remove(position);
            notifyDataSetChanged();
        }
        holder.name.setText(mItems.get(position).getTitle());

        try {
            holder.date.setText(Order_adapter.getdate(mItems.get(position).getDt_date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.text.setText(mItems.get(position).getMsg());
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mItems.get(position).getType() == 1) {
//                    OutherActivty.new_frg = new OrderDetails(mItems.get(position).getBody_parms(), mContext);
                    Intent intent = new Intent(mContext, OrderDetails.class);
                    intent.putExtra(OrderDetails.REQUEST_ID, mItems.get(position).getBody_parms());
                    mContext.startActivity(intent);


                }
                Http_notification http_notification = new Http_notification();
                http_notification.Put_Read_notifiction(mContext, mItems.get(position).get_id(), new Http_notification.notificationListener() {
                    @Override
                    public void onSuccess(show_notif show_notif) {

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
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, text, date;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv);
            text = itemView.findViewById(R.id.text);

            date = itemView.findViewById(R.id.date_tv);


        }
    }
}