package com.vindicators.voters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.*;

/**
 * Created by delacez on 2/16/19.
 */

public class ChooseFriendsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //maybe add item decoration

        ChooseFriendsActivityAdapter friendsAdapter = new ChooseFriendsActivityAdapter(getUsers(),this);
        recyclerView.setAdapter(friendsAdapter);
    }

    private ArrayList<User> getUsers() {
        ArrayList<User> testList = new ArrayList<User>();
        testList.add(new User("dfdf", "Bob", "1", null));

        return testList;
    }
}
