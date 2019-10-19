package com.kh_sof_dev.gaz.Classes.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.kh_sof_dev.gaz.Classes.Order.point.points;

import static android.content.Context.MODE_PRIVATE;

public class user_info {
    private String supplier_id;
    private String id, phone, token, name, email, image, gender, city, pw, address;
    private String lat;
    private String lng;
    private long wallet;
    private int point;

    public user_info(Context mcontext) {
        final SharedPreferences sp = mcontext.getSharedPreferences("Login", MODE_PRIVATE);
        id = sp.getString("id", "");
        token = sp.getString("token", "");
        phone = sp.getString("phone", "+966 000-0000-00");
        name = sp.getString("name", "إسم العميل");
        email = sp.getString("email", "mail@email.com");
        image = sp.getString("image", "");
        gender = sp.getString("gender", "");
        city = sp.getString("city", " ");
        address = sp.getString("address", " ");
        lat = sp.getString("lat", "");
        lng = sp.getString("lng", "");
        pw = sp.getString("pw", "");
        wallet = sp.getLong("wallet", (long) 0.0);
        point = sp.getInt("point", 0);
        supplier_id = sp.getString("supplier_id", "");
    }

    public user_info(String supplier_id, Context context) {
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();

        Ed.putString("supplier_id", String.valueOf(supplier_id));

        Ed.apply();

    }

    public user_info(user user_, String pw, Context context) {
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("phone", user_.getPhoneNumber());
        Ed.putString("id", user_.getId());
        Ed.putString("token", user_.getToken());
        Ed.putString("name", user_.getFullName());
        Ed.putString("image", user_.getImage());
        if (!pw.isEmpty()) {
            Ed.putString("pw", pw);
        }
        Ed.putString("email", user_.getEmail());
        Ed.putString("city", user_.getCity());
        Ed.putString("address", user_.getAddress());
        Ed.putLong("wallet", user_.getWallet());
        Ed.putString("lat", String.valueOf(user_.getLat()));
        Ed.putString("lng", String.valueOf(user_.getLng()));

        Ed.apply();
        new user_info(context);
    }

    public user_info(Context context, Boolean sate) {
        SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("phone", null);
        Ed.putString("id", null);
        Ed.putString("token", null);
        Ed.putString("name", null);
        Ed.putString("image", null);
        Ed.putString("email", null);
        Ed.putString("city", null);
        Ed.putLong("wallet", (long) 0.0);
        Ed.apply();

    }

    public String getSupplier_id(Context mcontext) {
        final SharedPreferences sp = mcontext.getSharedPreferences("Login", MODE_PRIVATE);

        supplier_id = sp.getString("supplier_id", "");
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public user_info() {

    }

    public user_info(Context context, points points) {
        try {
            SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
            SharedPreferences.Editor Ed = sp.edit();
            Ed.putInt("point", points.getPoints());
            Ed.putLong("wallet", points.getMoney());
            Ed.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getImage() {
        return image;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImag() {
        return image;
    }

    public void setImag(String imag) {
        this.image = imag;
    }

    public void setWallet(long wallet) {
        this.wallet = wallet;
    }

    public Long getWallet() {
        return wallet;
    }

    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public user_info(user_info user_, Context mcontext) {
        SharedPreferences sp = mcontext.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("phone", user_.getPhone());
        Ed.putString("id", user_.getId());
        Ed.putString("token", user_.getToken());
        Ed.putString("name", user_.getName());
        Ed.putString("image", user_.getImag());
        Ed.putString("email", user_.getEmail());
        Ed.putString("gender", user_.getGender());
        Ed.putLong("wallet", user_.getWallet());
        Ed.apply();
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
