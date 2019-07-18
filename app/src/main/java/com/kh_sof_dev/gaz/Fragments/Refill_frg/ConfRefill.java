package com.kh_sof_dev.gaz.Fragments.Refill_frg;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.Refill_adapter;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.Fragments.Reservations.Payment;
import com.kh_sof_dev.gaz.R;


@SuppressLint("ValidFragment")
public class ConfRefill extends Fragment {

    public static String notes_ = "لا يوجد";
    private int order_type;

    @SuppressLint("ValidFragment")
    public ConfRefill(int order_type) {
        this.order_type = order_type;
    }

    public ConfRefill() {
    }

    private TextView date_tv, time_tv, note_tv, price, delivery, tax, total;
    private Button continue_btn;
    private ImageView back, edite_notes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_conf_refill, container, false);
        date_tv = (TextView) view.findViewById(R.id.date_tv);
        time_tv = (TextView) view.findViewById(R.id.time_tv);
        note_tv = (TextView) view.findViewById(R.id.notes_tv);

        price = (TextView) view.findViewById(R.id.price_tv);
        delivery = (TextView) view.findViewById(R.id.delivery_tv);
        tax = (TextView) view.findViewById(R.id.taxt_tv);
        total = (TextView) view.findViewById(R.id.totalPrice_tv);
        continue_btn = (Button) view.findViewById(R.id.continue_btn);
        back = (ImageView) view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainNew.goto_(new Refill(order_type), getContext());

            }
        });
        edite_notes = (ImageView) view.findViewById(R.id.edit_notes);
        edite_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EDitNotesPopup();
            }
        });
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNew.goto_(new Payment(order_type), getContext());

            }
        });
        /*******************************intialise info*****************************/
        date_tv.setText(RefillHome.date);
        time_tv.setText(RefillHome.time);
        RecyclerView RV = view.findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RV.setAdapter(new Refill_adapter(getContext(), RefillHome.productList));

        Double price_ = 0.0;
        for (Product p : RefillHome.productList
        ) {
            price_ = price_ + p.getPrice() * p.getQty();
            qty = qty + p.getQty();
        }


        calculate(price_);

        return view;
    }

    int qty = 0;

    private void calculate(Double price_) {
        final Setting setting = new Setting(getContext());
        Double delivry_ = 0.0;
        Double total_ = 0.0;
        Double taxe = 0.0;
        price.setText(price_.toString());
        delivry_ = qty * Double.valueOf((setting.getDelivery()));
        delivery.setText(delivry_.toString());
        taxe = Double.valueOf((setting.getTax())) * price_ * 0.01;
        tax.setText(taxe.toString());
        total_ = price_ + delivry_ + taxe;
        total.setText(total_.toString());
    }

    private void EDitNotesPopup() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_notes);
        Button save = dialog.findViewById(R.id.save_btn);
        final EditText notes = dialog.findViewById(R.id.notes_tv);
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                notes_ = notes.getText().toString();
                note_tv.setText(notes_);
            }
        });
    }

}
