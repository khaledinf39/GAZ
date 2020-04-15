package com.kh_sof_dev.gaz.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.Utils;
import com.kh_sof_dev.gaz.Fragments.Home;
import com.kh_sof_dev.gaz.Fragments.Notifications;
import com.kh_sof_dev.gaz.R;

import org.json.JSONArray;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainNew extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public FragmentManager fragmentManager;
    public Fragment fragment;
    public FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;
    LinearLayout bottombar;

    TextView username;
    TextView wallet;
    CircleImageView user_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_main_new);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        bottombar = findViewById(R.id.bottonbar);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = fragmentManager.findFragmentById(R.id.ContentLogin);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        //***********************selecte home bar defealt***************/
        bottomNavigationView.setSelectedItemId(R.id.Home);
        fragmentTransaction.replace(R.id.ContentLogin, new Home()).commit();
        fragmentTransaction.addToBackStack(null);
        //***************************************************************/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                if (fragment == null)
                    fragment = fragmentManager.findFragmentById(R.id.ContentLogin);

                switch (menuItem.getItemId()) {
                    case R.id.Search:
                        fragment = null;
                        startActivity(new Intent(MainNew.this, Search.class));
//                        fragment = new Search();
                        break;
                    case R.id.Home:
                        fragment = new Home();
                        break;
                    case R.id.Notification:
                        fragment = new Notifications();
                        break;
                    case R.id.MyReservations:
                        fragment = null;
                        startActivity(new Intent(MainNew.this, MyReservations.class));
//                        fragment = new MyReservations();
                        break;
                }

                if (fragment != null) {
                    fragmentTransaction.replace(R.id.ContentLogin, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemTextAppearance(R.style.text_white_sizeBig);

        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        username = headerView.findViewById(R.id.UserName);
        wallet = headerView.findViewById(R.id.wallet);
        user_img = headerView.findViewById(R.id.user_img);
        user_info user_info = new user_info(getApplicationContext());
        username.setText(user_info.getName());
        wallet.setText("لديك " + user_info.getPoint() + " نقطة");
        if (!user_info.getImag().isEmpty()) {
            Utils.showImage(getApplicationContext(), user_info.getImag(),
                    R.drawable.ic_user_img_gray, user_img);
//            Picasso.with(getApplicationContext())
//                    .load(user_info.getImag())
//                    .placeholder(R.drawable.ic_user_img_gray)
//                    .into(user_img);
        }
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainNew.this, EditProfile.class));
//                goto_auther(new EditProfile());
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragment = (Fragment) fragmentManager.findFragmentById(R.id.ContentLogin);
//                fragmentTransaction.replace(R.id.ContentLogin, new EditProfile()).commit();
//                fragmentTransaction.addToBackStack(null);
            }
        });

        testUpdate();
    }

    void testUpdate() {
        RequestQueue queue = Volley.newRequestQueue(this);  // this = context
        String url = getString(R.string.api) + "api/getUpdates";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response
                Log.e("Response", response);
                try {
                    JSONObject jobject = new JSONObject(response);
                    boolean status = jobject.optBoolean("status");
                    if (status) {
                        JSONArray items = jobject.optJSONArray("items");
                        JSONObject item = items.getJSONObject(0);
                        String androidVersion = item.optString("isAndroid");
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        String version = pInfo.versionName;
                        Log.e("androidVersion", androidVersion + " , " + version + " , " + compareVersionNames(version, androidVersion));
                        if (compareVersionNames(version, androidVersion) == -1)
                            updateApplication();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // error
                        Log.e("Error.Response", String.valueOf(error.getMessage()));
                    }
                }
        );
        queue.getCache().initialize();
        queue.add(postRequest);
        queue.getCache().clear();
    }

    public int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

        for (int i = 0; i < maxIndex; i++) {
            int oldVersionPart = Integer.valueOf(oldNumbers[i]);
            int newVersionPart = Integer.valueOf(newNumbers[i]);

            if (oldVersionPart < newVersionPart) {
                res = -1;
                break;
            } else if (oldVersionPart > newVersionPart) {
                res = 1;
                break;
            }
        }

        if (res == 0 && oldNumbers.length != newNumbers.length) {
            res = (oldNumbers.length > newNumbers.length) ? 1 : -1;
        }

        return res;
    }

    void updateApplication() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_force_update);

        Button update = dialog.findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        dialog.show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCancelable(false);
