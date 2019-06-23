package com.kh_sof_dev.gaz.Classes.Order.GetMayOrders; /************************* Mo’min J.Abusaada *************************/
//
//	Pagenation.java
import org.json.*;


public class Pagenation{

	private int pageNumber;
	private int size;
	private int totalElements;
	private int totalPages;

	public void setPageNumber(int pageNumber){
		this.pageNumber = pageNumber;
	}
	public int getPageNumber(){
		return this.pageNumber;
	}
	public void setSize(int size){
		this.size = size;
	}
	public int getSize(){
		return this.size;
	}
	public void setTotalElements(int totalElements){
		this.totalElements = totalElements;
	}
	public int getTotalElements(){
		return this.totalElements;
	}
	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}
	public int getTotalPages(){
		return this.totalPages;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Pagenation(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		pageNumber = jsonObject.optInt("pageNumber");
		size = jsonObject.optInt("size");
		totalElements = jsonObject.optInt("totalElements");
		totalPages = jsonObject.optInt("totalPages");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("pageNumber", pageNumber);
			jsonObject.put("size", size);
			jsonObject.put("totalElements", totalElements);
			jsonObject.put("totalPages", totalPages);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}