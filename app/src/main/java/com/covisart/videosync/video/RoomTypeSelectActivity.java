package com.covisart.videosync.video;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.covisart.videosync.*;
import com.covisart.videosync.database.DbManager;
import com.covisart.videosync.database.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RoomTypeSelectActivity extends AppCompatActivity {

    String TAG = "CovisartTest";
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_type_select);

        userId = getIntent().getStringExtra("USER_ID");

        EnterRoom(null);
    }

    public void createRoom(String roomName) {
        DbManager.GenerateRoom(userId,roomName,null);
    }
    public void CreateRoom(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Odanıza bir ad giriniz.");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createRoom(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    public void EnterRoom(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Oda adını giriniz");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbManager.JoinRoom(input.getText().toString(), null, new DbManager.DatabaseResultListener() {
                    @Override
                    public void DatabaseResult(Object value) {
                        Room val = (Room)value;

                        Log.d(TAG, "DatabaseResult: " + val.RoomName);
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}