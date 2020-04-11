package com.kh_sof_dev.gaz.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Adapters.OrderDetails_prod_adapter;
import com.kh_sof_dev.gaz.Adapters.Order_adapter;
import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.Order_getter;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.show_order;
import com.kh_sof_dev.gaz.R;

import java.util.Locale;

public class OrderDetails extends AppCompatActivity {
    public static final String REQUEST_DETAILS = "request_details";
    public static final String REQUEST_ID = "request_id";
    private TextView cancelReq_tv;
    private Button sellDone_btn;
    private RecyclerView productList_RV;
    private Dialog cancelReqDialog;

    Order_getter reqDetail = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_order_details);
        ImageView back_btn = findViewById(R.id.back_btn);
        cancelReq_tv = findViewById(R.id.cancelReq_tv);
//        ProgressBar progressBar = findViewById(R.id.progressBar);
        sellDone_btn = findViewById(R.id.sellDone_btn);

//        SharedPreferences sp1 = getSharedPreferences("ReqDetailsCurrent", MODE_PRIVATE);
//        String reqID = sp1.getString("ReqID", null);

        // httpRequest = new HttpRequest_GET();
        if (getIntent().hasExtra(REQUEST_DETAILS)) {
            reqDetail = (Order_getter) getIntent().getSerializableExtra(REQUEST_DETAILS);
            if (reqDetail != null)
                Request_Get_Details();
        }else if (getIntent().hasExtra(REQUEST_ID)){
            String reqid = getIntent().getStringExtra(REQUEST_ID);
            Http_orders http_orders = new Http_orders();
            http_orders.Getdetails_Order(this, reqid, new Http_orders.OnOrder_geter_lisennter() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onSuccess(show_order order) {
                    if (order.getStatusCode() == 200 && order.getItems().size() != 0) {
                        reqDetail = order.getItems().get(0);
                        Request_Get_Details();
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

        productList_RV = findViewById(R.id.productList_RV);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        productList_RV.setHasFixedSize(true);
        productList_RV.setLayoutManager(mLayoutManager);


        //************************Actions**************************/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                MainNew.goto_(new MyReservations(),getContext());
            }
        });

        cancelReq_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopUp();
            }
        });
        sellDone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_rate();
            }
        });
        //******************************************************/
    }

    public void showPopUp() {
        cancelReqDialog = new Dialog(this);
        cancelReqDialog.setContentView(R.layout.popup_cancel_req);
        cancelReqDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelReqDialog.show();
        final EditText cancelReq_et = cancelReqDialog.findViewById(R.id.cancelReq_et);
        final Button cancelReq_btn = cancelReqDialog.findViewById(R.id.cancelReq_btn);
        Button back_btn = cancelReqDialog.findViewById(R.id.back_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelReqDialog.dismiss();
            }
        });

        cancelReq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reqDetail != null && !cancelReq_et.getText().toString().trim().equals("")) {
                    Http_orders request_post = new Http_orders();
                    request_post.Post_updateOrder(OrderDetails.this, reqDetail.getId(), 5, new Http_orders.OnOrder_geter_lisennter() {
                        @Override
                        public void onSuccess(show_order order) {
                            Toast.makeText(OrderDetails.this, "تم الغاء طلبك ", Toast.LENGTH_LONG).show();
                            cancelReq_tv.setVisibility(View.GONE);
                            cancelReqDialog.dismiss();
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {
                            cancelReq_tv.setVisibility(View.GONE);
//                            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                            cancelReqDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private void Request_Get_Details() {
        TextView req_heqder = findViewById(R.id.req_header_id_tv);
        TextView req_id_tv = findViewById(R.id.req_id_tv);
        TextView req_state_tv = findViewById(R.id.req_state_tv);
        TextView req_date_tv = findViewById(R.id.req_date_tv);
        TextView username_tv = findViewById(R.id.username_tv);
        TextView userNumber_tv = findViewById(R.id.userNumber_tv);
        TextView userAddress_tv = findViewById(R.id.userAddress_tv);
        TextView notes_tv = findViewById(R.id.notes_tv);
        TextView payWay_tv = findViewById(R.id.payWay_tv);
        TextView deliver_time_tv = findViewById(R.id.deliver_time_tv);
        TextView deliver_date_tv = findViewById(R.id.deliver_date_tv);
        TextView subTotal_tv = findViewById(R.id.subTotal_tv);
        TextView delivery_cost_tv = findViewById(R.id.delivery_cost_tv);
        TextView totalCost_tv = findViewById(R.id.TotalCost_tv);
        TextView req_type_tv = findViewById(R.id.req_typ_tv);
        productList_RV = findViewById(R.id.productList_RV);
        //************************************/
        if (reqDetail.getStatusId() == 3) {
            sellDone_btn.setVisibility(View.VISIBLE);
        }
        if (reqDetail.getStatusId() == 5 || reqDetail.getStatusId() == 6 || reqDetail.getStatusId() == 3
                || reqDetail.getStatusId() == 4 || reqDetail.getStatusId() == 2) {
            cancelReq_tv.setVisibility(View.GONE);
        }
        req_id_tv.setText(reqDetail.getId());
        req_heqder.setText(reqDetail.getId());
//                    _id =reqDetail.get_id();
        String state_st = "";
        switch (reqDetail.getStatusId()) {
            case 1:
                state_st = "بانتظار استلام السائق";
                break;
            case 2:
                state_st = "جاري التوصيل";
                break;
            case 3:
                state_st = "تم الوصيل";
                break;
            case 4:
                state_st = " مكتمل";
                break;
            case 6:
                state_st = "ملغي من قبل السائق";
                break;
            case 5:
                state_st = "ملغي من قبل العميل";
                break;
            default:
                break;
        }
        req_state_tv.setText(state_st);
        req_date_tv.setText(Order_adapter.getdate(reqDetail.getCreateAt()));
        username_tv.setText(reqDetail.getUserId().getFullName());
        userNumber_tv.setText(reqDetail.getUserId().getPhoneNumber());
        userAddress_tv.setText(reqDetail.getAddressDetails());
        notes_tv.setText(reqDetail.getNotes());
        String payType_st = "";
        switch (reqDetail.getPaymentType()) {
            case 1:
                payType_st = "كاش";
                break;
            case 2:
                payType_st = "بطاقة";
                break;
            case 3:
                payType_st = "نقاطي";
                break;
            default:
                break;
        }
        String orderType = "";
        switch (reqDetail.getOrderType()) {
            case 1:
                orderType = "طلب منتج";
                break;
            case 3:
                orderType = "تعبئة خزان";
                break;
            case 2:
                orderType = "اعادة تعبئة اسطوانة";
                break;
            case 4:
                orderType = "شراء مستلزمات غاز";
                break;
            default:
                break;
        }
        req_type_tv.setText(orderType);
        productList_RV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        productList_RV.setAdapter(new OrderDetails_prod_adapter(this, reqDetail.getItems()));

        payWay_tv.setText(payType_st);
        try {
            deliver_time_tv.setText(Order_adapter.getdate(reqDetail.getDelivery_date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        deliver_date_tv.setText(reqDetail.getDelivery_time());
        String subTotal_st = String.format(Locale.ENGLISH, "%.2f", reqDetail.getSubTotal());
        subTotal_tv.setText(subTotal_st);
        String deliveryCost_st = String.format(Locale.ENGLISH, "%.2f", reqDetail.getDeliveryCost());
        delivery_cost_tv.setText(deliveryCost_st);
        String totalCost_st = String.format(Locale.ENGLISH, "%.2f", reqDetail.getTotal());
        totalCost_tv.setText(totalCost_st);
    }

    int nb_start = 0;

    private void popup_rate() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_rate_req);
        final TextView comment = dialog.findViewById(R.id.comment);
        dialog.show();
        Button send_rate = dialog.findViewById(R.id.send_rates_btn);


        final ImageView start1 = dialog.findViewById(R.id.star1_img);
        final ImageView start2 = dialog.findViewById(R.id.star2_img);
        final ImageView start3 = dialog.findViewById(R.id.star3_img);
        final ImageView start4 = dialog.findViewById(R.id.star4_img);
        final ImageView start5 = dialog.findViewById(R.id.star5_img);
        start1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nb_start = rate(start1, start2, start3, start4, start5, start1);
            }
        });
        start5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nb_start = rate(start1, start2, start3, start4, start5, start5);
            }
        });
        start2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nb_start = rate(start1, start2, start3, start4, start5, start2);
            }
        });
        start3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nb_start = rate(start1, start2, start3, start4, start5, start3);
            }
        });
        start4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nb_start = rate(start1, start2, start3, start4, start5, start4);
            }
        });

        send_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment_txt = comment.getText().toString();
                if (comment_txt.isEmpty() || nb_start == 0) {
                    comment.setError("نرجو إدخال نص التقيم وإختار عدد النجوم ");
                    return;
                }
                Http_orders post = new Http_orders();
                post.Post_PostADD_rat(getApplicationContext(), reqDetail.getId()
                        , comment_txt, nb_start, new Http_orders.OnOrder_geter_lisennter() {
                            @Override
                            public void onSuccess(show_order order) {
//                                Toast.makeText(getApplicationContext(),order.getMessage(),Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFailure(String msg) {
//                                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }

                        });

                post.Post_updateOrder(OrderDetails.this, reqDetail.getId(), 4, new Http_orders.OnOrder_geter_lisennter() {
                    @Override
                    public void onSuccess(show_order order) {
//                                   Toast.makeText(getContext(),order.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {
//                            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    private int rate(ImageView start1, ImageView start2, ImageView start3, ImageView start4,
                     ImageView start5, ImageView start_sel) {
        int start_on = R.drawable.start_on;
        int start_off = R.drawable.icon_star;
        switch (start_sel.getId()) {
            case R.id.star1_img:
                nb_start = 1;
                start1.setImageResource(start_on);
                start2.setImageResource(start_off);
                start3.setImageResource(start_off);
                start4.setImageResource(start_off);
                start5.setImageResource(start_off);
                break;
            case R.id.star2_img:
                nb_start = 2;
                start1.setImageResource(start_on);
                start2.setImageResource(start_on);
                start3.setImageResource(start_off);
                start4.setImageResource(start_off);
                start5.setImageResource(start_off);
                break;
            case R.id.star3_img:
                nb_start = 3;
                start1.setImageResource(start_on);
                start2.setImageResource(start_on);
                start3.setImageResource(start_on);
                start4.setImageResource(start_off);
                start5.setImageResource(start_off);
                break;
            case R.id.star4_img:
                nb_start = 4;
                start1.setImageResource(start_on);
                start2.setImageResource(start_on);
                start3.setImageResource(start_on);
                start4.setImageResource(start_on);
                start5.setImageResource(start_off);
                break;
            case R.id.star5_img:
                nb_start = 5;
                start1.setImageResource(start_on);
                start2.setImageResource(start_on);
                start3.setImageResource(start_on);
                start4.setImageResource(start_on);
                start5.setImageResource(start_on);
                break;
        }
        return nb_start;
    }


}