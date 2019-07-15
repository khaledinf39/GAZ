package com.kh_sof_dev.gaz.Classes.constant;

import org.json.JSONException;
import org.json.JSONObject;


import org.json.JSONArray;

import java.util.*;


public class show_setting {

    private List<Setting> items;
    private String message;
    private boolean status;
    private int statusCode;

    public void setItems(List<Setting> items) {
        this.items = items;
    }

    public List<Setting> getItems() {
        return this.items;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public show_setting(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }
        JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
        if (itemsJsonArray != null) {
            items = new ArrayList<>();
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
                items.add(new Setting(itemsObject));
            }

        }
        message = jsonObject.optString("message");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (items != null && items.size() > 0) {
                JSONArray itemsJsonArray = new JSONArray();

            }
            jsonObject.put("message", message);
            jsonObject.put("status", status);
            jsonObject.put("status_code", statusCode);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }



}