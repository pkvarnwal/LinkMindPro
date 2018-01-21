package com.linkmindpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.linkmindpro.adapters.IntroViewPagerAdapter;
import com.linkmindpro.fragment.FragmentFive;
import com.linkmindpro.fragment.FragmentFour;
import com.linkmindpro.fragment.FragmentOne;
import com.linkmindpro.fragment.FragmentThree;
import com.linkmindpro.fragment.FragmentTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constraint.com.linkmindpro.R;

public class ViewPagerActivity extends AppCompatActivity {

    private Fragment [] fragments;
    private IntroViewPagerAdapter mAdapter;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_dots)
    TabLayout tabLayout;
    @BindView(R.id.button_get_started)
    Button buttonGetStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.bind(this);

        setUpPager();
    }

    @OnClick(R.id.button_get_started)
    void getStartedTapped() {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    private void setUpPager() {
        fragments = new Fragment[4];

        fragments[0] = FragmentOne.getInstance();
        fragments[1] = FragmentTwo.getInstance();
        fragments[2] = FragmentThree.getInstance();
        fragments[3] = FragmentFour.getInstance();
//        fragments[4] = FragmentFive.getInstance();

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
