package com.kh_sof_dev.gaz.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kh_sof_dev.gaz.Fragments.Welcome1;
import com.kh_sof_dev.gaz.Fragments.Welcome2;
import com.kh_sof_dev.gaz.Fragments.Welcome3;
import com.kh_sof_dev.gaz.R;

import me.relex.circleindicator.CircleIndicator;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_intro);
        SharedPreferences sp = getBaseContext().getSharedPreferences("Intro", MODE_PRIVATE);


        if (sp.getBoolean("state", false)) {
            startActivity(new Intent(Intro.this, TestLocation.class));
            finish();
        }

        final TextView skip = findViewById(R.id.skip_tv);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intro.this, TestLocation.class));
                finish();
            }
        });
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        CircleIndicator indicator = findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 2) {
                    skip.setVisibility(View.GONE);
                } else
                    skip.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Welcome1();
                case 1:
                    return new Welcome2();
                case 2:
                    return new Welcome3();
                default:
                    return null;
            }
        }

    }
}
