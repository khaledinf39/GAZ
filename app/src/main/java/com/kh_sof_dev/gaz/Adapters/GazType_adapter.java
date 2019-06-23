package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kh_sof_dev.gaz.Activities.MainNew;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class GazType_adapter extends RecyclerView.Adapter<GazType_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
private View mView;
private Fragment mFragment;
    //vars
    public interface OnGaztype_selected_listenner{
        void onAdd(int postion);
        void onDellet(int postion);
        void onFailure(String msg);
    }
    private OnGaztype_selected_listenner listenner;
    private List<Product> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;

    public GazType_adapter(Context context, List<Product> names,OnGaztype_selected_listenner listenner) {
        mItems = names;
        mContext = context;
//        Item_selected=0;

        this.listenner=listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_refill_main, parent, false);

mView=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.prod_name.setText(mItems.get(position).getName());
        Glide.with(mContext).load(mItems.get(position).getImage())
        .into(holder.prod_img);
        holder.prod_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.radio.isChecked()) {
                    listenner.onAdd(position);
                    holder.radio.setChecked(true);
                }else {
                    listenner.onDellet(position);
                    holder.radio.setChecked(false);
                }

            }
        });
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.radio.isChecked()) {
                    listenner.onAdd(position);
                    holder.radio.setChecked(true);
                }else {
                    listenner.onDellet(position);
                    holder.radio.setChecked(false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView prod_name;
        ImageView prod_img;
        RadioButton radio;
      public ViewHolder(View itemView) {
            super(itemView);
            prod_img=itemView.findViewById(R.id.img);
           prod_name = itemView.findViewById(R.id.name_tv);
          radio = itemView.findViewById(R.id.radio);



        }
    }
}