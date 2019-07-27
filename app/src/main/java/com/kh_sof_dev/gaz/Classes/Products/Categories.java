package com.kh_sof_dev.gaz.Classes.Products;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class Categories implements Serializable {

    private int v;
    private String id;
    private String image;
    private String name;

    public void setV(int v) {
        this.v = v;
    }

    public int getV() {
        return this.v;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public Categories() {
    }

    public Categories(JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        id = jsonObject.optString("_id");
        name = jsonObject.optString("name");
        try {
            image = jsonObject.optString("image");
            v = jsonObject.optInt("__v");
        } catch (Exception exc) {
            exc.printStackTrace();
        }


    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("__v", v);
            jsonObject.put("_id", id);
            jsonObject.put("image", image);
            jsonObject.put("name", name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}