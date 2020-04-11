package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.SearchResults;

import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Search_adapter extends RecyclerView.Adapter<Search_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private View mView;
    //vars
    private List<com.kh_sof_dev.gaz.Classes.Products.Product> mItems;
    public static int Item_selected;
    private Context mContext;

    public Search_adapter(Context context, List<com.kh_sof_dev.gaz.Classes.Products.Product> names) {
        mItems = names;
        mContext = context;
        Item_selected = 0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_main, parent, false);

        mView = view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.prod_name.setText(mItems.get(position).getName());
        holder.type.setText("");
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, SearchResults.class);
                intent.putExtra(SearchResults.productName, mItems.get(position).getName());
                mContext.startActivity(intent);
//                MainNew.goto_(new SearchResults(mItems.get(position).getName()), mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView prod_name, type;

        public ViewHolder(View itemView) {
            super(itemView);
            prod_name = itemView.findViewById(R.id.main_tv);
            type = itemView.findViewById(R.id.type_tv);


        }
    }
}