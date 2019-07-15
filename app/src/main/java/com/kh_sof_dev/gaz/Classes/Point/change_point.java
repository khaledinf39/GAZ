package com.kh_sof_dev.gaz.Classes.Point;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class change_point {
    private List<point> points;
    private boolean status;
    private String message;
    private int statusCode;
    public change_point(JSONObject jsonObject) throws JSONException {

       // this.points = new ArrayList<>();
        if (jsonObject==null)
        {return;}

//        JSONArray catigoriessJsonArray = jsonObject.optJSONArray("points");
//        if(catigoriessJsonArray != null){
//
//            for (int i = 0; i < catigoriessJsonArray.length(); i++) {
//                JSONObject hayberObject = catigoriessJsonArray.optJSONObject(i);
//                points.add(new point(hayberObject));
//
//            }
//
//        }
        message = jsonObject.optString("message");

        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
    }

}
