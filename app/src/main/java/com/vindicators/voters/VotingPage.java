package com.vindicators.voters;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by delacez on 2/16/19.
 */

public class VotingPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<RestaurantFirebase> restaurants = new ArrayList<>();

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

        restaurants.add(new RestaurantFirebase("id", "name", 2));
        final VotingPageAdapter votingAdapter = new VotingPageAdapter(restaurants,VotingPage.this);
        recyclerView.setAdapter(votingAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search Restaurants");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getRestaurants(newText, new Callback() {
                    @Override
                    public void onCallback(Object value) {
                        restaurants = (ArrayList<RestaurantFirebase>) value;
                        votingAdapter.updateData(restaurants);
                    }
                });

                return false;
            }
        });


        getRestaurants("", new Callback() {
            @Override
            public void onCallback(Object value) {
                restaurants = new ArrayList<>();

                for(int i = 0; i < ((ArrayList<RestaurantFirebase>)value).size(); i ++){
                    restaurants.add(((ArrayList<RestaurantFirebase>)value).get(i));
                }
                votingAdapter.updateData(restaurants);
            }
        });


    }

    private void getRestaurants(String query, Callback cb) {

        final Callback callback = cb;

        final FirebaseServices fHelper = new FirebaseServices();
        fHelper.USERS_REF.child(fHelper.mAuth.getCurrentUser().getUid()).child("current").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               final String vid = (String) dataSnapshot.getValue();
               fHelper.getVotesRestaurants(vid, new Callback() {
                   @Override
                   public void onCallback(Object value) {
                       callback.onCallback(value);
                   }
               });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE ERROR", databaseError.getMessage());
            }
        });


        }

    public void setUsers(ArrayList<User> users){
        this.restaurants = restaurants;
    }
}

