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
public class FavouriteFragment extends Fragment implements TabLayout.OnTabSelectedListener {
    private View view;
    private TabLayout favourite_tabs;
    private ViewPager favourite_view_pager;
    public FavouriteFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_favourite, container, false);
        favourite_tabs=view.findViewById(R.id.favourite_tabs);
        favourite_view_pager=view.findViewById(R.id.favourite_view_pager);

        //Adding the tabs using addTab() method
        favourite_tabs.addTab(favourite_tabs.newTab().setText("Photos"));
        favourite_tabs.addTab(favourite_tabs.newTab().setText("Videos"));
        favourite_tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        Status_photos_adapter adapter=new Status_photos_adapter(getChildFragmentManager(),favourite_tabs.getTabCount(),3);
        favourite_view_pager.setAdapter(adapter);
        favourite_tabs.setOnTabSelectedListener(this);
        //setupViewPager(viewPager);
        favourite_view_pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(favourite_tabs));
        return view;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        favourite_view_pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
