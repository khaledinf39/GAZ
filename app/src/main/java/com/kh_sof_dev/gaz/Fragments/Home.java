package com.kh_sof_dev.gaz.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.Gategories;
import com.kh_sof_dev.gaz.Adapters.Top20Products;
import com.kh_sof_dev.gaz.Classes.Database.DBManager;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.point.show_points;
import com.kh_sof_dev.gaz.Classes.Products.Http_products;
import com.kh_sof_dev.gaz.Classes.Products.show_main_catigories;
import com.kh_sof_dev.gaz.Classes.Products.show_products;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.Classes.constant.Setting;
import com.kh_sof_dev.gaz.Classes.constant.show_setting;
import com.kh_sof_dev.gaz.Fragments.Refill_frg.Tab_home_gaz;
import com.kh_sof_dev.gaz.R;

public class Home extends Fragment {

    public Home() {

    }

    RecyclerView speacialRV, categoriesRV, productRV;
    Top20Products adapter;
    Gategories categ_adapter;

    ImageView go_basket;
    private SliderLayout mDemoSlider;
    private TextView count, Allcatig;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_home, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));


        speacialRV = (RecyclerView) view.findViewById(R.id.spacialRV);
        categoriesRV = (RecyclerView) view.findViewById(R.id.categoriesRV);
        productRV = (RecyclerView) view.findViewById(R.id.productRV);

        final ProgressBar progressBar = view.findViewById(R.id.progress);
        /********************************count order**************************/
        count = (TextView) view.findViewById(R.id.basketCount_tv);
        Allcatig = (TextView) view.findViewById(R.id.seeAllPro_tv);
        Allcatig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNew.goto_(new Categories(), getContext());
            }
        });
        DBManager db = new DBManager(getContext());
        db.open();
        Long count_order = db.get_order_count();
        db.close();
        if (count_order != 0) {
            count.setText(count_order.toString());
            count.setVisibility(View.VISIBLE);
        } else {
            count.setVisibility(View.GONE);
        }

        /**************************************************************/
        go_basket = (ImageView) view.findViewById(R.id.basket);
        go_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainNew.goto_(new Car(), getContext());
            }
        });
        speacialRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        final Http_products http_products = new Http_products();
        http_products.Get_top20_Products(getContext(), new Http_products.productsListener() {
            @Override
            public void onSuccess(show_products products) {
                adapter = new Top20Products(getContext(), products.getProducts(), Home.this);
                speacialRV.setAdapter(adapter);
                // Toast.makeText(getContext(),products.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

            }
        });

        categoriesRV.setLayoutManager(new GridLayoutManager(getContext(), 2));
        http_products.Getcatigories(getContext(), 10, new Http_products.categoriesListener() {
            @Override
            public void onSuccess(show_main_catigories catigories) {
                categ_adapter = new Gategories(getContext(), catigories.getcatigoriess(), new Gategories.categorie_selected_listenner() {
                    @Override
                    public void onSuccess(com.kh_sof_dev.gaz.Classes.Products.catigories catigories) {
                        progressBar.setVisibility(View.GONE);
                        MainNew.goto_(new AllProducts(catigories), getContext());
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                try {
                    categoriesRV.setAdapter(categ_adapter);
                    // Toast.makeText(getContext(),catigories.getMessage(),Toast.LENGTH_SHORT).show();
                } catch (Exception ef) {

                }

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
        Setting();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            if (viewPager != null)
                viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (viewPager != null)
                viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));

            DBManager db = new DBManager(getContext());
            db.open();
            long count_order = db.get_order_count();
            db.close();
            if (count_order != 0) {
                count.setText(String.valueOf(count_order));
                count.setVisibility(View.VISIBLE);
            } else {
                count.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Setting() {

        Http_get_constant http = new Http_get_constant();
        http.setting(getContext(), new Http_get_constant.sittingListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(show_setting setting) {
                if (setting.isStatus()) {
                    Long nb_order = Long.valueOf(10);
                    Long ring = Long.valueOf(0);
                    Double tax = 0.0;
                    Double delivery = 0.0;
                    for (Setting i : setting.getItems()
                    ) {
                        switch (i.getId()) {
                            case "5c921977c4410f17e1c1ac4c":
                                nb_order = i.getValue().longValue();
                                break;
                            case "5c6758e0c65f421a494cef89":
                                ring = i.getValue().longValue();
                                break;
                            case "5c9218bec4410f17e1c1ac4b":
                                tax = i.getValue();
//                                MainNew.tax= i.getValue().longValue();
                                break;
                            case "5c6758d9c65f421a494cef88":
                                delivery = i.getValue();
//                                MainNew.delivery= i.getValue().longValue();
                                break;
                        }

                    }
                    Log.d("setting", tax + "   " + delivery);
                    Setting setting1 = new Setting(getContext());
                    setting1.setDelivery(delivery);
                    tax = tax * 100;
                    setting1.setTax(tax.longValue());
                    setting1.setRinge(ring);
                    setting1.setNb_order(nb_order);
                    new Setting(setting1, getContext());
                    Setting setting2 = new Setting(getContext());
                    Log.d("setting", setting2.getTax() + "   " + setting2.getDelivery());
                    // Toast.makeText(getContext(),"Setting"+delivery+" "+tax,Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });


        /***********************************************/
        Http_orders http_orders = new Http_orders();
        http_orders.GetPoints(getContext(), new Http_orders.OnPoint_lisennter() {
            @Override
            public void onSuccess(show_points show_points) {
                if (show_points.isStatus()) {
                    new user_info(getContext(), show_points.getItems().get(0));
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

    private ViewPager viewPager;
    private Fragment[] PAGES = new Fragment[]{
            new Tab_home_gaz()
    };

    public class MyPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }


//        @Override
//        public CharSequence getPageTitle(int position) {
//            return PAGE_TITLES[position];
//        }

    }
}
