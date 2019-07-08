package com.kh_sof_dev.gaz.Fragments;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.Fragments.Refill_frg.RefillHome;
import com.kh_sof_dev.gaz.R;

import java.util.List;


@SuppressLint("ValidFragment")
public class Payment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int order_typ;

    @SuppressLint("ValidFragment")
    public Payment(int order_typ) {
        // Required empty public constructor
        this.order_typ=order_typ;

    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
private LinearLayout visa,cash,paytabs;
    private ImageView back;
    private int  payment_typ;
    private  Double price_=0.0,total=0.0;
    private Button continue_btn;
    private TextView cashTV,visaTV,pointTV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_payment, container, false);
        paytabs=(LinearLayout) view.findViewById(R.id.point_lay);
        back=(ImageView)view.findViewById(R.id.back_btn);
        visa=(LinearLayout)view.findViewById(R.id.visa);
        cash=(LinearLayout)view.findViewById(R.id.cash);
        cashTV=(TextView)view.findViewById(R.id.cash_tv);

        visaTV=(TextView)view.findViewById(R.id.visa_tv);
        pointTV=(TextView)view.findViewById(R.id.point_tv);
        continue_btn=(Button)view.findViewById(R.id.continue_btn);
        paytabs.setOnClickListener(this);
        cash.setOnClickListener(this);
        visa.setOnClickListener(this);
        continue_btn.setOnClickListener(this);
        back.setOnClickListener(this);





        if (order_typ==1){
            DBManager db=new DBManager(getContext());
            db.open();
            final List<Product> products=db.fetch_order();
            for (Product p:products
            ) {
                price_=price_+p.getPrice()*p.getQty();
            }
        }
        if (order_typ!=1){
//            price_= RefillHome.price_refill*RefillHome.qty;
            for (Product p:RefillHome.productList
            ) {
                price_=price_+p.getPrice()*p.getQty();
            }
        }
        Setting setting=new Setting(getContext());
        Double delivry_=Double.valueOf((setting.getDelivery()))*price_*0.01;
        Double taxe=Double.valueOf((setting.getTax()))*price_*0.01;
        total=price_+delivry_+taxe;
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.back_btn:
                MainNew.goto_(new Car(),getContext() );
                break;
            case R.id.continue_btn:

                MainNew.goto_(new ConfReservation1(payment_typ,order_typ),getContext() );
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
                payment_typ=1;
                change_back_pay(payment_typ);
                break;
        }
    }

    private void checkPoint() {
        user_info user_info=new user_info(getContext());
        if (!(user_info.getWallet()<total)){
            payment_typ = 3;
            change_back_pay(payment_typ);
        }else {
            Toast.makeText(getContext(), "نقاطك لا تكفي لشراء المنتج",Toast.LENGTH_LONG).show();

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void change_back_pay(int payment_typ) {

        switch (payment_typ){
            case 2:
                visa.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways2));
visaTV.setTextColor(getContext().getColor(R.color.white));
                cashTV.setTextColor(getContext().getColor(R.color.btn_blue));
                pointTV.setTextColor(getContext().getColor(R.color.btn_blue));
                paytabs.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways));
                cash.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways));
                break;
            case 3:
                visa.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways));
                cashTV.setTextColor(getContext().getColor(R.color.btn_blue));
                visaTV.setTextColor(getContext().getColor(R.color.btn_blue));
                pointTV.setTextColor(getContext().getColor(R.color.white));
                paytabs.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways2));
                cash.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways));
                break;
            case 1:
                visa.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways));
                cashTV.setTextColor(getContext().getColor(R.color.white));
                visaTV.setTextColor(getContext().getColor(R.color.btn_blue));
                pointTV.setTextColor(getContext().getColor(R.color.btn_blue));
                paytabs.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways));
                cash.setBackground(getContext().getDrawable(R.drawable.bg_pay_ways2));
                break;
        }
    }
}
