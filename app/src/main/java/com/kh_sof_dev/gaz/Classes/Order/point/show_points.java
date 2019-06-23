package com.kh_sof_dev.gaz.Classes.Order.point;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class show_points {

    private List<points> items;
    private String message;
    private boolean status;
    private int statusCode;

    public void setItems(List<points> items){
        this.items = items;
    }
    public List<points> getItems(){
        return this.items;
    }
    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
    public boolean isStatus()
    {
        return this.status;
    }
    public void setStatusCode(int statusCode){
        this.statusCode = statusCode;
    }
    public int getStatusCode(){
        return this.statusCode;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public show_points(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
        if(itemsJsonArray != null){
            items = new ArrayList<>();
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
                items.add(new points(itemsObject));
            }

        }		message = jsonObject.opt("message").toString();
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */


}
