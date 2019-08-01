package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.OrderProduct;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class OrderDetails_prod_adapter extends RecyclerView.Adapter<OrderDetails_prod_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<OrderProduct> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;
    private  int qty;
    public interface orderPrice_listenner{
        void onSuccess(Double addPrice);
        void onStart();
        void onFailure(String msg);
    }
    orderPrice_listenner listenner;
    public OrderDetails_prod_adapter(Context context, List<OrderProduct> names) {
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
        holder.pro_nam.setText(mItems.get(position).getProductId().getName());
        holder.nb.setText(mItems.get(position).getQty()+"");

        Utils.showImage(mContext, mItems.get(position).getProductId().getImage(),
                R.drawable.placeholder, holder.pro_img);
//        Picasso.with(mContext).load(mItems.get(position).getProductId().getImage())
//                .placeholder(R.drawable.placeholder)
//        .into(holder.pro_img);


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