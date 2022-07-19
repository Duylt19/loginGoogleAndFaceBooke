package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    ImageView fbBtn;
    CallbackManager callbackManager;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView imgGoogleLogin;
    TextView tvRegister;
    TextView tvUsername;
    TextView tvPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            navigateToSecondActivity();
        }
        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            startActivity(new Intent(MainActivity.this, SecondActivity2.class));
            finish();
        }
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(MainActivity.this, "LOGNIN SUCCESSFULL", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, SecondActivity2.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(@NonNull FacebookException exception) {
                        // App code
                    }
                });
        tvUsername = findViewById(R.id.username);
        tvPassword = findViewById(R.id.password);
        imgGoogleLogin = findViewById(R.id.google_btn);
        tvRegister = findViewById(R.id.user_register);
        tvRegister.setOnClickListener(v -> navigateToRegisterActivity());
        imgGoogleLogin = findViewById(R.id.google_btn);
        imgGoogleLogin.setOnClickListener(view -> SignIn());
        fbBtn = findViewById(R.id.fb_btn);
        fbBtn.setOnClickListener(v -> LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Collections.singletonList("public_profile")));
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.logintn);

        //admin and admin

        loginbtn.setOnClickListener(view -> {
            if (tvUsername.getText().toString().equals("admin") && tvPassword.getText().toString().equals("admin")) {
                //correct
                Toast.makeText(MainActivity.this, "LOGNIN SUCCESSFULL", Toast.LENGTH_LONG).show();
                UserLoginDetailModel model = new UserLoginDetailModel("admin", "admin", "admin@gmail.com ");
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.putExtra("UserLoginDetailModel", model);
                startActivity(intent);
            } else
                //incorrect
                Toast.makeText(MainActivity.this, "LOGNIN FAILED !!!", Toast.LENGTH_LONG).show();
        });
    }

    private void navigateToRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void SignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Log.e("TAG", "onActivityResult: " + e.getMessage());
            }
        }
    }

    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
