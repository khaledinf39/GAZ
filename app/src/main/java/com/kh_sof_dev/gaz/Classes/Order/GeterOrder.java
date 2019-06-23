package com.kh_sof_dev.gaz.Classes.Order;
/************************* Mo’min J.Abusaada *************************/
//
//	AddOrder.java

import com.kh_sof_dev.gaz.Classes.Products.Product;
import com.kh_sof_dev.gaz.Classes.User.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GeterOrder {
// more details
    private String comment,ratedate;
    private int rate;
    private Boolean isRate,isOpen;


    ////////////////

    private String Notes;
    private String addressDetails;
    private Double deliveryCost;
    private String delivery_time;
    private String delivery_date;
    private List<item> items;
    private Double lat;
    private Double lng;
    private int paymentType;
    private Double subTotal;
    private String orderType;
    private String city;
    private user user_id;


    //////////////get and set///////////////////////

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRatedate() {
        return ratedate;
    }

    public void setRatedate(String ratedate) {
        this.ratedate = ratedate;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(Boolean rate) {
        isRate = rate;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public user getUser_id() {
        return user_id;
    }

    public void setUser_id(user user_id) {
        this.user_id = user_id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public List<item> getItems() {
        return items;
    }

    public void setItems(List<item> items) {
        this.items = items;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public GeterOrder(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }
        orderType = jsonObject.opt("orderType").toString();
        delivery_date = jsonObject.opt("delivery_date").toString();
        Notes = jsonObject.opt("Notes").toString();
        addressDetails = jsonObject.opt("addressDetails").toString();
        delivery_time = jsonObject.opt("delivery_time").toString();
        deliveryCost = jsonObject.getDouble("deliveryCost");
        JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
        if (itemsJsonArray != null) {
            items = new ArrayList<>();
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
                {
                    items.add(new item(itemsObject));

                }

            }

            lat = jsonObject.getDouble("lat");
            lng = jsonObject.getDouble("lng");
            paymentType = jsonObject.optInt("paymentType");
            subTotal = jsonObject.getDouble("subTotal");
            city = jsonObject.getString("city");
            user_id = new user(jsonObject.optJSONObject("user_id"));
try{
    comment = jsonObject.getString("comment");
    ratedate = jsonObject.getString("rateDate");
    rate = jsonObject.getInt("rate");
    isOpen = jsonObject.getBoolean("isOpen");
    isRate = jsonObject.getBoolean("isRate");

}catch (Exception e){

}

        }
    }
        /**
         * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
         */
        public JSONObject toJsonObject () throws JSONException {
            JSONObject jsonObject = new JSONObject();


                jsonObject.put("Notes", Notes);
                jsonObject.put("addressDetails", addressDetails);
                jsonObject.put("delivery_time", delivery_time);
                jsonObject.put("deliveryCost", deliveryCost);
//                if (items != null && items.size() > 0) {
//                    JSONArray itemsJsonArray = new JSONArray();
//                    for (items itemsElement : items) {
//                        itemsJsonArray.put(itemsElement.toJsonObject());
//                    }
//                    jsonObject.put("items", itemsJsonArray);
//                }
                jsonObject.put("lat", lat);
                jsonObject.put("lng", lng);
                jsonObject.put("paymentType", paymentType);
                jsonObject.put("subTotal", subTotal);
                jsonObject.put("orderType", orderType);

            return jsonObject;
        }
    }

class item{
        private String id;
        private Double price;
        private int qty;
        private Product product_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }
    public item(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }
        id = jsonObject.opt("_id").toString();
        price = jsonObject.getDouble("price");
        qty = jsonObject.getInt("qty");
product_id=new Product(jsonObject.optJSONObject("product_id"));





    }

}
