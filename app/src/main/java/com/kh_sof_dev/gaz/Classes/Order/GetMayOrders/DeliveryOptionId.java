package com.kh_sof_dev.gaz.Classes.Order.GetMayOrders; /************************* Mo’min J.Abusaada *************************/
//
//	DeliveryOptionId.java
import org.json.*;


public class DeliveryOptionId{

	private String id;
	private String name;

	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public DeliveryOptionId(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		id = jsonObject.opt("_id").toString();
		name = jsonObject.opt("name").toString();
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