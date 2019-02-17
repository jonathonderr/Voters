package com.vindicators.voters;

import android.content.*;
import android.location.*;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseServices fHelper;
    Button loginButton;
    Button forgotButton;
    EditText emailField;
    EditText passwordField;
    public static final String EXTRA_MESSAGE = "com.vindicators.voters.MESSAGE";

    @Override
    //onCreate - does this the first time the app is opened (unless app process is killed)
    protected void onCreate(Bundle savedInstanceState) {
        //FirebaseAuth.getInstance().signOut();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add locationmanager initialization if we plan on updating location

        FirebaseApp.initializeApp(this);

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
                    fHelper.addUserFriend(fHelper.mAuth.getCurrentUser().getUid(), "PhubbEkVb2Tp57BrfmjnTxcY2yi1", new Callback() {
                        @Override
                        public void onCallback(Object value) {

                        }
                    });
                } else {
                    Log.d("AUTH", "No current user!!");
                }
            }
        });
    }


        @Override
        //onStart - does this every time the app is opened
        protected void onStart () {
            super.onStart();
            LocationManager mylocation = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE); //
            final boolean gpsEnabled = mylocation.isProviderEnabled(LocationManager.GPS_PROVIDER);  //checks if gps provider is enabled on device

        if (!gpsEnabled) { //if gps provider is disabled on device
                           // Build an alert dialog here that requests that the user enable
                           // the location services, then when the user clicks the "OK" button,
                           // call enableLocationSettings()
            AlertDialog.Builder dialog = new AlertDialog.Builder(this); //alert pop-up will display
                    dialog.setTitle("TURN ON LOCATION SERVICES");
                    dialog.setMessage("Please turn on location services my guy");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) { //after "ok" button is clicked, call enableLocationSettings, dismiss pop-up
                            enableLocationSettings();
                            dialog.dismiss(); //MyActivity.dismiss();
                        }
                    });
            dialog.show();
        }
    }

        private void enableLocationSettings () { //takes user to location settings
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }

        public void forgotButtonPressed(){
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




