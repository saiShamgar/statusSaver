package com.pg.whatsstatussaver.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.CircularProgressDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pg.whatsstatussaver.R;

import java.util.ArrayList;

public class MySplashScreenAdapter extends PagerAdapter {

    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;

    public MySplashScreenAdapter(Context context, ArrayList<String> images, int position) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.custom_view_photo, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.cusviewPhotos_img);

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(context)
                .load(images.get(position))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_image_black_24dp)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(myImage);

        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
