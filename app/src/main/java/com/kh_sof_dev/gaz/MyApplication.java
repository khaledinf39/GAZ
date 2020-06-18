package com.kh_sof_dev.gaz;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    public static final String PRODUCT_TANK_CATEGORY_ID = "5c8cb6c10a34fc002491f406";

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(this);
        Realm.init(this);

    }

    public static Realm getRealm(){
        RealmConfiguration config1 = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();

        return Realm.getInstance(config1);
    }
}
