package com.vindicators.voters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    Button VoteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        VoteButton = (Button) findViewById(R.id.LetsVote);
        VoteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoteButtonPressed();
            }
        });

    }

    public void VoteButtonPressed() {
        VoteButton.setText("change this text");

    }
}

