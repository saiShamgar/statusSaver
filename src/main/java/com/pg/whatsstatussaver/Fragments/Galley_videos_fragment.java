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
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pg.whatsstatussaver.Adapters.AdapterForThumbNails;
import com.pg.whatsstatussaver.Adapters.Gallery_Photos_Adapter;
import com.pg.whatsstatussaver.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Galley_videos_fragment extends Fragment {
    private View view;
    private RecyclerView gallery_videos_recycler;
    private SwipeRefreshLayout _gallery_videos_refresh;


    private ArrayList<Bitmap> images=new ArrayList<>();
    private ArrayList<String> image_path=new ArrayList<>();
    private Gallery_Photos_Adapter adapter;
    public Galley_videos_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_galley_videos_fragment, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gallery_videos_recycler=view.findViewById(R.id.gallery_videos_recycler);
        _gallery_videos_refresh=view.findViewById(R.id._gallery_videos_refresh);

        _gallery_videos_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                _gallery_videos_refresh.setRefreshing(true);
                getImages();
            }
        });
        getImages();

//        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
//            @Override protected int getVerticalSnapPreference() {
//                return LinearSmoothScroller.SNAP_TO_START;
//            }
//        };
// smoothScroller.setTargetPosition(position);
// layoutManager.startSmoothScroll(smoothScroller);
    }




    private void getImages() {
        images.clear();
        image_path.clear();
       // String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/.Statuses";
       // String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images";
         String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Video";
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
                    image_path.add(imagePath.getAbsolutePath());
                    String path2=imagePath.getAbsolutePath();
                    MyTask task=new MyTask();
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,path2);
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
            // Log.e("bitmap", String.valueOf(bitmap));
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
            images.add(StringToBitMap(s));

            adapter=new Gallery_Photos_Adapter(getActivity(),images,image_path);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
            gallery_videos_recycler.setLayoutManager(gridLayoutManager);
            gallery_videos_recycler.setAdapter(adapter);
            _gallery_videos_refresh.setRefreshing(false);
        }
    }
}
