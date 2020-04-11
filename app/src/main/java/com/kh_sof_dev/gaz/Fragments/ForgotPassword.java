package com.kh_sof_dev.gaz.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.Classes.User.Http_user;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.R;

public class ForgotPassword extends Fragment {

    public ForgotPassword() {

    }

    private EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_forgot_password, container, false);
        email = view.findViewById(R.id.email_et);
        Button send = view.findViewById(R.id.send);
        ImageView logout = view.findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivityForFragment().changeFragment(new LoginFrag());
//                _goto(new LoginFrag());
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget_function();
            }
        });
        return view;
    }

//    private void _goto(Fragment frg) {
//        Login.fragmentManager = getFragmentManager();
//        Login.fragmentTransaction = Login.fragmentManager.beginTransaction();
//        Login.fragment = frg;
//        Login.fragmentTransaction.replace(R.id.ContentLogin, Login.fragment);
//        Login.fragmentTransaction.commit();
//    }

    private void forget_function() {
        String email_ = email.getText().toString();
        if (email_.isEmpty() || !email_.contains("@")) {
            email.setError(email.getHint());
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(getActivityForFragment());
        dialog.setTitle("إرسال كلمة المرور");
        dialog.setMessage("يتم إرسال كلمة المرور الى بريدك، نرجو الانتظار   !");
        dialog.show();
        Http_user http_user = new Http_user();
        http_user.Post_forget_password(getContext(), "", email_, new Http_user.user_createListener() {
            @Override
            public void onSuccess(new_account new_account) {
                Toast.makeText(getActivityForFragment(), new_account.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

                Toast.makeText(getActivityForFragment(), msg, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private Login getActivityForFragment() {
        return ((Login) getActivity());
    }

}
