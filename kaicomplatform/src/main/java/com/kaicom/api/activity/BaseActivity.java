package com.kaicom.api.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * BaseActivity
 * 
 * @author scj
 */
@Deprecated
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // 禁止屏幕翻转
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        // 设置软键盘默认隐藏和重新编排layout,为软键盘分配多余空间
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                |WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        
    }

    /** 
     * 跳转到另一个activty
     * <br>不销毁当前activity
     * @param activity Class<?>
     */
    protected void openActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }
    
}
