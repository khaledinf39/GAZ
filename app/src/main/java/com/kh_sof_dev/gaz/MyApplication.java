package com.kh_sof_dev.gaz;

import androidx.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;
import com.google.firebase.FirebaseApp;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends MultiDexApplication {
    public static final String PRODUCT_TANK_CATEGORY_ID = "5c8cb6c10a34fc002491f406";

    @Override
    public void onCreate() {
        super.onCreate();
//        Fabric.with(this, new Crashlytics());
        FirebaseApp.initializeApp(this);
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
