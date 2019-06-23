package com.kh_sof_dev.gaz.Fragments;


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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Activities.MainNew;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@SuppressLint("ValidFragment")
public class ConfReservation1 extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int payment_type,order_type;

    @SuppressLint("ValidFragment")

    public ConfReservation1(int payment_typ, int order_type) {
        // Required empty public constructor
        this.payment_type=payment_typ;
        this.order_type=order_type;

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
private ImageView back,edite_inf,edit_pay,edit_car;
    private TextView deliveryTV,taxtTV,total,nameTV,addressTv,priceTV;
    private RecyclerView orderRV;
    private Button continue_btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_conf_reservation1, container, false);
        back=(ImageView)view.findViewById(R.id.back_btn);
        edite_inf=(ImageView)view.findViewById(R.id.Edit_icon1);
        edit_pay=(ImageView)view.findViewById(R.id.Edit_icon);
        edit_car=(ImageView)view.findViewById(R.id.Edit_icon2);
        deliveryTV=(TextView)view.findViewById(R.id.tax_tv);
        total=(TextView)view.findViewById(R.id.totalPrice_tv);
        nameTV=(TextView)view.findViewById(R.id.Name_tv);
        priceTV=(TextView)view.findViewById(R.id.price_tv);
        addressTv=(TextView)view.findViewById(R.id.Address_tv);
        taxtTV=(TextView)view.findViewById(R.id.delivery_tv);
        continue_btn=(Button)view.findViewById(R.id.continue_btn);
        back.setOnClickListener(this);
        edit_car.setOnClickListener(this);
        edit_pay.setOnClickListener(this);
        edite_inf.setOnClickListener(this);
        orderRV=(RecyclerView)view.findViewById(R.id.orderRV);
        orderRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        DBManager dbManager=new DBManager(getContext());
        dbManager.open();
       // orderRV.setAdapter(new BasketReserv_adapter(getContext(),dbManager.fetch_order()));
        dbManager.close();

        fetch_order();
        return view;
    }
    Double price_ = 0.0;
    Double delivry_= 0.0;
    Double total_=0.0;
    Double taxe=0.0;

    private void calculate(Double price_) {
        final Setting setting=new Setting(getContext());
priceTV.setText(price_.toString());
        delivry_=qty*Double.valueOf((setting.getDelivery()));
        taxtTV.setText(delivry_.toString());
        taxe=Double.valueOf((setting.getTax()))*price_*0.01;
        deliveryTV.setText(taxe.toString());
        total_=price_+delivry_+taxe;
        total.setText(total_.toString());
        Log.d("setting gaz ","tax"+taxe+" delev "+delivry_+"qty"+qty);
    }
    int qty=0;
    private void fetch_order() {


        final DBManager db=new DBManager(getContext());
        db.open();
        final List<Product> products=db.fetch_order();

        if (order_type==1){
            for (Product p:products
            ) {
                price_=price_+p.getPrice()*p.getQty();
                qty=qty+p.getQty();
            }
           // orderRV.setVisibility(View.GONE);
            orderRV.setAdapter(new BasketReserv_adapter(getContext(),products));
        }
if (order_type!=1){


    for (Product p:RefillHome.productList
    ) {
        price_=price_+p.getPrice()*p.getQty();
        qty=qty+p.getQty();
    }

    orderRV.setAdapter(new BasketReserv_adapter(getContext(),RefillHome.productList));
   }

        calculate(price_);

        final user_info user_info=new user_info(getContext());
        nameTV.setText(user_info.getName());
        addressTv.setText(Shipping.locationAdd);

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddOrder order =new AddOrder();

                order.setAddressDetails(Shipping.locationAdd);
                order.setPaymentType(payment_type);
                if (Shipping.mLatLng!=null) {
                    order.setLat(Shipping.mLatLng.latitude);
                    order.setLng(Shipping.mLatLng.longitude);
                    Log.d("location",Shipping.mLatLng.latitude+"  ........"+Shipping.mLatLng.longitude);
                }
                /**************************just for test ******************************/
//                order.setLat(31.5139203);
//                order.setLng(34.4398477);
                /*****************************************************************************/
                order.setSubTotal(price_+taxe);
                order.setOrderType(order_type);
                order.setDeliveryCost(delivry_);                     // parameter total 11 - time and date =9
                List<OrderItem> orderItems=new ArrayList<>();
                /////9 parameters
                JSONObject cart;
                if (order_type==1) {

                    order.setNotes(Car.notes_);
                    for (Product p:products
                    ) {
                        orderItems.add(new OrderItem(p));
                    }
                    order.setItems(orderItems);
                     cart = order.Basket_toJsonObject();
                }else {
                    order.setDelivery_date(RefillHome.date);
                    order.setDelivery_time(RefillHome.time);
                    order.setNotes(ConfRefill.notes_);


                    for (Product p:RefillHome.productList
                    ) {
                        orderItems.add(new OrderItem(p));
                    }

                    order.setItems(orderItems);
                     cart = order.Refill_toJsonObject();
                }
                Http_orders http_orders=new Http_orders();

                    http_orders.Post_send_Order(getContext(), cart, new Http_orders.OnOrder_lisennter() {
                        @Override
                        public void onSuccess(send_order order) {

//                            Toast.makeText(getContext(),order.getMessage(),Toast.LENGTH_LONG).show();
                            if (order.getStatusCode()==200){
                                DBManager dbManager=new DBManager(getContext());
                                db.open();
                                db.clear_db();
                                db.close();


                                startActivity(new Intent(getContext(),MainNew.class));
                                MainNew.goto_(new Send_request_Succ(),getContext()
                                );
//                                MainNew._goto(ConfReservation1.this,new Home(),View.VISIBLE);
                            }
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {
                            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                        }
                    });

            }
        });
    }
    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.back_btn:
                MainNew.goto_(new Car(),getContext());
                break;
            case R.id.Edit_icon:
                MainNew.goto_(new Payment(order_type),getContext());
                break;
            case R.id.Edit_icon1:
                MainNew.goto_(new Shipping(2,order_type),getContext());
                break;
            case R.id.Edit_icon2:
                MainNew.goto_(new Car(),getContext());
                break;

        }

    }
}
