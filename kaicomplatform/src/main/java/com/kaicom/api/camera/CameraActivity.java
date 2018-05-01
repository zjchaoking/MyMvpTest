package com.kaicom.api.camera;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kaicom.api.log.KlLoger;
import com.kaicom.api.scan.ScanManager;
import com.kaicom.api.view.dialog.DialogTools;
import com.kaicom.api.view.toast.ToastTools;
import com.kaicom.fw.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 拍照
 *
 * @author scj
 */
public class CameraActivity extends Activity implements SurfaceHolder.Callback {

    public static final String TAG = "Kaicom-Camera";
    /**
     * 拍照的RequestCode
     */
    public static final int CAMERA_REQUEST = 0x27;
    /**
     * 快拍
     */
    public static final String IS_SNAPSHOT = "is_snapshot";
    /**
     * 未拍摄能否关闭
     */
    public static final String CAN_CLOSE = "can_close";
    boolean isOver = false;
    /**
     * 摄像头设置
     */
    public static final String CAMERA_CONFIG_KEY = "camera_config";

    public static final String CAMERA_RESULT = "camera_result";
    private boolean canClose = true;

    private CheckBox flashLightCheck;
    private Button takeBtn, saveBtn;
    private boolean isRetakeBtn; // 是否是重拍
    private SurfaceView mPhotoView;
    private SurfaceHolder mSurfaceHolder;

