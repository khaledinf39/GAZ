package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Activities.MainNew;
import com.kh_sof_dev.gaz.Fragments.SearchResults;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Search_adapter extends RecyclerView.Adapter<Search_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
private View mView;
private Fragment mFragment;
    //vars
    private List<com.kh_sof_dev.gaz.Classes.Products.Product> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;

    public Search_adapter(Context context, List<com.kh_sof_dev.gaz.Classes.Products.Product> names, Fragment mFragment) {
        mItems = names;
        mContext = context;
        Item_selected=0;
        this.mFragment=mFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_main, parent, false);

mView=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.prod_name.setText(mItems.get(position).getName());
       holder.type.setText("");
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNew.goto_(new SearchResults(mItems.get(position).getName()),mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView prod_name,type;

      public ViewHolder(View itemView) {
            super(itemView);
            prod_name = itemView.findViewById(R.id.main_tv);
 type = itemView.findViewById(R.id.type_tv);


        }
    }
}