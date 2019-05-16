package com.pg.whatsstatussaver.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pg.whatsstatussaver.Fragments.Fav_Video_Frgament;
import com.pg.whatsstatussaver.Fragments.Fav_photo_Fragment;
import com.pg.whatsstatussaver.Fragments.FavouriteFragment;
import com.pg.whatsstatussaver.Fragments.GalleryFragment;
import com.pg.whatsstatussaver.Fragments.Galley_Photos_Fragment;
import com.pg.whatsstatussaver.Fragments.Galley_videos_fragment;
import com.pg.whatsstatussaver.Fragments.Photos_Fragment;
import com.pg.whatsstatussaver.Fragments.StatusFragment;
import com.pg.whatsstatussaver.Fragments.Videos_Fragment;

public class Status_photos_adapter extends FragmentStatePagerAdapter {
    int tabCount;
    int status;
    public Status_photos_adapter(FragmentManager fm, int tabCount,int status) {
        super(fm);
        this.tabCount=tabCount;
        this.status=status;
    }

    @Override
    public Fragment getItem(int position) {

        if (status==1){
            switch (position) {
                case 0:
                    Photos_Fragment tab1 = new Photos_Fragment();
                    return tab1;
                case 1:
                    Videos_Fragment tab2 = new Videos_Fragment();
                    return tab2;
            }
        }
        if (status==2){
            switch (position) {
                case 0:
                    Galley_Photos_Fragment tab1 = new Galley_Photos_Fragment();
                    return tab1;
                case 1:
                    Galley_videos_fragment tab2 = new Galley_videos_fragment();
                    return tab2;
            }
        }

        if (status==3){
            switch (position) {
                case 0:
                    Fav_photo_Fragment tab1 = new Fav_photo_Fragment();
                    return tab1;
                case 1:
                    Fav_Video_Frgament tab2 = new Fav_Video_Frgament();
                    return tab2;
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
