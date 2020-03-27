package com.covisart.videosync;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    private VideoView player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        player = findViewById(R.id.videoView);

        final MediaController controller = new MediaController(this);
        controller.setAnchorView(player);
        controller.setMediaPlayer(player);
        player.setMediaController(controller);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        if (MainActivity.DEBUG){
            Uri defaultUri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.a1);
            player.setVideoURI(defaultUri);
            Play(null);
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setVolume(0f,0f);
                }
            });
        }

    }

    public void Play(View v){
        player.start();
    }

    public void OpenVideo(View v){
        OpenVideoWithDialog();
    }

    public void Pause(View v){
        player.pause();
    }

    public void pass10seconds(View v){
        player.seekTo( player.getCurrentPosition() + 10000);
    }


    public void OpenVideoWithDialog(){
        Intent intent = new Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select a file"), 123);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
            player.setVideoURI(selectedfile);
            Log.d("covisarttest", "onActivityResult: " + selectedfile);
        }
    }
}