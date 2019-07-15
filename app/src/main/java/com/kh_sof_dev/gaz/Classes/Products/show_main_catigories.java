package com.kh_sof_dev.gaz.Classes.Products;

import com.kh_sof_dev.gaz.Classes.pagatination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;


public class show_main_catigories{

    private List<catigories> catigoriess;
    private String message;
    private com.kh_sof_dev.gaz.Classes.pagatination pagatination;
    private boolean status;
    private int statusCode;

    public void setcatigoriess(List<catigories> catigoriess){
        this.catigoriess = catigoriess;
    }
    public List<catigories> getcatigoriess(){
        return this.catigoriess;
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
    public show_main_catigories(JSONObject jsonObject) throws JSONException {
        catigoriess = new ArrayList<>();
        if(jsonObject == null){
            return;
        }
        JSONArray catigoriessJsonArray = jsonObject.optJSONArray("items");
        if(catigoriessJsonArray != null){
            for (int i = 0; i < catigoriessJsonArray.length(); i++) {
                JSONObject catigoriessObject = catigoriessJsonArray.optJSONObject(i);
                catigoriess.add(new catigories(catigoriessObject));
                 }
         // catigoriess = catigoriessArrayList;
        }		message = jsonObject.optString("message");

//       pagatination=new pagatination(jsonObject.getJSONObject("pagenation"));
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
            if(catigoriess != null && catigoriess.size() > 0){
                JSONArray catigoriessJsonArray = new JSONArray();
                for(catigories catigoriessElement : catigoriess){
                    catigoriessJsonArray.put(catigoriessElement.toJsonObject());
                }
                jsonObject.put("catigoriess", catigoriessJsonArray);
            }
            jsonObject.put("message", message);
            jsonObject.put("pagatination", pagatination);
            jsonObject.put("status", status);
            jsonObject.put("status_code", statusCode);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}