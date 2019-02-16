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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //LOGIN BUTTON
        final Button loginButton = (Button)findViewById(R.id.login);
        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setText("change my text");
            }
        });

        final Button forgotButton = (Button)findViewById(R.id.forgotPassword);
        forgotButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotButton.setText("change this text");
            }
        });

    }

    }
