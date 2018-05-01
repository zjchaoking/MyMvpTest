package com.kaicom.api.upgrade;

import static com.kaicom.api.KaicomApplication.app;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaicom.api.upgrade.request.RequestData;
import com.kaicom.api.upgrade.response.IsUpgradeRespXml;
import com.kaicom.api.upgrade.response.IsUpgradeResponse;
import com.kaicom.api.upgrade.response.UpgradeFileInfo;

/**
 * <h3>apk升级网络服务类</h3>
 * 
 * <p>
 * 主要封装了升级网络服务。 <br>
 * 
 * @author wxf
 */
public class WebService {

	private static final String TAG = "WebService";

	public static final int TIME_REQ_OUT = 15 * 1000;

	public static final int TIME_READ_OUT = 15 * 1000;

	public final static String ENCODING = "UTF-8";

	private String uriAPI;

	private String prifix;

	public WebService(String addr, int port) throws Exception {
		// TODO Auto-generated constructor stub
		if (isNetworkAvailable()) {
			prifix = "http://" + addr + ":" + port;

			uriAPI = prifix + "/Service.asmx/IsNeedUpdate";
			new URI(uriAPI);
		} else {
			throw new Exception("网络不可用");
		}
	}

	public WebService(UpgradeConfig config) throws Exception {
		// TODO Auto-generated constructor stub
		if (isNetworkAvailable()) {
			prifix = "http://" + config.getAddr() + ":" + config.getPort();

			uriAPI = prifix + "/Service.asmx/IsNeedUpdate";

			new URI(uriAPI);
		} else {
			throw new Exception("网络不可用");
		}

	}

	public IsUpgradeResponse query(RequestData request) throws Exception {

		IsUpgradeResponse resp;

		String responseStr = excutePost(request.getParams());

		if (responseStr.equals("")) {
			throw new Exception("请求失败");
		}

		IsUpgradeRespXml response = IsUpgradeRespXml.unmarshaller(responseStr);

		if (response.getContent().equals("")) {
			throw new Exception("请求异常");
		}

		@SuppressWarnings("unchecked")
		List<IsUpgradeResponse> respList = (List<IsUpgradeResponse>) fromJson(
				response.getContent(),
				new TypeToken<List<IsUpgradeResponse>>() {
				}.getType());

		if (respList.size() != 0) {
			resp = (IsUpgradeResponse) respList.get(0);
		} else {
			throw new Exception("返回数据异常");
		}

		return resp;
	}

	/**
	 * 通过Uri 获得下载文件信息
	 * 
	 * @author wxf
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public UpgradeFileInfo getDownFile(String uri) throws Exception {

		HttpGet get = new HttpGet(uri);
		HttpEntity entity = null;
		try {
			entity = getRequstStatus(get);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("请求失败");
		}

		if (entity != null) {

			try {
				return new UpgradeFileInfo(entity.getContentLength(),
						entity.getContent());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new Exception("请求失败");
			}

		} else {
			return null;
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String excutePost(List parameters) throws Exception {

		HttpPost httpRequest = new HttpPost(uriAPI);

		// 发出HTTP request
		httpRequest.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));

		return requstStatus(httpRequest);
	}

	private String requstStatus(HttpUriRequest httpurirequest) throws Exception {

		String strResult = "";

		HttpClient client = setTimeOutClient();

		// 取得HTTP response

		HttpResponse httpResponse = client.execute(httpurirequest);

		// 若状态码为200 ok
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 取出回应字串
			strResult = EntityUtils.toString(httpResponse.getEntity());

		} else {

		}

		return strResult;
	}

	private HttpEntity getRequstStatus(HttpUriRequest httpurirequest)
			throws Exception {

		HttpEntity entity = null;

		HttpClient client = setTimeOutClient();

		// 取得HTTP response

		HttpResponse httpResponse = client.execute(httpurirequest);

		// 若状态码为200 ok
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 取出回应字串
			entity = httpResponse.getEntity();

		} else {

		}

		return entity;
	}

	/**
	 * 设置超时时间和创建httpClient s
	 * 
	 * @return
	 */
	private HttpClient setTimeOutClient() {
		HttpParams params = new BasicHttpParams(); // 实例化Post参数对象
		HttpConnectionParams.setConnectionTimeout(params, TIME_REQ_OUT);// 设置请求超时
		HttpConnectionParams.setSoTimeout(params, TIME_READ_OUT); // 设置响应超时
		HttpClient client = new DefaultHttpClient(params); // 实例化一个连接对象
		return client;
	}

	/**
	 * 判断当前网络是否可用
	 * 
	 * @return 如果可用返回true，否则返回false
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) app
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	private <T> T fromJson(String json, Type typeOfT) {

		Gson gson = new Gson();

		T result = gson.fromJson(json, typeOfT);

		return result;
	}

}
