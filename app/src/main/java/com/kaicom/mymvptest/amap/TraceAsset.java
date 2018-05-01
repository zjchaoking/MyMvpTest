package com.kaicom.mymvptest.amap;

import com.amap.api.maps.model.LatLng;
import com.kaicom.api.util.DateUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TraceAsset {
	public static List<LatLng> parseLocationsData(String filePath) {
		List<LatLng> locLists = new ArrayList<LatLng>();
		InputStream input = null;
		InputStreamReader inputReader = null;
		BufferedReader bufReader = null;
		try {
			input = new FileInputStream(new File(filePath));
			inputReader = new InputStreamReader(input);
			bufReader = new BufferedReader(inputReader);
			String line = "";
            boolean isRead =false;
			while ((line = bufReader.readLine()) != null) {
				String[] strArray = null;
				strArray = line.split(",");
                if(isRead = false){
                    isRead = true;
                    System.out.println("读取经纬度："+line);
                }
				LatLng newpoint = new LatLng(Double.parseDouble(strArray[1]), Double.parseDouble(strArray[2]));
				if (locLists.size()==0 || newpoint.toString()!= locLists.get(locLists.size()-1).toString()) {
					locLists.add(newpoint);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufReader != null) {
					bufReader.close();
					bufReader = null;
				}
				if (inputReader != null) {
					inputReader.close();
					inputReader = null;
				}
				if (input != null) {
					input.close();
					input = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return locLists;
	}

    /**
     * 保存CVS文件
     * @param latLng
     * @param filePath
     */
	public static void saveLocationsData(LatLng latLng, String filePath) {
        FileOutputStream fos =null;
        OutputStreamWriter osw =null;
        BufferedWriter bw =null;
		try {
            fos = new FileOutputStream(new File(filePath),true);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.append(DateUtil.getYearMonthDayTime());
            bw.append(",");
            bw.append(String.valueOf(latLng.latitude));
            bw.append(",");
            bw.append(String.valueOf(latLng.longitude));
            bw.append("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
                    bw.close();
                    bw = null;
				}
				if (fos != null) {
                    fos.close();
                    fos = null;
				}
				if (osw != null) {
                    osw.close();
                    osw = null;
				}
				if (osw != null) {
                    osw.close();
                    osw = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
