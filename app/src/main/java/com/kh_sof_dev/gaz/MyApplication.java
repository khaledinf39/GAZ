package com.kh_sof_dev.gaz;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class MyApplication extends Application {
    public static final String PRODUCT_TANK_CATEGORY_ID = "5c8cb6c10a34fc002491f406";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(this);
        Realm.init(this);
    }
}
