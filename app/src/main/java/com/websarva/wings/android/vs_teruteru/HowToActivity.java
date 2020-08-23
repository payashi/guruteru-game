package com.websarva.wings.android.vs_teruteru;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HowToActivity extends AppCompatActivity {

    private ViewPager pager;
    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        pager = findViewById(R.id.pager);
        adapter = new HowToViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

}
