package com.kh_sof_dev.gaz.Fragments.Refill_frg;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.Classes.constant.ads;
import com.kh_sof_dev.gaz.Classes.constant.show_ads;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.MainNew;

public class Tab_home_gaz extends Fragment {

//    int order_type = 23;
//
//    public Tab_home_gaz(int order_type) {
//        this.order_type = order_type;
//    }

    public Tab_home_gaz() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_tab_home_gaz, container, false);
//
        Button refill_btn = view.findViewById(R.id.go_btn);

        refill_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainNew.goto_(new Refill(4), getContext());

            }
        });
        Button shop_btn = view.findViewById(R.id.shop_btn);

        shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainNew.goto_(new Refill(2), getContext());

            }
        });

        /***************Ads******************************/

        final SliderLayout mDemoSlider = (SliderLayout) view.findViewById(R.id.slider_l);
        final TextSliderView textSliderView = new TextSliderView(getContext());

        Http_get_constant constant = new Http_get_constant();
        constant.GetAds(getContext(), new Http_get_constant.OnoffersListener() {
            @Override
            public void onSuccess(show_ads Ads) {
                if (Ads.isStatus()) {
                    for (ads offer : Ads.getItems()
                    ) {
                        String url = offer.getImage();

                        textSliderView
                                .description(offer.getName())
                                .image(url)   //url_maps.get(url_maps.keySet())
                                .setScaleType(BaseSliderView.ScaleType.Fit);
//                        .setOnSliderClickListener(Home.this);

//            i++;
                        //add your extra information
                        textSliderView.bundle(new Bundle());
                        textSliderView.getBundle()
                                .putString("extra", url);

                        try {
                            mDemoSlider.addSlider(textSliderView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // you can change animasi, time page and anythink.. read more on github
                    mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                    mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                    mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                    mDemoSlider.setDuration(4000);
//            mDemoSlider.addOnPageChangeListener(getContext());
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
        return view;
    }

}
