package com.kh_sof_dev.gaz.Fragments.Reservations;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Classes.Order.coupon.test_coupon;
import com.kh_sof_dev.gaz.Fragments.Car;
import com.kh_sof_dev.gaz.Fragments.Shipping;
import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.BasketReserv_adapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Order.AddOrder;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.OrderItem;
import com.kh_sof_dev.gaz.Classes.Order.send_order;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.Fragments.Refill_frg.ConfRefill;
import com.kh_sof_dev.gaz.Fragments.Refill_frg.RefillHome;
import com.kh_sof_dev.gaz.R;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class ConfReservation1 extends Fragment implements View.OnClickListener {

    private int payment_type, order_type;

    @SuppressLint("ValidFragment")
    public ConfReservation1(int payment_typ, int order_type) {
        this.payment_type = payment_typ;
        this.order_type = order_type;

    }

    public ConfReservation1() {

    }

    private ImageView back, edite_inf, edit_pay, edit_car;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_conf_reservation1, container, false);

        lay_coupon=view.findViewById(R.id.copon_lay);
        application_btn=view.findViewById(R.id.application_btn);
        coupon_edt=view.findViewById(R.id.enter_cobon_et);
        coupone_msg=view.findViewById(R.id.copoun_msg);
        avl=view.findViewById(R.id.avi);
        if (payment_type!=1){
            lay_coupon.setVisibility(View.GONE);
        }
        application_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               test_coupon();
            }
        });


        back = (ImageView) view.findViewById(R.id.back_btn);
        edite_inf = (ImageView) view.findViewById(R.id.Edit_icon1);
        edit_pay = (ImageView) view.findViewById(R.id.Edit_icon);
        edit_car = (ImageView) view.findViewById(R.id.Edit_icon2);
        deliveryTV = (TextView) view.findViewById(R.id.tax_tv);
        total = (TextView) view.findViewById(R.id.totalPrice_tv);
        nameTV = (TextView) view.findViewById(R.id.Name_tv);
        priceTV = (TextView) view.findViewById(R.id.price_tv);
        addressTv = (TextView) view.findViewById(R.id.Address_tv);
        taxtTV = (TextView) view.findViewById(R.id.delivery_tv);
        continue_btn = (Button) view.findViewById(R.id.continue_btn);
        back.setOnClickListener(this);
        edit_car.setOnClickListener(this);
        edit_pay.setOnClickListener(this);
        edite_inf.setOnClickListener(this);
        orderRV = (RecyclerView) view.findViewById(R.id.orderRV);
        orderRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        DBManager dbManager = new DBManager(getContext());
        dbManager.open();
        // orderRV.setAdapter(new BasketReserv_adapter(getContext(),dbManager.fetch_order()));
        dbManager.close();

        fetch_order();
        return view;
    }
private String coupon=null;
    private void test_coupon() {
        avl.setVisibility(View.VISIBLE);
        application_btn.setVisibility(View.GONE);
        Http_orders http_orders =new Http_orders();
        http_orders.Post_test_coupon(getContext(), coupon_edt.getText().toString(), new Http_orders.OnCoupon_lisennter() {
            @Override
            public void onSuccess(test_coupon test_coupon) {
                avl.setVisibility(View.GONE);
                application_btn.setVisibility(View.VISIBLE);
                coupone_msg.setText(test_coupon.getMessage());
                if (test_coupon.getStatusCode()==200){

                    double discount=test_coupon.getItems().getDiscount();
               calculate(price_-(price_*discount));
               coupon=coupon_edt.getText().toString();

               coupone_msg.setText(test_coupon.getMessage()+" لديك خصم : "+discount*100+"%");
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

    Double price_ = 0.0;
    Double delivry_ = 0.0;
    Double total_ = 0.0;
    Double taxe = 0.0;

    private void calculate(Double price_) {
        final Setting setting = new Setting(getContext());
        priceTV.setText(price_.toString());
        delivry_ = qty * Double.valueOf((setting.getDelivery()));
        taxtTV.setText(delivry_.toString());
        taxe = Double.valueOf((setting.getTax())) * price_ * 0.01;
        deliveryTV.setText(taxe.toString());
        total_ = price_ + delivry_ + taxe;
        total.setText(total_.toString());
        Log.d("setting gaz ", "tax" + taxe + " delev " + delivry_ + "qty" + qty);
    }

    int qty = 0;

    private void fetch_order() {


        final DBManager db = new DBManager(getContext());
        db.open();
        final List<Product> products = db.fetch_order();

        if (order_type == 1) {
            for (Product p : products
            ) {
                price_ = price_ + p.getPrice() * p.getQty();
                qty = qty + p.getQty();
            }
            // orderRV.setVisibility(View.GONE);
            orderRV.setAdapter(new BasketReserv_adapter(getContext(), products));
        }
        if (order_type != 1) {


            for (Product p : RefillHome.productList
            ) {
                price_ = price_ + p.getPrice() * p.getQty();
                qty = qty + p.getQty();
            }

            orderRV.setAdapter(new BasketReserv_adapter(getContext(), RefillHome.productList));
        }

        calculate(price_);

        final user_info user_info = new user_info(getContext());
        nameTV.setText(user_info.getName());
        addressTv.setText(Shipping.locationAdd);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddOrder order = new AddOrder();

                order.setSuppiler_id(user_info.getSupplier_id(getActivity()));
                order.setCoupon(coupon);

                order.setAddressDetails(Shipping.locationAdd);
                order.setPaymentType(payment_type);
                if (Shipping.mLatLng != null) {
                    order.setLat(Shipping.mLatLng.latitude);
                    order.setLng(Shipping.mLatLng.longitude);
                    Log.d("location", Shipping.mLatLng.latitude + "  ........" + Shipping.mLatLng.longitude);
                }
                /**************************just for test ******************************/
//                order.setLat(31.5139203);
//                order.setLng(34.4398477);
                /*****************************************************************************/
                order.setSubTotal(price_ + taxe);
                order.setOrderType(order_type);
                order.setDeliveryCost(delivry_);                     // parameter total 11 - time and date =9
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
                    order.setNotes(ConfRefill.notes_);


                    for (Product p : RefillHome.productList
                    ) {
                        orderItems.add(new OrderItem(p));
                    }

                    order.setItems(orderItems);
                    cart = order.Refill_toJsonObject();
                }
                Http_orders http_orders = new Http_orders();

                http_orders.Post_send_Order(getContext(), cart, new Http_orders.OnOrder_lisennter() {
                    @Override
                    public void onSuccess(send_order order) {

//                            Toast.makeText(getContext(),order.getMessage(),Toast.LENGTH_LONG).show();
                        if (order.getStatusCode() == 200) {
                            DBManager dbManager = new DBManager(getContext());
                            db.open();
                            db.clear_db();
                            db.close();


                            startActivity(new Intent(getContext(), MainNew.class));
                            MainNew.goto_(new Send_request_Succ(), getContext()
                            );
//                                MainNew._goto(ConfReservation1.this,new Home(),View.VISIBLE);
                        }
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back_btn:
                MainNew.goto_(new Car(), getContext());
                break;
            case R.id.Edit_icon:
                MainNew.goto_(new Payment(order_type), getContext());
                break;
            case R.id.Edit_icon1:
                MainNew.goto_(new Shipping(2, order_type), getContext());
                break;
            case R.id.Edit_icon2:
                MainNew.goto_(new Car(), getContext());
                break;

        }

    }
}
