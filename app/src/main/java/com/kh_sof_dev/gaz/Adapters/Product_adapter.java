package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kh_sof_dev.gaz.Activities.MainNew;
import com.kh_sof_dev.gaz.Fragments.Product_details;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Product_adapter extends RecyclerView.Adapter<Product_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
private View mView;
private Fragment mFragment;
    //vars
    private List<com.kh_sof_dev.gaz.Classes.Products.Product> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;

    public Product_adapter(Context context, List<com.kh_sof_dev.gaz.Classes.Products.Product> names, Fragment mFragment) {
        mItems = names;
        mContext = context;
        Item_selected=0;
        this.mFragment=mFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_products, parent, false);

mView=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.prod_name.setText(mItems.get(position).getName());
        Glide.with(mContext).load(mItems.get(position).getImage())
                .placeholder(R.drawable.placeholder)
        .into(holder.prod_img);
        holder.nb_start.setText(mItems.get(position).getRate()+"");
        holder.type.setText("");
        holder.price.setText(mItems.get(position).getPrice().toString());
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNew.goto_(new Product_details(mItems.get(position),false),mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView prod_name,price,nb_start,type;
        ImageView prod_img;
      public ViewHolder(View itemView) {
            super(itemView);
            prod_img=itemView.findViewById(R.id.productImg);
           prod_name = itemView.findViewById(R.id.productName);

          price=itemView.findViewById(R.id.price);
          type = itemView.findViewById(R.id.type_tv);

          nb_start=itemView.findViewById(R.id.nb_star);


        }
    }
}