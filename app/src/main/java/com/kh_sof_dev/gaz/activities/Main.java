package com.kh_sof_dev.gaz.activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.kh_sof_dev.gaz.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Main extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    private AccessTokenTracker accessTokenTracker;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.layout_f_loginfrag);

        callbackManager= CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.setAuthType("rerequest");
        // Creating CallbackManager
//        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            // This method is invoked everytime access token changes
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                useLoginInformation(currentAccessToken);

            }
        };
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"error_login", Toast.LENGTH_SHORT).show();
            }
        });

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(getApplicationContext(), "succ 2", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(getApplicationContext(), "cancel 2", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(getApplicationContext(), "error 2", Toast.LENGTH_SHORT).show();
                        exception.printStackTrace();

                    }
                });

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
                    Toast.makeText(getApplicationContext(),name+email+"fb info",Toast.LENGTH_LONG).show();
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
    private void goMainScreen() {
        Intent intent = new Intent(getApplicationContext(), MainNew.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("FRAGMENT", "onResultCalled");

        Toast.makeText(getApplicationContext(),data.toString()+"",Toast.LENGTH_LONG).show();
    }
}
