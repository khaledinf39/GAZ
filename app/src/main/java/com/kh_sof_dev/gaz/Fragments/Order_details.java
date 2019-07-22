package com.kh_sof_dev.gaz.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Adapters.OrderDetails_prod_adapter;
import com.kh_sof_dev.gaz.Adapters.Order_adapter;
import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.Order_getter;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.show_order;
import com.kh_sof_dev.gaz.R;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


@SuppressLint("ValidFragment")
public class Order_details extends Fragment {

    private ImageView back_btn;
    private TextView req_heqder, req_id_tv, req_type_tv, req_state_tv, req_date_tv, username_tv, userNumber_tv, userAddress_tv, notes_tv, payWay_tv, deliver_time_tv, deliver_date_tv, subTotal_tv, delivery_cost_tv, TotalCost_tv, cancelReq_tv;
    private Button sellDone_btn;
    public static Map<String, Object> DetailsDataMap = new HashMap<>();
    private RecyclerView productList_RV;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;
    String TAG = "ReqDetailsCurrent";
    private Dialog cancelReqDialog;

    private String ReqID;

    Order_getter reqDetail = null;

    public Order_details() {

    }

    public Order_details(Order_getter reqDetail) {
        // Required empty public constructor
        this.reqDetail = reqDetail;
    }

    public Order_details(String reqid, Context mcoxt) {
        // Required empty public constructor
        Http_orders http_orders = new Http_orders();
        http_orders.Getdetails_Order(mcoxt, reqid, new Http_orders.OnOrder_geter_lisennter() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(show_order order) {
                if (order.getStatusCode() == 200 && order.getItems().size() != 0) {
                    reqDetail = order.getItems().get(0);
                    Request_Get_Details(mView);
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View mView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.layout_f_order_details, container, false);
        mView = view;
        back_btn = view.findViewById(R.id.back_btn);
        cancelReq_tv = view.findViewById(R.id.cancelReq_tv);
        progressBar = view.findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);
        sellDone_btn = view.findViewById(R.id.sellDone_btn);

        SharedPreferences sp1 = getContext().getSharedPreferences("ReqDetailsCurrent", MODE_PRIVATE);
        ReqID = sp1.getString("ReqID", null);

        // httpRequest = new HttpRequest_GET();
        if (reqDetail != null) {
            Request_Get_Details(view);
        }
        //TODO -********************Adapter Products******************

        productList_RV = view.findViewById(R.id.productList_RV);
        mLayoutManager = new LinearLayoutManager(getContext());
        productList_RV.setHasFixedSize(true);
        productList_RV.setLayoutManager(mLayoutManager);


        /************************Actions**************************/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
        /******************************************************/
        return view;
    }

    public void showPopUp() {
        cancelReqDialog = new Dialog(getActivity());
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
                if (!cancelReq_et.getText().toString().trim().equals("")) {
                    cancelReqDialog.dismiss();
                    Http_orders request_post = new Http_orders();
                    request_post.Post_updateOrder(getActivity(), reqDetail.getId(), 5, new Http_orders.OnOrder_geter_lisennter() {
                        @Override
                        public void onSuccess(show_order order) {
                            Toast.makeText(getContext(), "تم الغاء طلبك ", Toast.LENGTH_LONG).show();
                            cancelReq_tv.setVisibility(View.GONE);
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {
                            cancelReq_tv.setVisibility(View.GONE);
//                            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Request_Get_Details(final View view) {
        req_heqder = view.findViewById(R.id.req_header_id_tv);
        req_id_tv = view.findViewById(R.id.req_id_tv);
        req_state_tv = view.findViewById(R.id.req_state_tv);
        req_date_tv = view.findViewById(R.id.req_date_tv);
        username_tv = view.findViewById(R.id.username_tv);
        userNumber_tv = view.findViewById(R.id.userNumber_tv);
        userAddress_tv = view.findViewById(R.id.userAddress_tv);
        notes_tv = view.findViewById(R.id.notes_tv);
        payWay_tv = view.findViewById(R.id.payWay_tv);
        deliver_time_tv = view.findViewById(R.id.deliver_time_tv);
        deliver_date_tv = view.findViewById(R.id.deliver_date_tv);
        subTotal_tv = view.findViewById(R.id.subTotal_tv);
        delivery_cost_tv = view.findViewById(R.id.delivery_cost_tv);
        TotalCost_tv = view.findViewById(R.id.TotalCost_tv);
        req_type_tv = view.findViewById(R.id.req_typ_tv);
        productList_RV = view.findViewById(R.id.productList_RV);
        /************************************/
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
        productList_RV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        productList_RV.setAdapter(new OrderDetails_prod_adapter(getContext(), reqDetail.getItems()));

        payWay_tv.setText(payType_st);
        try {
            deliver_time_tv.setText(Order_adapter.getdate(reqDetail.getDelivery_date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        deliver_date_tv.setText(reqDetail.getDelivery_time());
        String subTotal_st = String.format("%.2f", reqDetail.getSubTotal()) + "";
        subTotal_tv.setText(subTotal_st);
        String deliveryCost_st = String.format("%.2f", reqDetail.getDeliveryCost()) + "";
        delivery_cost_tv.setText(deliveryCost_st);
        String totalCost_st = String.format("%.2f", reqDetail.getTotal()) + "";
        TotalCost_tv.setText(totalCost_st);
    }

    int nb_start = 0;

    private void popup_rate() {
        final Dialog dialog = new Dialog(getActivity());
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

                post.Post_updateOrder(getContext(), reqDetail.getId(), 4, new Http_orders.OnOrder_geter_lisennter() {
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