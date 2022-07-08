package com.kh_sof_dev.gaz.Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kh_sof_dev.gaz.MyApplication;
import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.activities.TestLocation;

import org.json.JSONObject;

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

    public static void checkResponse(String response) {
        try {
            JSONObject json = new JSONObject(response);
            int httpResponse = json.optInt("statusCode");
            String message = json.optString("message");
            if (httpResponse < 400) return;
//            400 invalid token —> login
            if (httpResponse == 400) {
                Context context = MyApplication.instance.getApplicationContext();
                SharedPreferences.Editor loginInf =
                        context.getSharedPreferences("Login", Context.MODE_PRIVATE).edit();
                if (!TextUtils.isEmpty(message))
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                loginInf.putString("id", null);
                loginInf.putString("token", null);
                loginInf.apply();
                Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
