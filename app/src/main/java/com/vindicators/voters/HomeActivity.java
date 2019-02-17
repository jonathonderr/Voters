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
    public static final String EXTRA_MESSAGE = "com.vindicators.voters.MESSAGE";

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
        Intent intent = new Intent(HomeActivity.this, CreateGroup.class);
        Button voteButton = (Button) findViewById(R.id.LetsVote);
        String message = voteButton.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void addFriendsButtonPressed() {
        Intent intent = new Intent(HomeActivity.this, FriendsList.class);
        Button addFriendsButton = (Button) findViewById(R.id.AddFriends);
        String message = addFriendsButton.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}

