package com.kh_sof_dev.gaz.Classes.Order.GetMayOrders;
//************************* Mo’min J.Abusaada *************************/

import com.kh_sof_dev.gaz.Classes.User.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Order_getter implements Serializable {

    private String id;
    private String notes;
    private String city;
    private int statusId;
    private Double total = 0.0;
    private String addressDetails;
    private String createAt;
    private String delivery_date;
    private Double deliveryCost = 0.0;
    private int orderType;
    private String delivery_time;
    private List<OrderProduct> items;
    private double lat = 0.0;
    private double lng = 0.0;
    private int paymentType;
    private Double subTotal = 0.0;
    private user userId;
    private DriverId driverId;

    public DriverId getDriverId() {
        return driverId;
    }

    public void setDriverId(DriverId driverId) {
        this.driverId = driverId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDelivery_date() {
        return delivery_date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public List<OrderProduct> getItems() {
        return items;
    }

    public void setItems(List<OrderProduct> items) {
        this.items = items;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
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

    public user getUserId() {
        return userId;
    }

    public void setUserId(user userId) {
        this.userId = userId;
    }

    public Order_getter() {
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public Order_getter(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }
        id = jsonObject.optString("_id");
        try {
            delivery_date = jsonObject.optString("delivery_date");
            delivery_time = jsonObject.optString("delivery_time");
        } catch (Exception e) {
            e.printStackTrace();
        }
        notes = jsonObject.optString("Notes");
        statusId = jsonObject.optInt("StatusId");
        total = jsonObject.optDouble("Total");
        addressDetails = jsonObject.optString("addressDetails");
        //city = jsonObject.optString("city");
        createAt = jsonObject.optString("createAt");
        deliveryCost = jsonObject.optDouble("deliveryCost");
        JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
        if (itemsJsonArray != null) {
            items = new ArrayList<>();
            for (int i = 0; i < itemsJsonArray.length(); i++) {
                JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
                items.add(new OrderProduct(itemsObject));
            }

        }
        lat = jsonObject.optDouble("lat");
        lng = jsonObject.optDouble("lng");
        paymentType = jsonObject.optInt("paymentType");
        subTotal = jsonObject.optDouble("subTotal");
//
        orderType = jsonObject.optInt("orderType");
        userId = new user(jsonObject.optJSONObject("user_id"));
        try {
        } catch (Exception e) {


        }
        try {
            driverId = new DriverId(jsonObject.optJSONObject("driver_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */

}