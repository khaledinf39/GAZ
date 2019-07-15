package com.kh_sof_dev.gaz.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Classes.User.Http_user;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;


public class ChangePassword extends Fragment {

    public ChangePassword() {

    }

    EditText old, newPw, rePW;
    ImageView back;
    Button save;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_f_change_password, container, false);
        progressBar = view.findViewById(R.id.progress);
        old = view.findViewById(R.id.oldPW);
        newPw = view.findViewById(R.id.newPw);
        rePW = view.findViewById(R.id.re_password_et);
        back = view.findViewById(R.id.back_btn);
        save = view.findViewById(R.id.save_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePW();
            }
        });
        return view;
    }

    private void changePW() {


        String newPss = newPw.getText().toString();
        String oldPss = old.getText().toString();
        String rePss = rePW.getText().toString();
        user_info user_info = new user_info(getContext());
        if (oldPss.isEmpty() || !oldPss.equals(user_info.getPw())) {
            old.setError(old.getHint());
            return;
        }
        if (newPss.isEmpty()) {
            newPw.setError(newPw.getHint());
            return;
        }
        if (rePss.isEmpty() || !rePss.equals(newPss)) {
            rePW.setError(rePW.getHint());
            return;
        }
        if (oldPss.isEmpty()) {
            old.setError(old.getHint());
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        Http_user http_user = new Http_user();
        http_user.Post_change_password(getContext(), newPss, new Http_user.user_createListener() {
            @Override
            public void onSuccess(new_account new_account) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "تم تعديل كلمة المرور بنجاح", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

            }
        });

    }

}
