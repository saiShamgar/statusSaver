package com.pg.whatsstatussaver;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

public class Play_video extends AppCompatActivity {

    int position;
    private ArrayList<String> image=new ArrayList<>();
    private VideoView play_video_vv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        image=getIntent().getExtras().getStringArrayList("image");
        position=getIntent().getExtras().getInt("position");

        play_video_vv=findViewById(R.id.play_video_vv);
        Uri uri=Uri.parse(image.get(position));
        play_video_vv.setVideoURI(uri);

        MediaController controller=new MediaController(this);
        play_video_vv.setMediaController(controller);
        controller.setAnchorView(play_video_vv);
        play_video_vv.start();
        play_video_vv.seekTo(100);

    }
}
