
package com.kh_sof_dev.gaz.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kh_sof_dev.gaz.Classes.Firebase.NewMessage;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;
import com.google.firebase.database.DatabaseReference;

import java.util.Date;
import java.util.List;

public class Chat_adapter extends RecyclerView.Adapter<Chat_adapter.MyViewHolder> {
        private List<NewMessage> mDataset;
        public static final int MSG_LEFT = 0;
        public static final int MSG_RIGHT = 1;
        DatabaseReference reference;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
                // each data item is just a string in this case
                TextView msg,msg_time;

                public MyViewHolder(View v) {
                        super(v);
                        msg = v.findViewById(R.id.msg_tv);
                        msg_time = v.findViewById(R.id.msg_time);
                }
        }
        Context mcontext;
        // Provide a suitable constructor (depends on the kind of dataset)
        public Chat_adapter(Context mcontext, List<NewMessage> mDataset) {
                this.mDataset = mDataset;
                this.mcontext=mcontext;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public Chat_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v;
                if(viewType == MSG_LEFT){
                        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_sender, parent, false);
//                        LayoutInflater.from(mcontext)
//                                .inflate(R.layout.row_chat_sender, null);
                }else{
                        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chat_receiver, parent, false);

//                        v =  LayoutInflater.from(mcontext)
//                                .inflate(R.layout.row_chat_receiver, null);
                }
                MyViewHolder vh = new MyViewHolder(v);
                return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
                // - get element from your dataset at this position
                // - replace the contents of the view with that element
                holder.msg.setText(mDataset.get(position).getMsg());
                String dateString = DateFormat.format("yyyy/M/d h:m aa",
                        new Date(mDataset.get(position).getLong_time())).toString();
                holder.msg_time.setText(dateString);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
                return mDataset.size();
        }

        @Override
        public int getItemViewType(int position) {
                user_info user_info=new user_info(mcontext);
                if(!mDataset.get(position).getSender_id().equals(user_info.getId()))
                        return MSG_LEFT;
                else
                        return MSG_RIGHT;

        }
}
