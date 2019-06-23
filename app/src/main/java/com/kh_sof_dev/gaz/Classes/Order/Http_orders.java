package com.kh_sof_dev.gaz.Classes.Order;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kh_sof_dev.gaz.Classes.Order.point.show_points;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Http_orders {
    RequestQueue queue=null;
    public interface OnOrder_lisennter{
        void onSuccess(send_order order);

        void onStart();
        void onFailure(String msg);
    }
    public interface OnOrder_geter_lisennter{
        void onSuccess(show_order order);

        void onStart();
        void onFailure(String msg);
    }
    public interface Ontestplace_lisennter{
        void onSuccess(testMyplace test);

        void onStart();
        void onFailure(String msg);
    }
    public interface OnPoint_lisennter{
        void onSuccess(show_points show_points);

        void onStart();
        void onFailure(String msg);
    }
    public void Post_send_Order(final Context mcontext, final JSONObject cart , final OnOrder_lisennter listener){
        if (queue==null){
            queue = Volley.newRequestQueue(mcontext);
        }

        String url =mcontext.getString(R.string.api)+"api/order";
        final user_info user_info=new user_info(mcontext);
       // POST parameters

        Log.d("parameter",cart.toString());
        Log.d("parameter",user_info.getToken());

// Request a json response from the provided URL
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, cart,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("response",jsonObject.toString());
                        listener.onSuccess(new send_order(jsonObject));
                    }
                }, new Response.ErrorListener (){

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mcontext,cart.toString(),Toast.LENGTH_LONG).show();
                listener.onFailure(volleyError.toString());
                Log.d("response_error",volleyError.toString());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String>  Headers = new HashMap<String, String>();
                Headers.put("token",user_info.getToken());
                Headers.put("Accept","application/json");
                Headers.put("Content-Type","application/json");
                return Headers;
            }
            @Override
            public String getBodyContentType(){
                return "application/json";
            }
        };


// Add the request to the RequestQueue.
        queue.getCache().initialize();
        queue.add(jsonObjectRequest);
        queue.getCache().clear();
        // prepare the Request

    }

    public void GetMy_Order(final Context mcontext, String req_nb, int limit, int page, final OnOrder_geter_lisennter listener)
    {
        user_info user_info_=new user_info(mcontext);
        listener.onStart();
        String url=mcontext.getString(R.string.api)+"api/getUserOrder?id="+user_info_.getId()+"&staustId="+req_nb+"&page="+page+"&limit="+ limit +"";
        Log.d("my Order",url);
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
                        listener.onSuccess(new show_order(response));


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Log.d("Error.Response", mcontext.getString(R.string.networke));
                    }
                }
        );

// add it to the RequestQueue
        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }
    public void Get_TestMyPlace(final Context mcontext,Double lat,Double lng, final Ontestplace_lisennter listener){
        user_info user_info_=new user_info(mcontext);
        listener.onStart();
        String url=mcontext.getString(R.string.api)+"api/checkAvailableDrivers";
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }

        /*****************/
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat", lat);
            jsonObject.put("lng", lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("parrameter", jsonObject.toString());
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            listener.onSuccess(new testMyplace(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
listener.onFailure(mcontext.getString(R.string.networke)+"لا وجود لسائقين في هذه المنطقة ");
//                        Log.d("Error.Response", mcontext.getString(R.string.networke));
                    }
                }
        );

// add it to the RequestQueue
        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }
    public void Getdetails_Order(final Context mcontext,String order_id, final OnOrder_geter_lisennter listener)
    {
        user_info user_info_=new user_info(mcontext);
        listener.onStart();
        String url=mcontext.getString(R.string.api)+"api/getOrderDetails?id="+order_id;
        Log.d("url",url);
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
                        listener.onSuccess(new show_order(response));


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Log.d("Error.Response", mcontext.getString(R.string.networke));
                    }
                }
        );

// add it to the RequestQueue
        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }

    public void Post_PostADD_rat(final  Context mcontext, final String order_id, final String comment,
                                 final int rat_score, final OnOrder_geter_lisennter listener ){
        final user_info user_info_=new user_info(mcontext);
        listener.onStart();
        RequestQueue queue = Volley.newRequestQueue(mcontext);  // this = context
        String url = mcontext.getString(R.string.api)+"api/addRate?id="+order_id;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        String jsonData = response;
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        listener.onSuccess(new show_order(Jobject));



                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        listener.onFailure(mcontext.getString(R.string.networke));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  Header = new HashMap<String, String>();
//
                Header.put("token",user_info_.getToken());


                return Header;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//
                parameters.put("rate",rat_score+"");
                parameters.put("comment",comment);

                return parameters;
            }
        };
        queue.getCache().initialize();
        queue.add(postRequest);
queue.getCache().clear();
        // prepare the Request

    }

    public void Post_updateOrder(final Context mcontext, final String order_id, final int state,
                                  final OnOrder_geter_lisennter listener)  {
        if (queue==null){
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        final user_info user_info_=new user_info(mcontext);
        String url = mcontext.getString(R.string.api)+"api/updateOrderByUser?id="+order_id;



        listener.onStart();

// Request a json response from the provided URL

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        String jsonData = response;
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        listener.onSuccess(new show_order(Jobject));

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        listener.onFailure(String.valueOf("Error "+mcontext.getString(R.string.networke)));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  Params = new HashMap<String, String>();
                Params.put("StatusId",state+"");
//                if (state!=7) {
//                    Params.put("Notes", Notes);
//                }
                return Params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  Headers = new HashMap<String, String>();
                Headers.put("token",user_info_.getToken());

                return Headers;
            }
        };
        queue.add(postRequest);

// Add the request to the RequestQueue.


    }

    public void GetPoints(final Context mcontext, final OnPoint_lisennter listenner)
    {
        if (queue==null){
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        final user_info user_info_=new user_info(mcontext);
        String url=mcontext.getString(R.string.api)+"api/updateUserPoint/"+user_info_.getId();
        listenner.onStart();
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        show_points get_user_points_=new show_points(response);
                        listenner.onSuccess(get_user_points_);


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                    Log.d("Error.Response", mcontext.getString(R.string.networke));
                        listenner.onFailure(mcontext.getString(R.string.networke));
                    }
                }

        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  Header = new HashMap<String, String>();
//
                Header.put("token",user_info_.getToken());


                return Header;
            }

        };

// add it to the RequestQueue
        queue.getCache().clear();
// add it to the RequestQueue
        queue.add(getRequest);
    }
}
