package com.kaicom.mymvptest.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.datasource.TraceRecordsOper;
import com.kaicom.mymvptest.utils.DialogUtil;
import com.kaicom.mymvptest.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by jc on 2017/8/29.
 */

public abstract class BaseActivity extends Activity {

    private ImageView ivBack;//头部返回按钮
    private TextView tvTitle,tvRightText;
    protected TraceRecordsOper traceRecordsOper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(getViewId());
        ButterKnife.bind(this);
        DialogUtil.context = this;
        //设置状态栏透明
//        setStatusBarColor();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        traceRecordsOper = new TraceRecordsOper();
        initView();
        initListeners();
        init();
    }

    private void initListeners() {
        if(ivBack!=null){
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setTitle(String title){
        if(tvTitle!=null){
            tvTitle.setText(title);
        }
    }
    /**
     * 设置顶部右侧的文字
     * @param rightText
     */
    protected void setRightText(String rightText){
        if(tvRightText!=null){
            tvRightText.setText(rightText);
        }
    }

    private void setStatusBarColor() {
        StatusBarUtil.setTransparent(this);
    }

    protected abstract int getViewId();
    protected abstract void init();

    private void initView() {
        ivBack = (ImageView)findViewById(R.id.iv_back);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvRightText = (TextView)findViewById(R.id.tv_right_text);
    }

    /**
     * 跳转Activity界面
     * @param cls
     */
    protected void toNextActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    /**
     * 跳转Activity界面
     * @param cls
     */
    protected void toNextActivityWithBundle(Class cls,Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
