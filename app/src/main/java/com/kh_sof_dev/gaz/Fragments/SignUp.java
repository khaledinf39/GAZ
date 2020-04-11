package com.kh_sof_dev.gaz.Fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.kh_sof_dev.gaz.Adapters.City_adapter;
import com.kh_sof_dev.gaz.Classes.User.Http_user;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.Classes.User.user;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.Classes.constant.City;
import com.kh_sof_dev.gaz.Classes.constant.Http_get_constant;
import com.kh_sof_dev.gaz.Classes.constant.show_cites;
import com.kh_sof_dev.gaz.R;
import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.activities.MainNew;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends Fragment {

    public SignUp() {

    }

    user_info mUser = null;

    @SuppressLint("ValidFragment")
    public SignUp(user_info user_info) {
        mUser = user_info;
    }

    ImageView back;
    EditText first_name, famly_name, email, password, repassword, phone;
    Button logup;
    TextView login;
    Spinner gender, city;
    List<City> cityList;

    private String cite_name = null;

    String number;
    CountryCodePicker ccp;
    ProgressBar progressBar;
    String fcmtoken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_sign_up, container, false);
        progressBar = view.findViewById(R.id.progress);
        back = (ImageView) view.findViewById(R.id.back_icon);
        logup = (Button) view.findViewById(R.id.logup_btn);
        login = (TextView) view.findViewById(R.id.login_tv);
        first_name = (EditText) view.findViewById(R.id.firstName_et);
        famly_name = (EditText) view.findViewById(R.id.familyName_et);
        phone = (EditText) view.findViewById(R.id.phoneNo_et);
        email = (EditText) view.findViewById(R.id.email_et);
        password = (EditText) view.findViewById(R.id.password_et);
        repassword = (EditText) view.findViewById(R.id.re_password_et);
        gender = (Spinner) view.findViewById(R.id.account_typ_et);
        city = (Spinner) view.findViewById(R.id.city_et);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Login) getActivity()).changeFragment(new LoginFrag());
//                _goto(new LoginFrag());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Login) getActivity()).changeFragment(new LoginFrag());
//                _goto(new LoginFrag());
            }
        });
        logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logup_function();
            }
        });
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone);

        FirebaseApp.initializeApp(getContext());
        fcmtoken = FirebaseInstanceId.getInstance().getToken();

        Loading_cities();
        Loading_data();
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
        return view;
    }

    int RegisterType = 3;

    private void Loading_data() {
        if (mUser != null) {
            famly_name.setText(mUser.getGender());
            first_name.setText(mUser.getName());
            email.setText(mUser.getEmail());
            RegisterType = mUser.getPoint();
        }
    }

    private void Loading_cities() {
        cityList = new ArrayList<>();
        City c = new City("id", "المدينة");
        cityList.add(c);
        final City_adapter adapter = new City_adapter(getActivity(), cityList, new City_adapter.citylissener() {
            @Override
            public void onsuccessfully(String city) {
                cite_name = city;
            }
        });
        city.setAdapter(adapter);
        Http_get_constant http_get_constant = new Http_get_constant();
        http_get_constant.cites(getContext(), new Http_get_constant.citesListener() {
            @Override
            public void onSuccess(show_cites cites) {
                progressBar.setVisibility(View.GONE);
                cityList.addAll(cites.getItems());
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

    private void logup_function() {
        String firstname = first_name.getText().toString();
        String famlyname = famly_name.getText().toString();
        String email_ = email.getText().toString();
        number = ccp.getFullNumberWithPlus();
        final String pass = password.getText().toString();
        String repass = repassword.getText().toString();
        if (firstname.isEmpty()) {
            first_name.setError(first_name.getHint());

            return;
        }
//        if (famlyname.isEmpty()){
//            famly_name.setError(famly_name.getHint());
//
//            return ;
//        }
        if (number.isEmpty()) {
            phone.setError(phone.getHint());

            return;
        }
        if (email_.isEmpty() || !email_.contains("@")) {
            email.setError(email.getHint());

            return;
        }
//        if (gender.getSelectedItemPosition()==0){
//            Toast.makeText(getContext(),"إختار الجنس من فضلك",Toast.LENGTH_LONG).show();
//
//            return ;
//        }
        if (city.getSelectedItemPosition() == 0) {
            Toast.makeText(getContext(), "إختار المدينة من فضلك", Toast.LENGTH_LONG).show();

            return;
        }

//        if (Login.mLatLng==null ){
//            Toast.makeText(getContext(),"يرجى فتح محدد الـ  GPS لأخذ إحداثياتك",Toast.LENGTH_LONG).show();
//
//            return ;
//        }

        if (pass.isEmpty() || !pass.equals(repass)) {
            password.setError(password.getHint());
            repassword.setError(repassword.getHint());
            return;
        }
        try {
            final ProgressDialog dialog = new ProgressDialog(getContext());
            dialog.setTitle("تسجيل حساب جديد");
            dialog.setMessage("يتم تسجيل حساب جديد نرجو الانتظار   !");
            dialog.show();
            final Http_user http_user = new Http_user();
            http_user.Post_create_user(getContext(), RegisterType
                    , number, firstname, pass, gender.getSelectedItem().toString()
                    , email_, cite_name, new Http_user.user_createListener() {
                        @Override
                        public void onSuccess(new_account new_account) {
                            try {
                                Toast.makeText(getContext(), new_account.getMessage(), Toast.LENGTH_LONG).show();
                                Log.d("fcmToken", fcmtoken);
                                http_user.Put_ActvitPhone_user(getContext(), new_account.getItems().getId(), "1234"
                                        , fcmtoken, new Http_user.user_createListener() {
                                            @Override
                                            public void onSuccess(com.kh_sof_dev.gaz.Classes.User.new_account new_account) {
                                                if (new_account.isStatus()) {
                                                    dialog.dismiss();
                                                    user user_ = new_account.getItems();
                                                    if (mUser != null) {
                                                        user_.setImage(mUser.getImage());
                                                    }
                                                    new user_info(user_, pass, getContext());
                                                    Toast.makeText(getContext(), new_account.getMessage(), Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(getContext(), MainNew.class));
                                                    getActivity().finish();
                                                } else {
                                                    dialog.dismiss();
                                                    Toast.makeText(getContext(), new_account.getMessage(), Toast.LENGTH_LONG).show();

                                                }
                                            }

                                            @Override
                                            public void onStart() {

                                            }

                                            @Override
                                            public void onFailure(String msg) {
                                                dialog.dismiss();
                                                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                            }
                                        });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {
                            try {
                                dialog.dismiss();
                                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void country_cod() {
//    List<Country> countries = new ArrayList<Country>();
//    // Get ISO countries, create Country object and
//    // store in the collection.
//    String[] isoCountries = Locale.getISOCountries();
//    for (String country : isoCountries) {
//        Locale locale = new Locale("en", country);
//        String iso = locale.getISO3Country()+getContext().getString(R.array.city_array);
//        String code = locale.getCountry();
//        String name = locale.getDisplayCountry();
//
//        if (!"".equals(iso) && !"".equals(code) && !"".equals(name)) {
//            countries.add(new Country(iso, code, name));
//        }
//    }
//    }

//    private void _goto(Fragment frg) {
//        Login.fragmentManager = getFragmentManager();
//        Login.fragmentTransaction = Login.fragmentManager.beginTransaction();
//        Login.fragment = frg;
//        Login.fragmentTransaction.replace(R.id.ContentLogin, Login.fragment);
//        Login.fragmentTransaction.commit();
//    }
}
