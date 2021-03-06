package com.vindicators.voters;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

/**
 * Created by delacez on 2/16/19.
 */

public class SelectRestaurant extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    Button restaurantButton;
    public ArrayList<RestaurantFirebase> restaurants = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_restaurant);
        final RestaurantAdapter restaurantAdapter = new RestaurantAdapter(restaurants,SelectRestaurant.this);


        recyclerView = findViewById(R.id.restaurant_list);

        //recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration= new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(restaurantAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search Restaurants");
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
                        restaurants = (ArrayList<RestaurantFirebase>) value;
                        restaurantAdapter.updateData(restaurants);
                    }
                });

                return false;
            }
        });


        getUsers("", new Callback() {
            @Override
            public void onCallback(Object value) {
                restaurants = (ArrayList<RestaurantFirebase>) value;
                restaurantAdapter.updateData(restaurants);
            }
        });

        //RESTAURANT BUTTON
        restaurantButton = (Button) findViewById(R.id.goToVotesButton);
        restaurantButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantButtonPressed(restaurantAdapter);
            }
        });

    }


    private void getUsers(String query, Callback cb) {

        final Callback callback = cb;

        FirebaseServices fHelper = new FirebaseServices();
        fHelper.searchUsers( query, new Callback() {
            @Override
            public void onCallback(Object users) {
                ArrayList<RestaurantFirebase> restaurants = new ArrayList<>();
                restaurants.add(new RestaurantFirebase("jimmyjohns_98335","Jimmy Johns",0));
                restaurants.add(new RestaurantFirebase("dairyqueen_98335","Dairy Queen",0));
                restaurants.add(new RestaurantFirebase("applebees_98335","Applebees",0));
                restaurants.add(new RestaurantFirebase("ll_98335","Lunchbox Laboratory",0));
                restaurants.add(new RestaurantFirebase("whdh_98335","WWU Hackathon's Dining Hall",0));

                callback.onCallback(restaurants);
            }
        });

    }

    public void restaurantButtonPressed(final RestaurantAdapter adapter){
        FirebaseServices fHelper = new FirebaseServices();
        fHelper.USERS_REF.child(fHelper.mAuth.getCurrentUser().getUid()).child("current").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String vid = (String) dataSnapshot.getValue();
                addRestaurantsToVote(vid , adapter.selectedRestaurants);
                Intent intent = new Intent(SelectRestaurant.this, VotingPage.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DATABASE ERROR", databaseError.getMessage());
            }
        });

    }

    public static void addRestaurantsToVote(String vid, ArrayList<RestaurantFirebase> restMutate){
        final FirebaseServices fHelper = new FirebaseServices();
        final  ArrayList<RestaurantFirebase> restaraunts = restMutate;
        final String v = vid;
        if(restMutate.isEmpty()){
            return;
        }

        fHelper.addVotesRestaurantNoVote(vid,restMutate.get(restMutate.size() - 1).id, restMutate.get(restMutate.size() - 1).name, new Callback() {
            @Override
            public void onCallback(Object value) {
                restaraunts.remove(restaraunts.size() - 1);
                addRestaurantsToVote(v, restaraunts);
            }
        });


    }



}

