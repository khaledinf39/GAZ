package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.activities.OutherActivty;
import com.kh_sof_dev.gaz.Classes.Ntification.Http_notification;
import com.kh_sof_dev.gaz.Classes.Ntification.notification;
import com.kh_sof_dev.gaz.Classes.Ntification.show_notif;
import com.kh_sof_dev.gaz.Fragments.Order_details;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Notification_adapter extends RecyclerView.Adapter<Notification_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
private View mView;
private Fragment mFragment;
    //vars
    private List<notification> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;

    public Notification_adapter(Context context, List<notification> names, Fragment mFragment) {
        mItems = names;
        mContext = context;
        Item_selected=0;
        this.mFragment=mFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notifications_main, parent, false);

mView=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
if (mItems.get(position).getRead()){
    mItems.remove(position);
    notifyDataSetChanged();
}
        holder.name.setText(mItems.get(position).getTitle());

       try{
           holder.date.setText(Order_adapter.getdate(mItems.get(position).getDt_date()));
       }catch (Exception e){
           e.printStackTrace();
       }
        holder.text.setText(mItems.get(position).getMsg());
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if (mItems.get(position).getType()==1){
    OutherActivty.new_frg=new Order_details(mItems.get(position).getBody_parms(),mContext );
    mContext.startActivity(new Intent(mContext,OutherActivty.class));


   }
                Http_notification http_notification=new Http_notification();
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,text,date;

      public ViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_tv);
           text = itemView.findViewById(R.id.text);

          date=itemView.findViewById(R.id.date_tv);



        }
    }
}