//        builder.setTitle("تنبيه");
//        builder.setMessage("النسخة الأخيرة اللتي تستخدمها قديمة - نرجو تحديث التطبيق للأهمية")
//                .setPositiveButton("تحديث", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
//                        try {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                        } catch (android.content.ActivityNotFoundException anfe) {
//                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//                        }
//                    }
//                })
//                .setNegativeButton("لا أريد التحديث", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finish();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.setTitle("تحديث التطبيث");
//        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        bottomNavigationView.setSelectedItemId(R.id.Home);
//        fragmentTransaction.replace(R.id.ContentLogin, new Home()).commit();
//        fragmentTransaction.addToBackStack(null);

        user_info user_info = new user_info(getApplicationContext());
        username.setText(user_info.getName());
        wallet.setText("لديك " + user_info.getPoint() + " نقطة");
        if (!user_info.getImag().isEmpty()) {
            Utils.showImage(getApplicationContext(), user_info.getImag(),
                    R.drawable.ic_user_img_gray, user_img);
//            Picasso.with(getApplicationContext())
//                    .load(user_info.getImag())
//                    .placeholder(R.drawable.ic_user_img_gray)
//                    .into(user_img);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
                Log.d("Activity", "ON RESULT CALLED");
            }
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            finish();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_new, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d("id_select", id + "");
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragment = fragmentManager.findFragmentById(R.id.ContentLogin);

        switch (id) {
            case R.id.nav_profile:
                startActivity(new Intent(MainNew.this, Profile.class));
//                fragment = new Profile();
                break;
//            case R.id.nav_follow:
//                break;
            case R.id.nav_favorite:
                startActivity(new Intent(MainNew.this, MyFavorites.class));
//                fragment = new MyFavorites();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(MainNew.this, ChangePassword.class));
//                fragment = new ChangePassword();
                break;
            case R.id.nav_terms:
                startActivity(new Intent(MainNew.this, UserAgreement.class));
//                fragment = new UserAgreement();
                break;
            case R.id.nav_about_us:
                startActivity(new Intent(MainNew.this, AboutUs.class));
//                fragment = new AboutUs();
                break;
//            case R.id.nav_help:
////fragment=new
//                break;
            case R.id.nav_rate:
                startActivity(new Intent(MainNew.this, RateApp.class));
//                fragment = new RateApp();
                break;
            case R.id.nav_share:
//fragment=new Follow_order(new Order_getter());
//                fragment = null;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "انصحك بهذا النطبيق");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
//            case R.id.nav_logout :
//                Http_user http_user=new Http_user();
//                final ProgressDialog dialog=new ProgressDialog(this);
//                dialog.setMessage("تسجيل الخروج...");
//                dialog.show();
//                http_user.Post_Logout(getApplicationContext(), new Http_user.user_createListener() {
//                    @Override
//                    public void onSuccess(new_account new_account) {
//                       if (new_account.isStatus())
//                       {
//                           dialog.dismiss();
//                           Toast.makeText(getApplicationContext(),new_account.getMessage(),Toast.LENGTH_LONG).show();
//                           new user_info(getApplicationContext(),false);
//                           startActivity(new Intent(MainNew.this,Logo.class));
//
//                           MainNew.this.finish();
//                       }
//                    }
//
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onFailure(String msg) {
//                        dialog.dismiss();
//                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
//                    }
//                });
//
//               return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

//        if (fragment != null) {
//            goto_auther(fragment);
//            fragmentTransaction.replace(R.id.ContentLogin, fragment).commit();
//            fragmentTransaction.addToBackStack(null);
//            bottombar.setVisibility(View.GONE);
//        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

//    private void goto_auther(Fragment new_frg) {
//        OutherActivty.new_frg = new_frg;
//        startActivity(new Intent(MainNew.this, OutherActivty.class));
//    }

//    public static void goto_(Fragment new_frg, Context mcontext) {
//        OutherActivty.new_frg = new_frg;
//        mcontext.startActivity(new Intent(mcontext, OutherActivty.class));
//    }


//    public static void _goback(Fragment frg, Fragment new_frg, int bottom_visibility) {
//
//        fragmentManager = frg.getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragment = new_frg;
//        fragmentTransaction.remove(fragment);
//        fragmentTransaction.commit();
//        bottombar.setVisibility(bottom_visibility);
//    }
}
