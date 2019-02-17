package com.vindicators.voters;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.sql.Array;
import java.util.*;

/**
 * Created by delacez on 2/16/19.
 */

public class CreateGroup extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        recyclerView = (RecyclerView) findViewById(R.id.my_friends_list);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        final CreateGroupAdapter friendsAdapter = new CreateGroupAdapter(users,CreateGroup.this);
        recyclerView.setAdapter(friendsAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search Friends");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getUsers(newText, new Callback() {
                    @Override
                    public void onCallback(Object value) {
                        users = (ArrayList<User>) value;
                        friendsAdapter.updateData(users);
                    }
                });

                return false;
            }
        });


        getUsers("", new Callback() {
            @Override
            public void onCallback(Object value) {
                users = (ArrayList<User>) value;
                friendsAdapter.updateData(users);
            }
        });


        Button continueButton = findViewById(R.id.ChooseRest);
        continueButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueToRestaurants(friendsAdapter);
            }
        });


    }

    private void getUsers(String query, Callback cb) {

        final Callback callback = cb;

        FirebaseServices fHelper = new FirebaseServices();
        fHelper.searchUsers( query, new Callback() {
            @Override
            public void onCallback(Object users) {
                callback.onCallback(users);
            }
        });

    }

    public void setUsers(ArrayList<User> users){
        this.users = users;
    }

    public void continueToRestaurants(CreateGroupAdapter adapter){
        final FirebaseServices fHelper = new FirebaseServices();
        final CreateGroupAdapter ADAPTER = adapter;
        fHelper.createVote(fHelper.mAuth.getCurrentUser().getUid(), new Callback() {
            @Override
            public void onCallback(Object v) {
                String vid = (String) v;
               addFriendsToVote(vid, ADAPTER.selectedUsers);
                fHelper.USERS_REF.child(fHelper.mAuth.getCurrentUser().getUid()).child("current").setValue(vid);
                Intent intent = new Intent(CreateGroup.this, SelectRestaurant.class);
                startActivity(intent);
            }
        });


    }

    public static void addFriendsToVote(String vid, ArrayList<User> usersToMutate){
        final FirebaseServices fHelper = new FirebaseServices();
        final  ArrayList<User> users = usersToMutate;
        final String v = vid;
        if(usersToMutate.isEmpty()){
            return;
        }

        fHelper.addVotesFriend(vid,usersToMutate.get(usersToMutate.size() - 1).uid, new Callback() {
            @Override
            public void onCallback(Object value) {
                fHelper.USERS_REF.child(users.get(users.size() - 1).uid).child("current").setValue(v);
                users.remove(users.size() - 1);
                addFriendsToVote(v, users);
            }
        });

    }

}

