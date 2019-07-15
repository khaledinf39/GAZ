package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.Notification_adapter;
import com.kh_sof_dev.gaz.Classes.Ntification.Http_notification;
import com.kh_sof_dev.gaz.Classes.Ntification.show_notif;
import com.kh_sof_dev.gaz.R;

public class Notifications extends Fragment {

    public Notifications() {

    }

    private TextView notiy_nb;
    private RecyclerView notif_RV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.layout_f_notifications, container, false);
        notif_RV = (RecyclerView) view.findViewById(R.id.notifications_RV);
        notif_RV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        notiy_nb = (TextView) view.findViewById(R.id.nb_notification);
        ImageView back_btn = (ImageView) view.findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainNew.class));
//                MainNew._goto(MyReservations.this,new Home(),View.VISIBLE);
            }
        });
        final ProgressBar progressBar = view.findViewById(R.id.progress);
        final Http_notification notification = new Http_notification();
        notification.Getnotification(getContext(), new Http_notification.notificationListener() {
            @Override
            public void onSuccess(show_notif show_notif) {
                progressBar.setVisibility(View.GONE);
                if (show_notif.getItems().size() != 0) {
                    notiy_nb.setText(show_notif.getItems().size() + "");
                    view.findViewById(R.id.no_notification).setVisibility(View.GONE);
                    view.findViewById(R.id.notification).setVisibility(View.VISIBLE);
//                    Toast.makeText(getContext(),show_notif.getMessage(),Toast.LENGTH_SHORT).show();
                }
                notif_RV.setAdapter(new Notification_adapter(getContext(), show_notif.getItems(), Notifications.this));

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });

        return view;
    }

}
