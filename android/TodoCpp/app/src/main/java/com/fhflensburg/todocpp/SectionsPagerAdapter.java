package com.fhflensburg.todocpp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Sections Pager Adapter
 *
 * Author: Maik Hansen
 *
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 *
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    /* constructor */
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /* new instance with given position + 1 */
    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(position + 1);
    }

    /* tab count */
    @Override
    public int getCount() {
        return 2;
    }

    /* tab titles */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TASK LIST";
            case 1:
                return "ADD TASK";
        }
        return null;
    }
}