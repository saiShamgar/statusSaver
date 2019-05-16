package com.pg.whatsstatussaver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pg.whatsstatussaver.InterFace.OnItemclickListener;
import com.pg.whatsstatussaver.R;
import com.pg.whatsstatussaver.ViewPhotos;

import java.io.File;
import java.util.ArrayList;

public class Favaurite_Adapter extends RecyclerView.Adapter<Favaurite_Adapter.ViewHolder> {
    private Context context;
    private ArrayList<String> images;
    private int status;
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<String> path;
    private Bitmap bitmap;
    private View view;

    private OnItemclickListener onItemclickListener;



    public Favaurite_Adapter(Context context, ArrayList<String> images, int status,OnItemclickListener onItemclickListener) {
        this.context = context;
        this.images = images;
        this.status = status;
        this.onItemclickListener=onItemclickListener;
    }

    public Favaurite_Adapter(Context context, ArrayList<Bitmap> images,ArrayList<String> video_urls) {
        this.context = context;
        this.bitmaps = images;
        this.path=video_urls;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fav_custom_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if (status==1){
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
                    .into(holder.fav_image);

            holder.fav_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view_photos=new Intent(context, ViewPhotos.class);
                    view_photos.putStringArrayListExtra("image",images);
                    view_photos.putExtra("position",i);
                    context.startActivity(view_photos);
                }
            });

            holder.fav_photo_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String descPath = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/Favourite";
                    File src = new File(images.get(i));
                    File file = new File(descPath+"/"+src.getName());
                    boolean deleted = file.delete();
                    if (deleted){
                        onItemclickListener.OnItemClick(i);
                    }


                }
            });

        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (status==1){
            return images.size();
        }
        else return bitmaps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView fav_image,id_play_icon_fav,fav_photo_icon,fav_photo_share;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_image=itemView.findViewById(R.id.fav_image);
            id_play_icon_fav=itemView.findViewById(R.id.id_play_icon_fav);
            fav_photo_icon=itemView.findViewById(R.id.fav_photo_icon);
            fav_photo_share=itemView.findViewById(R.id.fav_photo_share);
        }
    }
}
