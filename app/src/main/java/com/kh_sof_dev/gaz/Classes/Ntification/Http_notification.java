package com.kh_sof_dev.gaz.Classes.Ntification;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Http_notification {
    public interface notificationListener{
        void onSuccess(show_notif show_notif);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void Getnotification(final Context mcontext, final notificationListener listener)
    {
        final user_info user_info=new user_info(mcontext);
        try{ listener.onStart();
            String url=mcontext.getString(R.string.api)+"api/notifications";


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            if (!TextUtils.isEmpty(response.toString())) {
                                Utils.checkResponse(response.toString());
                            }
                            try {
                                listener.onSuccess(new show_notif(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            try {
                                if (response != null) {
                                    String res = new String(response.data, StandardCharsets.UTF_8);
                                    Log.e("error response", res);
                                    if (!TextUtils.isEmpty(res)) {
                                        Utils.checkResponse(res);
                                    }
                                }
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

//                        Log.d("Error.Response", error.getMessage());
                        }
                    }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  param = new HashMap<String, String>();
                    param.put("token",user_info.getToken());
                    return  param;
                }
            };

            queue.getCache().initialize();
// add it to the RequestQueue
            queue.add(getRequest);
            queue.getCache().clear();
        }catch (Exception ex){

        }}

    public void Put_Read_notifiction(final Context mcontext,final String id_notfy,final  notificationListener lisenner){
        RequestQueue queue = Volley.newRequestQueue(mcontext);  // this = context
        final user_info user_info_=new user_info(mcontext);
        String url = mcontext.getString(R.string.api)+"api/notifications/"+id_notfy;

// Request a json response from the provided URL

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        String jsonData = response;
                        if (!TextUtils.isEmpty(response)) {
                            Utils.checkResponse(response);
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error.getMessage()));
                        NetworkResponse response = error.networkResponse;
                        try {
                            if (response != null) {
                                String res = new String(response.data, StandardCharsets.UTF_8);
                                Log.e("error response", res);
                                if (!TextUtils.isEmpty(res)) {
                                    Utils.checkResponse(res);
                                }
                            }
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                    }
                }
        )
//        {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String>  Headers = new HashMap<String, String>();
//                Headers.put("token",user_info_.getToken());
//
//                return Headers;
//            }
//        }
                ;
        queue.add(postRequest);

// Add the request to the RequestQueue.


    }

}
