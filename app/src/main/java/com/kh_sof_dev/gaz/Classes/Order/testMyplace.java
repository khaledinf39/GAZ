package com.kh_sof_dev.gaz.Classes.Order;
/************************* Mo’min J.Abusaada *************************/
//
//	show_Myorder.java

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class testMyplace {

    private String message;
    private int statusCode;
private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * Instantiate the instance using the passed jsonObject to set the properties values
     */
    public testMyplace(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            return;
        }
        JSONArray itemsJsonArray = jsonObject.optJSONArray("items");

        status = jsonObject.getBoolean("status");
            message = jsonObject.opt("message").toString();
            statusCode = jsonObject.optInt("status_code");
        }

        /**
         * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
         */


    }


