package com.kh_sof_dev.gaz.Classes.Order;
/************************* Mo’min J.Abusaada *************************/
//
//	show_Myorder.java
import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.Order_getter;
import com.kh_sof_dev.gaz.Classes.pagatination;

import org.json.*;
import java.util.*;


public class show_order {

    private List<Order_getter> items;
    private String message;
    private int statusCode;
private pagatination pagatination;

    public com.kh_sof_dev.gaz.Classes.pagatination getPagatination() {
        return pagatination;
    }

    public void setPagatination(com.kh_sof_dev.gaz.Classes.pagatination pagatination) {
        this.pagatination = pagatination;
    }

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
    public show_order(JSONObject jsonObject)  {
        if (jsonObject == null) {
            return;
        }
        JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
        if (itemsJsonArray != null) {
            items = new ArrayList<>();
            try {
                pagatination=new pagatination(jsonObject.optJSONObject("pagenation"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            message = jsonObject.optString("message");
            statusCode = jsonObject.optInt("status_code");
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
                {
                    try {
                        items.add(new Order_getter(itemsObject));
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

    public List<Order_getter> getItems() {
        return items;
    }

    public void setItems(List<Order_getter> items) {
        this.items = items;
    }
}