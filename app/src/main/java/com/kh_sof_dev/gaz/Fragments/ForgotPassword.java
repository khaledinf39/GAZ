package com.kh_sof_dev.gaz.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotPassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotPassword extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ForgotPassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForgotPassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotPassword newInstance(String param1, String param2) {
        ForgotPassword fragment = new ForgotPassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
ImageView logout;
    Button send;
    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.layout_f_forgot_password, container, false);
        email=(EditText)view.findViewById(R.id.email_et);
        send=(Button)view.findViewById(R.id.send);
        logout=(ImageView)view.findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _goto(new LoginFrag());
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
    private void _goto(Fragment frg){
        Login.fragmentManager = getFragmentManager();
        Login.fragmentTransaction = Login.fragmentManager.beginTransaction();
        Login.fragment = frg;
        Login.fragmentTransaction.replace(R.id.ContentLogin, Login.fragment);
        Login.fragmentTransaction.commit();
    }
    private void forget_function() {
        String email_=email.getText().toString();
        if (email_.isEmpty() || !email_.contains("@"))
        {
            email.setError(email.getHint());
            return;
        }
        final ProgressDialog dialog=new ProgressDialog(getContext());
        dialog.setTitle("إرسال كلمة المرور");
        dialog.setMessage("يتم إرسال كلمة المرور الى بريدك، نرجو الانتظار   !");
        dialog.show();
        Http_user http_user=new Http_user();
        http_user.Post_forget_password(getContext(), "", email_, new Http_user.user_createListener() {
            @Override
            public void onSuccess(new_account new_account) {
                Toast.makeText(getContext(),new_account.getMessage(),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailure(String msg) {

                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


}
