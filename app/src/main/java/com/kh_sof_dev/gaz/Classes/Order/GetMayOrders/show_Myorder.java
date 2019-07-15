package com.kh_sof_dev.gaz.Classes.Order.GetMayOrders; /************************* Mo’min J.Abusaada *************************/
//
//	show_Myorder.java
import org.json.*;
import java.util.*;


public class show_Myorder {

	private List<Order_getter> ordergetters;
	private String message;
	private Pagenation pagenation;
	private boolean status;
	private int statusCode;

	public void setOrdergetters(List<Order_getter> ordergetters){
		this.ordergetters = ordergetters;
	}
	public List<Order_getter> getOrdergetters(){
		return this.ordergetters;
	}
	public void setMessage(String message){
		this.message = message;
	}
	public String getMessage(){
		return this.message;
	}
	public void setPagenation(Pagenation pagenation){
		this.pagenation = pagenation;
	}
	public Pagenation getPagenation(){
		return this.pagenation;
	}
	public void setStatus(boolean status){
		this.status = status;
	}
	public boolean isStatus()
	{
		return this.status;
	}
	public void setStatusCode(int statusCode){
		this.statusCode = statusCode;
	}
	public int getStatusCode(){
		return this.statusCode;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public show_Myorder(JSONObject jsonObject) throws JSONException {
		if(jsonObject == null){
			return;
		}
		JSONArray itemsJsonArray = jsonObject.optJSONArray("items");
		if(itemsJsonArray != null){
			ordergetters = new ArrayList<>();
			for (int i = 0; i < itemsJsonArray.length(); i++) {
				JSONObject itemsObject = itemsJsonArray.optJSONObject(i);
				ordergetters.add(new Order_getter(itemsObject));
			}

		}		message = jsonObject.optString("message");
		pagenation = new Pagenation(jsonObject.optJSONObject("pagenation"));
		status = jsonObject.optBoolean("status");
		statusCode = jsonObject.optInt("status_code");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			if(ordergetters != null && ordergetters.size() > 0){
				JSONArray itemsJsonArray = new JSONArray();
				for(Order_getter itemsElement : ordergetters){
					//itemsJsonArray.put(itemsElement.());
				}
				jsonObject.put("ordergetters", itemsJsonArray);
			}
			jsonObject.put("message", message);
			jsonObject.put("pagenation", pagenation.toJsonObject());
			jsonObject.put("status", status);
			jsonObject.put("status_code", statusCode);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}