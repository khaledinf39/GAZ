package com.kh_sof_dev.gaz.Classes.constant.contact_us;

import org.json.JSONException;
import org.json.JSONObject;


public class contact_us{

    private String id;
    private String data;
    private String name;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setData(String data){
        this.data = data;
    }
    public String getData(){
        return this.data;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public contact_us(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        id = jsonObject.optString("_id");
        data = jsonObject.optString("data");
        name = jsonObject.optString("name");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", id);
            jsonObject.put("data", data);
            jsonObject.put("name", name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}


