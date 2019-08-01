package com.kh_sof_dev.gaz.Classes;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Utils {

    public static void showImage(Context context, String url, ImageView imageView) {
        if (url != null && !TextUtils.isEmpty(url))
            Picasso.with(context)
                    .load(url)
                    .into(imageView);
    }

    public static void showImage(Context context, String url, int placeholder, ImageView imageView) {
        if (url != null && !TextUtils.isEmpty(url))
            Picasso.with(context)
                    .load(url)
                    .placeholder(placeholder)
                    .into(imageView);
    }
}
