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
import com.pg.whatsstatussaver.LocalDatabase.DatabaseClient;
import com.pg.whatsstatussaver.LocalDatabase.ImagesUrlTable;
import com.pg.whatsstatussaver.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
        List<ImagesUrlTable> taskList = DatabaseClient
                .getInstance(getActivity())
                .getAppDatabase()
                .operations()
                .getAll();

        if (taskList.size()>0){
            for (int i=0;i<taskList.size();i++){
                images.add(taskList.get(i).getUrl());
            }
            _fav_photos_refresh.setRefreshing(false);
        }
        else {
            _fav_photos_refresh.setRefreshing(false);
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
