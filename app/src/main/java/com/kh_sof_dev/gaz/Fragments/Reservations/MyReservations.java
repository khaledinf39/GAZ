package com.kh_sof_dev.gaz.Fragments.Reservations;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.kh_sof_dev.gaz.activities.MainNew;

import com.kh_sof_dev.gaz.R;


public class MyReservations extends Fragment {

    private ViewPager mViewPager;
    private final String[] PAGE_TITLES = new String[]{
            "الملغية",
            "السابقة",
            "الحالية",
            "بإنتظار الموافقة",




//            "الملغية من طرف السائق"


    };

    private final Fragment[] PAGES = new Fragment[]{
            new Reservation_Canceled(),
            new Reservation_Complet(),
            new Reservation_Current(),
            new Reservation_wait(),




//            new Reservation_Canceled_devier()


    };

    public MyReservations() {
        // Required empty public constructor
    }
    Boolean refillhome=true;
    @SuppressLint("ValidFragment")
    public MyReservations(Boolean refillhome) {
        this.refillhome=refillhome;
    }


    public static MyReservations newInstance(String param1, String param2) {
        MyReservations fragment = new MyReservations();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private ImageView back_btn,refillImg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_my_reservations, container, false);
        /***************************TabLayout***********************/
        mViewPager =  view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new MyReservations.MyPagerAdapter(getChildFragmentManager()));
        mViewPager.setOffscreenPageLimit(1);
        final TabLayout tabLayout =  view.findViewById(R.id.tab_layout);
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
//        if (refillhome) {
//            tabLayout.setAccessibilityPaneTitle(PAGE_TITLES[1]);
//        }
        /***************************************************************************/
        back_btn=(ImageView)view.findViewById(R.id.back_icon);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(getContext(),MainNew.class));
//                MainNew._goto(MyReservations.this,new Home(),View.VISIBLE);
            }
        });
        return view;
    }
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
