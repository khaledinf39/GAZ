package com.kh_sof_dev.gaz.Fragments.Reservations;


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
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kh_sof_dev.gaz.activities.Chat;
import com.kh_sof_dev.gaz.Classes.Firebase.car_informatiom;
import com.kh_sof_dev.gaz.Classes.Order.GetMayOrders.Order_getter;
import com.kh_sof_dev.gaz.Classes.url.DirectionsJSONParser;
import com.kh_sof_dev.gaz.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Follow_order extends Fragment implements OnMapReadyCallback {

    Order_getter order_getter;

    public Follow_order(Order_getter order_getter1) {
        // Required empty public constructor
        this.order_getter = order_getter1;

//for test

//        Order_getter order_getter=new Order_getter();
//        DriverId driverId=new DriverId();
//        driverId.setName("اسامة");
//        driverId.setImage("http://res.cloudinary.com/diszvlmqq/image/upload/v1553974183/modxph0glxmfk7vbgdfn.png");
//        driverId.setId("5c90eac40f98b2960fa275a9");
//        order_getter.setDriverId(driverId);
//        order_getter.setLat(31.5139203f);
//        order_getter.setLng(34.4398477f);
//        order_getter.setDeliveryCost(15);
//        order_getter.setId("5ca26af47200f800249b9cfb");
//        this.order_getter = order_getter;
    }


    private TextView address1, address2, orderState, driverName/*, coste*/, carNB, carName;
    private CircleImageView driverImg;
    private Button callBtn, msgBtn;
    private ImageView back;
    private car_informatiom car_informatiom;
    private Double lat = null, lng = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.layout_f_reservation__current, container, false);
        address1 = (TextView) view.findViewById(R.id.address1_tv);
        address2 = (TextView) view.findViewById(R.id.address2_tv);
        orderState = (TextView) view.findViewById(R.id.orderState_tv);
        carName = (TextView) view.findViewById(R.id.DriverCar);
        carNB = (TextView) view.findViewById(R.id.CarNo_tv);

        driverName = (TextView) view.findViewById(R.id.DriverName);
//        coste = (TextView) view.findViewById(R.id.cost);
        back = (ImageView) view.findViewById(R.id.back_btn);
        driverImg = (CircleImageView) view.findViewById(R.id.DriverImg);
        callBtn = (Button) view.findViewById(R.id.msgDriver_btn);
        msgBtn = (Button) view.findViewById(R.id.callDriver_btn);
        /**********************************intialise***********************************/
        Glide.with(getContext())
                .load(order_getter.getDriverId().getImage())
                .placeholder(R.drawable.ic_user_img_gray)
                .into(driverImg);
        driverName.setText(order_getter.getDriverId().getName());
