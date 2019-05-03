package com.pg.whatsstatussaver.CustomService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.MediaStore;

import com.pg.whatsstatussaver.Events.EventBusClass;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GettingImages extends Service {

    public ArrayList<String> images=new ArrayList<>();
    public ArrayList<Bitmap> thumbNails=new ArrayList<>();
    private String image_path,thumbnail_path;

    AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pendingIntent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getImages();
        return Service.START_NOT_STICKY;

    }

    private void getImages() {
        images.clear();
        thumbNails.clear();
        long interval =10 * 1000;

//        alarmIntent = new Intent(this, GettingImages.class);
//        pendingIntent = PendingIntent.getService(this, 0, alarmIntent, 0);
//        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/.Statuses";
        //String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images";
        // String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Video";
        File f = new File(path);
        //  File file[] = f.listFiles();
        //Log.e("images",file.toString());
        File files[] = f.listFiles();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        }
        for (File imagePath : files) {
            try {
                if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                        || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                        || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                        || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                        || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")) {
                  image_path = imagePath.getAbsolutePath();
                  images.add(image_path);
                }

                if (imagePath.getName().contains(".mp4") || imagePath.getName().contains(".MP4")
                        || imagePath.getName().contains(".3gp") || imagePath.getName().contains(".3GP")) {
                    thumbnail_path = imagePath.getAbsolutePath();
                    thumbNails.add(createVideoThumbNail(thumbnail_path));
                }
                EventBus.getDefault().post(new EventBusClass.ActivityServiceMessage(images,thumbNails));

////                if (!images.contains(image_path) || !thumbnail_path.contains(thumbnail_path)){
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + interval - SystemClock.elapsedRealtime() % 1000, pendingIntent);
//                    }
            //    }

            }
            //  }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap createVideoThumbNail(String path){
        Bitmap  bitmap= ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);

//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        byte[] byteArray = byteArrayOutputStream .toByteArray();
//
//        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return bitmap;
    }


}
