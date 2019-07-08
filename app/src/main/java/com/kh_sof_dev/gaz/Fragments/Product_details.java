package com.kh_sof_dev.gaz.Fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Product_details extends Fragment {

private Product mProduct;
private Boolean ishote;
    public Product_details(Product product,Boolean hote) {
        mProduct=product;
        ishote=hote;
        // Required empty public constructor
    }

private TextView rat,price,name,details,hot,nb_basket,waranty;
    private ImageView back,pro_img,like,add_basket;
    private Button Buy;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_product_details, container, false);

        rat=(TextView) view.findViewById(R.id.rating_tv);
        name=(TextView)view.findViewById(R.id.productName);
        nb_basket=(TextView)view.findViewById(R.id.basketCount_tv);
        price=(TextView)view.findViewById(R.id.price_tv);
        details=(TextView)view.findViewById(R.id.details_tv);
        hot=(TextView)view.findViewById(R.id.hot_tv);
        waranty=(TextView)view.findViewById(R.id.warnaty);
        back=(ImageView) view.findViewById(R.id.back_btn);
        pro_img=(ImageView)view.findViewById(R.id.img);
        add_basket=(ImageView)view.findViewById(R.id.add_basket);
        like=(ImageView)view.findViewById(R.id.like_btn);
        Buy=(Button) view.findViewById(R.id.buy_btn);
      /***************************************intialise******************/
        final DBManager manager=new DBManager(getContext(), new DBManager.OnAddOrder_listenner() {
            @Override
            public void add_basket(Boolean state) {

            }
        });
        basketCount();
      like_function();
//      if (ishote){
//          hot.setVisibility(View.VISIBLE);
//      }else {
//          hot.setVisibility(View.GONE);
//      }
      waranty.setText(mProduct.getWarrenty());
      rat.setText(mProduct.getRate()+"");
      name.setText(mProduct.getName());
      price.setText(mProduct.getPrice().toString());
      details.setText(mProduct.getDescription());
        Glide.with(getContext()).load(mProduct.getImage()).into(pro_img);

      /********************************action******************************/
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              getActivity().finish();
//              MainNew._goto(Product_details.this,new Home(),View.VISIBLE);
          }
      });
      like.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              if (like_function()){
                  manager.open();
                  manager.deleteBestPro(mProduct.getID_());
                  manager.close();
                  like_function();
                  like.setImageResource(R.drawable.ic_like);
              }else {
                  manager.open();
                  manager.insert_bestProd(mProduct);
                  manager.close();

                  like_function();
                  like.setImageResource(R.drawable.ic_like1);
              }
          }
      });
        Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manager.open();
                manager.insert_order(mProduct);
                manager.close();
                basketCount();
                MainNew.goto_(new Car(),getContext());
            }
        });
      add_basket.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              MainNew.goto_(new Car(),getContext());
          }
      });

        return view;
    }

    private void basketCount() {
        DBManager manager=new DBManager(getContext());
        manager.open();
        long count=manager.get_order_count();
        manager.close();
        if (count!=0){
            nb_basket.setVisibility(View.VISIBLE);
            nb_basket.setText(count+"");

        }else {
            nb_basket.setVisibility(View.GONE);
        }
    }

    private Boolean like_function() {
        DBManager dbManager=new DBManager(getContext());
        dbManager.open();
        List<Product> products= dbManager.fetch_bestProd();
        dbManager.close();
        for (Product p:products
        ) {
            if (p.getId().equals(mProduct.getId()))
            {
                like.setImageResource(R.drawable.ic_like1);
                mProduct.setID_(p.getID_());
                return true;

            }
        }

        like.setImageResource(R.drawable.ic_like);
        return false;
    }


}
