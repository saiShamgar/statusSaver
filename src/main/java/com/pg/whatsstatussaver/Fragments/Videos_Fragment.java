package com.pg.whatsstatussaver.Fragments;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.pg.whatsstatussaver.CustomService.GettingStatusVideos;
import com.pg.whatsstatussaver.CustomService.GettingWhatAppVideoThumbNails;
import com.pg.whatsstatussaver.InterFace.Videos;
import com.pg.whatsstatussaver.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Videos_Fragment extends Fragment  {
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        videos_recyclerview=view.findViewById(R.id.videos_recyclerview);
        videos_refresh=view.findViewById(R.id.videos_refresh);

        videos_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onRefresh() {
                videos_refresh.setRefreshing(true);
                JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
                ComponentName componentName = new ComponentName(getActivity(), GettingStatusVideos.class);
                JobInfo jobInfo = new JobInfo.Builder(2, componentName).setOverrideDeadline(10).build();
                jobScheduler.schedule(jobInfo);

                LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                        new IntentFilter("status-videos"));
            }
        });
        JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getActivity(), GettingStatusVideos.class);
        JobInfo jobInfo = new JobInfo.Builder(2, componentName).setOverrideDeadline(10).build();
        jobScheduler.schedule(jobInfo);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("status-videos"));

        adapterForThumbNails=new AdapterForThumbNails(getActivity(),thumbNails);
        videos_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        videos_recyclerview.setAdapter(adapterForThumbNails);


    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = getActivity().getIntent().getExtras();
            HashMap<String, ArrayList<Bitmap>> hashMap = (HashMap<String, ArrayList<Bitmap>>)intent.getSerializableExtra("map");
            thumbNails.clear();
            thumbNails.addAll(hashMap.get("bitmap"));
            adapterForThumbNails.notifyDataSetChanged();
            videos_refresh.setRefreshing(false);
        }
    };

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }


}
