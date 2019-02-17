package com.vindicators.voters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    FirebaseServices fHelper;
    Button loginButton;
    Button forgotButton;
    EditText emailField;
    EditText passwordField;
    public static final String EXTRA_MESSAGE = "com.vindicators.voters.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth.getInstance().signOut();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);

        fHelper = new FirebaseServices();

        //LOGIN BUTTON
        loginButton = (Button) findViewById(R.id.login);

        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonPressed();
            }
        });

        forgotButton = (Button) findViewById(R.id.forgotPassword);
        forgotButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotButtonPressed();
            }
        });

        //If user exists, move to home page
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Log.d("AUTH", "Auth: User logged in!!");
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    Button loginButton = (Button) findViewById(R.id.login);
                    String message = loginButton.getText().toString();
                    intent.putExtra(EXTRA_MESSAGE, message);
                    startActivity(intent);
                } else {
                    Log.d("AUTH", "No current user!!");
                }
            }
        });

    }

    public void forgotButtonPressed() {
        forgotButton.setText("change this text");

    }

    /*
    Function Logins in user if credentials are accurate, or creates a new user if credentials are unknown
     */
    public void loginButtonPressed() {

        final String email = emailField.getText().toString();
        final String password = passwordField.getText().toString();


        fHelper.signIn(email, password, new Callback() {
            @Override
            public void onCallback(Object value) {

                if (value == null) {
                    final EditText taskEditText = new EditText(MainActivity.this);
                    taskEditText.setHint("Username");
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("User not recognized")
                            .setMessage("Would you like to create an account?")
                            .setView(taskEditText)
                            .setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    fHelper.createUser(email, password, taskEditText.getText().toString());
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                }
            }
        });

    }

}




