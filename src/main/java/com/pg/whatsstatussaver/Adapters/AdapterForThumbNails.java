package com.pg.whatsstatussaver.Adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.pg.whatsstatussaver.Play_video;
import com.pg.whatsstatussaver.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class AdapterForThumbNails extends RecyclerView.Adapter<AdapterForThumbNails.ViewHolder> {

    private Context context;
    private ArrayList<Bitmap> images;
    private ArrayList<String> path;


    public AdapterForThumbNails(Context context, ArrayList<Bitmap> images,ArrayList<String> urls) {
        this.context = context;
        this.images = images;
        this.path=urls;
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
        holder.status_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view_photos=new Intent(context, Play_video.class);
                view_photos.putStringArrayListExtra("image",path);
                view_photos.putExtra("position",i);
                context.startActivity(view_photos);
            }
        });

        holder.status_video_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Title");
                intent.setType("video/mp4");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(path.get(i)));
                try {
                    context.startActivity(Intent.createChooser(intent,"Upload video via:"));
                } catch (android.content.ActivityNotFoundException ex) {

                }
            }
        });

        holder.status_video_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descPath = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Video";
                if (copyFileOrDirectory(path.get(i),descPath)){
                    Toast.makeText(context,"file saved to whatsApp gallery",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView status_image,status_video_share,status_video_download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status_image=itemView.findViewById(R.id.status_image);
            status_video_share=itemView.findViewById(R.id.status_video_share);
            status_video_download=itemView.findViewById(R.id.status_video_download);
        }
    }

    public boolean copyFileOrDirectory(String srcDir, String dstDir) {

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
        return true;
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
