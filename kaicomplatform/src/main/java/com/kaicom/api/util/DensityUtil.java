package com.kaicom.api.util;

import static com.kaicom.api.KaicomApplication.app;
import android.util.TypedValue;

/**
 * 单位转换工具类
 * @author scj
 */
public final class DensityUtil {

    private DensityUtil() {
        throw new RuntimeException("￣ 3￣");
    }

    /** 
     * dp转px 
     * @param val 
     * @return 
     */
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, app.getResources().getDisplayMetrics());
    }

    /** 
     * sp转px 
     *  
     * @param val 
     * @return 
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, app.getResources().getDisplayMetrics());
    }

    /** 
     * px转dp 
     *  
     * @param pxVal 
     * @return 
     */
    public static float px2dp(float pxVal) {
        final float scale = app.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /** 
     * px转sp 
     *  
     * @param pxVal 
     * @return 
     */
    public static float px2sp(float pxVal) {
        return (pxVal / app.getResources().getDisplayMetrics().scaledDensity);
    }

}
