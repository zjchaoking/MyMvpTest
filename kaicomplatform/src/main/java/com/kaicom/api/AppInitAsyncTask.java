/**
 * 
 */
package com.kaicom.api;

import android.os.AsyncTask;

/**
 * APP异步配置数据初始化进度
 * @author wxf
 *
 */
public class AppInitAsyncTask extends AsyncTask<Void, Integer, Void> {
	InitListener iListener;
	
	/**
	 * 初始化监听
	 */
	public AppInitAsyncTask(InitListener iListener) {
		this.iListener = iListener;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		
		super.onPreExecute();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if(iListener!=null){
			iListener.onFinish();
		}
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onProgressUpdate(Progress[])
	 */
	@Override
	protected void onProgressUpdate(Integer... values) {
		
		super.onProgressUpdate(values);
	}
	
	public interface InitListener{
		void onExecute();
		void onFinish();
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {
		if(iListener!=null){
			iListener.onExecute();
		}
		return null;
	}
	
}
