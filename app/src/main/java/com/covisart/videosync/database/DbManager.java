package com.covisart.videosync.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DbManager {

    private static final String USERS = "Users";
    private static final String ROOMS = "Rooms";
    static String TAG ="CovisartTest";
    static public void GenerateRoom(String userId, String RoomName, String Password) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference dbRef = db.getReference(ROOMS);

        RoomRef roomRef = new RoomRef();
        roomRef.Password = Password;
        roomRef.UserRef = userId;

        dbRef   .child(RoomName)
                .setValue(roomRef);

        Room room = new Room();
        room.RoomName = RoomName;

        db.getReference(USERS).child(userId).child("Room").setValue(room);
    }
    public interface DatabaseResultListener {
        void DatabaseResult(Object value);
    }

    static public void JoinRoom(final String roomName, String Password, final DatabaseResultListener databaseResultListener) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        DatabaseReference dbRef = db.getReference(ROOMS).child(roomName);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RoomRef roomRef  = dataSnapshot.getValue(RoomRef.class);

                String userRef = roomRef.UserRef;
                Log.d(TAG, "onDataChange(userref): " + userRef);
                    final DatabaseReference asef = FirebaseDatabase.getInstance().getReference(USERS).child(userRef).child("Room");
                asef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Room room = dataSnapshot.getValue(Room.class);

                        Log.d(TAG, "onDataChange: " + room.RoomName);

                        databaseResultListener.DatabaseResult(room);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.d(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        dbRef.addListenerForSingleValueEvent(postListener);


    }

    static public void UpdateUser(String userId, String username) {
        DatabaseReference Users = FirebaseDatabase.getInstance().getReference(USERS);

        User user = new User();
        user.UserName = username;

        Users.child(userId)
                .setValue(user);
    }


}
