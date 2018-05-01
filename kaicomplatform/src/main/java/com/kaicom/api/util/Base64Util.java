package com.kaicom.api.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Base64工具类
 * 
 * @author scj
 *
 */
public final class Base64Util {
    
    private Base64Util() {
        throw new RuntimeException("╮(╯▽╰)╭");
    }

    /**
     * 将图片转换成Base64
     * @param imgFilePath 文件目录
     * @return 如果文件不存在则返回""
     * @throws IOException 
     */
    public static String base64FromImage(String imgFilePath) throws IOException {
        if (imgFilePath == null || imgFilePath.length() <= 0)
            return "";
        File file = new File(imgFilePath);
        return base64FromImage(file);
    }
    
    /**
     * 将图片转换成Base64
     * @param imgFile
     * @return 如果文件不存在则返回""
     * @throws IOException 
     */
    public static String base64FromImage(File imgFile) throws IOException {
        if (!imgFile.exists())
            return "";
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] bytes = null;
        InputStream in = null;
        try {
            in = new FileInputStream(imgFile);
            bytes = new byte[in.available()];
            in.read(bytes);
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteToStr(bytes);
    }

    /**
     * 将bitmap转成base64字符串 
     * @param bitmap JPEG格式
     * @return
     */
    public static String bitmapToStr(Bitmap bitmap) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        return byteToStr(bytes);
    }

    /**
     * 将图片字节数组转成base64字符串 
     * @param bytes
     * @return
     */
    public static String byteToStr(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 将Base64字符串转成Bitmap
     * @param str64
     * @return
     */
    public static Bitmap strToBitmap(String str64) {
        Bitmap bitmap = null;
        byte[] bitmapArray;
        // 将字符串转换成Bitmap类型
        bitmapArray = Base64.decode(str64, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                bitmapArray.length);
        return bitmap;
    }

}
