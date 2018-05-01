package com.kaicom.api.camera;

import static com.kaicom.api.camera.CameraActivity.CAMERA_CONFIG_KEY;
import static com.kaicom.api.camera.CameraActivity.CAMERA_REQUEST;
import static com.kaicom.api.camera.CameraActivity.CAMERA_RESULT;

import com.kaicom.api.util.BitmapUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * <h4>照相机启动和结果处理工具类</h4>
 * @author scj
 *
 */
public final class CameraTools {

    private CameraTools() {
    }
    
    public static void jumpToCameraView(Activity activity) {
        jumpToCameraView(activity, null);
    }
    
    public static void jumpToCameraView(Activity activity, CameraConfig config) {
        Intent intent = new Intent(activity, CameraActivity.class);
        intent.putExtra(CAMERA_CONFIG_KEY, config);
        activity.startActivityForResult(intent, CAMERA_REQUEST);
    }
    
    public static boolean isCameraResult(int requestCode, int resultCode) {
        return requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK;
    }
    
    public static Bitmap getBitmapFromResult(Intent result) {
        byte[] data = getImgBytesFromResult(result);
        if (data == null)
            return null;
        
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return BitmapUtil.rotate(bitmap, 90);
    }
    
    public static byte[] getImgBytesFromResult(Intent result) {
        if (result == null)
            return null;
        
        byte[] data = result.getByteArrayExtra(CAMERA_RESULT);
        return data;
    }
    
}
