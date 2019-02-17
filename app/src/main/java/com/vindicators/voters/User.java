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
        return (username + ":" + selected);
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof User){
            User user = (User) o;
            if(user.username.equals(this.username)){
                return true;
            }

        }

        return false;
    }
}
