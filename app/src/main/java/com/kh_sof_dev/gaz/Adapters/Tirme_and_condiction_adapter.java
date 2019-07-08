package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.constant.About_us.cons;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Tirme_and_condiction_adapter extends RecyclerView.Adapter<Tirme_and_condiction_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
private View mView;
private Fragment mFragment;
    //vars
    private List<cons> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;

    public Tirme_and_condiction_adapter(Context context, List<cons> names) {
        mItems = names;
        mContext = context;
//        Item_selected=0;
//        this.mFragment=mFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tirm_and_condition, parent, false);

mView=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.tit.setText(mItems.get(position).getTitle());
        holder.webView.loadData(mItems.get(position).getContent(), "text/html", "UTF-8");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tit;
        WebView webView;

      public ViewHolder(View itemView) {
            super(itemView);
            tit = itemView.findViewById(R.id.title_tv);
 webView = itemView.findViewById(R.id.web);


        }
    }
}