package com.kh_sof_dev.gaz.Classes.Order.coupon;

import org.json.JSONException;
import org.json.JSONObject;

public class coupon {
    private String id,coupon,msg,dt_from,dt_to;
    private Double discount;
    public coupon(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return;
        }
        id = (String) jsonObject.opt("_id");
        coupon = jsonObject.opt("coupon").toString();
        dt_from = jsonObject.opt("dt_from").toString();
        dt_to = jsonObject.opt("dt_to").toString();
        msg = jsonObject.opt("msg").toString();
        discount = jsonObject.optDouble("discount_rate");

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDt_from() {
        return dt_from;
    }

    public void setDt_from(String dt_from) {
        this.dt_from = dt_from;
    }

    public String getDt_to() {
        return dt_to;
    }

    public void setDt_to(String dt_to) {
        this.dt_to = dt_to;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
