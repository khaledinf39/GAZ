package com.kh_sof_dev.gaz.activities;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Adapters.Refill_adapter;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.Fragments.Refill_frg.RefillHome;
import com.kh_sof_dev.gaz.R;


public class ConfRefill extends AppCompatActivity {

    public static String notes_ = "لا يوجد";
    private int order_type;

    private TextView note_tv;
    private TextView price;
    private TextView delivery;
    private TextView tax;
    private TextView total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_conf_refill);

        order_type = getIntent().getIntExtra(Refill.order_type_s, 0);

        TextView date_tv = findViewById(R.id.date_tv);
        TextView time_tv = findViewById(R.id.time_tv);
        note_tv = findViewById(R.id.notes_tv);

        price = findViewById(R.id.price_tv);
        delivery = findViewById(R.id.delivery_tv);
        tax = findViewById(R.id.taxt_tv);
        total = findViewById(R.id.totalPrice_tv);
        Button continue_btn = findViewById(R.id.continue_btn);
        ImageView back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfRefill.this, Refill.class);
                intent.putExtra(Refill.order_type_s, order_type);
                startActivity(intent);
//                MainNew.goto_(new Refill(order_type), getContext());

            }
        });
        ImageView edite_notes = findViewById(R.id.edit_notes);
        edite_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EDitNotesPopup();
            }
        });
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfRefill.this, Payment.class);
                intent.putExtra(Payment.order_type, order_type);
                startActivity(intent);
//                MainNew.goto_(new Payment(order_type), getContext());

            }
        });
        /*******************************intialise info*****************************/
        date_tv.setText(RefillHome.date);
        time_tv.setText(RefillHome.time);
        RecyclerView RV = findViewById(R.id.RV);
        RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final Refill_adapter adapter = new Refill_adapter(this, RefillHome.productList, new Refill_adapter.OnGaztype_selected_listenner() {
            @Override
            public void OnAdd(int postion) {

            }

            @Override
            public void OnDelete(int postion) {

            }
        });
        RV.setAdapter(adapter);


        Double price_ = 0.0;
        for (Product p : RefillHome.productList
        ) {
            price_ = price_ + p.getPrice() * p.getQty();
            qty = qty + p.getQty();
        }


        calculate(price_);

    }

    int qty = 0;

    private void calculate(Double price_) {
        final Setting setting = new Setting(this);
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
        final Dialog dialog = new Dialog(this);
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
