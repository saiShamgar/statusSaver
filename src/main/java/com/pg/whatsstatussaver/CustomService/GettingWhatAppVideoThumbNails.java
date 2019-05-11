package com.pg.whatsstatussaver.CustomService;

import android.app.ProgressDialog;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Base64;
import android.util.Log;

import com.pg.whatsstatussaver.BaseActivity;
import com.pg.whatsstatussaver.InterFace.Videos;
import com.pg.whatsstatussaver.Utils.AppUtils;
import com.pg.whatsstatussaver.Utils.CustomProgressDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GettingWhatAppVideoThumbNails extends JobService  {
    JobParameters params;
    GettingVideos getVideos;
    public ArrayList<String> images=new ArrayList<>();
    public ArrayList<Bitmap> thumbNails=new ArrayList<>();
    private String image_path,thumbnail_path;



    @Override
    public boolean onStartJob(JobParameters params) {
        this.params = params;
        Log.d("VideoService", "Work to be called from here");
        getImages();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("TestService", "System calling to stop the job here");
        if (getVideos != null)
            getVideos.cancel(true);
        return false;
    }

    public  class GettingVideos extends AsyncTask<ArrayList<Bitmap>, ArrayList<Bitmap>, ArrayList<Bitmap>>{
        private String path;
        public GettingVideos(String thumbnail_path) {
            this.path=thumbnail_path;
        }

        @Override
        protected ArrayList<Bitmap> doInBackground(ArrayList<Bitmap>... arrayLists) {
            thumbNails.add(createVideoThumbNail(path));
            return thumbNails;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmaps) {
            super.onPostExecute(bitmaps);
            sendMessage(bitmaps);
           // Log.e("thumbnail", String.valueOf(bitmaps.size()));
        }
    }


    private void getImages() {
        images.clear();
        thumbNails.clear();
       // long interval =10 * 1000;
       // String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/.Statuses";
        //String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images";

        String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Video";
        File f = new File(path);
        File files[] = f.listFiles();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        }
        for (File imagePath : files) {
            try {
                if (imagePath.getName().contains(".mp4") || imagePath.getName().contains(".MP4")
                        || imagePath.getName().contains(".3gp") || imagePath.getName().contains(".3GP")) {
                    thumbnail_path = imagePath.getAbsolutePath();
                    getVideos = new GettingVideos(thumbnail_path);
                    getVideos.execute();
                }
            }
            //  }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Bitmap createVideoThumbNail(String path){
        Bitmap  bitmap= ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
        return bitmap;
    }

    private void sendMessage(ArrayList<Bitmap> strings) {
      //  Log.e("sender", "Broadcasting message");
        HashMap<String, ArrayList<Bitmap>> hashMap = new HashMap<String, ArrayList<Bitmap>>();
        hashMap.put("bitmap",strings);
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("map",hashMap);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
