package com.zbar.lib.decode;

import android.os.Handler;
import android.os.Message;
import static com.zbar.lib.decode.DecodeWhatConstant.*;
import com.zbar.lib.CaptureActivity;
import com.zbar.lib.camera.CameraManager;

public final class CaptureActivityHandler extends Handler {

	DecodeThread decodeThread = null;
	CaptureActivity activity = null;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(CaptureActivity activity) {
		this.activity = activity;
		decodeThread = new DecodeThread(activity);
		decodeThread.start();
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		switch (message.what) {
		case AUTO_FOCUS:
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, AUTO_FOCUS);
			}
			break;
		case RESTART_PREVIEW:
			restartPreviewAndDecode();
			break;
		case DECODE_SUCCEEDED:
			state = State.SUCCESS;
			activity.handleDecode((String) message.obj);
			break;
		case DECODE_FAILED:
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					DECODE);
			break;
		default:
			break;
		}

	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		removeMessages(DECODE_SUCCEEDED);
		removeMessages(DECODE_FAILED);
		removeMessages(DECODE);
		removeMessages(AUTO_FOCUS);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					DECODE);
			CameraManager.get().requestAutoFocus(this, AUTO_FOCUS);
		}
	}

}
