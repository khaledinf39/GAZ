package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.constant.City;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Time_adapter extends RecyclerView.Adapter<Time_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private View mView;
    private Fragment mFragment;

    //vars
    public interface OnGaztype_selected_listenner {
        void onSuccess(int postion);

        void onStart();

        void onFailure(String msg);
    }

    private OnGaztype_selected_listenner listenner;
    private List<City> mItems = new ArrayList<>();
    public static int Item_selected;
    private Context mContext;

    public Time_adapter(Context context, List<City> names, OnGaztype_selected_listenner listenner) {
        mItems = names;
        mContext = context;
        Item_selected = 0;

        this.listenner = listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_time_popup, parent, false);

        mView = view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.time.setText(mItems.get(position).getName());

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onSuccess(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_tv);


        }
    }
}