package com.kh_sof_dev.gaz.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kh_sof_dev.gaz.Classes.Order.Http_orders;
import com.kh_sof_dev.gaz.Classes.Order.testMyplace;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.MainNew;

import java.util.List;
import java.util.Locale;


@SuppressLint("ValidFragment")
public class Shipping extends Fragment implements OnMapReadyCallback {
    public static String locationAdd = "";
    private Boolean flag;
    private String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET
    };

    private void get_location() {
        flag = displayGpsStatus();
        if (flag) {
            locationListener = new MyLocationListener();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, 15);
                } else if (locationListener != null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                }
            }
        } else {
            alertbox();
//            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    /*---------- Listener class to get coordinates ------------- */
    int gps = 0;

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {


            if (gps == 0 && mMap != null) {
                make_marke(new LatLng(loc.getLatitude(), loc.getLongitude()));
//                dialog.dismiss();
                gps = 1;
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    /**
     * /*----Method to Check GPS is enable or disable -----
     */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.popup_opengps);
        dialog.setCancelable(false);
        Button cancel = dialog.findViewById(R.id.cancel_btn);
//        TextView tex1 = (TextView) dialog.findViewById(R.id.tx1_tv);
//        TextView tex2 = (TextView) dialog.findViewById(R.id.tx2_tv);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button go_sitting = dialog.findViewById(R.id.open_sitting_btn);
        go_sitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);
                dialog.cancel();
            }
        });
        dialog.show();


    }


    /***********************************************************************************/
    private int Maptyp = 1;

    int order_type;

    @SuppressLint("ValidFragment")
    public Shipping(int typ2_Order_1_test, int order_type) {
        Maptyp = typ2_Order_1_test;
        ////1 for test
        /////2 for order
        this.order_type = order_type;

    }

    public Shipping() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ImageView like;
    private Boolean like_stat = false;
    private Button continue_btn;
    private TextView location_tv;
    //    private ProgressDialog dialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_shipping, container, false);
        //***************************************************************/
//        dialog = new ProgressDialog(getContext());
//        dialog.setMessage("يرجي الانتظار حتى يتم تحديد موقعك..");
//        dialog.show();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        get_location();
        //*****************************************************************/
        like = view.findViewById(R.id.like_btn);
        ImageView back = view.findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Maptyp == 2) {
                    getActivity().finish();
//            MainNew._goto(Shipping.this, new Home(), View.VISIBLE);
                } else {
//            MainNew.goto_(new Refill(refillhome),getContext());
                    getActivity().finish();
                }
            }
        });
        location_tv = view.findViewById(R.id.address1_tv);
        continue_btn = view.findViewById(R.id.continue_btn);
        ConstraintLayout layout1 = view.findViewById(R.id.layout1);
        if (Maptyp == 1) {
            layout1.setVisibility(View.GONE);

        }
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Maptyp == 2) {
                    MainNew.goto_(new Payment(order_type), getActivity());
                } else {
                    getActivity().finish();
//            MainNew.goto_( new Refill(refillhome), getContext());
                }
            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like_stat) {
                    like.setImageResource(R.drawable.ic_like_border);
                    like_stat = false;
                } else {
                    like.setImageResource(R.drawable.ic_like1);
                    like_stat = true;
                }
            }
        });

// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
//        mapFragment.getMapAsync(this);
        String googleError = null;
        switch (MapsInitializer.initialize(getActivity())) { // or GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx)
            case ConnectionResult.SERVICE_MISSING:
                googleError = "Failed to connect to google mapping service: Service Missing";
                break;
            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                googleError = "Failed to connect to google mapping service: Google Play services out of date. Service Version Update Required";
                break;
            case ConnectionResult.SERVICE_DISABLED:
                googleError = "Failed to connect to google mapping service: Service Disabled. Possibly app is missing API key or is not a signed app permitted to use API key.";
                break;
            case ConnectionResult.SERVICE_INVALID:
                googleError = "Failed to connect to google mapping service: Service Invalid. Possibly app is missing API key or is not a signed app permitted to use API key.";
                break;
//            case ConnectionResult.DATE_INVALID: googleError = "Failed to connect to google mapping service: Date Invalid"; break;
        }
        if (googleError != null)
            Log.d("MyApp", googleError);
        //********************************************************/
        return view;
    }


    /********************************MAP***********************************/
    private GoogleMap mMap;
    private LocationManager locationManager = null;
    private LocationListener locationListener = null;


    public static LatLng mLatLng = null;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        enableMyLocationIfPermitted();

//        user_info user_info = new user_info(getContext());
//mLatLng=new LatLng(Double.valueOf(user_info.getLat()),Double.valueOf(user_info.getLng()));
//make_marke(mLatLng);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                make_marke(latLng);
            }
        });
    }

    private void make_marke(final LatLng latLng) {

        try {
            MarkerOptions options = new MarkerOptions();
            options.position(latLng).draggable(true).title("المنزل");
            mMap.clear();
            mMap.addMarker(options);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5


            if (addresses.size() != 0) {
                location_tv.setText(addresses.get(0).getAddressLine(0));
                locationAdd = location_tv.getText().toString();
                if (Maptyp == 2) {
                    mLatLng = latLng;
                    continue_btn.setEnabled(true);
                    continue_btn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_login_btn_radues10));
                    user_info user_info = new user_info(getActivity());
                    user_info.setLat(String.valueOf(latLng.latitude));
                    user_info.setLng(String.valueOf(latLng.longitude));
                    user_info.setCity(location_tv.getText().toString());
                    new user_info(user_info, getActivity());
                } else {
                    Http_orders http_orders = new Http_orders();
                    final ProgressDialog dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("يتم التأكد من وجود سائقين في هده المتطقة ...");
                    dialog.setTitle("التأكد من وجود سائقين");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.show();

                    http_orders.Get_TestMyPlace(getActivity(), latLng.latitude, latLng.longitude,
                            new Http_orders.Ontestplace_lisennter() {
                                @Override
                                public void onSuccess(testMyplace test) {
                                    dialog.dismiss();
                                    if (test.getStatus()) {
                                        mLatLng = latLng;
                                        continue_btn.setEnabled(true);
                                        continue_btn.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_login_btn_radues10));
                                        user_info user_info = new user_info(getActivity());
                                        user_info.setLat(String.valueOf(latLng.latitude));
                                        user_info.setLng(String.valueOf(latLng.longitude));
                                        user_info.setCity(location_tv.getText().toString());
                                        new user_info(user_info, getActivity());
//                                        Toast.makeText(getActivity(), test.getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), test.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onFailure(String msg) {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableMyLocationIfPermitted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(permissions, 15);


            } else if (mMap != null) {
                mMap.setMyLocationEnabled(true);

            }
        }
    }

    private void showDefaultLocation() {
//        Toast.makeText(getContext(), "Location permission not granted, " + "showing default location", Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(24.7241504, 46.2620508);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 15) {
            boolean grant = false;
            for (int i = 0; i < permissions.length; i++) {
                int grantResult = grantResults[i];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    grant = true;
                } else {
                    grant = false;
                    break;
                }

            }
            if (grant) {
                showDefaultLocation();
            } else
                requestPermissions(permissions, 15);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
//                    != PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(permissions, 15);
//
//
//            } else {
//                showDefaultLocation();
//            }
//        }
//        return;
    }

//    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
//            new GoogleMap.OnMyLocationButtonClickListener() {
//                @Override
//                public boolean onMyLocationButtonClick() {
//                    if (mLatLng!=null){
//                        make_marke(Login.mLatLng);
//                        mMap.setMinZoomPreference(12);
//                    }

//                    return false;
//                }
//            };


}
