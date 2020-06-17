package com.kh_sof_dev.gaz.activities;


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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kh_sof_dev.gaz.Adapters.City_adapter;
import com.kh_sof_dev.gaz.Classes.Http.upload_img.VolleyMultipartRequest;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.Classes.constant.City;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.Classes.constant.show_cites;
import com.kh_sof_dev.gaz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.login.widget.ProfilePictureView.TAG;


public class EditProfile extends AppCompatActivity {
    private String[] list = {"ذكر", "انثى"};
    private Typeface tfavv;
    private Spinner city, mySpinner;
    private String cite_name = null;
    List<City> cityList;

    static final int PICK_IMAGE_REQUEST = 1;

    private void Loading_cities() {
        Http_get_constant http_get_constant = new Http_get_constant();
        http_get_constant.cites(this, new Http_get_constant.citesListener() {
            @Override
            public void onSuccess(show_cites cites) {
                cityList = cites.getItems();
                City_adapter adapter = new City_adapter(EditProfile.this, cityList, new City_adapter.citylissener() {
                    @Override
                    public void onsuccessfully(String city) {
                        cite_name = city;
                    }
                });
                city.setAdapter(adapter);
                for (int position = 0; position < cityList.size(); position++
                ) {

                    City c = cityList.get(position);
                    if (c.getId().equals(user_info.getAddress())) {
                        Log.d("city position", position + "");
                        cite_name = c.getName();
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

    Bitmap bitmap = null;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST) {
//                Uri picUri = data.getData();
//                Log.d("picUri", picUri.toString());
                // Log.d("filePath", filePath);
                // user_img.setImageURI(picUri);
                try {
                    Uri contentURI = data.getData();

                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);

                    if (bitmap != null) {
                        user_img.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }

    }

    /********uploaimg*******/
    private RequestQueue rQueue;
//    private ArrayList<HashMap<String, String>> arraylist;

    private void uploadImage(final Bitmap bitmap) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("تعديل الحساب");
        dialog.setMessage("يتم تعديل حسابك الأن..!");
        dialog.show();
        Log.d("uploadImage", user_info.getId() + "  " + user_info.getToken());
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                "https://gazapp.herokuapp.com/api/uploadUserPhoto",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {


                        try {
                            Log.d("ressssssoo", new String(response.data));
                            rQueue.getCache().clear();

                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            //JSONObject jsonObjectRequest=jsonObject.optJSONObject("items");
                            user_info.setImag(jsonObject.optString("url"));
                            Log.d("ressssssoo", jsonObject.optString("url"));


                            Put_profilInfo(EditProfile.this);

                            dialog.dismiss();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(EditProfile.this, "Error  uploading" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Put_profilInfo(EditProfile.this);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }


            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> Headers = new HashMap<>();
                Headers.put("token", user_info.getToken());

                return Headers;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (bitmap != null) {
                    params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                }
                return params;
            }
        };


        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(this);
        rQueue.add(volleyMultipartRequest);
    }

    public void Put_profilInfo(final Context mcontext) {
        RequestQueue queue;
        try {
            queue = Volley.newRequestQueue(mcontext);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String url = "https://gazapp.herokuapp.com/api/updateUserAndroid";

        progressBar.setVisibility(View.VISIBLE);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(response);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        user_info = new user_info(new new_account(Jobject).getItems(), "", EditProfile.this);
                        Toast.makeText(EditProfile.this, " تم تعديل حسابك " + new new_account(Jobject).getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error.getMessage()));

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> Headers = new HashMap<>();
                Headers.put("token", user_info.getToken());
                Log.d("token", user_info.getToken());
                return Headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> Params = new HashMap<>();
                Params.put("_id", user_info.getId());
                Params.put("full_name", user_info.getName());
//                Params.put("gender",user_info.getGender());
                Params.put("address", user_info.getCity());
                Params.put("image", user_info.getImag());
                Params.put("currentCity", user_info.getCity());
                Params.put("phone_number", user_info.getPhone());
//
                return Params;
            }

        };
        queue.add(postRequest);

// Add the request to the RequestQueue.


    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(EditProfile.this
                                    , "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

//                        // check for permanent denial of any permission
//                        if (report.isAnyPermissionPermanentlyDenied()) {
//                            // show alert dialog navigating to Settings
//
//                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(EditProfile.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    //    /*************************/
    ImageView change_img, back;
    CircleImageView user_img;
    EditText name, email, phone;
    Button save;
    user_info user_info;
    ProgressBar progressBar;

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_edit_profile);
        progressBar = findViewById(R.id.progress);
        mySpinner = findViewById(R.id.gender);
        spinner2meth();
        change_img = findViewById(R.id.change_img);
        back = findViewById(R.id.back_icon);
        name = findViewById(R.id.name_tv);
        phone = findViewById(R.id.phoneNo_et);
        email = findViewById(R.id.UserEmail_et);

        save = findViewById(R.id.save_btn);
        user_img = findViewById(R.id.UserImg);
        //***************************action**************************/
        user_info = new user_info(this);
        name.setText(user_info.getName());
        email.setText(user_info.getEmail());
        phone.setText(user_info.getPhone());

        if (!user_info.getImag().isEmpty()) {
            Utils.showImage(this, user_info.getImag(), R.drawable.ic_user_img_gray, user_img);
//            Picasso.with(this)
//                    .load(user_info.getImag())
//                    .placeholder(R.drawable.ic_user_img_gray)
//                    .into(user_img);

        }


        change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestMultiplePermissions();
                if (isStoragePermissionGranted()) {
                    imageBrowse();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                _goback(EditProfile.this);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_function();
            }
        });

        city = findViewById(R.id.city_et);
        Loading_cities();
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    cite_name = cityList.get(position).getId();
                } catch (Exception fg) {
                    fg.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    private void update_function() {
        String name_ = name.getText().toString();
        String email_ = email.getText().toString();
        String phone_ = phone.getText().toString();

        if (name_.isEmpty()) {
            name.setError(name.getHint());

            return;
        }
        if (phone_.isEmpty()) {
            phone.setError(name.getHint());

            return;
        }
        if (email_.isEmpty() || !email_.contains("@")) {
            email.setError(email.getHint());

            return;
        }
        if (cite_name == null) {
            Toast.makeText(this, "إختار المدينة من فضلك", Toast.LENGTH_LONG).show();

            return;
        }


        user_info.setCity(cite_name);
        user_info.setEmail(email_);
        user_info.setName(name_.trim());
        user_info.setGender(mySpinner.getSelectedItem().toString());
        user_info.setCity(cite_name);
        user_info.setPhone(phone_);
        if (bitmap != null) {
            uploadImage(bitmap);
        } else {
            Put_profilInfo(this);
        }

//        Toast.makeText(getContext(),user_info.getName(),Toast.LENGTH_LONG).show();

    }

    public void spinner2meth() {

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list) {
            @NonNull
            public View getView(int position, View convertView, @NonNull android.view.ViewGroup parent) {
                tfavv = Typeface.createFromAsset(getAssets(), "fonts/cairo_regular.ttf");
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(tfavv);
                v.setTextColor(Color.parseColor("#272d39"));
                v.setTextSize(15);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull android.view.ViewGroup parent) {
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

//    private void _goback(Fragment frg) {
//        MainNew.fragmentManager = getFragmentManager();
//        MainNew.fragmentTransaction = MainNew.fragmentManager.beginTransaction();
//        MainNew.fragment = frg;
//        MainNew.fragmentTransaction.remove(MainNew.fragment);
//        MainNew.fragmentTransaction.commit();
//    }
}
