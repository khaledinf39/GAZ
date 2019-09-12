package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.kh_sof_dev.gaz.Adapters.Gategories;
import com.kh_sof_dev.gaz.Adapters.Top20Products;
import com.kh_sof_dev.gaz.Classes.Database.OrderDetails;
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
import com.kh_sof_dev.gaz.activities.AllProducts;
import com.kh_sof_dev.gaz.activities.Car;
import com.kh_sof_dev.gaz.activities.Categories;

import io.realm.Realm;
import io.realm.RealmResults;

public class Home extends Fragment {

    public Home() {

    }

    RecyclerView speacialRV, categoriesRV, productRV;
    Top20Products adapter;
    Gategories categ_adapter;

    ImageView go_basket;
    private TextView count;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_home, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));


        speacialRV = view.findViewById(R.id.spacialRV);
        categoriesRV = view.findViewById(R.id.categoriesRV);
        productRV = view.findViewById(R.id.productRV);

        final ProgressBar progressBar = view.findViewById(R.id.progress);
        //********************************count order**************************/
        count = view.findViewById(R.id.basketCount_tv);
        TextView allcatig = view.findViewById(R.id.seeAllPro_tv);
        allcatig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Categories.class));
//                MainNew.goto_(new Categories(), getActivity());
            }
        });
        try {
//            DBManager db = new DBManager(getActivity());
//            db.open();
//            long count_order = db.get_order_count();
//            db.close();

            long count_order = getOrderCount();
            if (count_order != 0) {
                count.setText(String.valueOf(count_order));
                count.setVisibility(View.VISIBLE);
            } else {
                count.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //**************************************************************/
        go_basket = view.findViewById(R.id.basket);
        go_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Car.class));
//                MainNew.goto_(new Car(), getActivity());
            }
        });
        speacialRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        final Http_products http_products = new Http_products();
        http_products.Get_top20_Products(getContext(), new Http_products.productsListener() {
            @Override
            public void onSuccess(show_products products) {
                adapter = new Top20Products(getContext(), products.getProducts());
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
                    public void onSuccess(com.kh_sof_dev.gaz.Classes.Products.Categories Categories) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getActivity(), AllProducts.class);
                        intent.putExtra(AllProducts.category, Categories);
                        startActivity(intent);
//                        MainNew.goto_(new AllProducts(Categories), getContext());
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
                    // Toast.makeText(getContext(),Categories.getMessage(),Toast.LENGTH_SHORT).show();
                } catch (Exception ef) {
                    ef.printStackTrace();
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

            long count_order = getOrderCount();
//            DBManager db = new DBManager(getContext());
//            db.open();
//            long count_order = db.get_order_count();
//            db.close();
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

    long getOrderCount() {
        long count_order = 0;
        Realm realm = Realm.getDefaultInstance();
        RealmResults<OrderDetails> orderDetailsList = realm.where(OrderDetails.class).findAll();
        if (!orderDetailsList.isEmpty())
            count_order = orderDetailsList.size();
        return count_order;
    }

    public void Setting() {

        Http_get_constant http = new Http_get_constant();
        http.setting(getContext(), new Http_get_constant.sittingListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(show_setting setting) {
                if (setting.isStatus()) {
                    long nb_order = 10L;
                    long ring = 0L;
                    double tax = 0.0;
                    double delivery = 0.0;
                    for (Setting i : setting.getItems()
                    ) {
                        switch (i.getName()) {
                            case "5c921977c4410f17e1c1ac4c":
                                nb_order = Long.parseLong(i.getValue());
                                break;
                            case "5c6758e0c65f421a494cef89":
                                ring = Long.parseLong(i.getValue());
                                break;
                            case "قيمة الضريبة":
                                tax = Double.parseDouble(i.getValue());
//                                MainNew.tax= i.getValue().longValue();
                                break;
                            case "قيمة التوصيل":
                                delivery = Double.parseDouble(i.getValue());
//                                MainNew.delivery= i.getValue().longValue();
                                break;
                        }

                    }
                    Log.d("setting", tax + "   " + delivery);
                    Setting setting1 = new Setting(getActivity());
                    setting1.setDelivery(delivery);
                    tax = tax * 100;
                    setting1.setTax((long) tax);
                    setting1.setRinge(ring);
                    setting1.setNb_order(nb_order);
                    setting1.addSetting(setting1, getActivity());
//                    Setting setting2 = new Setting(getActivity());
//                    Log.d("setting", setting2.getTax() + "   " + setting2.getDelivery());
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


        //***********************************************/
        Http_orders http_orders = new Http_orders();
        http_orders.GetPoints(getContext(), new Http_orders.OnPoint_lisennter() {
            @Override
            public void onSuccess(show_points show_points) {
                if (show_points.isStatus()) {
                    new user_info(getActivity(), show_points.getItems().get(0));
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

        MyPagerAdapter(FragmentManager fragmentManager) {
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
