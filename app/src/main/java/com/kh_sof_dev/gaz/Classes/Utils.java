package com.kh_sof_dev.gaz.Classes;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Utils {

    public static void showImage(Context context, String url, ImageView imageView) {
        if (url != null && !TextUtils.isEmpty(url)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !url.startsWith("https")) {
                url = url.replace("http", "https");
            }
            Glide.with(context).load(url).into(imageView);
//            Picasso.with(context)
//                    .load(url)
//                    .into(imageView);
        }
    }

    public static void showImage(Context context, String url, int placeholder, ImageView imageView) {
        if (url != null && !TextUtils.isEmpty(url)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !url.startsWith("https")) {
                url = url.replace("http", "https");
            }
            Glide.with(context).load(url).placeholder(placeholder).into(imageView);
//            Picasso.with(context)
//                    .load(url)
//                    .placeholder(placeholder)
//                    .into(imageView);
        }
    }
}
