package com.kaicom.api.manager;

import android.content.Context;
import android.os.Build;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.KaicomJNIProxy;
import com.kaicom.api.KaicomJNIProxyPhone;

import kaicom.android.app.KaicomJNI;

/**
 * 机器识别工具
 *
 * @author scj
 */
public final class ModelRecognizerTools {

    /**
     * 巴枪机型
     */
    private static final String[] scannerModel = {"420", "510", "520", "520S", "521", "585", "550","585P","K7"};


    private ModelRecognizerTools() {
    }

    public static KaicomJNIProxy getKaicomJNIProxy(Context context) {
        if (isBarcodeScanner()) {
            KaicomJNI kaicomJNI = KaicomJNI.getInstance(context);
            KaicomApplication.kaicomJNI = kaicomJNI;
            return new KaicomJNIProxy(kaicomJNI);
        } else {
            return new KaicomJNIProxyPhone();
        }
    }

    /**
     * 是否是巴枪
     *
     * @return
     */
    public static boolean isBarcodeScanner() {
        String model = Build.MODEL;
        if (model == null)
            return false;
        model = model.trim();

        for (String each : scannerModel) {
            if (each.equals(model)) {
                return true;
            }
        }
        return false;
    }

}
