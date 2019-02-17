package com.vindicators.voters;
import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import retrofit2.Call;

//import static org.junit.Assert.assertNotNull;


//Used for an array of all restaurants
class Restaurant{
    String name;
    String price;
    ArrayList<String> address;
    double rating;
    double distance;
    String pictureURL;
    String isClosed;
    boolean flag;
};

public class APIHelperFunctions {
    YelpFusionApiFactory yelpFusionApiFactory;
    YelpFusionApi yelpFusionApi;

    public static ArrayList<Restaurant> restaurantArrayList = new ArrayList<Restaurant>();
    static Business business[];

    public APIHelperFunctions() throws IOException {
        yelpFusionApiFactory = new YelpFusionApiFactory();
        yelpFusionApi = yelpFusionApiFactory.createAPI(Constants.YELP_TOKEN);
    }
    //Adds the closest 30 restaurants in the given area
    public static void getRestaurantsInArea(){
        int arrayCounter = 0;//array counter

        //keeps adding to restaurant arraylist until the number of restaurants is 40 or all the restaurants
        //in the area are added


        Restaurant Temp = new Restaurant();

        while(arrayCounter < 30){
            Temp.name = business[arrayCounter].getName();
            Temp.address = business[arrayCounter].getLocation().getDisplayAddress();
            Temp.distance = business[arrayCounter].getDistance();
            Temp.price = business[arrayCounter].getPrice();
            Temp.rating = business[arrayCounter].getRating();
            Temp.pictureURL = business[arrayCounter].getImageUrl();
            Temp.isClosed = checkClosed(business[arrayCounter].getIsClosed());
            Temp.flag = false;
            restaurantArrayList.add(Temp);
        }
    }

/*
    public static double getDistance(double lon1, double lat1, double lon2, double lat2){
        double a = java.lang.Math.abs(lon2 - lon1);
        double b = java.lang.Math.abs(lat2 - lat1);
        return java.lang.Math.sqrt((a*a)+(b*b));
    }*/

    public void restaurantScan() throws IOException {
        HashMap<String, String> parms = new HashMap<>();
        parms.put("term", "restaurants");
        parms.put("latitude", "48.769768");
        parms.put("longitude","-122.485886" );
        parms.put("radius", "20");
        parms.put("total", "30");
        Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(parms);
        SearchResponse response = call.execute().body();
        //assertNotNull(response);
        getRestaurantsInArea();
    }

    public static String checkClosed(boolean i){
        if(i == true){
            return "Closed";
        } else{
            return "Open";
        }
    }
    //Function that retrieves the rating


    //Function that retrieves the $ amount
    //function that retrieves the address



}
