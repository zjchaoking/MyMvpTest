package com.kaicom.api.log.upload;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import com.kaicom.api.log.CrashHandler;
import com.kaicom.api.log.KlLoger;
import com.kaicom.api.log.upload.FtpClientCpl.FtpResult;
import com.kaicom.api.log.upload.FtpUploadRunner.FtpUploadListener;
import com.kaicom.api.preference.PlatformPreference;
import com.kaicom.api.util.FileUtil;
import com.kaicom.api.util.NetUtil;
import com.kaicom.api.view.dialog.DialogTools;
import com.kaicom.api.view.toast.ToastTools;

import java.util.Collections;
import java.util.List;

/**
 * 日志上传
 * @author scj
 *
 */
public class LogUploader {
	
	private String serverPath;
	private Handler handler = new Handler(Looper.getMainLooper());
	
	public LogUploader(String serverPath) {
		this.serverPath = serverPath;
	}

	/**
	 * 自动上传crash文件, 上传到服务器的名称为：customer_siteId_jobId_crash_date.zip
	 * @param customer 客户名
	 * @param siteId 网点编号
	 * @param jobId 工号
	 */
	public void autoUploadCrash(String customer, String siteId, String jobId) {
		if (!NetUtil.isNetworkAvailable()) {
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					ToastTools.showToast("上传异常日志失败，当前网络不可用");
				}
			});
			return;
		}
		boolean hasCrash = PlatformPreference.getInstance().hasCrash();
		if (hasCrash) {
			List<String> fileList = getFileList(CrashHandler.getGlobalpath());
			if (fileList.size() == 0) {
				ToastTools.showToast("没有需要上传日志文件");
				return;
			}
			String localFile = CrashHandler.getGlobalpath() + fileList.get(0);

			StringBuilder serverName = new StringBuilder();
			serverName.append(customer).append("_");
			serverName.append(siteId).append("_");
			serverName.append(jobId).append("_");
			serverName.append(
					FileUtil.getFileNameWithoutExtension(fileList.get(0)))
					.append(".zip");

			FtpUploadRunner runner = new FtpUploadRunner(serverName.toString(),
					localFile, new FtpUploadListener() {

						@Override
						public void onSuccess() {
							ToastTools.showToast("异常日志上传成功");
							PlatformPreference.getInstance().setCrash(false);
						}

						@Override
						public void onFail(FtpResult result) {
							if (result != null) {
								KlLoger.error("异常日志自动上传失败", result.name());
							}
							ToastTools.showToast("异常日志上传失败");
						}
					});
			runner.setServerPath(serverPath);
			new Thread(runner).start();
		}

	}

	private List<String> getFileList(String dir) {
		List<String> fileList = FileUtil.getFileNameList(dir, "log");
		Collections.sort(fileList);
		Collections.reverse(fileList);
		return fileList;
	}

	/**
	 * 上传日志文件
	 * 
	 * @param serverName
	 *            服务器上文件名称：客户名_网点编号_工号_日期.zip
	 * @param localFile
	 *            本地文件路径-绝对路径
	 */
	public void uploadLog(final Activity act, String serverName,
			String localFile) {
		DialogTools.showSpinnerProDialog(act, "正在上传日志文件...");
		FtpUploadRunner runner = new FtpUploadRunner(serverName, localFile,
				new FtpUploadListener() {

					@Override
					public void onSuccess() {
						DialogTools.cancelSpinnerProDialog();
						ToastTools.showLazzToast("日志上传成功");
					}

					@Override
					public void onFail(FtpResult result) {
						DialogTools.cancelSpinnerProDialog();
						ToastTools.showLazzToast("日志上传失败 " + result.name());
					}
				});

		runner.setServerPath(serverPath);
		new Thread(runner).start();
	}

	
}
