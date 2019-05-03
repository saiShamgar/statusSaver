package com.pg.whatsstatussaver.Adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pg.whatsstatussaver.Fragments.FavouriteFragment;
import com.pg.whatsstatussaver.Fragments.GalleryFragment;
import com.pg.whatsstatussaver.Fragments.StatusFragment;

public class TabsAccessorAdaptor extends FragmentPagerAdapter
{
    public TabsAccessorAdaptor(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
                case 0:
                    StatusFragment statusFragment=new StatusFragment();
                    return statusFragment;

                case 1:
                    GalleryFragment galleryFragment=new GalleryFragment();
                    return galleryFragment;

                case 2:
                    FavouriteFragment favouriteFragment=new FavouriteFragment();
                    return favouriteFragment;

                default:
                    return null;

        }



    }

    @Override
    public int getCount()
    {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position)
    {
        switch (position)
        {
            case 0:

            return "Status";

            case 1:

                return "Gallery";

            case 2:

                return "Favourite";

            default:

                return null;
        }
    }


}
