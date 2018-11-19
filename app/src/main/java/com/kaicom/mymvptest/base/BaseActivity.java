package com.kaicom.mymvptest.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaicom.api.util.StringUtil;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.datasource.TraceRecordsOper;
import com.kaicom.mymvptest.files.FilePreference;
import com.kaicom.mymvptest.ui.activity.MainMenuActivity;
import com.kaicom.mymvptest.utils.DialogUtil;
import com.kaicom.mymvptest.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * Created by jc on 2017/8/29.
 */

public abstract class BaseActivity extends FragmentActivity {

    private ImageView ivBack;//头部返回按钮
    private TextView tvTitle,tvRightText;
    protected TraceRecordsOper traceRecordsOper;
    protected FilePreference filePreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
                |WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(getViewId());
        ButterKnife.bind(this);
        //设置状态栏透明
        if(!(this instanceof MainMenuActivity)){
            setStatusBarColor();
        }
        traceRecordsOper = new TraceRecordsOper();
        filePreference = FilePreference.getInstance();
        initView();
        initListeners();
        init();
    }

    /**
     * 判断是否已登录
     * @return
     */
    public static boolean isLogin(){
        return !StringUtil.isEmpty(FilePreference.getInstance().getNickname());
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
//        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setTranslucent(this,1);
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
        DialogUtil.context = this;
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
