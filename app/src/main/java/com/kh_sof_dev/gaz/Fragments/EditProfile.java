package com.kh_sof_dev.gaz.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kh_sof_dev.gaz.Activities.Login;
import com.kh_sof_dev.gaz.Activities.MainNew;
import com.kh_sof_dev.gaz.Adapters.City_adapter;
import com.kh_sof_dev.gaz.Classes.Http.upload_img.VolleyMultipartRequest;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.City;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.Classes.constant.show_cites;
import com.kh_sof_dev.gaz.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.facebook.login.widget.ProfilePictureView.TAG;


public class EditProfile extends Fragment {
    private String list[]={"ذكر","انثى"};
    private Typeface tfavv;
    private Spinner city,mySpinner;
    private String cite_name=null;
    List<City> cityList;
    public EditProfile() {
        // Required empty public constructor
    }


    public static EditProfile newInstance(String param1, String param2) {
        EditProfile fragment = new EditProfile();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    static final int PICK_IMAGE_REQUEST = 1;
    private void Loading_cities() {
        Http_get_constant http_get_constant=new Http_get_constant();
        http_get_constant.cites(getContext(), new Http_get_constant.citesListener() {
            @Override
            public void onSuccess(show_cites cites) {
                cityList=cites.getItems();
                City_adapter adapter=new City_adapter(getActivity(), cityList, new City_adapter.citylissener() {
                    @Override
                    public void onsuccessfully(String city) {
                        cite_name=city;
                    }
                });
                city.setAdapter(adapter);
                for (int position=0;position<cityList.size();position++
                     ) {

                    City c=cityList.get(position);
                    if (c.getId().equals(user_info.getAddress())){
                        Log.d("city position",position+"");
                        cite_name=c.getName();
                        city.setSelection(position);
                    }
                }
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
    private void imageBrowse() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }
    Bitmap bitmap=null;
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if(requestCode == PICK_IMAGE_REQUEST){
                Uri picUri = data.getData();


                Log.d("picUri", picUri.toString());
                // Log.d("filePath", filePath);
                Uri contentURI = data.getData();
                // user_img.setImageURI(picUri);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmap!=null)
                {
                    user_img.setImageBitmap(bitmap);
                }


            }

        }

    }

    /********uploaimg*******/
    private RequestQueue rQueue;
    private ArrayList<HashMap<String, String>> arraylist;
    private void uploadImage(final Bitmap bitmap ){



        final ProgressDialog dialog=new ProgressDialog(getContext());
        dialog.setTitle("تعديل الحساب");
        dialog.setMessage("يتم تعديل حسابك الأن..!");
        dialog.show();
        Log.d("uploadImage", user_info.getId()+"  "+user_info.getToken());
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                "https://gazapp.herokuapp.com/api/uploadUserPhoto",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("ressssssoo",new String(response.data));
                        rQueue.getCache().clear();

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(new String(response.data));
                            //JSONObject jsonObjectRequest=jsonObject.getJSONObject("items");
                            user_info.setImag(jsonObject.getString("url"));
                            Log.d("ressssssoo",jsonObject.getString("url"));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                            dialog.dismiss();

                        Put_profilInfo(getContext());


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Error  uploading"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        Put_profilInfo(getContext());
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  Params = new HashMap<String, String>();


                return Params;
            }



            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String>  Headers = new HashMap<String, String>();
                Headers.put("token",user_info.getToken());

                return Headers;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (bitmap!=null) {
                    params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }
                return params;
            }
        };



        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(volleyMultipartRequest);
    }
    public void Put_profilInfo(final Context mcontext){
        RequestQueue queue=null;
try {
     queue = Volley.newRequestQueue(mcontext);  // this = context
}catch (Exception e){
    e.printStackTrace();
    return;
}

        String url = "https://gazapp.herokuapp.com/api/updateUserAndroid";

// Request a json response from the provided URL
progressBar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url,
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
                      user_info=  new user_info(new new_account(Jobject).getItems(),"",getContext());
Toast.makeText(getContext()," تم تعديل حسابك "+new new_account(Jobject).getMessage(),Toast.LENGTH_LONG).show();
progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error.getMessage()));

                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders()  {
                Map<String, String>  Headers = new HashMap<String, String>();
                Headers.put("token",user_info.getToken());
Log.d("token",user_info.getToken());
                return Headers;
            }
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String>  Params = new HashMap<String, String>();
                Params.put("_id",user_info.getId());
                Params.put("full_name",user_info.getName());
