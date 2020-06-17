package com.kh_sof_dev.gaz.Classes.User;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Http_user {
    public interface user_createListener{
        void onSuccess(new_account new_account);
        void onStart();
        void onFailure(String msg);
    }
    public interface user_loginListener{
        Boolean onSuccess(new_account new_account);
        void onStart();
        Boolean onFailure(String msg);
    }
    RequestQueue queue=null;
    public void Post_create_user(final Context mcontext,final int RegisterType, final String phone_nb, final String full_name, final String password,
                                 final String gender, final String email,final String city, final user_createListener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = mcontext.getString(R.string.api)+"api/users";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new new_account(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        lisenner.onFailure(String.valueOf(mcontext.getString(R.string.networke)));
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//
                parameters.put("RegisterType", String.valueOf(RegisterType));
                parameters.put("password", password);
                parameters.put("email", email);
//                parameters.put("gender", gender);
                parameters.put("full_name", full_name);
                parameters.put("phone_number",phone_nb);
                parameters.put("city", city);
                if (Login.mLatLng!=null) {
                    parameters.put("lat", String.valueOf(Login.mLatLng.latitude));
                    parameters.put("lng", String.valueOf(Login.mLatLng.longitude));
                }else {
                    parameters.put("lat", "10.00");
                    parameters.put("lng", "10.00");


                }
                Log.e("parameters", parameters.toString());
                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }
    public void Put_ActvitPhone_user(final Context mcontext, final String id,
                                      final String cod, final String fcmtoken, final user_createListener listenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = mcontext.getString(R.string.api)+"api/verfiy";//"http://httpbin.org/post";

        listenner.onStart();
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

                            listenner.onSuccess(new new_account(new JSONObject(jsonData)));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        listenner.onFailure(mcontext.getString(R.string.networke));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//
                parameters.put("id",id);
                parameters.put("verify_code", cod);
                parameters.put("fcmToken", fcmtoken);

                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }

    public void Post_Login(final Context mcontext, final String fcmtoken, final String password, final String phone_number, final user_loginListener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = mcontext.getString(R.string.api)+"api/login";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new new_account(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        lisenner.onFailure(String.valueOf(mcontext.getString(R.string.networke)));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//
                parameters.put("password", password);
                parameters.put("phone_number", phone_number);
                parameters.put("fcmToken", fcmtoken);

                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }
    public void Post_forget_password(final Context mcontext, final String token, final String email, final user_createListener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = mcontext.getString(R.string.api)+"api/forgetPassword";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new new_account(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        lisenner.onFailure(String.valueOf(mcontext.getString(R.string.networke)));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//

                parameters.put("email", email);

                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }
    public void Post_show_profile(final Context mcontext, final String token, final user_createListener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = mcontext.getString(R.string.api)+"api/showprofile";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new new_account(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        lisenner.onFailure(String.valueOf(mcontext.getString(R.string.networke)));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }

        };
         queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }
    public void Post_Logout(final Context mcontext,  final user_createListener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        final String token=new user_info(mcontext).getToken();

        String url = mcontext.getString(R.string.api)+"api/logout";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new new_account(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        lisenner.onFailure(String.valueOf(mcontext.getString(R.string.networke)));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }

        };
         queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }
    public void Post_change_password(final Context mcontext, final String newpassword, final user_createListener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        user_info user_info=new user_info(mcontext);
        final String token=user_info.getToken();
        String url = mcontext.getString(R.string.api)+"api/changePassword";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new new_account(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        lisenner.onFailure(String.valueOf(mcontext.getString(R.string.networke)));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//

                parameters.put("password", newpassword);

                return parameters;
            }
        };
         queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }
    public void Post_update_fcmToken(final Context mcontext, final String token, final String fcmtoken, final user_createListener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = mcontext.getString(R.string.api)+"api/changePassword";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new new_account(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(mcontext.getString(R.string.networke)));
                        lisenner.onFailure(String.valueOf(mcontext.getString(R.string.networke)));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//

                parameters.put("fcmToken", fcmtoken);

                return parameters;
            }
        };
         queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }


}
