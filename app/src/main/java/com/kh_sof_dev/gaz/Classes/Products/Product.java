package com.kh_sof_dev.gaz.Classes.Products;

import org.json.JSONException;
import org.json.JSONObject;


public class Product {

    private String id;
    private String description;
    private String category_id;
    private String createat;
    private String image;
    private String warrenty;
    private String name;
    private int rate;
    private Double price,price_buy_new;

    ///fore order
    private int qty;

    public int getID_() {
        return ID_;
    }

    public void setID_(int ID_) {
        this.ID_ = ID_;
    }

    private int ID_;

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice_buy_new() {
        return price_buy_new;
    }

    public void setPrice_buy_new(Double price_buy_new) {
        this.price_buy_new = price_buy_new;
    }

    public Product() {
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public Product(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return;
        }
        id = jsonObject.optString("_id");
        description = jsonObject.optString("description");
        rate = jsonObject.getInt("rate");
        createat = jsonObject.optString("createat");
        price = jsonObject.getDouble("price");
        price_buy_new = jsonObject.getDouble("price_buy_new");
        image = jsonObject.optString("image");

        name = jsonObject.optString("name");
        warrenty = jsonObject.optString("warrenty");
        category_id = jsonObject.getString("category_id");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("_id", id);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}