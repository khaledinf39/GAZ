package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Adapters.BasketReserv_adapter;
import com.kh_sof_dev.gaz.Classes.Database.OrderDetails;
import com.kh_sof_dev.gaz.Classes.Order.AddOrder;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.OrderItem;
import com.kh_sof_dev.gaz.Classes.Order.coupon.test_coupon;
import com.kh_sof_dev.gaz.Classes.Order.send_order;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.Fragments.Refill_frg.RefillHome;
import com.kh_sof_dev.gaz.MyApplication;
import com.kh_sof_dev.gaz.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ConfReservation1 extends AppCompatActivity implements View.OnClickListener {
    public static final String payment_type_s = "payment_type";
    public static final String note_s = "note";
    private int payment_type, order_type;

    private TextView deliveryTV, taxtTV, total, nameTV, addressTv, priceTV;
    private RecyclerView orderRV;
    private Button continue_btn;

    /******************************Coupon*******************************/
    LinearLayout lay_coupon;
    Button application_btn;
    TextView coupone_msg;
    EditText coupon_edt;
    AVLoadingIndicatorView avl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_conf_reservation1);

        payment_type = getIntent().getIntExtra(payment_type_s, 0);
        order_type = getIntent().getIntExtra(Refill.order_type_s, 0);

        lay_coupon = findViewById(R.id.copon_lay);
        application_btn = findViewById(R.id.application_btn);
        coupon_edt = findViewById(R.id.enter_cobon_et);
        coupone_msg = findViewById(R.id.copoun_msg);
        avl = findViewById(R.id.avi);
        if (payment_type != 1) {
            lay_coupon.setVisibility(View.GONE);
        }
        application_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_coupon();
            }
        });


        ImageView back = findViewById(R.id.back_btn);
        ImageView edite_inf = findViewById(R.id.Edit_icon1);
        ImageView edit_pay = findViewById(R.id.Edit_icon);
        ImageView edit_car = findViewById(R.id.Edit_icon2);
        deliveryTV = findViewById(R.id.tax_tv);
        total = findViewById(R.id.totalPrice_tv);
        nameTV = findViewById(R.id.Name_tv);
        priceTV = findViewById(R.id.price_tv);
        addressTv = findViewById(R.id.Address_tv);
        taxtTV = findViewById(R.id.delivery_tv);
        continue_btn = findViewById(R.id.continue_btn);
        back.setOnClickListener(this);
        edit_car.setOnClickListener(this);
        edit_pay.setOnClickListener(this);
        edite_inf.setOnClickListener(this);
        orderRV = findViewById(R.id.orderRV);
        orderRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        DBManager dbManager = new DBManager(this);
//        dbManager.open();
        // orderRV.setAdapter(new BasketReserv_adapter(getContext(),dbManager.fetch_order()));
