package com.kaicom.api.camera;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Build;

import java.io.IOException;

/**
 * 封装Camera基本操作
 *
 * @author scj
 */
public class CameraOperation {

    private Camera mCamera;
    private Camera.Parameters parameters = null;
    private Camera.AutoFocusCallback mAutoFocusCallback;
    private boolean isPreview; // 是否预览

    /**
     * 初始化摄像头<br>
     * 如果扫描头打开则优先关闭扫描头, 以避免二维扫描头和摄像头冲突
     */
    public void initCamera() throws IOException {
        // 关闭扫描头
        /*
        if (ScanManager.getInstance().isOpen()) {
            ScanManager.getInstance().release();
            SystemClock.sleep(200);
        }*/
        // 打开硬件摄像头
        openCamera();
        if (cameraIsOpen()) {
            setParameters();
        }
    }

    /**
     * 开启照相机
     *
     * @throws IOException 如果机器的摄像头有问题获取不到Camera对象，也会抛此异常
     */
    private void openCamera() throws IOException {
        if (mCamera == null) {
            try {
                mCamera = Camera.open();
                setAutoFocusCallback();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        // 处理摄像头坏掉的机器
        if (mCamera == null)
            throw new IOException("摄像头有问题，请检查摄像头");
    }

    /**
     * 照相机是否开启
     *
     * @return
     */
    public boolean cameraIsOpen() {
        return mCamera != null;
    }

    /*
     * 设置扫描头参数
     */
    @SuppressWarnings("deprecation")
    private void setParameters() {
        if (parameters == null) {
            parameters = mCamera.getParameters();// 得到摄像头的参数
            parameters.setPictureFormat(PixelFormat.JPEG);// 设置照片的格式
            parameters.setJpegQuality(100);// 设置照片的质量
//            if (Build.VERSION.SDK_INT > 11) {
//                parameters.setPictureSize(1440, 1920);
//            }
            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(parameters);
        }
    }


    private void setAutoFocusCallback() {
        // 设置自动对焦接口
        mAutoFocusCallback = new Camera.AutoFocusCallback() {

            public void onAutoFocus(boolean success, Camera camera) {
                // KlLoger.debug("cameraOper", "auto focus：" + success);
                // if (success) {
                // camera.setOneShotPreviewCallback(null);
                // }

            }
        };
    }

    /**
     * 获得Camera对象
     *
     * @return
     */
    public Camera getCamera() {
        return mCamera;
    }

    /**
     * 打开闪光灯
     */
    public void openFlashLight() {
        if (checkCameraNotNull()) {
            parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
        }
    }

    /**
     * 关闭闪光灯
     */
    public void closeFlashLight() {
        if (checkCameraNotNull()) {
            parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
        }
    }

    /**
     * 自动对焦
     */
    public void autoFocus() {
        if (checkCameraNotNull()) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            mCamera.autoFocus(mAutoFocusCallback);
        }
    }

    /**
     * 检查 camera和parameters是否为空
     *
     * @return 两者都不为空时返回true
     */
    public boolean checkCameraNotNull() {
        return (mCamera != null && parameters != null);
    }

    /**
     * 开始预览
     */
    public void startPreview() {
        if (checkCameraNotNull()) {
            mCamera.startPreview();
            isPreview = true;
        }
    }

    /**
     * 停止预览
     */
    public void stopPreview() {
        if (cameraIsOpen()) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            isPreview = false;
        }
    }

    public void takePicture(PictureCallback jpeg) {
        if (checkCameraNotNull() && isPreview) {
            mCamera.takePicture(null, null, null, jpeg);
            isPreview = false;
        }
    }

    /**
     * 设置预览状态
     *
     * @param isPreview
     */
    public void setIsPreviewStatus(boolean isPreview) {
        this.isPreview = isPreview;
    }

    /**
     * 当前是否是预览状态
     *
     * @return
     */
    public boolean isPreview() {
        return isPreview;
    }

    /**
     * 释放照相机
     */
    public void release() {
        if (cameraIsOpen()) {
            if (isPreview)
                stopPreview();
            mCamera.release(); // 释放照相机
            mCamera = null;
            parameters = null;
        }
    }

}
