package com.vindicators.voters;

import android.support.annotation.NonNull;
import android.telecom.Call;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public void createUser(String email, String password, String username){

        final String userName = username;

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    currentUser = user;
                    createUserDatabase(user.getUid(), user.getEmail(), userName);
                    Log.d("UserCreation", "User: " + user + " has been created");
                }else{
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Log.d("UserCreation", e.getMessage());
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

    //DATA-VOTES

    public void createVote(String uid){
        final String uiD = uid;
        getVotesCount(new Callback() {
            @Override
            public void onCallback(Object voteCount) {
                DatabaseReference voteRef = VOTES_REF.child("v" + voteCount + uiD);
                voteRef.child("top_result").setValue("Jimmy Johns");
                voteRef.child("host").setValue(uiD);
            }
        });

    }

    public void addVotesFriend(String vid, String uid){
        final DatabaseReference voteRef = VOTES_REF.child(vid);
        final String uiD = uid;
        getVotesFriendsCount(vid, new Callback() {
            @Override
            public void onCallback(Object count){
                voteRef.child("friends").child("f" + count).setValue(uiD);
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
                int votes  = (int) dataSnapshot.getValue();
                callback.onCallback(votes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCallback(null);
            }
        });
    }


}


