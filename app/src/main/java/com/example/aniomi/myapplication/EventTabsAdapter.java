package com.example.aniomi.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.example.aniomi.myapplication.EventTabs.int_items;

/**
 * Created by aniomi on 11/3/17.
 */

public class EventTabsAdapter  extends FragmentPagerAdapter {
    public EventTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GroupList_fragment();
            case 1:
                return new Group_Invitation();

        }
        return null;
    }

    @Override
    public int getCount() {


        return int_items;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "GOING";
            case 1:
                return "MAYBE";
            case 2:
                return "NOT GOING";
            case 3:
                return "INVITATION";

        }

        return null;
    }
}
