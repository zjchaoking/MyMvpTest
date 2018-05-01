package com.kaicom.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

/**
 * <h3>资源工具类</h3>
 * <br>
 * 读取raw和assets中的文本
 * @author scj
 *
 */
public final class ResourceUtil {

    private ResourceUtil() {
        throw new RuntimeException("…（⊙＿⊙；）…");
    }

    /**
     * 从assets中读取文本
     * @param context
     * @param fileName
     * @return
     */
    public static String geFileFromAssets(Context context, String fileName) {
        if (context == null || StringUtil.isEmpty(fileName)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("");
        InputStream in = null;
        try {
            in = context.getResources().getAssets().open(fileName);
            return readTextFromStream(sb, in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从raw中读取文本
     * @param context
     * @param resId
     * @return
     */
    public static String geFileFromRaw(Context context, int resId) {
        if (context == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        try {
            in = context.getResources().openRawResource(resId);
            return readTextFromStream(sb, in);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 从输入流中读取文本
     * @param sb 
     * @param in
     * @return String字符串
     * @throws IOException
     */
    private static String readTextFromStream(StringBuilder sb, InputStream in)
            throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
