package com.kh_sof_dev.gaz.Classes.Order;
/************************* Mo’min J.Abusaada *************************/
//
//	AddOrder.java

import org.json.*;

import java.util.*;


public class AddOrder{


    private String Notes;
    private String addressDetails;
     private Double deliveryCost;
    private String delivery_time;
    private String delivery_date;
    private List<OrderItem> items;
    private Double lat;
    private Double lng;
    private int paymentType;
    private Double subTotal;
    private int orderType;

    public AddOrder() {
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
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

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */

    public AddOrder(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }
        orderType = jsonObject.getInt("orderType");
        delivery_date = jsonObject.optString("delivery_date");
        try {
            Notes = jsonObject.optString("Notes");
            addressDetails = jsonObject.optString("addressDetails");
            delivery_time = jsonObject.optString("delivery_time");
            try {
                deliveryCost = jsonObject.getDouble("deliveryCost");
            } catch (JSONException e) {
                e.printStackTrace();
            }
           JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
            if (itemsJsonArray != null) {
                items = new ArrayList<>();
                for (int i = 0; i < itemsJsonArray.length(); i++) {
                    JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
                    try {
                        items.add(new OrderItem(itemsObject));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
            try {
                lat = jsonObject.getDouble("lat");
                lng = jsonObject.getDouble("lng");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            paymentType = jsonObject.optInt("paymentType");
            try {
                subTotal = jsonObject.getDouble("subTotal");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            } catch (Exception ex) {
        }




    }

    /**
     * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
     */
    public JSONObject Basket_toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
///9 parameter
            jsonObject.put("Notes", Notes);
            jsonObject.put("addressDetails", addressDetails);
            //jsonObject.put("delivery_time", delivery_time);
            jsonObject.put("deliveryCost", deliveryCost);
            if(items != null && items.size() > 0){
                JSONArray itemsJsonArray = new JSONArray();
                for(OrderItem itemsElement : items){
                    itemsJsonArray.put(itemsElement.toJsonObject());
                }
                jsonObject.put("items", itemsJsonArray);
            }
            jsonObject.put("lat", lat);
            jsonObject.put("lng", lng);
            jsonObject.put("paymentType", paymentType);
            jsonObject.put("subTotal", subTotal);
            jsonObject.put("orderType", orderType);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject Refill_toJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
/// 11 parameter
            jsonObject.put("Notes", Notes);
            jsonObject.put("addressDetails", addressDetails);
            jsonObject.put("delivery_time", delivery_time);
            jsonObject.put("delivery_date", delivery_date);
            jsonObject.put("deliveryCost", deliveryCost);
            if(items != null && items.size() > 0){
                JSONArray itemsJsonArray = new JSONArray();
                for(OrderItem itemsElement : items){
                    itemsJsonArray.put(itemsElement.toJsonObject());
                }
                jsonObject.put("items", itemsJsonArray);
            }
            jsonObject.put("lat", lat);
            jsonObject.put("lng", lng);
            jsonObject.put("paymentType", paymentType);
            jsonObject.put("subTotal", subTotal);
            jsonObject.put("orderType", orderType);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject toJsonObject_for_test()
    {
        JSONObject jsonObject = new JSONObject();
        try {

            if(items != null && items.size() > 0){
                JSONArray itemsJsonArray = new JSONArray();
                for(OrderItem itemsElement : items){
                    itemsJsonArray.put(itemsElement.toJsonObject());
                }
                jsonObject.put("items", itemsJsonArray);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }
//    public JSONObject toJsonObject2()
////    {
////        JSONObject jsonObject = new JSONObject();
////        try {
////            jsonObject.put("BasketId", basketId);
////            jsonObject.put("Notes", notes);
////            jsonObject.put("StatusId", statusId);
////            jsonObject.put("addressDetails",addressDetails);
////            jsonObject.put("coupon", coupon);
////            jsonObject.put("deliveryCost", deliveryCost);
////            jsonObject.put("deliveryOption_id", deliveryOptionId);
////            jsonObject.put("delivery_date", deliveryDate);
////            jsonObject.put("delivery_id", deliveryId);
////            jsonObject.put("isBasket", isBasket);
////            jsonObject.put("isNotFound", isNotFound);
////            if(items != null && items.size() > 0){
////                JSONArray itemsJsonArray = new JSONArray();
////                for(OrderItem itemsElement : items){
////                    itemsJsonArray.put(itemsElement.toJsonObject());
////                }
////                jsonObject.put("items", itemsJsonArray);
////            }
////            jsonObject.put("lat", lng);
////            jsonObject.put("lng", lat);
////            jsonObject.put("paymentType", paymentType);
////            jsonObject.put("subTotal", subTotal);
////            jsonObject.put("user_id", userId);
////        } catch (JSONException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////        return jsonObject;
////    }

}