package com.pg.whatsstatussaver.Fragments;


import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pg.whatsstatussaver.Adapters.Gallery_Photos_Adapter;
import com.pg.whatsstatussaver.CustomService.GettingWhatAppVideoThumbNails;
import com.pg.whatsstatussaver.InterFace.Videos;
import com.pg.whatsstatussaver.R;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Galley_videos_fragment extends Fragment implements Videos{
    private View view;
    private RecyclerView gallery_videos_recycler;
    private SwipeRefreshLayout _gallery_videos_refresh;


    private ArrayList<Bitmap> images;
    private ArrayList<String> image_path=new ArrayList<>();
    private Gallery_Photos_Adapter adapter;

    private ProgressDialog progressDialog;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gallery_videos_recycler=view.findViewById(R.id.gallery_videos_recycler);
        _gallery_videos_refresh=view.findViewById(R.id._gallery_videos_refresh);

        images=new ArrayList<>();
        _gallery_videos_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                _gallery_videos_refresh.setRefreshing(true);
                JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
                ComponentName componentName = new ComponentName(getActivity(), GettingWhatAppVideoThumbNails.class);
                JobInfo jobInfo = new JobInfo.Builder(1, componentName).setOverrideDeadline(10).build();
                jobScheduler.schedule(jobInfo);

                LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                        new IntentFilter("custom-event-name"));
               // getImages();
            }
        });

        JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getActivity(), GettingWhatAppVideoThumbNails.class);
        JobInfo jobInfo = new JobInfo.Builder(1, componentName).setOverrideDeadline(10).build();
        jobScheduler.schedule(jobInfo);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        adapter=new Gallery_Photos_Adapter(getActivity(),images);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
        gallery_videos_recycler.setLayoutManager(gridLayoutManager);
        gallery_videos_recycler.setAdapter(adapter);
      //  gallery_videos_recycler.setNestedScrollingEnabled(true);

//        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {
//            @Override protected int getVerticalSnapPreference() {
//                return LinearSmoothScroller.SNAP_TO_START;
//            }
//        };
// smoothScroller.setTargetPosition(position);
// layoutManager.startSmoothScroll(smoothScroller);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = getActivity().getIntent().getExtras();
            HashMap<String, ArrayList<Bitmap>> hashMap = (HashMap<String, ArrayList<Bitmap>>)intent.getSerializableExtra("map");
            images.clear();
            images.addAll(hashMap.get("bitmap"));
            adapter.notifyDataSetChanged();
            _gallery_videos_refresh.setRefreshing(false);
        }
    };

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public void gettingVideos(ArrayList<String> bitmaps) {
        Log.e("bitmaps",bitmaps.toString());
    }
}
