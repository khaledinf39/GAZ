package com.kh_sof_dev.gaz.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Fragments.Refill_frg.RefillHome;
import com.kh_sof_dev.gaz.R;

public class Refill extends AppCompatActivity {
    public static final String order_type_s = "order_type";

    private ViewPager mViewPager;
    int order_type;

    private ImageView refillImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_refill);

        order_type = getIntent().getIntExtra(order_type_s, 0);

        ImageView back_btn = findViewById(R.id.back_icon);
        refillImg = findViewById(R.id.refillImg);
        //***************************TabLayout***********************/
        mViewPager = findViewById(R.id.viewpager);
        TextView typeOrder_tv = findViewById(R.id.typeorder_tv);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        if (order_type == 4) {
            typeOrder_tv.setText("شراء مستلزمات غاز");
            tabLayout.setVisibility(View.GONE);
            mViewPager.setAdapter(new Refill.MyPagerAdapter2(getSupportFragmentManager()));
            refillImg.setImageResource(R.drawable.ic_product7);
        } else {
            mViewPager.setAdapter(new Refill.MyPagerAdapter(getSupportFragmentManager()));
            mViewPager.setOffscreenPageLimit(1);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1f57ff"));
            tabLayout.setSelectedTabIndicatorHeight((int) (4 * getResources().getDisplayMetrics().density));
            tabLayout.setTabTextColors(Color.parseColor("#9d9d9d"), Color.parseColor("#0d0e10"));

            mViewPager.setCurrentItem(1);

            tabLayout.post(new Runnable() {
                @Override
                public void run() {
                    tabLayout.setupWithViewPager(mViewPager);
                }
            });
        }
//
        //***************************************************************************/

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Refill.this, MainNew.class));
//                MainNew._goto(Refill.this,new Home(),View.VISIBLE);
            }
        });
    }

    private final String[] PAGE_TITLES = new String[]{
            "خزان غاز",
            "إسطوانة غاز"
    };

    private final Fragment[] PAGES = new Fragment[]{
            new RefillHome(3),   //false
            new RefillHome(2)   //true
    };

    public class MyPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                refillImg.setImageResource(R.drawable.ic_product8);
            } else {
                refillImg.setImageResource(R.drawable.ic_product7);
            }
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


    private final String[] PAGE_TITLES2 = new String[]{
            ""


    };

    private final Fragment[] PAGES2 = new Fragment[]{
            new RefillHome(4)


    };

    public class MyPagerAdapter2 extends android.support.v4.app.FragmentStatePagerAdapter {

        public MyPagerAdapter2(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            return PAGES2[position];
        }

        @Override
        public int getCount() {
            return PAGES2.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES2[position];
        }

    }
}
