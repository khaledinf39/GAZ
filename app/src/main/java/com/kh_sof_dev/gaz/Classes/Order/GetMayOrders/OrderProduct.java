package com.kh_sof_dev.gaz.Classes.Order.GetMayOrders;
/************************* Mo’min J.Abusaada *************************/
//
//	point.java

import com.kh_sof_dev.gaz.Classes.Products.Product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class OrderProduct implements Serializable {

    private String _id;
    private Product productId;
    private int qty;
    private Double price;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public OrderProduct(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }

        _id = jsonObject.optString("_id");
        productId = new Product(jsonObject.optJSONObject("product_id"));
        qty = jsonObject.optInt("qty");
        price = jsonObject.optDouble("price");
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */

    public JSONObject toJsonObject2() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("isOffer", false);
            jsonObject.put("isOfferQuota", false);
            jsonObject.put("price", 10);
            jsonObject.put("price_name", "حبة");
            jsonObject.put("product_id", "5bfb04d43794590016055590");
            jsonObject.put("qty", 12);
            jsonObject.put("supplier_id", "5bfa59301504700016c0cd20");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}