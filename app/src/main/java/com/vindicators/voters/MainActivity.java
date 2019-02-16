package com.vindicators.voters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    FirebaseServices fHelper;
    Button loginButton;
    Button forgotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fHelper = new FirebaseServices();

        //LOGIN BUTTON
        loginButton = (Button)findViewById(R.id.login);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonPressed();
            }
        });

        forgotButton = (Button)findViewById(R.id.forgotPassword);
        forgotButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotButtonPressed();
            }
        });

    }

    public void forgotButtonPressed(){
        forgotButton.setText("change this text");

    }

    public void loginButtonPressed(){
        loginButton.setText("change my text");

    }

    }
