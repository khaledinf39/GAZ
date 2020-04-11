package com.kh_sof_dev.gaz.activities;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kh_sof_dev.gaz.Classes.Firebase.car_informatiom;
import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.Order_getter;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.Classes.url.DirectionsJSONParser;
import com.kh_sof_dev.gaz.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Follow_order extends AppCompatActivity implements OnMapReadyCallback {

    Order_getter order_getter;

    private TextView carNB;
    private TextView carName;
    private Button callBtn;
    private car_informatiom car_informatiom;
    private double lat = 0.0, lng = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_reservation__current);
        TextView address1 = findViewById(R.id.address1_tv);
        TextView address2 = findViewById(R.id.address2_tv);
        carName = findViewById(R.id.DriverCar);
        carNB = findViewById(R.id.CarNo_tv);

        TextView driverName = findViewById(R.id.DriverName);
        ImageView back = findViewById(R.id.back_btn);
        CircleImageView driverImg = findViewById(R.id.DriverImg);
        callBtn = findViewById(R.id.msgDriver_btn);
        Button msgBtn = findViewById(R.id.callDriver_btn);
        //**********************************intialise***********************************/
        try {
            if (getIntent().hasExtra(OrderDetails.REQUEST_DETAILS)) {
                order_getter = (Order_getter) getIntent().getSerializableExtra(OrderDetails.REQUEST_DETAILS);
                if (order_getter != null) {
                    if (order_getter.getDriverId() != null) {
                        Utils.showImage(this, order_getter.getDriverId().getImage(), R.drawable.ic_user_img_gray, driverImg);
//                        Picasso.with(this)
//                                .load(order_getter.getDriverId().getImage())
//                                .placeholder(R.drawable.ic_user_img_gray)
//                                .into(driverImg);
                        driverName.setText(order_getter.getDriverId().getName());
                        address1.setText(order_getter.getDriverId().getAddress());
                    }

                    if (order_getter.getAddressDetails() != null)
                        address2.setText(order_getter.getAddressDetails());
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

                    mapFragment.getMapAsync(this);
                    //******************************action*********************************/
                    msgBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (order_getter.getId() != null) {
//                                Chat.order_id = order_getter.getId();
                                startActivity(new Intent(Follow_order.this, Chat.class).putExtra(Chat.order_id, order_getter.getId()));
                            }
                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
//                MainNew._goto(Follow_order.this,new Home(),View.VISIBLE);
                        }
                    });

                    callBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", car_informatiom.getDriverPhone()
                                    , null)));
                        }
                    });
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference();

                    if (order_getter.getId() != null && order_getter.getDriverId() != null && order_getter.getDriverId().getId() != null)
                        reference.child("tracking").child(order_getter.getId()).child(order_getter.getDriverId().getId())
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        try {
                                            if (dataSnapshot.exists()) {
                                                car_informatiom = dataSnapshot.getValue(car_informatiom.class);
                                                if (car_informatiom != null) {
                                                    carName.setText(String.valueOf(car_informatiom.getCar_name() + " لونها " + car_informatiom.getCar_color()));
                                                    carNB.setText(car_informatiom.getCar_number());
                                                    callBtn.setEnabled(true);
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /********************************MAP***********************************/
    private GoogleMap mMap;
    LatLng start, end;
    Marker firstMarker = null, secondMarker = null;
    DatabaseReference referenceLoc;
    ChildEventListener childEventListenerLoc;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Map_draw(new LatLng(order_getter.getLat(), order_getter.getLng()));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        referenceLoc = database.getReference().child("tracking").child(order_getter.getId());
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("تحميل البيانات");
        dialog.setMessage("يتم تحميل احداثيات ....!");
        dialog.show();
        Log.e("order ids", order_getter.getId() + "   driver= " + order_getter.getDriverId().getId());

        childEventListenerLoc = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                drowDriverMarker(dataSnapshot, dialog);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                drowDriverMarker(dataSnapshot, dialog);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                drowDriverMarker(dataSnapshot, dialog);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                drowDriverMarker(dataSnapshot, dialog);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        referenceLoc.addChildEventListener(childEventListenerLoc);

    }

    void drowDriverMarker(DataSnapshot dataSnapshot, ProgressDialog dialog) {
        try {
            Log.e("dataSnapshot", dataSnapshot.toString());
            if (Objects.equals(dataSnapshot.getKey(), "lat")) {
                lat = dataSnapshot.getValue(Double.class);
            }
            if (Objects.equals(dataSnapshot.getKey(), "lng")) {
                lng = dataSnapshot.getValue(Double.class);
            }
            if (lat > 0.0 && lng > 0.0) {
                try {
                    Map_draw(new LatLng(lat, lng));
                    if (firstOpen) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
                        firstOpen = false;
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        if (referenceLoc != null && childEventListenerLoc != null)
            referenceLoc.removeEventListener(childEventListenerLoc);
        super.onPause();
    }

    boolean firstOpen = true;
    LatLng destination = null;

    private void Map_draw(LatLng origin) {
        mMap.clear();

        Log.e("order latlng", order_getter.getLat() + " ,  " + order_getter.getLng());
        if (destination == null) {
            destination = new LatLng(order_getter.getLat(), order_getter.getLng());

        }
        MarkerOptions options1 = new MarkerOptions();
        options1.position(origin).draggable(true).title(getResources().getString(R.string.start_point))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_car_icon, R.drawable.ic_car_icon));


        firstMarker = mMap.addMarker(options1);
        start = new LatLng(firstMarker.getPosition().latitude, firstMarker.getPosition().longitude);

        MarkerOptions options2 = new MarkerOptions();
        options2.position(destination).draggable(true).title(getResources().getString(R.string.end_point))
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_ovalpoint_icon, R.drawable.ic_oval_icon));

        secondMarker = mMap.addMarker(options2);
        end = new LatLng(secondMarker.getPosition().latitude, secondMarker.getPosition().longitude);


//        LatLngBounds bounds = new LatLngBounds.Builder()
//                .include(origin)
//                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));

        if (firstMarker != null && secondMarker != null) {

            DrawPolyLine();
        } else {
            Toast.makeText(this, getResources().getString(R.string.marker_empty), Toast.LENGTH_LONG).show();
        }


    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId, int vectorDrawableBackground) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableBackground);
        assert background != null;
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        assert vectorDrawable != null;
        vectorDrawable.setBounds(+20, +20, vectorDrawable.getIntrinsicWidth() - 20, vectorDrawable.getIntrinsicHeight() - 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception ignored) {

        } finally {
            if (iStream != null) {
                iStream.close();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }

    @SuppressLint("CheckResult")
    private void DrawPolyLine() {
        // Getting URL to the Google Directions API
        String url = getDirectionsUrl(start, end);
        DownloadTask downloadTask = new DownloadTask();
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);


    }

    private String getDirectionsUrl(LatLng userLocation, LatLng doc_location) {
        String str_origin = "origin=" + userLocation.latitude + "," + userLocation.longitude;
        // Destination of route
        String str_dest = "destination=" + doc_location.latitude + "," + doc_location.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters +
                "&key=AIzaSyDKhB_cnnxmylJycsDUoi9XbsJTWQJY91Q";

        //+ getContext().getString(R.string.google_maps_key);
        Log.d("url line map", url);
        return url;
    }

    // Fetches data from url passed
    @SuppressLint("StaticFieldLeak")
    class DownloadTask extends AsyncTask<String, Void, String> {
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
            // For storing data from web service
            String data = "";
            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception ignored) {

            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    @SuppressLint("StaticFieldLeak")
    class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions;
            if (result == null)
                result = new ArrayList<>();
            if (result.size() < 1) {
                return;
            }
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    double lat = 0.0;
                    double lng = 0.0;

                    if (point.containsKey("lat"))
                        lat = Double.parseDouble(point.get("lat") + "");
                    if (point.containsKey("lng"))
                        lng = Double.parseDouble(point.get("lng") + "");

                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(6);
                lineOptions.color(Color.GREEN);

            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (referenceLoc != null && childEventListenerLoc != null)
            referenceLoc.removeEventListener(childEventListenerLoc);
    }
}

