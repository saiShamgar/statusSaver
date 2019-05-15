package com.pg.whatsstatussaver.Adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pg.whatsstatussaver.R;
import com.pg.whatsstatussaver.ViewPhotos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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

        holder.status_photo_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = getBitmapFromView(holder.status_image);
                try {
                    File file = new File(context.getExternalCacheDir(), "logicchip.png");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
//                  file.setReadable(true, false);
                    final Intent intent = new Intent(Intent.ACTION_SEND);
                    Uri apkURI = FileProvider.getUriForFile(
                            context,
                            context.getApplicationContext()
                                    .getPackageName() + ".provider", file);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        intent.setDataAndType(apkURI, Intent.normalizeMimeType("image/png"));
                    }
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //   intent.setPackage("com.whatsapp");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra(Intent.EXTRA_TEXT, "MLA name : "+news.getMla()+"\nAddress : "+holder.postlocation.getText()+"\n"+news.getDescription()+
//                            "\nPlayStore Link : https://play.google.com/store/apps/details?id=shamgar.org.peoplesfeedback");                            intent.putExtra(Intent.EXTRA_STREAM, apkURI);
//                            intent.setType("image/png");
                    intent.putExtra(Intent.EXTRA_STREAM, apkURI);
                    context.startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

     holder.status_photo_download.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String descPath = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images";
          if (copyFileOrDirectory(images.get(i),descPath)){
              Toast.makeText(context,"file saved to whatsApp gallery",Toast.LENGTH_LONG).show();
          }
         }
     });

    }
    private Bitmap getBitmapFromView(ImageView view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private ImageView status_image,status_photo_share,status_photo_download;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            status_image=itemView.findViewById(R.id.status_image);
            status_photo_share=itemView.findViewById(R.id.status_photo_share);
            status_photo_download=itemView.findViewById(R.id.status_photo_download);
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
