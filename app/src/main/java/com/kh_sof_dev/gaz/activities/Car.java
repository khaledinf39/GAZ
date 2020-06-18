package com.kh_sof_dev.gaz.activities;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Adapters.Basket_adapter;
import com.kh_sof_dev.gaz.Classes.Database.OrderDetails;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.MyApplication;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class Car extends AppCompatActivity {

    public static String notes_ = "لا يوجد";

    private TextView count;
    private TextView price;
    private TextView deleviry;
    private TextView total;
    private TextView tax;
    private RecyclerView basketRV;

    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_car);

        realm = MyApplication.getRealm();

//        TextView notes = findViewById(R.id.notes_tv);
        count = findViewById(R.id.order_count_tv);
        Button start = findViewById(R.id.start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Car.this, MainNew.class));
//                finish();
//        MainNew.goto_(Car.this,new Home(),View.VISIBLE);

            }
        });

        price = findViewById(R.id.price_tv);
        deleviry = findViewById(R.id.tax_tv);

        tax = findViewById(R.id.delivery_tv);
        total = findViewById(R.id.total_tv);
//        notes = (TextView) view.findViewById(R.id.notes_tv);
        ImageView back = findViewById(R.id.back_btn);
        ImageView edite = findViewById(R.id.edit_note_img);
        basketRV = findViewById(R.id.basketRV);
        Button continue_btn = findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Car.this, Shipping.class);
                intent.putExtra(Shipping.typ2_Order_1_test, 2);
                intent.putExtra(Shipping.order_type_s, 1);
                startActivity(intent);
//                MainNew.goto_(new Shipping(2, 1), getContext());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Car.this, MainNew.class));
                finish();
//                MainNew.goto_(new Home(),getContext());
            }
        });
        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EDitNotesPopup();
            }
        });
//*********************************order fetch***********************************************/
        fetch_order();


//*****************************count order******************************************/
        RealmResults<OrderDetails> orderDetails = realm.where(OrderDetails.class).findAll();
        count.setText(String.valueOf(orderDetails.size()));
//        DBManager db = new DBManager(this);
//        db.open();
//        Long count_order = db.get_order_count();
//        db.close();
//        count.setText(count_order.toString());
        if (!orderDetails.isEmpty()) {
            findViewById(R.id.empty_car).setVisibility(View.GONE);
            findViewById(R.id.car).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.empty_car).setVisibility(View.VISIBLE);
            findViewById(R.id.car).setVisibility(View.GONE);
        }
    }

    Double price_ = 0.0;
    int qty = 0;

    private void fetch_order() {
        final RealmResults<OrderDetails> orderDetailsList = realm.where(OrderDetails.class).findAll();
        List<Product> products = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            Product p = new Product();
            p.setID_((int) orderDetails.getId());
            p.setId(orderDetails.getProductId());
            p.setName(orderDetails.getProductName());
            p.setQty(orderDetails.getQuantity());
            p.setPrice(orderDetails.getPrice());
            p.setImage(orderDetails.getImage());
            products.add(p);

            price_ += p.getPrice() * p.getQty();
            qty = qty + p.getQty();
        }

//        final DBManager db = new DBManager(this);
//        db.open();
//        List<Product> products = db.fetch_order();
//
//        for (Product p : products
//        ) {
//            price_ += p.getPrice() * p.getQty();
//            qty = qty + p.getQty();
//        }

        calculate(price_);
        basketRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        basketRV.setAdapter(new Basket_adapter(this, products, new Basket_adapter.orderPrice_listenner() {
            @Override
            public void onSuccess(Double priceadd, int qty1) {
                price_ = price_ + priceadd;
                qty = qty1;
                calculate(price_);
//                db.open();
//                long nb = db.get_order_count();
//                db.close();
                long nb = orderDetailsList.size();
                if (nb == 0) {
                    recreate();
                } else {
                    count.setText(nb + "");
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        }));
    }

    private void calculate(double price_) {
        final Setting setting = new Setting(this);
        double delivry_;
        double total_;
        double taxe;
        price.setText(String.valueOf(price_));
        delivry_ = qty * setting.getDelivery();
        tax.setText(String.valueOf(delivry_));
        taxe = Double.valueOf((setting.getTax())) * price_ * 0.01;
        deleviry.setText(String.valueOf(taxe));
        total_ = price_ + delivry_ + taxe;
        total.setText(String.valueOf(total_));
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
                notes.setText(notes_);
            }
        });
    }

}
