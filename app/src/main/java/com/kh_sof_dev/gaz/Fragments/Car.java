package com.kh_sof_dev.gaz.Fragments;


import android.app.Dialog;
import android.content.Intent;
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

import com.kh_sof_dev.gaz.Activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.Basket_adapter;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Car#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Car extends Fragment {

    public static String notes_="لا يوجد";

    public Car() {
        // Required empty public constructor
    }
private Button start,continue_btn;

    public static Car newInstance() {
        Car fragment = new Car();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
private TextView count,price,deleviry,total,notes,tax;
    private ImageView edite,back;
    private RecyclerView basketRV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.layout_f_car, container, false);
start=(Button)view.findViewById(R.id.start_btn);
        continue_btn=(Button)view.findViewById(R.id.start_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        notes=(TextView)view.findViewById(R.id.notes_tv);
count=(TextView)view.findViewById(R.id.order_count_tv);
start.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(getContext(),MainNew.class));
//        MainNew.goto_(Car.this,new Home(),View.VISIBLE);

    }
});

        price=(TextView)view.findViewById(R.id.price_tv);
        deleviry=(TextView)view.findViewById(R.id.tax_tv);

        tax=(TextView)view.findViewById(R.id.delivery_tv);
        total=(TextView)view.findViewById(R.id.total_tv);
        notes=(TextView)view.findViewById(R.id.notes_tv);
        back=(ImageView) view.findViewById(R.id.back_btn);
        edite=(ImageView) view.findViewById(R.id.edit_note_img);
        basketRV=(RecyclerView) view.findViewById(R.id.basketRV);
        continue_btn=(Button)view.findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNew.goto_(new Shipping(2,1),getContext());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),MainNew.class));
//                MainNew.goto_(new Home(),getContext());
            }
        });
        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EDitNotesPopup();
            }
        });
/*********************************order fetch***********************************************/
fetch_order();


/*****************************count order******************************************/
        DBManager db=new DBManager(getContext());
        db.open();
        Long count_order=db.get_order_count();
        db.close();
        count.setText(count_order.toString());
        if (count_order !=0){
            view. findViewById(R.id.empty_car).setVisibility(View.GONE);
            view. findViewById(R.id.car).setVisibility(View.VISIBLE);
        }else {
            view. findViewById(R.id.empty_car).setVisibility(View.VISIBLE);
            view. findViewById(R.id.car).setVisibility(View.GONE);
        }
/****************************************************************************/
        return view;
    }
    Double price_ = 0.0;
int qty=0;
    private void fetch_order() {


        final DBManager db=new DBManager(getContext());
        db.open();
        List<Product> products=db.fetch_order();

        for (Product p:products
             ) {
            price_+=p.getPrice()*p.getQty();
            qty=qty+p.getQty();
        }

       calculate(price_);
        basketRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        basketRV.setAdapter(new Basket_adapter(getContext(), products, new Basket_adapter.orderPrice_listenner() {
            @Override
            public void onSuccess(Double priceadd,int qty1) {
              price_=price_+priceadd;
              qty=qty1;
               calculate(price_);
               DBManager dbManager=new DBManager(getContext());
               db.open();
               long nb=db.get_order_count();
               db.close();
               if (nb==0){
                   MainNew.goto_(new Car(),getContext());
               }else {
                   count.setText(nb+"");
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

    private void calculate(Double price_) {
        final Setting setting=new Setting(getContext());
        Double delivry_= 0.0;
        Double total_=0.0;
        Double taxe=0.0;
        price.setText(price_.toString());
        delivry_=qty*Double.valueOf((setting.getDelivery()));
        tax.setText(delivry_.toString());
        taxe=Double.valueOf((setting.getTax()))*price_*0.01;
        deleviry.setText(taxe.toString());
        total_=price_+delivry_+taxe;
        total.setText(total_.toString());
    }

    private void EDitNotesPopup() {
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.popup_notes);
        Button save=dialog.findViewById(R.id.save_btn);
        final EditText notes=dialog.findViewById(R.id.notes_tv);
        dialog.show();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                notes_=notes.getText().toString();
                notes.setText(notes_);
            }
        });
    }

}
