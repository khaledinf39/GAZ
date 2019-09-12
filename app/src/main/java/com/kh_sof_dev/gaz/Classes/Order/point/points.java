package com.kh_sof_dev.gaz.Classes.Order.point;

import org.json.JSONException;
import org.json.JSONObject;

public  class points {

    private int points;

    private Long money;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public points(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        try {
            money = jsonObject.optLong("money");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            points = jsonObject.optInt("points");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
//    public JSONObject toJsonObject()
//    {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("_id", id);
//            jsonObject.put("name", name);
//
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return jsonObject;
//    }
//
}