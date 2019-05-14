package com.pg.whatsstatussaver.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pg.whatsstatussaver.Play_video;
import com.pg.whatsstatussaver.R;
import com.pg.whatsstatussaver.ViewPhotos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.logging.FileHandler;


public class Gallery_Photos_Adapter extends RecyclerView.Adapter<Gallery_Photos_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<String> images;
    private int status;
    private ArrayList<Bitmap> bitmaps;
    private ArrayList<String> path;
    private Bitmap bitmap;

    public Gallery_Photos_Adapter(Context context, ArrayList<String> images, int status) {
        this.context = context;
        this.images = images;
        this.status = status;
    }

    public Gallery_Photos_Adapter(Context context, ArrayList<Bitmap> images,ArrayList<String> video_urls) {
        this.context = context;
        this.bitmaps = images;
        this.path=video_urls;
    }

    public void Add(Bitmap bitmap){
        this.bitmap=bitmap;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_custom_layout,parent,false);

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
                    .into(holder.gallery_image);

            holder.gallery_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view_photos=new Intent(context, ViewPhotos.class);
                    view_photos.putStringArrayListExtra("image",images);
                    view_photos.putExtra("position",i);
                    context.startActivity(view_photos);
                }
            });

        }else {
            holder.gallery_video_icon.setVisibility(View.VISIBLE);
            holder.gallery_image.setImageBitmap(bitmaps.get(i));
            holder.gallery_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view_photos=new Intent(context, Play_video.class);
                    view_photos.putStringArrayListExtra("image",path);
                    view_photos.putExtra("position",i);
                    context.startActivity(view_photos);
                }
            });
            //setHasStableIds(true);
//            //checking image is favourite or not
//            String descPath = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/Favourite";
//            File src = new File(path.get(i));
//            File file = new File(descPath+"/"+src.getName());
//            if(file.exists()){
//                holder.gallery_image_favourite_icon.setVisibility(View.VISIBLE);
//            }
//            else{
//                holder.gallery_image_favourite_icon.setVisibility(View.GONE);
//            }


//            holder.gallery_image.setOnTouchListener(new View.OnTouchListener() {
//
//                private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                    @Override
//                    public boolean onDoubleTap(MotionEvent e) {
//                        String descPath = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/Favourite";
//                        copyFileOrDirectory(path.get(i),descPath);
//                        holder.gallery_image_favourite_icon.setVisibility(View.VISIBLE);
//                        return super.onDoubleTap(e);
//                    }// implement here other callback methods like onFling, onScroll as necessary
//                });
//
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    gestureDetector.onTouchEvent(event);
//                    return true;
//
//                }
//            });


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
        private ImageView gallery_image,gallery_video_icon,gallery_image_favourite_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gallery_image=itemView.findViewById(R.id.gallery_image);
            gallery_video_icon=itemView.findViewById(R.id.gallery_video_icon);
            gallery_image_favourite_icon=itemView.findViewById(R.id.gallery_image_favourite_icon);
        }
    }

    public  void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

//            if (src.isDirectory()) {
//
//                String files[] = src.list();
//                int filesLength = files.length;
//                for (int i = 0; i < filesLength; i++) {
//                    String src1 = (new File(src, files[i]).getPath());
//                    String dst1 = dst.getPath();
//                    copyFileOrDirectory(src1, dst1);
//
//                }
//            } else {
//                copyFile(src, dst);
//            }
            copyFile(src, dst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }
}
