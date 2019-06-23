package com.kh_sof_dev.gaz.Classes.Order;
/************************* Mo’min J.Abusaada *************************/
//
//	show_Myorder.java

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class send_order {

    private List<AddOrder> items;
    private String message;
    private int statusCode;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
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
    public send_order(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        message = jsonObject.opt("message").toString();
        statusCode = jsonObject.optInt("status_code");

        JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
        if (itemsJsonArray != null) {
            items = new ArrayList<>();
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
                {
                    try {
                        items.add(new AddOrder(itemsObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }


        }

        /**
         * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
         */


    }

    public List<AddOrder> getItems() {
        return items;
    }

    public void setItems(List<AddOrder> items) {
        this.items = items;
    }
}