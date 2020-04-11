package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 2/6/2018.
 */

public class Refill_adapter extends RecyclerView.Adapter<Refill_adapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
private View mView;
private Fragment mFragment;
    //vars
    public interface OnGaztype_selected_listenner{
        void OnAdd(int postion);
        void OnDelete(int postion);

    }

    private List<Product> mItems = new ArrayList<>();
public static int Item_selected;
    private Context mContext;
private OnGaztype_selected_listenner listenner;
    public Refill_adapter(Context context, List<Product> names,OnGaztype_selected_listenner listenner) {
        mItems = names;
        mContext = context;
this.listenner=listenner;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //parent = theme type
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_refill, parent, false);

mView=view;
        return new ViewHolder(view); // Inflater means reading a layout XML
    }


int qty;
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
       qty=mItems.get(position).getQty();
       holder.nb.setText(qty+"");
       holder.prod_price.setText(mItems.get(position).getPrice().toString());
       holder.prod_name.setText(mItems.get(position).getName());
       holder.add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listenner.OnAdd(position);
//               qty++;
//               mItems.get(position).setQty(qty);
               notifyDataSetChanged();
           }
       });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.OnDelete(position);
//                if (qty==1){
//                    return;
//                }
//                qty--;
//                mItems.get(position).setQty(qty);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView prod_name,prod_price,nb;
        ImageView add,minus;

      public ViewHolder(View itemView) {
            super(itemView);
            prod_price=itemView.findViewById(R.id.price_tv);
           prod_name = itemView.findViewById(R.id.Name_tv);

          add=itemView.findViewById(R.id.add_btn);
          minus = itemView.findViewById(R.id.minus_btn);
          nb = itemView.findViewById(R.id.NoOfProducts_tv);

        }
    }
}