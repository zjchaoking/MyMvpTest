package com.kaicom.mymvptest.ui.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.kaicom.api.util.ApkUtil;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.base.BaseActivity;
import com.kaicom.mymvptest.inerface.OnFragmentInteractionListener;
import com.kaicom.mymvptest.ui.adapters.ViewPagerAdapter;
import com.kaicom.mymvptest.ui.fragment.IndexFragment;
import com.kaicom.mymvptest.ui.fragment.MeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnPageChange;

/**
 * 主业务界面
 */
public class MainMenuActivity extends BaseActivity implements OnFragmentInteractionListener {


    @BindView(R.id.view_pages)
    ViewPager viewPages;
    @BindView(R.id.tv_tab_index)
    TextView tvTabIndex;
    @BindView(R.id.tv_tab_me)
    TextView tvTabMe;
    private List<TextView> tabs;

    @Override
    protected int getViewId() {
        return R.layout.activity_main_menu;
    }

    @Override
    protected void init() {
        List<Fragment> fragments = new ArrayList<>();
        tabs = new ArrayList<>();
        tabs.add(tvTabIndex);
        tabs.add(tvTabMe);
        IndexFragment indexFragment = new IndexFragment();
        MeFragment meFragment = new MeFragment();
        fragments.add(indexFragment);
        fragments.add(meFragment);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPages.setAdapter(adapter);
        viewPages.setCurrentItem(0);
        setCurrentTabColor(0);
    }

    /**
     * 设置当前选中Tab的颜色为红色，其他为黑色
     * @param tabOrder
     */
    private void setCurrentTabColor(int tabOrder){
        for (int i = 0; i < tabs.size(); i++) {
            if(tabOrder ==i){
                tabs.get(i).setTextColor(getResources().getColor(R.color.red));
            }else{
                tabs.get(i).setTextColor(getResources().getColor(R.color.black));
            }
        }
    }

    @OnPageChange(R.id.view_pages)
    void onPageChange(int arg0){
        switch (arg0){
            case 0:
                setCurrentTabColor(0);
                break;
            case 1:
                setCurrentTabColor(1);
                break;
        }
    }
    @OnClick({R.id.tv_tab_me, R.id.tv_tab_index})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tab_index:
                viewPages.setCurrentItem(0);
                break;
            case R.id.tv_tab_me:
            viewPages.setCurrentItem(1);
            break;
        }


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ApkUtil.exit();
    }
}
