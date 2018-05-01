package com.kaicom.api.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

/**
 * ProgressDialog工具类
 */
public final class DialogTools {

	private static ProgressDialog dialog = null;
	private static ProgressDialog downloadDialog = null;

	private DialogTools() {
	}

	/**
	 * 显示STYLE_SPINNER样式的ProgressDialog
	 *
	 * @param act
	 * @param resId
	 */
	public static void showSpinnerProDialog(Activity act, int resId) {
		showSpinnerProDialog(act, act.getString(resId));
	}

	/**
	 * 显示STYLE_SPINNER样式的ProgressDialog
	 *
	 * @param act
	 * @param message
	 */
	public static synchronized void showSpinnerProDialog(Activity act,
			String message) {
		try {
			if (dialog == null) {
				Activity showActivity = (act.getParent() != null ? act
						.getParent() : act);
				dialog = new ProgressDialog(showActivity);
				dialog.setCancelable(false);
				dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			}
			dialog.setMessage(message);
			dialog.show();
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
	/**
	 * 显示下载进度的ProgressDialog
	 *
	 * @param message
	 */
	public static synchronized void showDownloadSpinnerProDialog(Context context,String message,int values) {
		try {
			if (downloadDialog == null) {
                downloadDialog = new ProgressDialog(context);
                downloadDialog.setCancelable(false);
                downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			}
            downloadDialog.setProgress(values);
            downloadDialog.setMessage(message);
            downloadDialog.show();
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
    /**
     * 更新下载ProgressDialog的进度
     *
     */
    public static synchronized void uploadProgress(int progress) {
        if (downloadDialog != null && downloadDialog.isShowing()) {
            downloadDialog.setProgress(progress);
        }
    }

    /**
     * 取消下载ProgressDialog的显示
     *
     */
    public static synchronized void cancelDownloadSpinnerProDialog() {
        if (downloadDialog != null && downloadDialog.isShowing()) {
            downloadDialog.dismiss();
            downloadDialog = null;
        }
    }
	/**
	 * 取消ProgressDialog的显示
	 *
	 */
	public static synchronized void cancelSpinnerProDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			dialog = null;
		}
	}

	/**
	 * 显示对话框.
	 *
	 * @param context
	 *            context
	 * @param msg
	 *            msg
	 */
	public static void showDialog(Context context, String msg) {

		new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}

	/**
	 * 显示对话框,自定义事件.
	 *
	 * @param context
	 *            上下文
	 * @param msg
	 *            显示消息
	 * @param successlistener
	 *            事件自行处理成功
	 */
	public static void showDialog(Context context, String msg,
			OnClickListener successlistener) {

		new AlertDialog.Builder(context).setTitle("温馨提示").setMessage(msg)
				.setPositiveButton("确定", successlistener)
				.setNeutralButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

}
