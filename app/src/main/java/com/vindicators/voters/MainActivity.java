package com.vindicators.voters;

import android.content.*;
import android.location.*;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseServices fHelper;
    Button loginButton;
    Button forgotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add locationmanager initialization if we plan on updating location

        FirebaseApp.initializeApp(this);

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
    @Override
    protected void onStart() {
        super.onStart();
        LocationManager mylocation = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //final boolean gpsEnabled = mylocation.isProviderEnabled(LocationManager.GPS_PROVIDER);
        final boolean gpsEnabled = false;

        if (!gpsEnabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("TURN ON LOCATION SERVICES");
                    dialog.setMessage("Please turn on location services before using this app");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            enableLocationSettings();
                            dialog.dismiss(); //MyActivity.dismiss();
                        }
                    });

            // Build an alert dialog here that requests that the user enable
            // the location services, then when the user clicks the "OK" button,
            // call enableLocationSettings()
        }

    }

    private void enableLocationSettings() {
        Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(settingsIntent);
    }

    public void forgotButtonPressed(){
        forgotButton.setText("change this text");

    }

    public void loginButtonPressed(){
        loginButton.setText("change my text");

    }


}
