package com.kaicom.mymvptest.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by jc on 2018/11/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private FragmentManager fm;
    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments!=null){
            return fragments.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(fragments!=null){
            return fragments.size();
        }
        return 0;
    }
}
