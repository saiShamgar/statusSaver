package com.pg.whatsstatussaver.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pg.whatsstatussaver.R;

import java.util.ArrayList;

public class AdapterForThumbNails extends RecyclerView.Adapter<AdapterForThumbNails.ViewHolder> {

    private Context context;
    private ArrayList<Bitmap> images;


    public AdapterForThumbNails(Context context, ArrayList<Bitmap> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.status_videos_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.status_image.setImageBitmap(images.get(i));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView status_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status_image=itemView.findViewById(R.id.status_image);
        }
    }
}
