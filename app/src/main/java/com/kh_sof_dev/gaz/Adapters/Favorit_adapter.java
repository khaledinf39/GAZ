package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Favorit_adapter extends RecyclerView.Adapter<Favorit_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Product> mItems = new ArrayList<>();
    public static int Item_selected;
    private Context mContext;

    public interface categorie_selected_listenner {
        void onSuccess(String id);

        void onStart();

        void onFailure(String msg);
    }

    categorie_selected_listenner listenner;

    public Favorit_adapter(Context context, List<Product> names) {
        mItems = names;
        mContext = context;
        Item_selected = 0;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_profile_fav, parent, false);


        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        if (Item_selected == position) {

        }

        Utils.showImage(mContext, mItems.get(position).getImage(),
                R.drawable.placeholder, holder.img);
//        Picasso.with(mContext).load(mItems.get(position).getImage())
//                .placeholder(R.drawable.placeholder)
//                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);


        }
    }
}