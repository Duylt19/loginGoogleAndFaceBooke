package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Model.User;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;

public class SecondActivity2 extends AppCompatActivity {
    ImageView imageView;
    TextView name;
    Button logOubtn;
    TextView tvNameUsername;
    EditText edtUsername;
    EditText edtPassword;
    Button btnPushData;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ArrayList<User> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnPushData = findViewById(R.id.btnPushData);
        listUser = new ArrayList<>();
        tvNameUsername = findViewById(R.id.tvNameUsername);
        logOubtn = findViewById(R.id.loguot);
        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        mDatabase = FirebaseDatabase.getInstance();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                (object, response) -> {
                    if (object != null) {
                        try {
                            String fullName = object.getString("name");
                            String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            name.setText(fullName);
                            Picasso.get().load(url).into(imageView);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("TAG", "onFailed");
                    }
                    // Application code
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();

        logOubtn.setOnClickListener(view -> {
            LoginManager.getInstance().logOut();
            startActivity(new Intent(SecondActivity2.this, MainActivity.class));
            finish();
        });

//        btnPushData.setOnClickListener(view -> {
//            mReference = mDatabase.getReference("list_user");
//            User user = new User(edtUsername.getText().toString(), edtPassword.getText().toString());
//            mReference.child(edtUsername.getText().toString()).setValue(user, (error, ref) -> Log.e("TAG", "onComplete: "));
//        });
    }
}
