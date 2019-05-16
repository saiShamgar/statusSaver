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
import android.widget.Toast;

import com.pg.whatsstatussaver.Adapters.Favaurite_Adapter;
import com.pg.whatsstatussaver.Adapters.Gallery_Photos_Adapter;
import com.pg.whatsstatussaver.InterFace.OnItemclickListener;
import com.pg.whatsstatussaver.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fav_photo_Fragment extends Fragment implements OnItemclickListener {
    private View view;

    private RecyclerView fav_photos_recycler;
    private SwipeRefreshLayout _fav_photos_refresh;


    private ArrayList<String> images=new ArrayList<>();
    private String image_path;
    private Favaurite_Adapter adapter;
    public Fav_photo_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_fav_photo_, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fav_photos_recycler=view.findViewById(R.id.fav_photos_recycler);
        _fav_photos_refresh=view.findViewById(R.id._fav_photos_refresh);
        _fav_photos_refresh.setRefreshing(false);
        _fav_photos_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                _fav_photos_refresh.setRefreshing(true);
                getImages();
            }
        });
        getImages();

        adapter=new Favaurite_Adapter(getActivity(),images,1,this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        fav_photos_recycler.setLayoutManager(gridLayoutManager);
        fav_photos_recycler.setHasFixedSize(true);
        fav_photos_recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getImages() {
        images.clear();
        // String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/.Statuses";
     //   String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/WhatsApp Images";
        String path = Environment.getExternalStorageDirectory().toString()+"/WhatsApp/Media/Favourite";
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

                    _fav_photos_refresh.setRefreshing(false);
                }

//                }else {
//                    _fav_photos_refresh.setRefreshing(false);
//                    Toast.makeText(getActivity(),"No favourites found",Toast.LENGTH_LONG).show();
//
//
//                }

            }
            //  }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void OnItemClick(int position) {
      //  Toast.makeText(getActivity(),"clicked",Toast.LENGTH_LONG).show();
        images.remove(position);
       fav_photos_recycler.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, images.size());

    }
}
