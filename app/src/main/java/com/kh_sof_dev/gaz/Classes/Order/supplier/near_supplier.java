package com.kh_sof_dev.gaz.Classes.Order.supplier;

import org.json.JSONException;
import org.json.JSONObject;


public class near_supplier {

    private supplier items;
    private String message;
    private boolean status;
    private int statusCode;

    public void setItems(supplier items){
        this.items = items;
    }
    public supplier getItems(){
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
    public near_supplier(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return;
        }

        items = new supplier(jsonObject.optJSONObject("items"));

        message = jsonObject.opt("message").toString();
        status = jsonObject.optBoolean("status");
        statusCode = jsonObject.optInt("status_code");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {

                jsonObject.put("items", items);

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