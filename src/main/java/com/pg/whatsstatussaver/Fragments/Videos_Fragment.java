package com.pg.whatsstatussaver.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pg.whatsstatussaver.Adapters.AdapterForThumbNails;
import com.pg.whatsstatussaver.Adapters.StatusAdapter;
import com.pg.whatsstatussaver.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos_Fragment extends Fragment {
    private View view;

    private RecyclerView videos_recyclerview;
    private SwipeRefreshLayout videos_refresh;

    private ArrayList<Bitmap> thumbNails=new ArrayList<>();
    private String image_path;

    private AdapterForThumbNails adapterForThumbNails;


    public Videos_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_videos_, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videos_recyclerview=view.findViewById(R.id.videos_recyclerview);
        videos_refresh=view.findViewById(R.id.videos_refresh);
        getImages();

        videos_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                videos_refresh.setRefreshing(true);
                getImages();
            }
        });

    }
    private void getImages() {
        thumbNails.clear();
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
                if (//imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
//                        || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
//                        || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                         imagePath.getName().contains(".mp4") || imagePath.getName().contains(".MP4")
                        || imagePath.getName().contains(".3gp") || imagePath.getName().contains(".3GP")) {
                    image_path = imagePath.getAbsolutePath();

                    MyTask task=new MyTask();
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,image_path);
                }
            }
            //  }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String createVideoThumbNail(String path){
        Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }


    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
          //  Log.e("bitmap", String.valueOf(bitmap));
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    class MyTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {

            String result=createVideoThumbNail(strings[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            thumbNails.add(StringToBitMap(s));

            adapterForThumbNails=new AdapterForThumbNails(getActivity(),thumbNails);
            videos_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
            videos_recyclerview.setAdapter(adapterForThumbNails);
            videos_refresh.setRefreshing(false);
        }
    }

}
