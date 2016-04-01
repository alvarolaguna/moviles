package com.sam.elderwatch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

public class LoginActivity extends AppCompatActivity {

    EditText myUser, myPass;
    Firebase myFirebaseRef;
    boolean userPressed, passPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        myUser = (EditText)findViewById(R.id.editText);
        myPass = (EditText)findViewById(R.id.editText2);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://elderwatch.firebaseio.com/");
        userPressed = passPressed = false;
        myUser.setText("a@gmail.com");
        myPass.setText("123");
    }

    public void eraseFieldUser(View v){
        if(!userPressed){
            userPressed = true;
            myUser.setText("");
        }
    }

    public void eraseFieldPass(View v){
        if(!passPressed){
            passPressed = true;
            myPass.setText("");
            myPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void createUser(View v){
        myFirebaseRef.createUser(myUser.getText().toString(), myPass.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Toast.makeText(getApplicationContext(), "User created successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Invalid E-Mail",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loginUser(View v){
        myFirebaseRef.authWithPassword(myUser.getText().toString(), myPass.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Toast.makeText(getApplicationContext(), "Login successfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, LiveStreamActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), "Wrong credentials",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
