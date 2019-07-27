package com.kh_sof_dev.gaz.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kh_sof_dev.gaz.Classes.User.Http_user;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;


public class ChangePassword extends AppCompatActivity {

    EditText old, newPw, rePW;
    ImageView back;
    Button save;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_f_change_password);
        progressBar = findViewById(R.id.progress);
        old = findViewById(R.id.oldPW);
        newPw = findViewById(R.id.newPw);
        rePW = findViewById(R.id.re_password_et);
        back = findViewById(R.id.back_btn);
        save = findViewById(R.id.save_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePW();
            }
        });
    }

    private void changePW() {


        String newPss = newPw.getText().toString();
        String oldPss = old.getText().toString();
        String rePss = rePW.getText().toString();
        user_info user_info = new user_info(this);
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
        http_user.Post_change_password(this, newPss, new Http_user.user_createListener() {
            @Override
            public void onSuccess(new_account new_account) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(ChangePassword.this, "تم تعديل كلمة المرور بنجاح", Toast.LENGTH_LONG).show();
                finish();
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
