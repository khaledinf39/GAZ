package com.kh_sof_dev.gaz.Classes.Products;

import com.kh_sof_dev.gaz.Classes.pagatination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class show_products {

    private List<Product> products;
    private String message;
    private pagatination pagatination;
    private boolean status;
    private int statusCode;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
    public void setPagatination(pagatination pagatination){
        this.pagatination = pagatination;
    }
    public pagatination getPagatination(){
        return this.pagatination;
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
    public show_products(JSONObject jsonObject) {
        products = new ArrayList<>();
        if(jsonObject == null){
            return;
        }
        JSONArray JsonArray = jsonObject.optJSONArray("items");
        if(JsonArray != null){
            for (int i = 0; i < JsonArray.length(); i++) {
                JSONObject catigoriessObject = JsonArray.optJSONObject(i);
                try {
                    products.add(new Product(catigoriessObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
         // catigoriess = catigoriessArrayList;
        }		message = jsonObject.optString("message");
        try {
            pagatination=new pagatination(jsonObject.optJSONObject("pagenation"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
            jsonObject.put("message", message);
            jsonObject.put("pagenation", pagatination);
            jsonObject.put("status", status);
            jsonObject.put("status_code", statusCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonObject;
    }

}