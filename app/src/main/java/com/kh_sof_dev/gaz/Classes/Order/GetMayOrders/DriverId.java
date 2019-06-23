package com.kh_sof_dev.gaz.Classes.Order.GetMayOrders; /************************* Mo’min J.Abusaada *************************/
//
//	DriverId.java
import org.json.*;
import java.util.*;


public class DriverId{

	private String id;
	private String address;
	private String createAt;
	private String dtDob;
	private String email;
	private Object fcmToken;
	private String image;
	private List<String> images;
	private boolean isBlock;
	private String name;
	private String password;
	private String phoneNumber;
	private String supplierId;
	private String token;

	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return this.id;
	}
	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	public void setCreateAt(String createAt){
		this.createAt = createAt;
	}
	public String getCreateAt(){
		return this.createAt;
	}
	public void setDtDob(String dtDob){
		this.dtDob = dtDob;
	}
	public String getDtDob(){
		return this.dtDob;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return this.email;
	}
	public void setFcmToken(Object fcmToken){
		this.fcmToken = fcmToken;
	}
	public Object getFcmToken(){
		return this.fcmToken;
	}
	public void setImage(String image){
		this.image = image;
	}
	public String getImage(){
		return this.image;
	}
	public void setImages(List<String> images){
		this.images = images;
	}
	public List<String> getImages(){
		return this.images;
	}
	public void setIsBlock(boolean isBlock){
		this.isBlock = isBlock;
	}
	public boolean isIsBlock()
	{
		return this.isBlock;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return this.password;
	}
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumber(){
		return this.phoneNumber;
	}
	public void setSupplierId(String supplierId){
		this.supplierId = supplierId;
	}
	public String getSupplierId(){
		return this.supplierId;
	}
	public void setToken(String token){
		this.token = token;
	}
	public String getToken(){
		return this.token;
	}

	public DriverId() {

	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */

	public DriverId(JSONObject jsonObject) throws JSONException {
		if(jsonObject == null){
			return;
		}
		id = jsonObject.opt("_id").toString();
		address = jsonObject.opt("address").toString();
		createAt = jsonObject.opt("createAt").toString();
		dtDob = jsonObject.opt("dt_dob").toString();
		email = jsonObject.opt("email").toString();
		fcmToken = jsonObject.opt("fcmToken").toString();
		image = jsonObject.opt("image").toString();
		JSONArray imagesTmp = jsonObject.optJSONArray("images");
		if(imagesTmp != null){
			images = new ArrayList<>();
			for(int i = 0; i < imagesTmp.length(); i++){
				images.add(imagesTmp.get(i).toString());
			}
		}
		isBlock = jsonObject.optBoolean("isBlock");
		name = jsonObject.opt("name").toString();
		password = jsonObject.opt("password").toString();
		phoneNumber = jsonObject.opt("phone_number").toString();
		supplierId = jsonObject.opt("supplier_id").toString();
		token = jsonObject.opt("token").toString();
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("_id", id);
			jsonObject.put("address", address);
			jsonObject.put("createAt", createAt);
			jsonObject.put("dt_dob", dtDob);
			jsonObject.put("email", email);
			jsonObject.put("fcmToken", fcmToken);
			jsonObject.put("image", image);
			jsonObject.put("images", images);
			jsonObject.put("isBlock", isBlock);
			jsonObject.put("name", name);
			jsonObject.put("password", password);
			jsonObject.put("phone_number", phoneNumber);
			jsonObject.put("supplier_id", supplierId);
			jsonObject.put("token", token);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}