package com.pg.whatsstatussaver.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pg.whatsstatussaver.Adapters.Status_photos_adapter;
import com.pg.whatsstatussaver.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private View view;
    private TabLayout gallery_tabs;
    private ViewPager gallery_view_pager;
    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_gallery, container, false);
        gallery_tabs=view.findViewById(R.id.gallery_tabs);
        gallery_view_pager=view.findViewById(R.id.gallery_view_pager);

        //Adding the tabs using addTab() method
        gallery_tabs.addTab(gallery_tabs.newTab().setText("Photos"));
        gallery_tabs.addTab(gallery_tabs.newTab().setText("Videos"));
        gallery_tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        Status_photos_adapter adapter=new Status_photos_adapter(getChildFragmentManager(),gallery_tabs.getTabCount(),2);
        gallery_view_pager.setAdapter(adapter);
        gallery_tabs.setOnTabSelectedListener(this);
        //setupViewPager(viewPager);
        gallery_view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(gallery_tabs));

        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        gallery_view_pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
