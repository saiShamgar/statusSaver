package com.pg.whatsstatussaver;

import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ViewPhotos extends AppCompatActivity {

    String image;
    private ImageView viewPhotos_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);

        viewPhotos_img=findViewById(R.id.viewPhotos_img);
        image=getIntent().getExtras().getString("image");

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(this)
                .load(image)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_image_black_24dp)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(viewPhotos_img);




    }
}
