package com.vindicators.voters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    FirebaseServices fHelper;

    Button loginButton;
    Button forgotButton;
    public static final String EXTRA_MESSAGE = "com.vindicators.voters.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fHelper = new FirebaseServices();

        //Forgot BUTTON

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


    /**
     * Called when the user taps the login button
     */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, HomeActivity.class);
        Button loginButton = (Button) findViewById(R.id.login);
        String message = loginButton.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
}




