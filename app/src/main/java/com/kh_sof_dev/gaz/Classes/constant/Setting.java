package com.kh_sof_dev.gaz.Classes.constant;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

public class Setting {
    private String id, name;
    private String value;
    private Long ringe, tax;
    double delivery;
    private Long nb_order;

    public Long getRinge() {
        return ringe;
    }

    public void setRinge(Long ringe) {
        this.ringe = ringe;
    }

    public Long getTax() {
        return tax;
    }

    public void setTax(Long tax) {
        this.tax = tax;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public Long getNb_order() {
        return nb_order;
    }

    public void setNb_order(Long nb_order) {
        this.nb_order = nb_order;
    }

    public Setting(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        id = jsonObject.optString("_id");
        name = jsonObject.optString("name");
        value = jsonObject.optString("value");
    }

    public Setting(Setting setting, Context mcontext) {

        /***************************save**************************/
        SharedPreferences sp = mcontext.getSharedPreferences("constant", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putLong("nb_order", setting.nb_order);
        Ed.putLong("tax", setting.tax);
        Ed.putLong("ringe", setting.ringe);
        Ed.putLong("delivery", Double.doubleToRawLongBits(setting.delivery));


        Ed.commit();
        new Setting(mcontext);
    }

    public Setting(Context mcontext) {
        final SharedPreferences sp = mcontext.getSharedPreferences("constant", MODE_PRIVATE);
        nb_order = sp.getLong("nb_order", 10);
        tax = sp.getLong("tax", 0);
        ringe = sp.getLong("ringe", 0);
        delivery = Double.longBitsToDouble(sp.getLong("delivery", Double.doubleToRawLongBits(0)));

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
