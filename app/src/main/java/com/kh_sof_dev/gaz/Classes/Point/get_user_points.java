package com.kh_sof_dev.gaz.Classes.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class get_user_points {
    private List<point> points;
    private boolean status;
    private String message;
    private int statusCode;

    public List<point> getPoints() {
        return points;
    }

    public void setPoints(List<point> points) {
        this.points = points;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public get_user_points(JSONObject jsonObject) throws JSONException {

        this.points = new ArrayList<>();
        if (jsonObject==null)
        {return;}

        JSONArray catigoriessJsonArray = jsonObject.optJSONArray("items");
        if(catigoriessJsonArray != null){

            for (int i = 0; i < catigoriessJsonArray.length(); i++) {
                JSONObject hayberObject = catigoriessJsonArray.optJSONObject(i);
                points.add(new point(hayberObject));

            }

        }
        message = jsonObject.optString("message");

        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
    }

}
