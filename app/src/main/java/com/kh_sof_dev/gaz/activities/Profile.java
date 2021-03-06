package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Adapters.Favorit_adapter;
import com.kh_sof_dev.gaz.Adapters.Resrvation_adapter;
import com.kh_sof_dev.gaz.Classes.Database.Best;
import com.kh_sof_dev.gaz.Classes.Database.OrderDetails;
import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.MyApplication;
import com.kh_sof_dev.gaz.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import io.realm.RealmResults;

public class Profile extends AppCompatActivity {

    CircleImageView user_img;
    TextView name, address, seeallRes, seeallFav;
    RecyclerView Rv_reser, Rv_favor;
    ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_profile);
        back = findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//MainNew._goto(Profile.this,new Home(),VISIBLE);
            }
        });
        user_img = findViewById(R.id.UserImg);
        name = findViewById(R.id.Name_tv);
        address = findViewById(R.id.address_tv);
        seeallFav = findViewById(R.id.seeAllFavo_tv);
        seeallRes = findViewById(R.id.seeAllReser_tv);
        Rv_favor = findViewById(R.id.my_favorit_RV);
        Rv_reser = findViewById(R.id.my_reservation_RV);
        Rv_favor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        Rv_reser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        Realm realm = MyApplication.getRealm();
        RealmResults<OrderDetails> orderDetailsList = realm.where(OrderDetails.class).findAll();
        List<Product> products = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            Product p = new Product();
            p.setID_((int) orderDetails.getId());
            p.setId(orderDetails.getProductId());
            p.setName(orderDetails.getProductName());
            p.setQty(orderDetails.getQuantity());
            p.setPrice(orderDetails.getPrice());
            p.setImage(orderDetails.getImage());
            products.add(p);
        }
        Rv_reser.setAdapter(new Resrvation_adapter(this, products));

        RealmResults<Best> orderDetailsListBest = realm.where(Best.class).findAll();
        List<Product> productsFav = new ArrayList<>();
        for (Best best : orderDetailsListBest) {
            Product p = new Product();
            p.setID_((int) best.getId());
            p.setId(best.getProductId());
            p.setName(best.getProductName());
            p.setQty(best.getQuantity());
            p.setPrice(best.getPrice());
            p.setImage(best.getImage());
            productsFav.add(p);
        }
        Rv_favor.setAdapter(new Favorit_adapter(this, productsFav));

//        DBManager dbManager = new DBManager(this);
//        dbManager.open();
//        Rv_reser.setAdapter(new Resrvation_adapter(this, dbManager.fetch_order()));
//        Rv_favor.setAdapter(new Favorit_adapter(this, dbManager.fetch_bestProd()));
//        dbManager.close();
        //****************************action***************************/
        user_info user_info = new user_info(this);
        if (!user_info.getImag().isEmpty()) {
            Utils.showImage(this, user_info.getImag(),
                    R.drawable.ic_user_img_gray, user_img);
//            Picasso.with(this)
//                    .load(user_info.getImag())
//                    .placeholder(R.drawable.ic_user_img_gray)
//                    .into(user_img);
        }
        name.setText(user_info.getName());
        address.setText(user_info.getCity());

        seeallRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, MyReservations.class));
//                _goto(new MyReservations());
            }
        });
        seeallFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this, MyFavorites.class));
//                _goto(new MyFavorites());
            }
        });
    }

//    private void _goto(Fragment frg) {
//        MainNew.fragmentManager = getFragmentManager();
//        MainNew.fragmentTransaction = MainNew.fragmentManager.beginTransaction();
//        MainNew.fragment = frg;
//        MainNew.fragmentTransaction.replace(R.id.ContentLogin, MainNew.fragment);
//        MainNew.fragmentTransaction.commit();
//    }
}
