package com.kh_sof_dev.gaz.Classes.Products;

import com.kh_sof_dev.gaz.Classes.pagatination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;


public class show_main_catigories{

    private List<Categories> catigoriesses;
    private String message;
    private com.kh_sof_dev.gaz.Classes.pagatination pagatination;
    private boolean status;
    private int statusCode;

    public void setcatigoriess(List<Categories> catigoriesses){
        this.catigoriesses = catigoriesses;
    }
    public List<Categories> getcatigoriess(){
        return this.catigoriesses;
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
        catigoriesses = new ArrayList<>();
        if(jsonObject == null){
            return;
        }
        JSONArray catigoriessJsonArray = jsonObject.optJSONArray("items");
        if(catigoriessJsonArray != null){
            for (int i = 0; i < catigoriessJsonArray.length(); i++) {
                JSONObject catigoriessObject = catigoriessJsonArray.optJSONObject(i);
                catigoriesses.add(new Categories(catigoriessObject));
                 }
         // catigoriesses = catigoriessArrayList;
        }		message = jsonObject.optString("message");

//       pagatination=new pagatination(jsonObject.optJSONObject("pagenation"));
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
            if(catigoriesses != null && catigoriesses.size() > 0){
                JSONArray catigoriessJsonArray = new JSONArray();
                for(Categories catigoriessElement : catigoriesses){
                    catigoriessJsonArray.put(catigoriessElement.toJsonObject());
                }
                jsonObject.put("catigoriesses", catigoriessJsonArray);
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