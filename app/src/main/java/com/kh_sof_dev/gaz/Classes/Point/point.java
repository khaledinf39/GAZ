package com.kh_sof_dev.gaz.Classes.Point;


import org.json.JSONException;
import org.json.JSONObject;

public class point {
    private String _id,user_id;
    private int points;

    public point(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return;
        }
        points = jsonObject.optInt("points");
        _id = jsonObject.optString("_id");
        user_id = jsonObject.optString("user_id");

    }
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", _id);
            jsonObject.put("points", points);
            jsonObject.put("user_id", user_id);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
