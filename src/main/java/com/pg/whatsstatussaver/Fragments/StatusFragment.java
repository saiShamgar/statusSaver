package com.pg.whatsstatussaver.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pg.whatsstatussaver.Adapters.AdapterForThumbNails;
import com.pg.whatsstatussaver.Adapters.StatusAdapter;
import com.pg.whatsstatussaver.Adapters.Status_photos_adapter;
import com.pg.whatsstatussaver.CustomService.GettingImages;
import com.pg.whatsstatussaver.Events.EventBusClass;
import com.pg.whatsstatussaver.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private RecyclerView recyclerView;

    private View view;
    private StatusAdapter adapter;
    private AdapterForThumbNails adapterForThumbNails;
    ArrayList<String> images=new ArrayList<>();
    ArrayList<Bitmap> thumbNails=new ArrayList<>();

    private TabLayout status_tabs;
    private ViewPager status_view_pager;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_status, container, false);
        status_tabs=view.findViewById(R.id.status_tabs);
        status_view_pager=view.findViewById(R.id.status_view_pager);

        //Adding the tabs using addTab() method
        status_tabs.addTab(status_tabs.newTab().setText("Photos"));
        status_tabs.addTab(status_tabs.newTab().setText("Videos"));
        Status_photos_adapter adapter=new Status_photos_adapter(getChildFragmentManager(),status_tabs.getTabCount(),1);
        status_view_pager.setAdapter(adapter);
        status_tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        status_tabs.setOnTabSelectedListener(this);
        //setupViewPager(viewPager);
        status_view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(status_tabs));

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        status_view_pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
