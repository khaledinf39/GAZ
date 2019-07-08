package com.kh_sof_dev.gaz.Fragments.Refill_frg;


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
import android.widget.TextView;


import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.R;


public class Refill extends Fragment {

    private ViewPager mViewPager;
    private final String[] PAGE_TITLES = new String[]{
            "خزان غاز",
            "إسطوانة غاز"


    };

    private final Fragment[] PAGES = new Fragment[]{
            new RefillHome(3),   //false
            new RefillHome(2)   //true


    };


    public Refill() {
        // Required empty public constructor
    }
    int order_type;
    @SuppressLint("ValidFragment")

    public Refill(int order_type) {
        this.order_type=order_type;
    }


    public static Refill newInstance(String param1, String param2) {
        Refill fragment = new Refill();
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
        View view = inflater.inflate(R.layout.layout_f_refill, container, false);
        back_btn=(ImageView)view.findViewById(R.id.back_icon);
        refillImg=(ImageView)view.findViewById(R.id.refillImg);
        /***************************TabLayout***********************/
        mViewPager =  view.findViewById(R.id.viewpager);
        TextView typeOrder_tv=view.findViewById(R.id.typeorder_tv);
        final TabLayout tabLayout =  view.findViewById(R.id.tab_layout);
        if (order_type==4){
            typeOrder_tv.setText("شراء مستلزمات غاز");
            tabLayout.setVisibility(View.GONE);
            mViewPager.setAdapter(new Refill.MyPagerAdapter2(getFragmentManager()));
            refillImg.setImageResource(R.drawable.ic_product7);
        }else {
            mViewPager.setAdapter(new Refill.MyPagerAdapter(getFragmentManager()));
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
        /***************************************************************************/

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(new Intent(getContext(),MainNew.class));
//                MainNew._goto(Refill.this,new Home(),View.VISIBLE);
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
            if (position==0){
              refillImg.setImageResource(R.drawable.ic_product8);
            }else {
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
