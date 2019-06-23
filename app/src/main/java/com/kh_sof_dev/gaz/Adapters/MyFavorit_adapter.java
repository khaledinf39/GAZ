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
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Fragments.Product_details;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class MyFavorit_adapter extends RecyclerView.Adapter<MyFavorit_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Product> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;
    Fragment mFragment;
    public interface categorie_selected_listenner{
        void onSuccess(String id);
        void onStart();
        void onFailure(String msg);
    }
    categorie_selected_listenner listenner;
    public MyFavorit_adapter(Context context, List<Product> names,Fragment mFragment) {
        mItems = names;
        mContext = context;
        Item_selected=0;
        this.mFragment=mFragment;

    }
private View mview;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_favorites_main, parent, false);
mview=view;

        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
if (Item_selected==position){

}
holder.price.setText(mItems.get(position).getPrice().toString());
holder.name.setText(mItems.get(position).getName());
        Glide.with(mContext).load(mItems.get(position).getImage())
        .into(holder.img);
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager dbManager=new DBManager(mContext);
                dbManager.open();
                dbManager.deleteBestPro(mItems.get(position).getID_());
                dbManager.close();

                mItems.remove(position);
                notifyDataSetChanged();
            }
        });
        mview.setOnClickListener(new View.OnClickListener() {
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

TextView name,price;
        ImageView img,delet;
      public ViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.productImg);
          name=itemView.findViewById(R.id.productName);
          price=itemView.findViewById(R.id.price_tv);
          delet=itemView.findViewById(R.id.delete_btn);

        }
    }
}