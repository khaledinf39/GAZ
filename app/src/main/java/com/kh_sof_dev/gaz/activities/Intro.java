package com.kh_sof_dev.gaz.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.kh_sof_dev.gaz.Fragments.Welcome1;
import com.kh_sof_dev.gaz.Fragments.Welcome2;
import com.kh_sof_dev.gaz.Fragments.Welcome3;
import com.kh_sof_dev.gaz.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class Intro extends IntroActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setFullscreen(false);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_a_intro);
/**************************TODO INTO TEXT***********************************/
        SharedPreferences sp=getBaseContext().getSharedPreferences("Intro", MODE_PRIVATE);


        if (sp.getBoolean("state",false)){
            startActivity(new Intent(Intro.this,Login.class));
            finish();
        }


/****************************************************************************/

        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .backgroundDark(R.color.intro_dot_dark_bg)
                .fragment(new Welcome1())// ,R.style.FragmentTheme
                .build());
        addSlide(new FragmentSlide.Builder()
                .background(R.color.white)
                .backgroundDark(R.color.intro_dot_dark_bg)
                .fragment(new Welcome2())// ,R.style.FragmentTheme
                .build());
        FragmentSlide fr=new FragmentSlide.Builder()
                .background(R.color.white)
                .backgroundDark(R.color.intro_dot_dark_bg)
                .fragment(new Welcome3())// ,R.style.FragmentTheme
                .canGoForward(false)

                .build();
//        FragmentSlide fr3=new FragmentSlide.Builder()
//                .background(R.color.white)
//                .backgroundDark(R.color.intro_dot_dark_bg)
//                .fragment(R.layout.layout_f_welcome4)// ,R.style.FragmentTheme
//                .canGoForward(false)

//                .build();

        addSlide(fr);
//        addSlide(fr3);
        setButtonBackVisible(false);
        setButtonNextVisible(false);


//        setButtonCtaVisible(true);
    }

}
