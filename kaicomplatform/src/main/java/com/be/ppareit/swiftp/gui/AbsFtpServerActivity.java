/**
 * 
 */
package com.be.ppareit.swiftp.gui;

import java.net.InetAddress;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.be.ppareit.swiftp.FsService;
import com.be.ppareit.swiftp.FsService.FsServiceBinder;
import com.be.ppareit.swiftp.FsSettings;
import com.be.ppareit.swiftp.RequestStartStopReceiver;

/**
 * <h4>实现FTP服务器功能，共享SD根目录卡</h4>
 * <br>请在AndroidManifest.xml中注册服务<br>
 * "service android:name="be.ppareit.swiftp.FsService""<br>
 * 若使用状态栏通知，使isNotification()该方法返回true
 * @author wxf
 *
 */
public abstract class AbsFtpServerActivity extends Activity {
	
	FsService mFsService;
	BroadcastReceiver mFsActionsReceiver;
	RequestStartStopReceiver mReqestStartStopReceiver;
	FsNotification mFsNotificationReceiver;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getResid());
		ftpServerInit();
		init();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		startServer();
		super.onStart();
	}
	
	/**
	 * 初始化调用
	 */
	protected abstract void init();

	/**
	 * 是否使用通知栏消息提示
	 * @return
	 */
	protected abstract boolean isNotification();
	
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			FsServiceBinder binder = (FsServiceBinder) service;
			mFsService = binder.getService();
		}
	};

	/**
	 * ftp服务初始化
	 */
	private void ftpServerInit() {
		
		
		initStartStopReceiver();
	    
	    initFsServiceReceiver();
	    if(isNotification()){
	    	initFsNotificationReceiver();
	    }
//	    bindService(new Intent().setClass(this, FsService.class), conn, Context.BIND_AUTO_CREATE);
	}

	/**
	 * 初始化消息通知栏
	 */
	private void initFsNotificationReceiver() {
		mFsNotificationReceiver = new  FsNotification();
	    IntentFilter filter = new IntentFilter();
        filter.addAction(FsService.ACTION_STARTED);
        filter.addAction(FsService.ACTION_STOPPED);
        registerReceiver(mFsNotificationReceiver, filter);
	}

	/**
	 * 初始化服务广播
	 */
	private void initFsServiceReceiver() {
		mFsActionsReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            // action will be ACTION_STARTED or ACTION_STOPPED
	        	updateRunningState();
	            
	            if (intent.getAction().equals(FsService.ACTION_FAILEDTOSTART)) {
	            	ftpServerResult("FTP server 启动失败，您尚未连接到 Wi-Fi 或以太网络？", false);
	            }
	        }
	    };
	    
		IntentFilter filter = new IntentFilter();
        filter.addAction(FsService.ACTION_STARTED);
        filter.addAction(FsService.ACTION_STOPPED);
        filter.addAction(FsService.ACTION_FAILEDTOSTART);
        registerReceiver(mFsActionsReceiver, filter);
	}

	/**
	 * 初始化控制广播
	 */
	private void initStartStopReceiver() {
		mReqestStartStopReceiver = new RequestStartStopReceiver();
		IntentFilter startStropFilter = new IntentFilter();
		startStropFilter.addAction(FsService.ACTION_START_FTPSERVER);
		startStropFilter.addAction(FsService.ACTION_STOP_FTPSERVER);
	        
		registerReceiver(mReqestStartStopReceiver, startStropFilter);
	}
	
	/**
	 * 启动服务
	 */
	public void startServer(){
		sendBroadcast(new Intent(FsService.ACTION_START_FTPSERVER));
	}
	
	/**
	 * 关闭服务
	 */
	public void stopServer(){
		sendBroadcast(new Intent(FsService.ACTION_STOP_FTPSERVER));
	}
	
	/**
	 * ftp server 运行状态
	 */
	private void updateRunningState() {
		if (FsService.isRunning() == true) {
			InetAddress address = FsService.getLocalInetAddress();
            if (address == null) {
            	ftpServerResult("服务运行异常，请检查网络", false);
            	return ;
            }
		
			String iptext = "ftp://" + address.getHostAddress() + ":"
                    + FsSettings.getPortNumber();
			
			ftpServerResult("请在同一局域网电脑上输入："+ iptext, true);
		}else{
			ftpServerResult("服务关闭", false);
		}
	}
	
	/**
	 * ftp服务返回信息
	 * @param reslut
	 */
	protected abstract void ftpServerResult(String reslut, boolean result);

	/**
	 * 获得界面的view
	 * @return
	 */
	protected abstract int getResid();
	
	/* (non-Javadoc)
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		stopServer();
//		unbindService(conn);
		super.finish();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		if(null != mFsNotificationReceiver){
			unregisterReceiver(mFsNotificationReceiver);
		}
		if(null != mReqestStartStopReceiver){
			unregisterReceiver(mReqestStartStopReceiver);
		}
		if(null != mFsActionsReceiver){
			unregisterReceiver(mFsActionsReceiver);
		}
		super.onDestroy();
	}

}
