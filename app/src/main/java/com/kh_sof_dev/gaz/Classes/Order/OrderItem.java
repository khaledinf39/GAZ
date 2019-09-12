package com.kh_sof_dev.gaz.Classes.Order;
/************************* Mo’min J.Abusaada *************************/
//
//	point.java
import com.kh_sof_dev.gaz.Classes.Products.Product;

import org.json.*;


public class OrderItem{

    private Double price;
    private String productId;
    private int qty;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public OrderItem(Product product) {
        this.productId=product.getId();
        this.price=product.getPrice();
        this.qty=product.getQty();
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public OrderItem(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return;
        }
       price = jsonObject.optDouble("price");
        productId = jsonObject.optString("product_id");
        qty = jsonObject.optInt("qty");
         }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("price", price);
            jsonObject.put("product_id", productId);
            jsonObject.put("qty", qty);
             } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }


}