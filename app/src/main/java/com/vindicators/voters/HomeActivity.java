package com.vindicators.voters;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    Button button2;
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

        FirebaseServices fHelper = new FirebaseServices();
        fHelper.USERS_REF.child(fHelper.mAuth.getCurrentUser().getUid()).child("current").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String vid = (String) dataSnapshot.getValue();
                if(!vid.isEmpty()){
                    Intent intent = new Intent(HomeActivity.this, FinalResults.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

