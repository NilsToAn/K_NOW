package com.studis.nife.k_now;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SwipeAdapter_set extends FragmentStatePagerAdapter {


    public SwipeAdapter_set (FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new PageFragment_set();
        return fragment;
    }

    @Override
    public int getCount() {
        return 1;
    }
}
