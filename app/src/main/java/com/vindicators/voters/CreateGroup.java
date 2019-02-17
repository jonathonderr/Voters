package com.vindicators.voters;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

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

        recyclerView = (RecyclerView) findViewById(R.id.restaurant_list);

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


        Button continueButton = findViewById(R.id.goToVotesButton);
        continueButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueToRestaurants();
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

    public void continueToRestaurants(){
        Intent intent = new Intent(CreateGroup.this, SelectRestaurant.class);
        startActivity(intent);
    }

}

