package com.kaicom.api.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 引导界面adapter
 * @author scj
 *
 */
public class GuidePagerAdapter extends PagerAdapter {

    private List<View> pageViews;
    
    public GuidePagerAdapter(List<View> pageViews) {
        this.pageViews = pageViews;
    }
    
    @Override    
    public int getCount() {    
        return pageViews == null ? 0 : pageViews.size();    
    }    

    @Override    
    public Object instantiateItem(View view, int position) {    
        ((ViewPager) view).addView(pageViews.get(position));    
        return pageViews.get(position);    
    }  
    
    @Override    
    public boolean isViewFromObject(View arg0, Object arg1) {    
        return arg0 == arg1;    
    }    

    @Override    
    public void destroyItem(View arg0, int arg1, Object arg2) {    
        ((ViewPager) arg0).removeView(pageViews.get(arg1));    
    }
   
}   