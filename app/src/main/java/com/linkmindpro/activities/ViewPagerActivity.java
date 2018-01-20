package com.linkmindpro.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.linkmindpro.adapters.IntroViewPagerAdapter;
import com.linkmindpro.fragment.FragmentFive;
import com.linkmindpro.fragment.FragmentFour;
import com.linkmindpro.fragment.FragmentOne;
import com.linkmindpro.fragment.FragmentThree;
import com.linkmindpro.fragment.FragmentTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import constraint.com.linkmindpro.R;

public class ViewPagerActivity extends AppCompatActivity {

    private Fragment [] fragments;
    private IntroViewPagerAdapter mAdapter;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);

        setUpPager();
    }

    private void setUpPager() {
        fragments = new Fragment[5];

        fragments[0] = FragmentOne.getInstance();
        fragments[1] = FragmentTwo.getInstance();
        fragments[2] = FragmentThree.getInstance();
        fragments[3] = FragmentFour.getInstance();
        fragments[4] = FragmentFive.getInstance();

        mAdapter = new IntroViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mAdapter);
        viewPager.setAdapter(mAdapter);
        try {
            tabLayout.setupWithViewPager(viewPager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
