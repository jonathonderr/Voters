package com.vindicators.voters;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        CreateGroupAdapter friendsAdapter = new CreateGroupAdapter(getUsers(),this);
        recyclerView.setAdapter(friendsAdapter);
    }

    private ArrayList<User> getUsers() {
        ArrayList<User> testList = new ArrayList<User>();
        testList.add(new User("dfdf334", "Bo343b", "1", null));
        testList.add(new User("df234df", "Bdf3fob", "2", null));
        testList.add(new User("dfdfffff", "Bodb", "3", null));
        testList.add(new User("dfdfffdgfdfd", "Bovb", "4", null));
        testList.add(new User("dfdffewfw", "Bonb", "5", null));
        testList.add(new User("dfdf", "Bob", "6", null));
        testList.add(new User("dfdf", "Bob", "7", null));
        testList.add(new User("dfdf", "Bob", "8", null));
        testList.add(new User("dfdf", "Bob", "9", null));
        testList.add(new User("dfdf", "Bob", "0", null));
        testList.add(new User("dfdf", "Bob", "777", null));
        testList.add(new User("dfdf", "Bob", "56", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));
        testList.add(new User("dfdf", "Bob", "1", null));

        return testList;
    }
}

