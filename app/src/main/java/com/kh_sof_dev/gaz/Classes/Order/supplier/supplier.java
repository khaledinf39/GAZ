package com.kh_sof_dev.gaz.Classes.Order.supplier;

import org.json.JSONException;
import org.json.JSONObject;

public class supplier {
    private String id,name,details,image,email,password,__v;

    public supplier(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return;
        }
        id = (String) jsonObject.opt("_id");
        name = jsonObject.opt("name").toString();
        details = jsonObject.opt("details").toString();
        image = jsonObject.opt("image").toString();
        __v = jsonObject.opt("__v").toString();
        email = jsonObject.opt("email").toString();
        password = jsonObject.opt("password").toString();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
