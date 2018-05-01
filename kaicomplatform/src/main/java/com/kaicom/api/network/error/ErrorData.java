/**
 * <p>Copyright: Copyright (c) 2013-11-26</p>
 * <p>Company: 恒生电子股份有限公司</p>
 * <p>Project: 投资赢家移动理财终端</p>
 * 
 * @author shadeien
 * @version 2013-11-26，上午9:48:56
 */
package com.kaicom.api.network.error;

import java.util.HashMap;
/**
 * ErrorData.
 * @author zhouhy04159
 *
 */
public class ErrorData {
    /**
     * url.
     */
    private String url;
    /**
     * msg.
     */
    private String msg;
/**
 * ErrorData.
 * @param responeData responeData
 */
    public ErrorData(HashMap<String, Object> responeData) {
        url = (String) responeData.get("url");
        msg = (String) responeData.get("msg");
    }
/**
 * ErrorData.
 */
    public ErrorData() {

    }
/**
 * 
 * @return String
 */
    public String getUrl() {
        return url;
    }
/**
 * 
 * @param url url
 */
    public void setUrl(String url) {
        this.url = url;
    }
/**
 * 
 * @return String
 */
    public String getMsg() {
        return msg;
    }
/**
 * 
 * @param msg msg
 */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
