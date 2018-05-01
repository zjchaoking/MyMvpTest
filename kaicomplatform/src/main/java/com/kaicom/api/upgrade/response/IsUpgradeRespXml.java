package com.kaicom.api.upgrade.response;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.Text;
import org.simpleframework.xml.core.Persister;

import com.kaicom.api.upgrade.WebService;

/**
 * 
 * <h3>解析是否升级协议返回数据</h3>
 * 
 * <p>
 * <br>
 * content 该内容指向协议中的内容
 * 
 * @author wxf
 */
public class IsUpgradeRespXml {
	
	@Namespace(reference="www.kaicom.cn")
	
	@Text(required=false)
	private String content = "";

	
	public static IsUpgradeRespXml unmarshaller(String content) throws Exception
	{
		Serializer serializer = new Persister();
		
		try {
			InputStream stream = new ByteArrayInputStream(content.getBytes(WebService.ENCODING));
			return serializer.read(IsUpgradeRespXml.class, stream);
		} catch (Exception e) {
			throw new Exception("数据协议异常");
		}
	}


	public String getContent() {
		return content;
	}
	
}
