package com.kh_sof_dev.gaz.Classes.constant;

import org.json.JSONException;
import org.json.JSONObject;

public  class City{

    private String id;

    private String name;

    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

    String item(){
        return name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }


    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public City(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        id = jsonObject.optString("_id");
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
            jsonObject.put("name", name);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}