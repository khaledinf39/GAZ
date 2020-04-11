package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Database.Best;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.ProductDetails;

import java.util.List;

import io.realm.Realm;


/**
 * Created by User on 2/6/2018.
 */

public class myFavoriteAdapter extends RecyclerView.Adapter<myFavoriteAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private List<Product> mItems;
    public static int Item_selected;
    private Context mContext;

    //    Fragment mFragment;
//    public interface categorie_selected_listenner {
//        void onSuccess(String id);
//
//        void onStart();
//
//        void onFailure(String msg);
//    }

//    categorie_selected_listenner listenner;

    public myFavoriteAdapter(Context context, List<Product> names) {
        mItems = names;
        mContext = context;
        Item_selected = 0;

    }

    private View mview;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_favorites_main, parent, false);
        mview = view;

        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
//        if (Item_selected == position) {
//
//        }
        holder.price.setText(String.valueOf(mItems.get(position).getPrice()));
        holder.name.setText(mItems.get(position).getName());

        Utils.showImage(mContext, mItems.get(position).getImage(), holder.img);
//        Picasso.with(mContext).load(mItems.get(position).getImage())
//                .into(holder.img);
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                Best best = realm.where(Best.class).equalTo("id", mItems.get(position).getID_()).findFirst();
                realm.beginTransaction();
                assert best != null;
                best.deleteFromRealm();
                realm.commitTransaction();

//                DBManager dbManager = new DBManager(mContext);
//                dbManager.open();
//                dbManager.deleteBestPro(mItems.get(position).getID_());
//                dbManager.close();

                mItems.remove(position);
                notifyDataSetChanged();
            }
        });
        mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProductDetails.class);
                intent.putExtra(ProductDetails.product, mItems.get(position));
                mContext.startActivity(intent);
//                MainNew.goto_(new ProductDetails(mItems.get(position), false), mContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
        ImageView img, delet;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.productImg);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.price_tv);
            delet = itemView.findViewById(R.id.delete_btn);

        }
    }
}