package com.vindicators.voters;

public class RestaurantFirebase {

    String id;
    String name;
    int votes;
    Boolean selected = false;


    public RestaurantFirebase(String id, String name, int votes){
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    @Override
    public String toString(){
        return (id + ":" + votes);
    }

}
