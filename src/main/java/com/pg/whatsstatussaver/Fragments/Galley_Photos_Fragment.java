package com.pg.whatsstatussaver.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pg.whatsstatussaver.Adapters.Gallery_Photos_Adapter;
import com.pg.whatsstatussaver.Adapters.StatusAdapter;
import com.pg.whatsstatussaver.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Galley_Photos_Fragment extends Fragment {

    private View view;
    private RecyclerView gallery_photos_recycler;
    private SwipeRefreshLayout _gallery_photos_refresh;


    private ArrayList<String> images=new ArrayList<>();
    private String image_path;
    private Gallery_Photos_Adapter adapter;
    public Galley_Photos_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_galley__photos_, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gallery_photos_recycler=view.findViewById(R.id.gallery_photos_recycler);
        _gallery_photos_refresh=view.findViewById(R.id._gallery_photos_refresh);

        _gallery_photos_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                _gallery_photos_refresh.setRefreshing(true);
                getImages();
            }
        });
        getImages();
    }

    private void getImages() {
        images.clear();
       // String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/.Statuses";
        String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images";
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

                    adapter=new Gallery_Photos_Adapter(getActivity(),images,1);
                    GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
                    gallery_photos_recycler.setLayoutManager(gridLayoutManager);
                    gallery_photos_recycler.setHasFixedSize(true);
                    gallery_photos_recycler.setAdapter(adapter);
                    _gallery_photos_refresh.setRefreshing(false);

                }

            }
            //  }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
