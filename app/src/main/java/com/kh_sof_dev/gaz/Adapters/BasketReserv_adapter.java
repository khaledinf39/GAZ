package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class BasketReserv_adapter extends RecyclerView.Adapter<BasketReserv_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Product> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;
    private  int qty;
    public interface orderPrice_listenner{
        void onSuccess(Double addPrice);
        void onStart();
        void onFailure(String msg);
    }
    orderPrice_listenner listenner;
    public BasketReserv_adapter(Context context, List<Product> names) {
        mItems = names;
        mContext = context;
        Item_selected=0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_conf_res_main, parent, false);


        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.price.setText(mItems.get(position).getPrice().toString());
        holder.pro_nam.setText(mItems.get(position).getName());
        holder.nb.setText(mItems.get(position).getQty()+"");
        Glide.with(mContext).load(mItems.get(position).getImage())
        .into(holder.pro_img);


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pro_nam,price,nb;
        ImageView pro_img;
      public ViewHolder(View itemView) {
            super(itemView);
            pro_img=itemView.findViewById(R.id.img);
           pro_nam = itemView.findViewById(R.id.productName);
          nb = itemView.findViewById(R.id.nb_prod);
          price = itemView.findViewById(R.id.price_tv);


        }
    }
}