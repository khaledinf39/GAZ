package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetails extends AppCompatActivity {
    public static final String product = "product";

    private Product mProduct;

    private TextView nb_basket;
    private ImageView like;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_product_details);
        TextView rat = findViewById(R.id.rating_tv);
        TextView name = findViewById(R.id.productName);
        nb_basket = findViewById(R.id.basketCount_tv);
        TextView price = findViewById(R.id.price_tv);
        TextView details = findViewById(R.id.details_tv);
//        TextView hot = findViewById(R.id.hot_tv);
        TextView waranty = findViewById(R.id.warnaty);
        ImageView back = findViewById(R.id.back_btn);
        ImageView pro_img = findViewById(R.id.img);
        ImageView add_basket = findViewById(R.id.add_basket);
        like = findViewById(R.id.like_btn);
        Button buy = findViewById(R.id.buy_btn);
        //***************************************intialise******************/
        final DBManager manager = new DBManager(this, new DBManager.OnAddOrder_listenner() {
            @Override
            public void add_basket(Boolean state) {

            }
        });
        basketCount();
        like_function();

        if (getIntent().hasExtra(product)) {
            mProduct = (Product) getIntent().getSerializableExtra(product);
            if (mProduct != null) {
                waranty.setText(mProduct.getWarrenty());
                rat.setText(mProduct.getRate() + "");
                name.setText(mProduct.getName());
                price.setText(mProduct.getPrice().toString());
                details.setText(mProduct.getDescription());
                Picasso.with(this).load(mProduct.getImage()).into(pro_img);

                //********************************action******************************/
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (like_function()) {
                            manager.open();
                            manager.deleteBestPro(mProduct.getID_());
                            manager.close();
                            like_function();
                            like.setImageResource(R.drawable.ic_like);
                        } else {
                            manager.open();
                            manager.insert_bestProd(mProduct);
                            manager.close();

                            like_function();
                            like.setImageResource(R.drawable.ic_like1);
                        }
                    }
                });
                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        manager.open();
                        manager.insert_order(mProduct);
                        manager.close();
                        basketCount();
                        startActivity(new Intent(ProductDetails.this, Car.class));
//                MainNew.goto_(new Car(), getContext());
                    }
                });

            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductDetails.this, Car.class));
            }
        });
    }

    private void basketCount() {
        DBManager manager = new DBManager(this);
        manager.open();
        long count = manager.get_order_count();
        manager.close();
        if (count != 0) {
            nb_basket.setVisibility(View.VISIBLE);
            nb_basket.setText(count + "");

        } else {
            nb_basket.setVisibility(View.GONE);
        }
    }

    private Boolean like_function() {
        DBManager dbManager = new DBManager(this);
        dbManager.open();
        List<Product> products = dbManager.fetch_bestProd();
        dbManager.close();
        for (Product p : products
        ) {
            if (p.getId().equals(mProduct.getId())) {
                like.setImageResource(R.drawable.ic_like1);
                mProduct.setID_(p.getID_());
                return true;

            }
        }

        like.setImageResource(R.drawable.ic_like);
        return false;
    }


}