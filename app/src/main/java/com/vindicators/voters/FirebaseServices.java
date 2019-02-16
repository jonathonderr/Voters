package com.vindicators.voters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

}
