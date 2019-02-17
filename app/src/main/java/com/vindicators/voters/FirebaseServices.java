package com.vindicators.voters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseServices {

    public DatabaseReference DATABASE_REF;
    public DatabaseReference USERS_REF;
    public DatabaseReference VOTES_REF;
    public DatabaseReference TEST_REF;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;


    public FirebaseServices(){
        DATABASE_REF = FirebaseDatabase.getInstance().getReference();
        USERS_REF = DATABASE_REF.child("users");
        VOTES_REF = DATABASE_REF.child("votes");
        TEST_REF = DATABASE_REF.child("test");
        mAuth = FirebaseAuth.getInstance();
        TEST_REF.child("status").setValue("Current: ONLINE");
    }

    //USER

    public void createUser(final String email, String password, String username){

        final String userName = username;
        final String eMail = email;
        final String passWord = password;

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    currentUser = user;
                    createUserDatabase(user.getUid(), user.getEmail(), userName);
                    signIn(eMail, passWord, new Callback() {
                        @Override
                        public void onCallback(Object value) {
                            //Do nothing
                        }
                    });
                }else{
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Log.d("UserCreation", e.getMessage());
                }
            }
        });
    }

    public void signIn(String email, String password, final Callback callback){

        if(email == null || password == null || email.isEmpty() || password.isEmpty()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    callback.onCallback(user);
                }else{
                    if(task.getException().getLocalizedMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted.")){
                        callback.onCallback(null);
                    }
                }
            }
        });
    }


    //DATABASE
    public void createUserDatabase(String uid, String email, String username) {
        DatabaseReference userRef = USERS_REF.child(uid);
        userRef.child("username").setValue(username);
        userRef.child("email").setValue(email);
    }

    public void getFriendsCount(){

    }

    public void getUser(String uid, final Callback callback){
        final String uiD = uid;
        DatabaseReference userRef = USERS_REF.child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String username = "";
                String email = "";
                String uid = uiD;
                ArrayList<String> votes = new ArrayList<>();


                for(DataSnapshot attribRaw: dataSnapshot.getChildren()){
                    switch(attribRaw.getKey()){
                        case "username":
                            username = (String) attribRaw.getValue();
                            break;
                        case "email":
                            email = (String) attribRaw.getValue();
                            break;
                        case "votes":
                            for(DataSnapshot vote:attribRaw.getChildren()){
                                votes.add((String) vote.getValue());
                            }

                    }
                }

                User user = new User(email, username, uid, votes);
                callback.onCallback(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DATABASE ERROR", databaseError.getMessage());
            }
        });
    }

    //DATA-VOTES

    public void createVote(String uid){
        final String uiD = uid;
        getVotesCount(new Callback() {
            @Override
            public void onCallback(Object voteCount) {
                DatabaseReference voteRef = VOTES_REF.child("v" + voteCount + uiD);
                voteRef.child("top_result").setValue("Not enough people have voted yet");
                voteRef.child("host").setValue(uiD);
                addVotesFriend("v" + voteCount + uiD, uiD);

                //For loop with restaurants --
                addVotesRestaurant("v" + voteCount + uiD, "Wendys", uiD);
            }
        });

    }

    public void addVotesFriend(String vid, String uid){
        final DatabaseReference voteRef = VOTES_REF.child(vid);
        final String uiD = uid;
        final String viD = vid;
        getVotesFriendsCount(vid, new Callback() {
            @Override
            public void onCallback(Object count){
                voteRef.child("friends").child("f" + count).setValue(uiD);
                getPathCount(USERS_REF.child(uiD).child("votes"), new Callback() {
                    @Override
                    public void onCallback(Object value) {
                        USERS_REF.child(uiD).child("votes").child("v" + value).setValue(viD);
                    }
                });
            }
        });
    }

    public void addVotesRestaurant(String vid, String rid, String uid){
        final DatabaseReference voteRef = VOTES_REF.child(vid);
        final String uiD = uid;
        final String riD = rid;
        final String viD = vid;

        getVotesRestaurantsCount(vid, new Callback() {
            @Override
            public void onCallback(Object count){
                voteRef.child("restaurants").child("r" + count).child("restaurant").setValue(riD);

                final Object counT = count;

                getVotesRestaurantsVotes(viD, "r" + count, new Callback(){
                    @Override
                    public void onCallback(Object votes){
                        voteRef.child("restaurants").child("r" + counT).child("votes").setValue((int)votes + 1);
                        //Log.d("RESTAURANT", votes.toString());

                        getPathCount(voteRef.child("restaurants").child("r" + counT).child("voters"), new Callback() {
                            @Override
                            public void onCallback(Object fCount) {
                                voteRef.child("restaurants").child("r" + counT).child("voters").child("f" + fCount).setValue(uiD);

                            }
                        });


                    }
                });

            }
        });
    }

    public void getVotesCount(final Callback callback){
        VOTES_REF.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count  = dataSnapshot.getChildrenCount();
                callback.onCallback(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void getVotesFriendsCount(String vID, final Callback callback){
        VOTES_REF.child(vID).child("friends").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count  = dataSnapshot.getChildrenCount();
                callback.onCallback(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void getVotesRestaurantsCount(String vID, final Callback callback){
        VOTES_REF.child(vID).child("restaurants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count  = dataSnapshot.getChildrenCount();
                callback.onCallback(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void getPathCount(DatabaseReference ref, final Callback callback){
      ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long count  = dataSnapshot.getChildrenCount();
                callback.onCallback(count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void getVotesRestaurantsVotes(String vID, String rid, final Callback callback){
        VOTES_REF.child(vID).child("restaurants").child(rid).child("votes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int votes  = (int) (dataSnapshot.getValue() == null ? 0 : dataSnapshot.getValue());
                callback.onCallback(votes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }

    public void getVotesRestaurants(String vid, final Callback callback){
        DatabaseReference voteRef = VOTES_REF.child(vid);
        final ArrayList<RestaurantFirebase> restaurants = new ArrayList<>();
        voteRef.child("restaurants").orderByChild("votes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot childRaw:dataSnapshot.getChildren()) {
                    String id = "";
                    String name = "";
                    int votes = 0;

                    for (DataSnapshot attribRaw : childRaw.getChildren()) {
                        if (attribRaw.getKey().equals("restaurant")) {
                            id = (String) attribRaw.getValue();
                            name = (String) attribRaw.getValue();
                        }
                        if (attribRaw.getKey().equals("votes")) {
                            Log.d("RESTAURANTS", attribRaw.getValue().toString());
                            votes = Integer.parseInt(attribRaw.getValue().toString());
                        }
                    }
                    RestaurantFirebase restaurant = new RestaurantFirebase(id, name, votes);
                    restaurants.add(0,restaurant);
                }
                callback.onCallback(restaurants);
                restaurants.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DATABASE ERROR", databaseError.getMessage());
            }
        });
    }

    public void getVotesFriends(String vid, final Callback callback){
        final DatabaseReference voteRef = VOTES_REF.child(vid);
        final ArrayList<User> friends = new ArrayList<>();
        voteRef.child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                 int count = 0;
                for(DataSnapshot friendRaw: dataSnapshot.getChildren()){
                    count++;

                    final int countFinal = count;
                    final String uid = (String) friendRaw.getValue();
                    getUser(uid, new Callback() {
                        @Override
                        public void onCallback(Object user) {
                            friends.add((User) user);
                            if(countFinal >= dataSnapshot.getChildrenCount()){
                                callback.onCallback(friends);
                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DATABASE ERROR", databaseError.getMessage());
            }
        });
    }

    public void getVotesTopRestaurant(String vid, Callback callback){

    }
}


