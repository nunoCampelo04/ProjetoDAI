package com.example.myapplication;
import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FirebaseService {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mUsersRef;

    public FirebaseService() {
        mDatabase = FirebaseDatabase.getInstance();
        mUsersRef = mDatabase.getReference("users");
        System.out.println(mDatabase);
    }

    public void saveUser(User user) {
        mUsersRef.child(user.getEmail()).setValue(user);
    }
    public interface LoginCallback {
        void onLogin(User user);
    }
    public void loginUser(String email, String password, final LoginCallback callback) {
        mUsersRef.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.onLogin(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Tratar erro
            }
        });


    }
}