//        dbManager.close();

        fetch_order();

        if (order_type == 3)
            edit_car.setVisibility(View.GONE);
    }

    private String coupon = null;

    private void test_coupon() {
        avl.setVisibility(View.VISIBLE);
        application_btn.setVisibility(View.GONE);
        Http_orders http_orders = new Http_orders();
        http_orders.Post_test_coupon(this, coupon_edt.getText().toString(), new Http_orders.OnCoupon_lisennter() {
            @Override
            public void onSuccess(test_coupon test_coupon) {
                avl.setVisibility(View.GONE);
                application_btn.setVisibility(View.VISIBLE);
                coupone_msg.setText(test_coupon.getMessage());
                if (test_coupon.getStatusCode() == 200) {

                    double discount = test_coupon.getItems().getDiscount();
                    calculate(price_ /*- (price_ * discount)*/, discount);
                    coupon = coupon_edt.getText().toString();

                    coupone_msg.setText(String.valueOf(test_coupon.getMessage() + " لديك خصم : " + discount * 100 + "%"));
                }

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    double price_ = 0.0;
    double delivry_ = 0.0;
    double delivryBase = 0.0;
    double total_ = 0.0;
    double taxe = 0.0;

    private void calculate(double price_, double discount) {
        final Setting setting = new Setting(this);
        double deliveryPrice;
        if (order_type != 3)
            deliveryPrice = setting.getDelivery();
        else
            deliveryPrice = setting.getDeliveryTank();
        priceTV.setText(String.valueOf(price_));
        delivryBase = qty * deliveryPrice;
        if (discount > 0)
            delivry_ = delivryBase - (delivryBase * discount);
        else
            delivry_ = delivryBase;
        taxtTV.setText(String.valueOf(delivry_));
        taxe = Double.valueOf((setting.getTax())) * price_ * 0.01;
        deliveryTV.setText(String.valueOf(taxe));
        total_ = price_ + delivry_ + taxe;
        total.setText(String.valueOf(total_));
        Log.e("setting gaz ", "tax" + taxe + " delivryBase " + delivryBase + " delev " + delivry_ + "qty" + qty);
    }

    int qty = 0;

    private void fetch_order() {
        final Realm realm = Realm.getDefaultInstance();
        RealmResults<com.kh_sof_dev.gaz.Classes.Database.OrderDetails> orderDetailsList = realm.where(com.kh_sof_dev.gaz.Classes.Database.OrderDetails.class).findAll();
        final List<Product> products = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            Product p = new Product();
            p.setID_((int) orderDetails.getId());
            p.setId(orderDetails.getProductId());
            p.setName(orderDetails.getProductName());
            p.setQty(orderDetails.getQuantity());
            p.setPrice(orderDetails.getPrice());
            p.setImage(orderDetails.getImage());
            products.add(p);
//            price_ = price_ + p.getPrice() * p.getQty();
//            qty = qty + p.getQty();
            Log.e("setting gaz ", "price_" + price_ + " qty " + qty);
        }

//        final DBManager db = new DBManager(this);
//        db.open();
//        final List<Product> products = db.fetch_order();

        if (order_type == 1 || order_type == 3) {
            for (Product p : products) {
                price_ = price_ + p.getPrice() * p.getQty();
                qty = qty + p.getQty();
            }
            // orderRV.setVisibility(View.GONE);
            orderRV.setAdapter(new BasketReserv_adapter(this, products));
        } else {
            for (Product p : RefillHome.productList) {
                price_ = price_ + p.getPrice() * p.getQty();
                qty = qty + p.getQty();
            }
            orderRV.setAdapter(new BasketReserv_adapter(this, RefillHome.productList));
        }

        calculate(price_, 0);

        final user_info user_info = new user_info(this);
        nameTV.setText(user_info.getName());
        addressTv.setText(Shipping.locationAdd);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddOrder order = new AddOrder();

                order.setSuppiler_id(user_info.getSupplier_id(ConfReservation1.this));
                order.setCoupon(coupon);

                order.setAddressDetails(Shipping.locationAdd);
                order.setPaymentType(payment_type);
                if (Shipping.mLatLng != null) {
                    order.setLat(Shipping.mLatLng.latitude);
                    order.setLng(Shipping.mLatLng.longitude);
                    Log.d("location", Shipping.mLatLng.latitude + "  ........" + Shipping.mLatLng.longitude);
                }
                //**************************just for test ******************************/
//                order.setLat(31.5139203);
//                order.setLng(34.4398477);
                //*****************************************************************************/
                order.setSubTotal(price_ + taxe);
                order.setOrderType(order_type);
                order.setDeliveryCost(delivryBase);                     // parameter total 11 - time and date =9
                List<OrderItem> orderItems = new ArrayList<>();
                /////9 parameters
                JSONObject cart;
                if (order_type == 1) {

                    order.setNotes(Car.notes_);
                    for (Product p : products
                    ) {
                        orderItems.add(new OrderItem(p));
                    }
                    order.setItems(orderItems);
                    cart = order.Basket_toJsonObject();
                } else {
                    order.setDelivery_date(RefillHome.date);
                    order.setDelivery_time(RefillHome.time);
                    order.setNotes(getIntent().getStringExtra(note_s));


                    for (Product p : RefillHome.productList
                    ) {
                        orderItems.add(new OrderItem(p));
                    }

                    order.setItems(orderItems);
                    cart = order.Refill_toJsonObject();
                }
                Http_orders http_orders = new Http_orders();

                http_orders.Post_send_Order(ConfReservation1.this, cart, new Http_orders.OnOrder_lisennter() {
                    @Override
                    public void onSuccess(send_order order) {

//                            Toast.makeText(getContext(),order.getMessage(),Toast.LENGTH_LONG).show();
                        if (order.getStatusCode() == 200) {
                            RealmResults<com.kh_sof_dev.gaz.Classes.Database.OrderDetails> orderDetailsList = realm.where(com.kh_sof_dev.gaz.Classes.Database.OrderDetails.class).findAll();
                            realm.beginTransaction();
                            orderDetailsList.deleteAllFromRealm();
                            realm.commitTransaction();
//                            realm.delete(OrderDetails.class);

//                            db.open();
//                            db.clear_db();
//                            db.close();


                            startActivity(new Intent(ConfReservation1.this, SendRequestSucc.class));
                            finish();
//                            startActivity(new Intent(getContext(), MainNew.class));
//                            MainNew.goto_(new SendRequestSucc(), getContext());
//                                MainNew._goto(ConfReservation1.this,new Home(),View.VISIBLE);
                        }
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(ConfReservation1.this, msg, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (order_type == 3) {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<com.kh_sof_dev.gaz.Classes.Database.OrderDetails> orderDetailsList = realm.where(com.kh_sof_dev.gaz.Classes.Database.OrderDetails.class).findAll();
            for (OrderDetails orderDetails : orderDetailsList) {
                if (orderDetails.getCategoryId().equals(MyApplication.PRODUCT_TANK_CATEGORY_ID)) {
                    realm.beginTransaction();
                    orderDetails.deleteFromRealm();
                    realm.commitTransaction();
                }
            }
            Intent intent = new Intent(this, MainNew.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else
            super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_btn:

                if (order_type == 3) {
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<com.kh_sof_dev.gaz.Classes.Database.OrderDetails> orderDetailsList = realm.where(com.kh_sof_dev.gaz.Classes.Database.OrderDetails.class).findAll();
                    for (OrderDetails orderDetails : orderDetailsList) {
                        if (orderDetails.getCategoryId().equals(MyApplication.PRODUCT_TANK_CATEGORY_ID)) {
                            realm.beginTransaction();
                            orderDetails.deleteFromRealm();
                            realm.commitTransaction();
                        }
                    }
                    Intent intent = new Intent(this, MainNew.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else
                    startActivity(new Intent(this, Car.class));

//                MainNew.goto_(new Car(), getContext());
                break;
            case R.id.Edit_icon:
                Intent intent1 = new Intent(this, Payment.class);
                intent1.putExtra(Payment.order_type, order_type);
                startActivity(intent1);
//                MainNew.goto_(new Payment(order_type), getContext());
                break;
            case R.id.Edit_icon1:
                Intent intent = new Intent(this, Shipping.class);
                intent.putExtra(Shipping.typ2_Order_1_test, 2);
                intent.putExtra(Shipping.order_type_s, order_type);
                startActivity(intent);
//                MainNew.goto_(new Shipping(2, order_type), getContext());
                break;
            case R.id.Edit_icon2:
                startActivity(new Intent(this, Car.class));
//                MainNew.goto_(new Car(), getContext());
                break;

        }
        finish();
    }
}
