package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Classes.Database.OrderDetails;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.Fragments.Refill_frg.RefillHome;
import com.kh_sof_dev.gaz.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class Payment extends AppCompatActivity implements View.OnClickListener {
    public static final String order_type = "order_type";
    int order_typ;

    private LinearLayout visa, cash, paytabs;
    private int payment_typ = 0;
    private Double price_ = 0.0, total = 0.0;
    private TextView cashTV, visaTV, pointTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_payment);

        order_typ = getIntent().getIntExtra(order_type, 0);

        paytabs = findViewById(R.id.point_lay);
        ImageView back = findViewById(R.id.back_btn);
        visa = findViewById(R.id.visa);
        cash = findViewById(R.id.cash);
        cashTV = findViewById(R.id.cash_tv);

        visaTV = findViewById(R.id.visa_tv);
        pointTV = findViewById(R.id.point_tv);
        Button continue_btn = findViewById(R.id.continue_btn);
        paytabs.setOnClickListener(this);
        cash.setOnClickListener(this);
        visa.setOnClickListener(this);
        continue_btn.setOnClickListener(this);
        back.setOnClickListener(this);


        if (order_typ == 1) {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<com.kh_sof_dev.gaz.Classes.Database.OrderDetails> orderDetailsList = realm.where(com.kh_sof_dev.gaz.Classes.Database.OrderDetails.class).findAll();
            for (OrderDetails orderDetails : orderDetailsList) {
                price_ = price_ + orderDetails.getPrice() * orderDetails.getQuantity();
            }
//            DBManager db = new DBManager(this);
//            db.open();
//            final List<Product> products = db.fetch_order();
//            for (Product p : products
//            ) {
//                price_ = price_ + p.getPrice() * p.getQty();
//            }
        }
        if (order_typ != 1) {
//            price_= RefillHome.price_refill*RefillHome.qty;
            for (Product p : RefillHome.productList
            ) {
                price_ = price_ + p.getPrice() * p.getQty();
            }
        }

        Setting setting = new Setting(this);
        double delivry_ = setting.getDelivery() * price_ * 0.01;
        double taxe = Double.valueOf((setting.getTax())) * price_ * 0.01;
        total = price_ + delivry_ + taxe;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_btn:
                startActivity(new Intent(this, Car.class));
                finish();
//                MainNew.goto_(new Car(), getContext());
                break;
            case R.id.continue_btn:
                if (payment_typ == 0) {
                    Toast.makeText(this, "اختار وسيلة الدفع", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Payment.this, ConfReservation1.class);
                intent.putExtra(ConfReservation1.payment_type_s, payment_typ);
                intent.putExtra(ConfReservation1.note_s, getIntent().getStringExtra(ConfReservation1.note_s));
                intent.putExtra(Refill.order_type_s, order_typ);
                startActivity(intent);
                finish();
//                MainNew.goto_(new ConfReservation1(payment_typ, order_typ), this);
                break;
            case R.id.point_lay:
                checkPoint();
                break;
            case R.id.visa:
//                payment_typ=2;
//
//              change_back_pay(payment_typ);
                break;
            case R.id.cash:
                payment_typ = 1;
                change_back_pay(payment_typ);
                break;
        }
    }

    private void checkPoint() {
        user_info user_info = new user_info(this);
        if (!(user_info.getWallet() < total)) {
            payment_typ = 3;
            change_back_pay(payment_typ);
        } else {
            Toast.makeText(this, "نقاطك لا تكفي لشراء المنتج", Toast.LENGTH_LONG).show();

        }
    }

    private void change_back_pay(int payment_typ) {
        switch (payment_typ) {
            case 2:
                visa.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways2));
                visaTV.setTextColor(ContextCompat.getColor(this, R.color.white));
                cashTV.setTextColor(ContextCompat.getColor(this, R.color.btn_blue));
                pointTV.setTextColor(ContextCompat.getColor(this, R.color.btn_blue));
                paytabs.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways));
                cash.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways));
                break;
            case 3:
                visa.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways));
                cashTV.setTextColor(ContextCompat.getColor(this, R.color.btn_blue));
                visaTV.setTextColor(ContextCompat.getColor(this, R.color.btn_blue));
                pointTV.setTextColor(ContextCompat.getColor(this, R.color.white));
                paytabs.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways2));
                cash.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways));
                break;
            case 1:
                visa.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways));
                cashTV.setTextColor(ContextCompat.getColor(this, R.color.white));
                visaTV.setTextColor(ContextCompat.getColor(this, R.color.btn_blue));
                pointTV.setTextColor(ContextCompat.getColor(this, R.color.btn_blue));
                paytabs.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways));
                cash.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_pay_ways2));
                break;
        }
    }
}
