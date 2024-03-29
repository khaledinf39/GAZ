package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Classes.Database.Best;
import com.kh_sof_dev.gaz.Classes.Database.OrderDetails;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.MyApplication;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

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
        final Realm realm = MyApplication.getRealm();
//        final DBManager manager = new DBManager(this, new DBManager.OnAddOrder_listenner() {
//            @Override
//            public void add_basket(Boolean state) {
//
//            }
//        });
        if (getIntent().hasExtra(product)) {
            mProduct = (Product) getIntent().getSerializableExtra(product);
            if (mProduct != null) {

                waranty.setText(mProduct.getWarrenty());
                rat.setText(mProduct.getRate() + "");
                name.setText(mProduct.getName());
                price.setText(mProduct.getPrice_buy_new() + "");
                details.setText(mProduct.getDescription());
//                Picasso.with(this).load(mProduct.getImage()).into(pro_img);
                Utils.showImage(this, mProduct.getImage(), pro_img);

                basketCount();
                like_function();

                //********************************action******************************/
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (like_function()) {
                            Best best = realm.where(Best.class).equalTo("id", mProduct.getID_()).findFirst();
                            if (best != null) {
                                realm.beginTransaction();
                                best.deleteFromRealm();
                                realm.commitTransaction();
                            }
//                            manager.open();
//                            manager.deleteBestPro(mProduct.getID_());
//                            manager.close();
                            like_function();
                            like.setImageResource(R.drawable.ic_like);
                        } else {
                            Best best = new Best();
                            best.setId(mProduct.getID_());
                            best.setProductId(mProduct.getId());
                            best.setProductName(mProduct.getName());
                            best.setPrice(mProduct.getPrice_buy_new());
                            best.setQuantity(mProduct.getQty());
                            best.setImage(mProduct.getImage());
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(best);
                            realm.commitTransaction();
//                            manager.open();
//                            manager.insert_bestProd(mProduct);
//                            manager.close();

                            like_function();
                            like.setImageResource(R.drawable.ic_like1);
                        }
                    }
                });
                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderDetails orderDetails = new OrderDetails();
                        orderDetails.setId(mProduct.getID_());
                        orderDetails.setProductId(mProduct.getId());
                        orderDetails.setProductName(mProduct.getName());
                        orderDetails.setPrice(mProduct.getPrice_buy_new());
                        orderDetails.setQuantity(1);
                        orderDetails.setImage(mProduct.getImage());
                        orderDetails.setCategoryId(mProduct.getCategory_id());
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(orderDetails);
                        realm.commitTransaction();
//                        manager.open();
//                        manager.insert_order(mProduct);
//                        manager.close();
//                        basketCount();
                        if (!mProduct.getCategory_id().equals("5c8cb6c10a34fc002491f406"))
                            startActivity(new Intent(ProductDetails.this, Car.class));
                        else {
                            Intent intent = new Intent(ProductDetails.this, Shipping.class);
                            intent.putExtra(Shipping.typ2_Order_1_test, 2);
                            intent.putExtra(Shipping.order_type_s, 3);
                            startActivity(intent);
                        }
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
        Realm realm = MyApplication.getRealm();
        RealmResults<com.kh_sof_dev.gaz.Classes.Database.OrderDetails> orderDetailsList = realm.where(OrderDetails.class).findAll();
        long count = orderDetailsList.size();
//        DBManager manager = new DBManager(this);
//        manager.open();
//        long count = manager.get_order_count();
//        manager.close();
        if (count != 0) {
            nb_basket.setVisibility(View.VISIBLE);
            nb_basket.setText(count + "");

        } else {
            nb_basket.setVisibility(View.GONE);
        }
    }

    private Boolean like_function() {
        Realm realm = MyApplication.getRealm();
        RealmResults<OrderDetails> orderDetailsList = realm.where(OrderDetails.class).findAll();
        List<Product> products = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            if (orderDetails != null && orderDetails.getProductId() != null && orderDetails.getProductId().equals(mProduct.getId())) {
                like.setImageResource(R.drawable.ic_like1);
                mProduct.setID_((int) orderDetails.getId());
                return true;

            }
        }
//        DBManager dbManager = new DBManager(this);
//        dbManager.open();
//        List<Product> products = dbManager.fetch_bestProd();
//        dbManager.close();
//        for (Product p : products
//        ) {
//            if (p.getId().equals(mProduct.getId())) {
//                like.setImageResource(R.drawable.ic_like1);
//                mProduct.setID_(p.getID_());
//                return true;
//
//            }
//        }

        like.setImageResource(R.drawable.ic_like);
        return false;
    }


}
