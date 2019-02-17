package com.vindicators.voters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    Button voteButton;
    Button addFriendsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        voteButton = (Button) findViewById(R.id.LetsVote);
        voteButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                voteButtonPressed();
            }
        });

        addFriendsButton = (Button) findViewById(R.id.AddFriends);
        addFriendsButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriendsButtonPressed();
            }
        });
    }

    public void voteButtonPressed() {
        voteButton.setText("change this text");

    }

    public void addFriendsButtonPressed() {
        addFriendsButton.setText("change this text too");

    }
}

