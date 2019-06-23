package com.kh_sof_dev.gaz.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuPresenter;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.kh_sof_dev.gaz.Classes.User.Http_user;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Fragments.AboutUs;
import com.kh_sof_dev.gaz.Fragments.ChangePassword;
import com.kh_sof_dev.gaz.Fragments.EditProfile;
import com.kh_sof_dev.gaz.Fragments.Home;
import com.kh_sof_dev.gaz.Fragments.MyFavorites;
import com.kh_sof_dev.gaz.Fragments.RateApp;
import com.kh_sof_dev.gaz.Fragments.Reservations.MyReservations;
import com.kh_sof_dev.gaz.Fragments.Notifications;
import com.kh_sof_dev.gaz.Fragments.Profile;
import com.kh_sof_dev.gaz.Fragments.Search;
import com.kh_sof_dev.gaz.Fragments.Settings;
import com.kh_sof_dev.gaz.Fragments.UserAgreement;
import com.kh_sof_dev.gaz.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainNew extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static FragmentManager fragmentManager;
    public static Fragment fragment;
    public static FragmentTransaction fragmentTransaction;
    static BottomNavigationView bottomNavigationView;
    static LinearLayout bottombar;

    TextView username;
    TextView wallet;
    CircleImageView user_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_a_main_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        bottombar = (LinearLayout) findViewById(R.id.bottonbar);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = (Fragment) fragmentManager.findFragmentById(R.id.ContentLogin);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        /***********************selecte home bar defealt***************/
        bottomNavigationView.setSelectedItemId(R.id.Home);
        fragmentTransaction.replace(R.id.ContentLogin, new Home()).commit();
        fragmentTransaction.addToBackStack(null);
        /***************************************************************/
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragment = (Fragment) fragmentManager.findFragmentById(R.id.ContentLogin);

                switch (menuItem.getItemId()) {
                    case R.id.Search:
                        fragment = new Search();
                        break;
                    case R.id.Home:
                        fragment = new Home();
                        break;
                    case R.id.Notification:
                        fragment = new Notifications();
                        break;
                    case R.id.MyReservations:

                        fragment = new MyReservations();
                        break;
                }

                if (fragment != null) {

                    fragmentTransaction.replace(R.id.ContentLogin, fragment).commit();
                    fragmentTransaction.addToBackStack(null);
                    return true;
                }
                return false;
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemTextAppearance(R.style.text_white_sizeBig);

        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.UserName);
        wallet = (TextView) headerView.findViewById(R.id.wallet);
        user_img = (CircleImageView) headerView.findViewById(R.id.user_img);
        user_info user_info = new user_info(getApplicationContext());
        username.setText(user_info.getName());
        wallet.setText("لديك " + user_info.getPoint() + " نقطة");
        if (!user_info.getImag().isEmpty()) {
            Glide.with(getApplicationContext())
                    .load(user_info.getImag())
                    .placeholder(R.drawable.ic_user_img_gray)
                    .into(user_img);
        }
        user_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goto_auther(new EditProfile());
//                fragmentManager = getSupportFragmentManager();
//                fragmentTransaction = fragmentManager.beginTransaction();
//                fragment = (Fragment) fragmentManager.findFragmentById(R.id.ContentLogin);
//
//                fragmentTransaction.replace(R.id.ContentLogin, new EditProfile()).commit();
//                fragmentTransaction.addToBackStack(null);
            }
        });

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
            Glide.with(getApplicationContext())
                    .load(user_info.getImag())
                    .placeholder(R.drawable.ic_user_img_gray)
                    .into(user_img);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            Toast.makeText(this, "NavView Error!", Toast.LENGTH_SHORT).show();
//            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d("id_select", id + "");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = (Fragment) fragmentManager.findFragmentById(R.id.ContentLogin);

        switch (id) {
            case R.id.nav_profile:
                fragment = new Profile();

                break;
            case R.id.nav_follow:

                break;
            case R.id.nav_favorite:
                fragment = new MyFavorites();
                break;
            case R.id.nav_settings:
                fragment = new ChangePassword();
                break;
            case R.id.nav_terms:
                fragment = new UserAgreement();
                break;
            case R.id.nav_about_us:
                fragment = new AboutUs();
                break;
            case R.id.nav_help:
//fragment=new
                break;
            case R.id.nav_rate:
                fragment = new RateApp();
                break;
            case R.id.nav_share:
//fragment=new Follow_order(new Order_getter());
                fragment = null;
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (fragment != null) {
            goto_auther(fragment);
//            fragmentTransaction.replace(R.id.ContentLogin, fragment).commit();
//            fragmentTransaction.addToBackStack(null);
//            bottombar.setVisibility(View.GONE);
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void goto_auther(Fragment new_frg) {
        OutherActivty.new_frg = new_frg;
        startActivity(new Intent(MainNew.this, OutherActivty.class));
    }

    public static void goto_(Fragment new_frg, Context mcontext) {
        OutherActivty.new_frg = new_frg;
        mcontext.startActivity(new Intent(mcontext, OutherActivty.class));
    }

    //
    public static void _goback(Fragment frg, Fragment new_frg, int bottom_visibility) {

        fragmentManager = frg.getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new_frg;
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
        bottombar.setVisibility(bottom_visibility);
    }
}
