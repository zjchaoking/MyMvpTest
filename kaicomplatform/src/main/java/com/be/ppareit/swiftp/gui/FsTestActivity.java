/**
 * 
 */
package com.be.ppareit.swiftp.gui;


import com.kaicom.fw.R;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * <h3>此activity为demo，也可用于项目中</h3>
 * 说明：该activity启动时调用onStart即可开启ftp服务，返回即停止服务。<br>
 * 使用该activity需要在androidManifest文件中注册
 * @author wxf
 *
 */
public class FsTestActivity extends AbsFtpServerActivity{

	Button startBtn, stopBtn;
	TextView text1, notice2;
	ProgressBar progressBar;
	
	/**
	 * 
	 */
	private void initListener() {
		startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startServer();
			}
		});
		
		stopBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopServer();
			}
		});
		
	}

	void initWidget(){
		startBtn = (Button)findViewById(R.id.start);
		stopBtn = (Button)findViewById(R.id.stop);
		
		text1 = (TextView)findViewById(R.id.textInfo1);
		notice2 = (TextView)findViewById(R.id.notice2);
		
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
	}

	/* (non-Javadoc)
	 * @see be.ppareit.swiftp.gui.AbsFtpServerActivity#init()
	 */
	@Override
	protected void init() {
		initWidget();
		initListener();
	}

	/* (non-Javadoc)
	 * @see be.ppareit.swiftp.gui.AbsFtpServerActivity#ftpServerResult(java.lang.String)
	 */
	@Override
	protected void ftpServerResult(String reslut, boolean res) {
		text1.setText(reslut);
		if(res){
			notice2.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	/* (non-Javadoc)
	 * @see be.ppareit.swiftp.gui.AbsFtpServerActivity#getResid()
	 */
	@Override
	protected int getResid() {
		return R.layout.test_ftp_main;
	}

	/* (non-Javadoc)
	 * @see be.ppareit.swiftp.gui.AbsFtpServerActivity#isNotification()
	 */
	@Override
	protected boolean isNotification() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	

}