//        coste.setText(order_getter.getDeliveryCost() + "");
//        coste.setVisibility(View.GONE);
        address2.setText(order_getter.getAddressDetails());
        address1.setText(order_getter.getDriverId().getAddress());
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        /******************************action*********************************/
        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat.order_id = order_getter.getId();
                startActivity(new Intent(getContext(), Chat.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
        Log.d("flowOrder", order_getter.getId()
                + "dv Id :" + order_getter.getDriverId().getId());


        reference.child("tracking").child(order_getter.getId()).child(order_getter.getDriverId().getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.exists()) {
                                car_informatiom = dataSnapshot.getValue(car_informatiom.class);
                                carName.setText(car_informatiom.getCar_name() + " لونها " + car_informatiom.getCar_color());
                                carNB.setText(car_informatiom.getCar_number());
                                callBtn.setEnabled(true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return view;
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
//mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//    @Override
//    public void onMapClick(LatLng latLng) {
//        Map_draw(latLng);
//
//    }
//});
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        referenceLoc = database.getReference().child("tracking").child(order_getter.getId());
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setTitle("تحميل البيانات");
        dialog.setMessage("يتم تحميل احداثيات ....!");
        dialog.show();
        Log.e("order ids", order_getter.getId() + "   driver= " + order_getter.getDriverId().getId());

        childEventListenerLoc = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                drowDriverMarker(dataSnapshot, dialog);

//                if (dataSnapshot.getKey().equals("lat")) {
//                    lat = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lat+" lat ",Toast.LENGTH_LONG).show();
//                }
//                if (dataSnapshot.getKey().equals("lng")) {
//                    lng = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lng+" lng ",Toast.LENGTH_LONG).show();
//                }
//                if (lat != null && lng != null) {
//                    Map_draw(new LatLng(lat, lng));
//                    dialog.dismiss();
////                    Toast.makeText(getContext(),lat+" "+lng,Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                drowDriverMarker(dataSnapshot, dialog);

//                if (dataSnapshot.getKey().equals("lat")) {
//                    lat = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lat+" lat ",Toast.LENGTH_LONG).show();
//                }
//                if (dataSnapshot.getKey().equals("lng")) {
//                    lng = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lng+" lng ",Toast.LENGTH_LONG).show();
//                }
//                if (lat != null && lng != null) {
//                    try {
//                        Map_draw(new LatLng(lat, lng));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
////                    Toast.makeText(getContext(),lat+" "+lng,Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                drowDriverMarker(dataSnapshot, dialog);

//                if (dataSnapshot.getKey().equals("lat")) {
//                    lat = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lat+" lat ",Toast.LENGTH_LONG).show();
//                }
//                if (dataSnapshot.getKey().equals("lng")) {
//                    lng = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lng+" lng ",Toast.LENGTH_LONG).show();
//                }
//                if (lat != null && lng != null) {
//                    try {
//                        Map_draw(new LatLng(lat, lng));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
////                    Toast.makeText(getContext(),lat+" "+lng,Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                drowDriverMarker(dataSnapshot, dialog);

//                if (dataSnapshot.getKey().equals("lat")) {
//                    lat = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lat+" lat ",Toast.LENGTH_LONG).show();
//                }
//                if (dataSnapshot.getKey().equals("lng")) {
//                    lng = dataSnapshot.getValue(Double.class);
////                    Toast.makeText(getContext(),lng+" lng ",Toast.LENGTH_LONG).show();
//                }
//                if (lat != null && lng != null) {
//                    try {
//                        Map_draw(new LatLng(lat, lng));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
////                    Toast.makeText(getContext(),lat+" "+lng,Toast.LENGTH_LONG).show();
//                }
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
            if (dataSnapshot.getKey().equals("lat")) {
                lat = dataSnapshot.getValue(Double.class);
            }
            if (dataSnapshot.getKey().equals("lng")) {
                lng = dataSnapshot.getValue(Double.class);
            }
            if (lat != null && lng != null) {
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
                .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_car_icon, R.drawable.ic_car_icon));


        firstMarker = mMap.addMarker(options1);
        start = new LatLng(firstMarker.getPosition().latitude, firstMarker.getPosition().longitude);

        MarkerOptions options2 = new MarkerOptions();
        options2.position(destination).draggable(true).title(getResources().getString(R.string.end_point))
                .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_ovalpoint_icon, R.drawable.ic_oval_icon));

        secondMarker = mMap.addMarker(options2);
        end = new LatLng(secondMarker.getPosition().latitude, secondMarker.getPosition().longitude);


        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(displaySize);
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));

        if (firstMarker != null && secondMarker != null) {

            DrawPolyLine();
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.marker_empty), 5);
        }


    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId, int vectorDrawableBackground) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableBackground);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
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
            StringBuffer sb = new StringBuffer();
            String line = "";
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

        // Executes in UI thread, after the parsing process
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            String distance = "";
            String duration = "";
            if (result.size() < 1) {
                // Toast.makeText(getContext(), "عذرا لم نستطع تحديد الطريق", Toast.LENGTH_LONG).show();
                return;
            }
//            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);
                    if (j == 0) {    // Get distance from the list
                        distance = (String) point.get("distance");
                        continue;
                    } else if (j == 1) { // Get duration from the list
                        duration = point.get("duration");
                        continue;
                    }
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
                    points.add(position);
                }
//                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(6);
                lineOptions.color(Color.GREEN);

            }
//
////            from = findViewById(R.id.start_address);
////            to = findViewById(R.id.end_address);
////            dec = findViewById(R.id.distance);
////            time = findViewById(R.id.duration);
////            dec.setText(distance + "   ");
////            Distance = distance;
////            time.setText(duration + "   ");
//            mMap.addPolyline(lineOptions);
//               Geocoder geocoder;
//              List<Address> addresses = null;
//               List<Address> addresses2 = null;
//               geocoder = new Geocoder(getContext(), Locale.getDefault());
//                try {
//                    addresses = geocoder.getFromLocation(start.latitude, start.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                    addresses2 = geocoder.getFromLocation(end.latitude, end.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                address1.setText(addresses.get(0).getAdminArea()+" , "+addresses.get(0).getLocality()+" , "
//                        +addresses.get(0).getAddressLine(0));
//                address2.setText(addresses2.get(0).getAdminArea()+" , "+addresses2.get(0).getLocality()+" , "
//                         +addresses2.get(0).getAddressLine(0));
////                 sTo = addresses2.get(0).getAdminArea()+" , "+addresses2.get(0).getLocality()+" , "
////                         +addresses2.get(0).getAddressLine(0);
////                 sFrom = addresses.get(0).getAdminArea()+" , "+addresses.get(0).getLocality()+" , "
////                          +addresses.get(0).getAddressLine(0);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (referenceLoc != null && childEventListenerLoc != null)
            referenceLoc.removeEventListener(childEventListenerLoc);
    }
}