//                Params.put("gender",user_info.getGender());
                Params.put("address",user_info.getCity());
                Params.put("image",user_info.getImag());
                Params.put("currentCity",user_info.getCity());
                Params.put("phone_number",user_info.getPhone());
//
                return Params;
            }

        }
                ;
        queue.add(postRequest);

// Add the request to the RequestQueue.


    }
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(getActivity())
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getContext()
                                    , "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
//    /*************************/
    ImageView change_img,back;
    CircleImageView user_img;
    EditText name,email,phone;
    Button save;
    user_info user_info;
    ProgressBar progressBar;
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_edit_profile, container, false);
        progressBar=view.findViewById(R.id.progress);
         mySpinner = (Spinner) view.findViewById(R.id.gender);
        spinner2meth();
         change_img=(ImageView)view.findViewById(R.id.change_img);
         back=(ImageView)view.findViewById(R.id.back_icon);
         name=(EditText)view.findViewById(R.id.name_tv);
        phone=(EditText)view.findViewById(R.id.phoneNo_et);
        email=(EditText)view.findViewById(R.id.UserEmail_et);

        save=(Button)view.findViewById(R.id.save_btn);
        user_img=(CircleImageView)view.findViewById(R.id.UserImg);
        /***************************action**************************/
        user_info=new user_info(getContext());
        name.setText(user_info.getName());
        email.setText(user_info.getEmail());
phone.setText(user_info.getPhone());

        if (!user_info.getImag().isEmpty()) {
            Glide.with(getContext())
                    .load(user_info.getImag())
                    .placeholder(R.drawable.ic_user_img_gray)
.into(user_img);

        }

//        if (user_info.getGender().equals("ذكر")) {
//            mySpinner.setSelection(1);
//        }else {
//            mySpinner.setSelection(0);
//        }

        change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestMultiplePermissions();
               if (isStoragePermissionGranted())
               {
                   imageBrowse();
               }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               _goback(EditProfile.this);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              update_function();
            }
        });

        city=(Spinner)view.findViewById(R.id.city_et);
        Loading_cities();
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    cite_name=cityList.get(position).getId();
                }catch (Exception fg){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return  view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
    private void update_function() {
        String name_=name.getText().toString();
        String email_=email.getText().toString();
        String phone_=phone.getText().toString();

        if (name_.isEmpty()){
            name.setError(name.getHint());

            return ;
        }
        if (phone_.isEmpty()){
            phone.setError(name.getHint());

            return ;
        }
        if (email_.isEmpty() || !email_.contains("@")){
            email.setError(email.getHint());

            return ;
        }
        if (cite_name==null){
            Toast.makeText(getContext(),"إختار المدينة من فضلك",Toast.LENGTH_LONG).show();

            return ;
        }


        user_info.setCity(cite_name);
        user_info.setEmail(email_);
        user_info.setName(name_.trim());
        user_info.setGender(mySpinner.getSelectedItem().toString());
        user_info.setCity(cite_name);
        user_info.setPhone(phone_);
        if (bitmap!=null){
            uploadImage(bitmap);
        }else {
            Put_profilInfo(getContext());
        }

//        Toast.makeText(getContext(),user_info.getName(),Toast.LENGTH_LONG).show();

    }
    public <ViewGroup> void spinner2meth(){

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, list)
        {
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                tfavv = Typeface.createFromAsset(getActivity().getAssets(),"fonts/cairo_regular.ttf");
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(tfavv);
                v.setTextColor(Color.parseColor("#272d39"));
                v.setTextSize(15);
                return v;
            }

            public View getDropDownView(int position, View convertView, android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(tfavv);
                v.setTextColor(Color.parseColor("#272d39"));
                v.setTextSize(15);
                return v;
            }
        };

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter1);
    }
    private void _goback(Fragment frg){
        MainNew.fragmentManager = getFragmentManager();
        MainNew.fragmentTransaction = MainNew.fragmentManager.beginTransaction();
        MainNew.fragment = frg;
        MainNew.fragmentTransaction.remove( MainNew.fragment);
        MainNew.fragmentTransaction.commit();
    }
}
