package com.kaicom.api.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

/**
 * 截屏工具类
 * @author scj
 *
 */
public final class ScreenShotUtil {
    
    private ScreenShotUtil() {
        throw new RuntimeException("╮(╯▽╰)╭");
    }

    /**
     * 截屏
     * @param activity
     * @return Bitmap
     */
    @SuppressWarnings("deprecation")
    public static Bitmap takeScreenShot(Activity activity) {
        //View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        //获取状态栏高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();//去掉标题栏

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);

        view.destroyDrawingCache();
        return b;
    }
    
}