    private CameraOperation cameraOper;
    private byte[] photo;
    private boolean isSnapshot; // 快拍

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent config = getIntent();
        if (config != null) {
            isSnapshot = config.getBooleanExtra(IS_SNAPSHOT, true);
            canClose = config.getBooleanExtra(CAN_CLOSE, true);
        }
        if (ScanManager.getInstance().isScanning()) {
            ScanManager.getInstance().endScan();
        }
        cameraOper = new CameraOperation();
        setupView();
        initView();
        initListener();
    }

    /**
     * 客户程序界面风格不同， 可自定义布局<br>
     * 注：控件id和onClick方法必须与原布局相同
     */
    protected void setupView() {
        setContentView(R.layout.camera_layout);
    }

    @SuppressWarnings("deprecation")
    private void initView() {
        takeBtn = (Button) findViewById(R.id.take_photo_btn);
        saveBtn = (Button) findViewById(R.id.save_photo_btn);
        mPhotoView = (SurfaceView) findViewById(R.id.photo_view);
        flashLightCheck = (CheckBox) findViewById(R.id.cb_flash_light);

        changeSaveBtnState(false);
        if (isSnapshot)
            saveBtn.setVisibility(View.GONE);

        takeBtn.requestFocus();

        mPhotoView.setFocusableInTouchMode(true);
        mPhotoView.setClickable(true);
        mSurfaceHolder = mPhotoView.getHolder();
        if (Build.VERSION.SDK_INT < 11) {
            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        mSurfaceHolder.addCallback(this);
    }

    private void changeSaveBtnState(boolean enabled) {
        saveBtn.setEnabled(enabled);
        saveBtn.setFocusable(enabled);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            takePhotoClick(takeBtn);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initListener() {
        mPhotoView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (cameraOper.checkCameraNotNull() && cameraOper.isPreview()) {
                    cameraOper.autoFocus();
                }
            }
        });
        flashLightCheck
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        if (isChecked)
                            cameraOper.openFlashLight();
                        else
                            cameraOper.closeFlashLight();
                    }
                });
    }

    // 点击拍照按钮
    public void takePhotoClick(View v) {
        canClose = true;
        if (isRetakeBtn) {
            photo = null;
            changeSaveBtnState(false);
            cameraOper.startPreview();
            if (flashLightCheck.isChecked())
                cameraOper.openFlashLight();
            takeBtn.setText(R.string.take_photo);
            isRetakeBtn = false;
        } else {
            DialogTools.showSpinnerProDialog(this, "正在获取图片...");
            // cameraOper.takePicture(pictureCallBack);
            // takeBtn.setText(R.string.retake_photo);
            // 取消重拍按钮
            takeBtn.setVisibility(View.INVISIBLE);
            isRetakeBtn = true;
            isOver = true;
        }
        takeBtn.setEnabled(false);
        takeBtn.postDelayed(recoverRunner, 150);
    }

    private Runnable recoverRunner = new Runnable() {

        @Override
        public void run() {
            takeBtn.setEnabled(true);
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER && mPhotoView.hasFocus()) {
            takeBtn.performClick();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    // 点击保存
    public void savePhotoClick(View v) {
        backPhoto();
    }

    private void backPhoto() {
        Intent result = new Intent();
        result.putExtra(CAMERA_RESULT, photo);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    // 开始拍照时调用该方法
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        takeBtn.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
        try {
            openCamera();
        } catch (IOException e) {
            KlLoger.error(TAG, "摄像头无法初始化", e);
            errorAndExit();
//                }
        }
//        }, 10);
    }

    private void openCamera() throws IOException {
        cameraOper.initCamera();
        if (cameraOper.checkCameraNotNull()) {
            cameraOper.getCamera().setPreviewDisplay(mSurfaceHolder);
            cameraOper.startPreview();
            cameraOper.autoFocus();
            cameraOper.getCamera().setPreviewCallback(new PreViewCallback());
        } else {
            errorAndExit();
        }
    }

    // 拍照状态改变时
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (cameraOper.cameraIsOpen())
            cameraOper.startPreview();
    }

    // 停止拍照时调用该方法
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraOper.release();
    }

    /**
     * 通过PreViewCallback获取拍照数据
     *
     * @author hb
     */
    public class PreViewCallback implements Camera.PreviewCallback {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            if (!isOver)// 默认直接返回,点击拍照才执行下面数据
                return;

            if (flashLightCheck.isChecked())
                cameraOper.closeFlashLight();

            cameraOper.stopPreview();
            DialogTools.cancelSpinnerProDialog();
            Size size = cameraOper.getCamera().getParameters().getPreviewSize();
            YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21,
                    size.width, size.height, null);
            ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();
            if (!yuvimage.compressToJpeg(
                    new Rect(0, 0, size.width, size.height), 80, outputSteam)) {
                return;
            }
            data = outputSteam.toByteArray();
            photo = PicCompressTools.compressByScale(data, 640, 480);
            if (isSnapshot) {
                backPhoto();
                isOver = false;
            } else {
                changeSaveBtnState(true);
            }

        }
    }

    /**
     * 照相回调
     */
    // private PictureCallback pictureCallBack = new Camera.PictureCallback() {
    //
    // @Override
    // public void onPictureTaken(byte[] data, Camera camera) {
    // if (flashLightCheck.isChecked())
    // cameraOper.closeFlashLight();
    //
    // // cameraOper.stopPreview();
    // DialogTools.cancelSpinnerProDialog(CameraActivity.this);
    // Size size = cameraOper.getCamera().getParameters().getPreviewSize();
    // YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21,
    // size.width, size.height, null);
    // ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();
    // if (!yuvimage.compressToJpeg(
    // new Rect(0, 0, size.width, size.height), 100, outputSteam)) {
    // return;
    // }
    // data = outputSteam.toByteArray();
    // // photo = PicCompressTools.compressByScale(data, 320f, 240f);
    // Bitmap b = BitmapFactory.decodeByteArray(data, 0, data.length);
    // Matrix m = new Matrix();
    // m.setRotate(90); // 逆时针旋转90度
    // b = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m,
    // true);
    // photo = PicCompressTools.comp(b, false);
    // if (isSnapshot) {
    // backPhoto();
    // } else {
    // changeSaveBtnState(true);
    // }
    // }
    //
    // };
    @Override
    public void onBackPressed() {
        if (!canClose) {
            return;
        }
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public void finish() {
        cameraOper.release();
        super.finish();
    }

    // 出现异常，强制结束
    private void errorAndExit() {
        ToastTools.showLazzToast("摄像头出现异常");
        finish();
        System.exit(1);
    }

    // 由于520机器在预览过程中待机后返回会卡机的问题，故在这边加入当待机时停止预览，待机后开启预览的机制
    @Override
    protected void onResume() {
        super.onResume();
        if (!cameraOper.isPreview())
            cameraOper.startPreview();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cameraOper.isPreview())
            cameraOper.stopPreview();

    }
}
