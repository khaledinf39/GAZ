package com.kh_sof_dev.gaz.Classes.constant;


import org.json.JSONException;
import org.json.JSONObject;

public class ads{

    private String id;
    private String details;
    private String image;
    private String name;
    private int priceAfter;
    private int priceBefore;
    private String type;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setDetails(String details){
        this.details = details;
    }
    public String getDetails(){
        return this.details;
    }
    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return this.image;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPriceAfter(int priceAfter){
        this.priceAfter = priceAfter;
    }
    public int getPriceAfter(){
        return this.priceAfter;
    }
    public void setPriceBefore(int priceBefore){
        this.priceBefore = priceBefore;
    }
    public int getPriceBefore(){
        return this.priceBefore;
    }
    public void setType(String type){
        this.type = type;
    }
    public String getType(){
        return this.type;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public ads(JSONObject jsonObject){
        if(jsonObject == null){
            return;
        }
        id = jsonObject.opt("_id").toString();
        details = jsonObject.opt("details").toString();
        image = jsonObject.opt("image").toString();
        name = jsonObject.opt("name").toString();
        priceAfter = jsonObject.optInt("price_after");
        priceBefore = jsonObject.optInt("price_before");
        type = jsonObject.opt("type").toString();
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", id);
            jsonObject.put("details", details);
            jsonObject.put("image", image);
            jsonObject.put("name", name);
            jsonObject.put("price_after", priceAfter);
            jsonObject.put("price_before", priceBefore);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}