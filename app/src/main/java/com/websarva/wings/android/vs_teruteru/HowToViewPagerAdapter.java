package com.websarva.wings.android.vs_teruteru;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class HowToViewPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_NUM = 2;
    public HowToViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int pos){
        Fragment fragment = null;
        switch (pos){
            case 0:
                fragment = new HowTo1();
                break;
            default:
                fragment = new HowTo2();
                break;
        }
        Log.d("test", "pos = " + String.valueOf(pos));
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }
}
