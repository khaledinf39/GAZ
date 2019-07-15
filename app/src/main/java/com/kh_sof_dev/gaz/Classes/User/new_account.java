package com.kh_sof_dev.gaz.Classes.User;

import org.json.JSONException;
import org.json.JSONObject;


public class new_account {

    private user items;
    private String message;
    private boolean status;
    private int statusCode;

    public void setItems(user items) {
        this.items = items;
    }

    public user getItems() {
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
    public new_account(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        message = jsonObject.optString("message");
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
        items = new user(jsonObject.optJSONObject("items"));

    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("items", items.toJsonObject());
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