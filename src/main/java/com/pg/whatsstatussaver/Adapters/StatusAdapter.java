package com.pg.whatsstatussaver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.pg.whatsstatussaver.ViewPhotos;

import java.util.ArrayList;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> images;

    public StatusAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images=images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.status_custom_layout,parent,false);

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
                .into(holder.status_image);

        holder.status_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view_photos=new Intent(context, ViewPhotos.class);
                view_photos.putStringArrayListExtra("image",images);
                view_photos.putExtra("position",i);
                context.startActivity(view_photos);
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView status_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status_image=itemView.findViewById(R.id.status_image);
        }
    }
}
