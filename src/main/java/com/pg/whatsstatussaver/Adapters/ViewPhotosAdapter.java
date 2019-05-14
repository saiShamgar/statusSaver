package com.pg.whatsstatussaver.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pg.whatsstatussaver.R;

import java.util.ArrayList;

public class ViewPhotosAdapter extends RecyclerView.Adapter<ViewPhotosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> images;
    private int position;

    public ViewPhotosAdapter(Context context, ArrayList<String> images, int position) {
        this.context = context;
        this.images = images;
        this.position = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_photo,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(context)
                .load(images.get(i))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_image_black_24dp)
                // read original from cache (if present) otherwise download it and decode it
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.viewPhotos_img);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView viewPhotos_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPhotos_img=itemView.findViewById(R.id.viewPhotos_img);
        }
    }
}
