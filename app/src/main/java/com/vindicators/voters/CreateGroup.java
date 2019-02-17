package com.vindicators.voters;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import java.util.*;

/**
 * Created by delacez on 2/16/19.
 */

public class CreateGroup extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

        getUsers(new Callback() {
            @Override
            public void onCallback(Object value) {
                ArrayList<User> users =(ArrayList<User>) value;
                CreateGroupAdapter friendsAdapter = new CreateGroupAdapter(users,CreateGroup.this);
                recyclerView.setAdapter(friendsAdapter);
            }
        });

    }

    private void getUsers(Callback cb) {

        final Callback callback = cb;

        FirebaseServices fHelper = new FirebaseServices();
        fHelper.searchUsers("", new Callback() {
            @Override
            public void onCallback(Object users) {
//                User newUser = new User("Bobsyourungle", "Dontnod", "1", null);
//                users.add(newUser);
//                users.add(newUser);
//                users.add(newUser);
//                users.add(newUser);
//                users.add(newUser);
//                users.add(newUser);
//                users.add(newUser);
//                users.add(newUser);

                callback.onCallback(users);
            }
        });

    }
}
