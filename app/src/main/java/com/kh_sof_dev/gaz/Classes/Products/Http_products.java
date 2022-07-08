package com.kh_sof_dev.gaz.Classes.Products;

import android.content.Context;
import android.content.Intent;
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
import com.kh_sof_dev.gaz.MyApplication;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.Login;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Http_products {
    public interface categoriesListener {
        void onSuccess(show_main_catigories catigories);

        void onStart();

        void onFailure(String msg);
    }

    public interface productsListener {
        void onSuccess(show_products products);

        void onStart();

        void onFailure(String msg);
    }

    private RequestQueue queue;

    public void Getcatigories(final Context mcontext, int page, final categoriesListener listener) {
        try {
            listener.onStart();
            String url = mcontext.getString(R.string.api) + "api/category";


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (!TextUtils.isEmpty(response.toString())) {
                                Utils.checkResponse(response.toString());
                            }
                            // display response
                            Log.d("Response", response.toString());
                            try {
                                listener.onSuccess(new show_main_catigories(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
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
            );

            queue.getCache().initialize();
// add it to the RequestQueue
            queue.add(getRequest);
            queue.getCache().clear();
        } catch (Exception ex) {

        }
    }

    public void Get_top20_Products(final Context mcontext, final productsListener listener) {
        try {
            listener.onStart();
            String url = mcontext.getString(R.string.api) + "api/top20prodcuts";


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (!TextUtils.isEmpty(response.toString())) {
                                Utils.checkResponse(response.toString());
                            }
                            // display response
                            Log.d("Response", response.toString());
                            listener.onSuccess(new show_products(response));


                        }
                    },
                    new Response.ErrorListener() {
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
            );

// add it to the RequestQueue
            queue.getCache().clear();
// add it to the RequestQueue
            queue.add(getRequest);
        } catch (Exception ex) {

        }
    }

    public void Get_details_Products(final Context mcontext, final String pro_id, final productsListener listener) {
        try {
            listener.onStart();
            String url = mcontext.getString(R.string.api) + "api/getSingleProduct/" + pro_id;


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (!TextUtils.isEmpty(response.toString())) {
                                Utils.checkResponse(response.toString());
                            }
                            // display response
                            Log.d("Response", response.toString());
                            try {
                                show_products products = new show_products(response);
                                products.getProducts().add(new Product(response.optJSONObject("items")));
                                listener.onSuccess(products);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
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
            );

            queue.getCache().initialize();
// add it to the RequestQueue
            queue.add(getRequest);
            queue.getCache().clear();
        } catch (Exception ex) {

        }
    }

    public void Post_shearch_products(final Context mcontext, final String prod_name, final productsListener listener) {
        listener.onStart();
        user_info user_info = new user_info(mcontext);
        final String supplier_id = user_info.getSupplier_id(mcontext);
        if (supplier_id.equals("")) {
            mcontext.startActivity(new Intent(mcontext, Login.class));

        }
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        ;//"http://httpbin.org/post";
        final String url = mcontext.getString(R.string.api) + "api/search?page=0&limit=10";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            Utils.checkResponse(response);
                        }
                        // response
                        Log.d("Response", response);
                        String jsonData = response;
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            listener.onFailure(e1.getMessage());
                        }
                        listener.onSuccess(new show_products(Jobject));


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error.getMessage()));
                        listener.onFailure("Error.Response");
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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("name", prod_name);
                param.put("supplier_id", supplier_id);
                return param;
            }


        };
        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }

    public void Post_products_category(final Context mcontext, final String categorie_id, final int orderNB, int limit, int page, final productsListener listener) {
        listener.onStart();

        user_info user_info = new user_info(mcontext);
        final String supplier_id = user_info.getSupplier_id(mcontext);
        if (supplier_id.equals("")) {
            mcontext.startActivity(new Intent(mcontext, Login.class));

        }
        Log.d("supllier_id", "Http_prod line 237  :" + supplier_id);

        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        ;//"http://httpbin.org/post";
        final String url = mcontext.getString(R.string.api) + "api/productscategory?page=" + page + "&limit=" + limit + "";
        Log.d("produt url", url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            Utils.checkResponse(response);
                        }
                        // response
                        Log.d("Response", response);
                        String jsonData = response;
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            listener.onFailure(e1.getMessage());
                        }
                        listener.onSuccess(new show_products(Jobject));


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error.getMessage()));
                        listener.onFailure("Error.Response");
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
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                if (orderNB == 4) {
                    param.put("isNewProduct", "true");
                    param.put("category_id", "5c681f80ad8747623305f634");
                }
                if (orderNB == 2) {
                    param.put("isReplacement", "true");
                    param.put("category_id", "5c681f80ad8747623305f634");
                }

                if (orderNB == 3) {
                    param.put("category_id", MyApplication.PRODUCT_TANK_CATEGORY_ID);
                }
                if (orderNB == 1) {
                    param.put("category_id", categorie_id);
                }

                param.put("supplier_id", supplier_id);

                return param;
            }


        };
        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();
        // prepare the Request

    }

    public void Post_checkOrderAvailable(final Context mContext, final String date, final productsListener listener) {
        listener.onStart();

        user_info user_info = new user_info(mContext);
        final String supplier_id = user_info.getSupplier_id(mContext);
        if (supplier_id.equals("")) {
            mContext.startActivity(new Intent(mContext, Login.class));

        }
        Log.d("supllier_id", "Http_prod line 237  :" + supplier_id);

        if (queue == null) {
            queue = Volley.newRequestQueue(mContext);  // this = context
        }
        ;//"http://httpbin.org/post";
        final String url = mContext.getString(R.string.api) + "api/canOrder ";
        Log.d("product url", url);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            Utils.checkResponse(response);
                        }
                        // response
                        Log.d("Response", response);
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            listener.onFailure(e1.getMessage());
                        }
                        listener.onSuccess(new show_products(Jobject));


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d("Error.Response", String.valueOf(error.getMessage()));
                        listener.onFailure("Error.Response");
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
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();

                param.put("supplier_id", supplier_id);
                param.put("order_date", date);

                return param;
            }


        };
        queue.getCache().initialize();
        queue.add(postRequest);
        queue.getCache().clear();
    }

}
