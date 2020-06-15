package com.kh_sof_dev.gaz.Classes.constant;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.About_us.show_s_cus;
import com.kh_sof_dev.gaz.Classes.constant.contact_us.show_contact_ways;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Http_get_constant {
    public interface socialListener {
        void onSuccess(show_contact_ways contact_ways);

        void onStart();

        void onFailure(String msg);
    }

    public interface staticPageListener {
        void onSuccess(show_s_cus stticpage);

        void onStart();

        void onFailure(String msg);
    }

    public interface citesListener {
        void onSuccess(show_cites cites);

        void onStart();

        void onFailure(String msg);
    }

    public interface sittingListener {
        void onSuccess(show_setting setting);

        void onStart();

        void onFailure(String msg);
    }

    public interface OnoffersListener {
        void onSuccess(show_ads ads);

        void onStart();

        void onFailure(String msg);
    }

    RequestQueue queue = null;

    ////1
    public void social(final Context mcontext, final socialListener listener) {
        listener.onStart();
        String url = mcontext.getString(R.string.api) + "api/social";
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        listener.onSuccess(new show_contact_ways(response));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }

    //2
    public void Contact(final Context mcontext, final socialListener listener) {
        listener.onStart();
        String url = mcontext.getString(R.string.api) + "api/Contact";
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        listener.onSuccess(new show_contact_ways(response));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }

    ///3
    public void getStaticPage(final Context mcontext, final staticPageListener listener) {
        listener.onStart();
        String url = mcontext.getString(R.string.api) + "api/getStaticPage";
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        listener.onSuccess(new show_s_cus(response));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }

    //4
    public void cites(final Context mcontext, final citesListener listener) {
        listener.onStart();
        String url = mcontext.getString(R.string.api) + "api/city";
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        listener.onSuccess(new show_cites(response));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onFailure(String.valueOf(error.getMessage()));

//                    Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }
    ///5

    public void setting(final Context mcontext, final sittingListener listener) {
        user_info user_info = new user_info(mcontext);
        String supplier_id = user_info.getSupplier_id(mcontext);
        if (supplier_id.equals("")) {
            mcontext.startActivity(new Intent(mcontext, Login.class));

        }
        Log.d("supllier_id", "Http_cons line 204  :" + supplier_id);

        listener.onStart();
        String url = mcontext.getString(R.string.api) + "api/settings/" + supplier_id;
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            listener.onSuccess(new show_setting(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.d("Error.Response", error.getMessage());
                    }
                }
        );

// add it to the RequestQueue
        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }

    ////6
    public void Time(final Context mcontext, final citesListener listener) {
        user_info user_info = new user_info(mcontext);
        String supplier_id = user_info.getSupplier_id(mcontext);
        if (supplier_id.equals("")) {
            mcontext.startActivity(new Intent(mcontext, Login.class));

        }
        Log.d("supllier_id", "Http_cont  Time  line 253  :" + supplier_id);

        listener.onStart();
        String url = mcontext.getString(R.string.api) + "api/delivery_time/" + supplier_id;
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        listener.onSuccess(new show_cites(response));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }

    ///7
    public void GetAds(final Context mcontext, final OnoffersListener listener) {
        listener.onStart();
        String url = mcontext.getString(R.string.api) + "api/adv/adv";
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);
        }  // this = context


        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        listener.onSuccess(new show_ads(response));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );

        queue.getCache().initialize();
        queue.add(getRequest);
        queue.getCache().clear();
    }
}
