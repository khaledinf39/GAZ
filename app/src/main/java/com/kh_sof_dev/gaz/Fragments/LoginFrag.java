package com.kh_sof_dev.gaz.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.kh_sof_dev.gaz.activities.Login;
import com.kh_sof_dev.gaz.activities.MainNew;
import com.kh_sof_dev.gaz.Classes.User.Http_user;
import com.kh_sof_dev.gaz.Classes.User.new_account;
import com.kh_sof_dev.gaz.Classes.User.user_info;
import com.kh_sof_dev.gaz.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.internal.FacebookDialogFragment.TAG;


public class LoginFrag extends Fragment {

    public LoginFrag() {
        // Required empty public constructor
    }

    EditText phone, password;
    TextView logup, forget;
    Button login;
    ImageView logout;
    private AccessTokenTracker accessTokenTracker;
    private LoginButton FaceBookButton;
    private CallbackManager callbackManager;
    String number;
    CountryCodePicker ccp;
    String fcmtoken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();
        View view = inflater.inflate(R.layout.layout_f_loginfrag, container, false);

        FirebaseApp.initializeApp(getContext());
        fcmtoken = FirebaseInstanceId.getInstance().getToken();

        final ProgressBar progressBar = view.findViewById(R.id.progress);
        login_with_google();
        Button signInButton = view.findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        FaceBookButton = (LoginButton) view.findViewById(R.id.loginButton);
        FaceBookButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        // Creating CallbackManager
//        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            // This method is invoked everytime access token changes
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                useLoginInformation(currentAccessToken);

            }
        };
        FaceBookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "succ", Toast.LENGTH_SHORT).show();
                AccessToken accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "cancel_login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "error_login", Toast.LENGTH_SHORT).show();
            }
        });


        phone = (EditText) view.findViewById(R.id.phoneNo_et);
        password = (EditText) view.findViewById(R.id.password_et);
        logup = (TextView) view.findViewById(R.id.logup_tv);
        forget = (TextView) view.findViewById(R.id.forget_tv);
        login = (Button) view.findViewById(R.id.login_btn);
        logout = (ImageView) view.findViewById(R.id.logout_btn);
        ccp = (CountryCodePicker) view.findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verify()) {
                    progressBar.setVisibility(View.VISIBLE);
                    Http_user http_user = new Http_user();
                    http_user.Post_Login(getContext(), fcmtoken, password.getText().toString()
                            , number, new Http_user.user_loginListener() {
                                @Override
                                public Boolean onSuccess(new_account new_account) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), new_account.getMessage(), Toast.LENGTH_LONG).show();
                                    if (new_account.isStatus()) {
                                        new user_info(new_account.getItems(), password.getText().toString(), getContext());
                                        startActivity(new Intent(getContext(), MainNew.class));
                                        getActivity().finish();
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public Boolean onFailure(String msg) {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                    return false;
                                }
                            });

                }
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _goto(new ForgotPassword());
            }
        });
        logup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _goto(new SignUp());
            }
        });
        return view;
    }

    int RC_SIGN_IN = 1;

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void goMainScreen() {
        Intent intent = new Intent(getContext(), MainNew.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    GoogleSignInClient mGoogleSignInClient;

    private void login_with_google() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        updateUI(account);

    }

    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Toast.makeText(getContext(), name + email + "fb info", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,email,picture.width(200)");
//        request.setParameters(parameters);
//        // Initiate the GraphRequest
//        request.executeAsync();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (alreadyloggedAccount != null) {
            Toast.makeText(getContext(), "Already Logged In" + alreadyloggedAccount.getDisplayName()
                    + alreadyloggedAccount.getEmail(), Toast.LENGTH_SHORT).show();
            // onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            useLoginInformation(accessToken);
        }

         /*
          Sign-out is initiated by simply calling the googleSignInClient.signOut API. We add a
          listener which will be invoked once the sign out is the successful
           */

    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, account.getDisplayName()));
//
//            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
            user_info user_info = new user_info(getContext());
            user_info.setPoint(2);
            user_info.setName(account.getGivenName());
            user_info.setGender(account.getFamilyName());
            user_info.setEmail(account.getEmail());
            try {
                user_info.setImag(account.getPhotoUrl().toString());
            } catch (Exception e) {

            }
            _goto(new SignUp(user_info));
            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    //On Succesfull signout we navigate the user back to LoginActivity
//                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                }
            });
//            Toast.makeText(getContext(),"googel login  "+account.getEmail()+account.getDisplayName(),Toast.LENGTH_LONG).show();
        } else {

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            return;
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("FRAGMENT", "onResultCalled");

        Toast.makeText(getContext(), data.toString() + "", Toast.LENGTH_LONG).show();
    }

    // [START handleSignInResult]
    private void handleSignInResult(com.google.android.gms.tasks.Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private boolean verify() {
        number = ccp.getFullNumberWithPlus();
        String pass = password.getText().toString();
        if (number.isEmpty()) {
            phone.setError(phone.getHint());

            return false;
        }
        if (pass.isEmpty()) {
            password.setError(password.getHint());
            return false;
        }

        return true;
    }


    private void _goto(Fragment frg) {
        Login.fragmentManager = getFragmentManager();
        Login.fragmentTransaction = Login.fragmentManager.beginTransaction();
        Login.fragment = frg;
        Login.fragmentTransaction.replace(R.id.ContentLogin, Login.fragment);
        Login.fragmentTransaction.commit();
    }

}
