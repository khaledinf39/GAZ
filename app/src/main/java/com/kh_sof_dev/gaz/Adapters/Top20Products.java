package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.ProductDetails;

import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Top20Products extends RecyclerView.Adapter<Top20Products.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    //vars
    private List<Product> mItems;
    public static int Item_selected;
    private Context mContext;
    private View mView;

    public Top20Products(Context context, List<Product> names) {
        mItems = names;
        mContext = context;
        Item_selected = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_results_spetial_products, parent, false);

        mView = view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.name_pro.setText(mItems.get(position).getName());
        holder.details_pro.setText(mItems.get(position).getDescription());
        holder.score.setText(mItems.get(position).getRate() + "");
        holder.type.setText("");
        holder.price.setText(mItems.get(position).getPrice_buy_new() + "");

        Utils.showImage(mContext, mItems.get(position).getImage(),
                R.drawable.placeholder, holder.pro_img);
//        Picasso.with(mContext).load(mItems.get(position).getImage())
//                .placeholder(R.drawable.placeholder)
//                .into(holder.pro_img);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra(ProductDetails.product, mItems.get(position));
                mContext.startActivity(intent);
//                MainNew.goto_(new ProductDetails(mItems.get(position), true), mContext);
            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra(ProductDetails.product, mItems.get(position));
                mContext.startActivity(intent);
//                MainNew.goto_(new ProductDetails(mItems.get(position), true), mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView price, name_pro, details_pro, score, type;
        Button details;
        ImageView pro_img;

        public ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            name_pro = itemView.findViewById(R.id.productName);
            details_pro = itemView.findViewById(R.id.productDetails);
            score = itemView.findViewById(R.id.score);
            type = itemView.findViewById(R.id.prod_typ);
            details = itemView.findViewById(R.id.details_btn);
            pro_img = itemView.findViewById(R.id.pro_img);

        }
    }
}