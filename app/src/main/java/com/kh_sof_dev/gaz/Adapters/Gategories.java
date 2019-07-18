package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.kh_sof_dev.gaz.Classes.Products.catigories;

import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Gategories extends RecyclerView.Adapter<Gategories.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<catigories> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;
    public interface categorie_selected_listenner{
        void onSuccess(catigories catigories);
        void onStart();
        void onFailure(String msg);
    }
    categorie_selected_listenner listenner;
    public Gategories(Context context, List<catigories> names,categorie_selected_listenner listenner) {
        mItems = names;
        mContext = context;
       // Item_selected=0;
        this.listenner=listenner;
    }
private View mView;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home_categories, parent, false);
mView=view;

        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.categories_tv.setText(mItems.get(position).getName());
        Picasso.with(mContext).load(mItems.get(position).getImage())
                .placeholder(R.drawable.placeholder)
        .into(holder.cat_img);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.onSuccess(mItems.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView categories_tv;
        ImageView cat_img;
      public ViewHolder(View itemView) {
            super(itemView);
            cat_img=itemView.findViewById(R.id.cat_img);
           categories_tv = itemView.findViewById(R.id.categories_tv);


        }
    }
}