package com.pg.whatsstatussaver;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pg.whatsstatussaver.Adapters.MySplashScreenAdapter;
import com.pg.whatsstatussaver.Adapters.ViewPhotosAdapter;

import java.util.ArrayList;

public class ViewPhotos extends AppCompatActivity {

    int position;
    private ArrayList<String> image=new ArrayList<>();
   private ViewPager viewPhotos_rec;
   private ViewPhotosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photos);

     //   viewPhotos_img=findViewById(R.id.viewPhotos_img);
        image=getIntent().getExtras().getStringArrayList("image");
        position=getIntent().getExtras().getInt("position");

        viewPhotos_rec=findViewById(R.id.viewPhotos_img);
        viewPhotos_rec.setAdapter(new MySplashScreenAdapter(this,image,position));
        viewPhotos_rec.setCurrentItem(position);
    }
}
