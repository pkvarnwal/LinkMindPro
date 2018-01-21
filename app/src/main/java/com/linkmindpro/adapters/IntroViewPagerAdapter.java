package com.linkmindpro.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class IntroViewPagerAdapter extends FragmentStatePagerAdapter {

    protected static Fragment [] fragments;
    private int mCount;
    private String prodName;


    public IntroViewPagerAdapter(FragmentManager fm, Fragment [] fragments) {
        super(fm);
        this.fragments = fragments;
        mCount = fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return  fragments[position];
    }

    @Override
    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }

    public int getItemPosition (Object object) {
        return POSITION_NONE;
    }
}
