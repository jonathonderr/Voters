package com.vindicators.voters;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.SearchView;

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

//        //RESTAURANT BUTTON
//        restaurantButton = (Button) findViewById(R.id.goToVotesButton);
//
//        restaurantButton.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                restaurantButtonPressed();
//            }
//        });

    }

//    @Override
//    public void onFinishInflate(){
//
//    }

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

    public void restaurantButtonPressed(){


    }


}

