package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.layout_f_notifications, container, false);
        notif_RV = view.findViewById(R.id.notifications_RV);
        notif_RV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        notiy_nb = view.findViewById(R.id.nb_notification);
//        ImageView back_btn = view.findViewById(R.id.back_btn);
//
//        back_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), MainNew.class));
////                MainNew._goto(MyReservations.this,new Home(),View.VISIBLE);
//            }
//        });
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
                notif_RV.setAdapter(new Notification_adapter(getContext(), show_notif.getItems()));

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
