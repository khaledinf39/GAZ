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
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Products.Product;

import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Basket_adapter extends RecyclerView.Adapter<Basket_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Product> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;
    private  int qty;
    public interface orderPrice_listenner{
        void onSuccess(Double addPrice,int qty);
        void onStart();
        void onFailure(String msg);
    }
    orderPrice_listenner listenner;
    public Basket_adapter(Context context, List<Product> names, orderPrice_listenner listenner) {
        mItems = names;
        mContext = context;
        Item_selected=0;
        this.listenner=listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_car_main, parent, false);


        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

final DBManager dbManager=new DBManager(mContext);
 qty=mItems.get(position).getQty();
holder.nb.setText(qty+"");
holder.price.setText(mItems.get(position).getPrice().toString());
        holder.pro_nam.setText(mItems.get(position).getName());
        Glide.with(mContext).load(mItems.get(position).getImage())
        .into(holder.pro_img);
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            qty++;
                mItems.get(position).setQty(qty);
                dbManager.open();
                dbManager.update_Item(mItems.get(position).getID_(),qty);
                dbManager.close();
                listenner.onSuccess(mItems.get(position).getPrice(),qty);
                notifyDataSetChanged();
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qty==1){
                    return;
                }
                qty--;
                mItems.get(position).setQty(qty);
                dbManager.open();
                dbManager.update_Item(mItems.get(position).getID_(),qty);
                dbManager.close();
                listenner.onSuccess(-mItems.get(position).getPrice(),qty);
                notifyDataSetChanged();
            }
        });
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dbManager.open();
               dbManager.delete(mItems.get(position).getID_());
               dbManager.close();

                listenner.onSuccess(-mItems.get(position).getPrice()*qty,0);
                mItems.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView pro_nam,price,nb;
        ImageView pro_img,add,minus,delet;
      public ViewHolder(View itemView) {
            super(itemView);
            pro_img=itemView.findViewById(R.id.productImg);
           pro_nam = itemView.findViewById(R.id.productName);
          add=itemView.findViewById(R.id.add_btn);
          minus = itemView.findViewById(R.id.minus_btn);
          delet=itemView.findViewById(R.id.delete_btn);
          price = itemView.findViewById(R.id.price_tv);
          nb = itemView.findViewById(R.id.NoOfProducts_tv);


        }
    }
}