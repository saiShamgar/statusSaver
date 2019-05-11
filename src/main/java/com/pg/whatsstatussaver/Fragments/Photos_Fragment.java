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
public class Photos_Fragment extends Fragment {


    private View view;
    private RecyclerView photos_recycler;
    private SwipeRefreshLayout photos_refresh;

    private ArrayList<String> images=new ArrayList<>();
    private String image_path;

    private StatusAdapter adapter;
    private int status;

    public Photos_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_photos_, container, false);
        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        photos_recycler=view.findViewById(R.id.photos_recycler);
        photos_refresh=view.findViewById(R.id.photos_refresh);

        photos_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photos_refresh.setRefreshing(true);
                getImages();
            }
        });
        getImages();
    }
        private void getImages() {
        images.clear();
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


                    adapter=new StatusAdapter(getActivity(),images);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                    photos_recycler.setLayoutManager(gridLayoutManager);
                    photos_recycler.setHasFixedSize(true);
                    photos_recycler.setAdapter(adapter);
                    photos_refresh.setRefreshing(false);

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
            Log.e("bitmap", String.valueOf(bitmap));
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

}
