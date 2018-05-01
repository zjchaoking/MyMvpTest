package com.kaicom.api.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 用于通过HTTP请求返回的数据JSON格式处理类.
 * 
 * @author Administrator
 * 
 */
public class ResponseJson {
/**
 * jsonString.
 */
    private String jsonString;
    /**
     * jsonObject.
     */
    private JSONObject jsonObject;
/**
 * eventId.
 */
    private Integer eventId;
    /**
     * event.
     */
    private String event = "";
/**
 * length.
 */
    private int length = 0;
    /**
     * msg.
     */
    private String msg;
    /**
     * data.
     */
    private Object data;
   /**
    * 数据结果集为普通对象，使用Map.
    */
    private Map<String, String> resultData;
    /**
     *  数据结果集为数组对象，使用Map数组.
     */
    private List<Map<String, String>> resultArrayData = new ArrayList<Map<String, String>>();
/**
 * dataString.
 */
    private String dataString;
/**
 * returnCode.
 */
    private String returnCode;
  /**
   * success.
   */
    private boolean success = false;
/**
 * isArray.
 */
    private boolean isArray = false;
/**
 * JSON_MSG.
 */
    public final static String JSON_MSG = "msg";
    /**
     * JSON_DATA.
     */
    public final static String JSON_DATA = "data";
    /**
     * JSON_DATA_STRING.
     */
    public final static String JSON_DATA_STRING = "dataString";
    /**
     * returnCode.
     */
    public final static String JSON_RETURN_CODE = "returnCode";
    /**
     * success.
     */
    public final static String JSON_SUCCESS = "success";
    /**
     * eventId.
     */
    public final static String JSON_EVENT_ID = "eventId";
/**
 * ResponseJson.
 * @param jsonString jsonString
 */
    public ResponseJson(String jsonString) {
        this.jsonString = jsonString;
        try {
            this.jsonObject = new JSONObject(jsonString);
            this.dataString = this.jsonObject.getString(JSON_DATA);
            this.success = ("true".equals(this.jsonObject
                    .getString(JSON_SUCCESS)));
            this.msg = this.jsonObject.getString(JSON_MSG);
            this.returnCode = this.jsonObject.getString(JSON_RETURN_CODE);
            this.event = this.jsonObject.getString(JSON_EVENT_ID);
            this.eventId = "".equals(event) ? null : Integer.parseInt(event);
            checkArray(dataString);
            if (isArray) {
                JSONArray ja = new JSONArray(dataString);
                this.data = ja;
                JSONObject json;
                for (int i = 0, n = ja.length(); i < n; i++) {
                    json = ja.getJSONObject(i);
                    resultArrayData.add(convetMap(json));
                }
            } else {
                this.data = new JSONObject(dataString);
                resultData = convetMap((JSONObject) data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
/**
 * ResponseJson.
 * @param jsonObject jsonObject
 */
    public ResponseJson(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.jsonString = jsonObject.toString();
        try {
            this.dataString = jsonObject.getString(JSON_DATA);
            this.success = ("true".equals(this.jsonObject
                    .getString(JSON_SUCCESS)));
            this.msg = this.jsonObject.getString(JSON_MSG);
            this.returnCode = this.jsonObject.getString(JSON_RETURN_CODE);
            this.event = this.jsonObject.getString(JSON_EVENT_ID);
            this.eventId = "".equals(event) ? null : Integer.parseInt(event);
            checkArray(dataString);
            if (isArray) {
                JSONArray ja = new JSONArray(dataString);
                this.data = ja;
                JSONObject json;
                for (int i = 0, n = ja.length(); i < n; i++) {
                    json = ja.getJSONObject(i);
                    resultArrayData.add(convetMap(json));
                }
            } else {
                this.data = jsonObject.get(JSON_DATA);
                resultData = convetMap((JSONObject) data);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
/**
 * convetMap.
 * @param jsonObject jsonObject
 * @return Map
 * @throws JSONException JSONException
 */

    public static Map<String, String> convetMap(JSONObject jsonObject)
            throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        String key = null;
        for (Iterator iterator = jsonObject.keys(); iterator.hasNext();) {
            key = iterator.next().toString();
            map.put(key, jsonObject.getString(key));
        }
        return map;
    }

    /**
	 * checkArray.
	 * @param  dataString dataString
	 */
    private void checkArray(String dataString) {
        if ('[' == dataString.charAt(0)) {
            isArray = true;
        } else {
            isArray = false;
        }
    }

    /** 
     * 单个对象获得方法 .
     * @param key key
     * @return String 
     * */
    public String getDataValue(String key) {
        if (isArray) {
            return getArrayDataValue(key, 0);
        } else {
            return resultData.get(key).toString();
        }
    }

    /**
     * 数组对象获取方法 .
     * @param key key
     * @param index index
     * @return String
     */
    public String getArrayDataValue(String key, int index) {
        if (isArray) {
            return this.resultArrayData.get(index).get(key);
        } else {
            return resultData.get(key).toString();
        }
    }

    /**
     * 获取对象返回是否为数组.
     * @return boolean
     */
    public boolean isArrayData() {
        return isArray;
    }
/**
 * getJsonString.
 * @return String
 */
    public String getJsonString() {
        return jsonString;
    }
/**
 * getJsonObject.
 * @return JSONObject
 */
    public JSONObject getJsonObject() {
        return jsonObject;
    }
/**
 * setJsonObject.
 * @param jsonObject jsonObject
 */
    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
/**
 * getEventId.
 * @return Integer
 */
    public Integer getEventId() {
        return eventId;
    }
/**
 * getMsg.
 * @return String
 */
    public String getMsg() {
        return msg;
    }
/**
 * getData.
 * @return Object
 */
    public Object getData() {
        return data;
    }
/**
 * getDataString.
 * @return String
 */
    public String getDataString() {
        return dataString;
    }
/**
 * getReturnCode.
 * @return String
 */
    public String getReturnCode() {
        return returnCode;
    }
/**
 * isSuccess.
 * @return boolean
 */
    public boolean isSuccess() {
        return success;
    }
/**
 * getResultData.
 * @return Map
 */
    public Map<String, String> getResultData() {
        return resultData;
    }
/**
 * getResultArrayData.
 * @return List
 */
    public List<Map<String, String>> getResultArrayData() {
        return resultArrayData;
    }

   /**
    * 取得返回是数据对象的长度，不是的话为0.
    * @return int
    */
    public int size() {
        if (isArray) {
            if (this.length == 0) {
                this.length = resultArrayData.size();
            }
            return this.length;
        } else {
            return 0;
        }

    }
/**
 * test.
 */
    public static void test() {
        try {
            String jsonStr = "{\"eventId\":\"\","
                    + "\n\r\"data\":{\"name\": \"wangxn\"},"
                    + "\n\r\"msg\":\"\"," + "\n\r\"returnCode\":\"\", "
                    + "\n\r\"success\":true" + "\n\r}";
            String jsonList = "{\"eventId\":\"\","
                    + "\n\r\"data\": [{\"name\": \"wangxn\"},{\"name\": \"yangxm\"}],"
                    + "\n\r\"msg\":\"\"," + "\n\r\"returnCode\":\"\", "
                    + "\n\r\"success\":true" + "\n\r}";

            JSONObject jsonObject = null;
            ResponseJson res = new ResponseJson(jsonStr);
            System.out.println(res.getDataValue("name"));
            System.out.println(res.isArray);
            System.out.println(res.getDataString());
            System.out.println(res.success);
            jsonObject = res.getJsonObject();

            res = new ResponseJson(jsonObject);
            System.out.println(res.getDataValue("name"));
            System.out.println(res.isArray);
            System.out.println(res.getDataString());
            System.out.println(res.success);

            res = new ResponseJson(jsonList);
            System.out.println(res.getArrayDataValue("name", 0));
            System.out.println(res.getArrayDataValue("name", 1));
            System.out.println(res.isArray);
            System.out.println(res.getDataString());
            System.out.println(res.success);
            jsonObject = res.getJsonObject();

            res = new ResponseJson(jsonObject);
            System.out.println(res.getDataValue("name"));
            System.out.println(res.isArray);
            System.out.println(res.getDataString());
            System.out.println(res.success);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
/**
 * main.
 * @param args args
 * @throws Exception Exception
 */
    public static void main(String[] args) throws Exception {
        String jsonStr = "{\"eventId\":\"\","
                + "\n\r\"data\":{\"name\": \"wangxn\"}," + "\n\r\"msg\":\"\","
                + "\n\r\"returnCode\":\"\", " + "\n\r\"success\":true"
                + "\n\r}";
        Map map = new HashMap();
        map.put("eventId", "");
        map.put("msg", "");
        map.put("returnCode", "");
        map.put("success", Boolean.TRUE);
        Map data1 = new HashMap();
        data1.put("name", "wxn");
        Map data2 = new HashMap();
        data2.put("name", "yxm");
        List list = new ArrayList();
        list.add(data1);
        list.add(data2);
        map.put("data", list);

        ResponseJson res = new ResponseJson(jsonStr);
        System.out.println(res.getDataValue("name"));
        System.out.println(res.isArray);
        System.out.println(res.getDataString());
        System.out.println(res.success);
        JSONObject jsonObject = new JSONObject(jsonStr);
        ResponseJson ret = new ResponseJson(jsonObject);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
