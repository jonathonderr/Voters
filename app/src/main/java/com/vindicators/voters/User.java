package com.vindicators.voters;

import java.util.ArrayList;

public class User {

    String email;
    String username;
    String uid;
    ArrayList<String> votes;
    Boolean selected = false;

    public User(String email, String username, String uid, ArrayList<String> votes){
        this.email = email;
        this.username = username;
        this.uid = uid;
        this.votes = votes;
    }
    @Override
    public String toString(){
        return (uid + ":" + votes);
    }

}
