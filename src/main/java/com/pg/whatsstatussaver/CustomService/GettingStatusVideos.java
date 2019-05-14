package com.pg.whatsstatussaver.CustomService;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GettingStatusVideos  extends JobService {

    JobParameters params;
    GettingVideos getVideos;
    public ArrayList<String> images=new ArrayList<>();
    public ArrayList<Bitmap> thumbNails=new ArrayList<>();
    public ArrayList<String> video_urls=new ArrayList<>();
    private String image_path,thumbnail_path;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("status Videos", "job started");
        this.params=params;
        getImages();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("status Videos", "System calling to stop the job here");
        if (getVideos != null)
            getVideos.cancel(true);
        return false;
    }

    public  class GettingVideos extends AsyncTask<ArrayList<Bitmap>, ArrayList<Bitmap>, ArrayList<Bitmap>> {
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
            sendMessage(bitmaps,video_urls);
           //  Log.e("thumbnail", String.valueOf(bitmaps.size()));
        }
    }


    private void getImages() {
        images.clear();
        thumbNails.clear();
        video_urls.clear();
        // long interval =10 * 1000;
         String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/.Statuses";
        //String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images";

        //String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Video";
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
                    video_urls.add(thumbnail_path);
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

    private void sendMessage(ArrayList<Bitmap> strings, ArrayList<String> video_urls) {
       //   Log.e("sender", "Broadcasting message");
        HashMap<String, ArrayList<Bitmap>> hashMap = new HashMap<String, ArrayList<Bitmap>>();
        hashMap.put("bitmap",strings);
        HashMap<String, ArrayList<String>> hashMap1 = new HashMap<String, ArrayList<String>>();
        hashMap1.put("urls",video_urls);
        Intent intent = new Intent("status-videos");
        intent.putExtra("map",hashMap);
        intent.putExtra("url",hashMap1);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
