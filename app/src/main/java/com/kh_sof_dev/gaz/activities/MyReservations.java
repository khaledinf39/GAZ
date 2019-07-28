package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.kh_sof_dev.gaz.Fragments.Reservations.Reservation_Canceled;
import com.kh_sof_dev.gaz.Fragments.Reservations.Reservation_Complet;
import com.kh_sof_dev.gaz.Fragments.Reservations.Reservation_Current;
import com.kh_sof_dev.gaz.Fragments.Reservations.Reservation_wait;
import com.kh_sof_dev.gaz.R;

public class MyReservations extends AppCompatActivity {

    private ViewPager mViewPager;
    private MyPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_my_reservations);
        mViewPager = findViewById(R.id.viewpager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(1);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1f57ff"));
        tabLayout.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#9d9d9d"), Color.parseColor("#0d0e10"));
        //tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setCurrentItem(3);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });
        //***************************************************************************/
        ImageView back_btn = findViewById(R.id.back_icon);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyReservations.this, MainNew.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                MainNew._goto(MyReservations.this,new Home(),View.VISIBLE);
            }
        });
    }

//    private ImageView refillImg;


    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null && mViewPager != null) {
            mViewPager.setCurrentItem(3);
            adapter.notifyDataSetChanged();
        }
    }

    private final String[] PAGE_TITLES = new String[]{
            "الملغية",
            "السابقة",
            "الحالية",
            "بإنتظار الموافقة"
//            "الملغية من طرف السائق"
    };

    private final Fragment[] PAGES = new Fragment[]{
            new Reservation_Canceled(),
            new Reservation_Complet(),
            new Reservation_Current(),
            new Reservation_wait()
//            new Reservation_Canceled_devier()
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

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }

    }
}
