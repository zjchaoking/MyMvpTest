package com.kaicom.api.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Bitmap工具类
 * 
 * @author scj
 *
 */
public final class BitmapUtil {

    private BitmapUtil() {
        throw new RuntimeException("╮(╯▽╰)╭");
    }

    /**
     * 旋转图片
     * @param bitmap Bitmap
     * @param orientationDegree 旋转角度
     * @return 旋转之后的图片
     */
    public static Bitmap rotate(Bitmap bitmap, int orientationDegree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(orientationDegree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    /**
     * 保存bitmap图片
     * @param bitmap
     * @param outFile
     * @return
     * @throws IOException 
     */
    public static boolean save(Bitmap bitmap, String outFile)
            throws IOException {
        if (TextUtils.isEmpty(outFile) || bitmap == null)
            return false;
        byte[] data = bitmap2byte(bitmap);
        return save(data, outFile);
    }

    /**
     * 保存图片字节
     * @param bitmapBytes
     * @param outFile
     * @return
     * @throws IOException
     */
    public static boolean save(byte[] bitmapBytes, String outFile)
            throws IOException {
        FileOutputStream output = null;
        FileChannel channel = null;
        try {
            output = new FileOutputStream(outFile);
            channel = output.getChannel();
            ByteBuffer buffer = ByteBuffer.wrap(bitmapBytes);
            channel.write(buffer);
            return true;
        } finally {
            IOUtil.close(channel);
            IOUtil.close(output);
        }
    }

    /**
     * 将Bitmap转化为字节数组
     * @param bitmap
     * @return byte[]
     * @throws IOException 
     */
    public static byte[] bitmap2byte(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] array = baos.toByteArray();
            baos.flush();
            return array;
        } finally {
            IOUtil.close(baos);
        }
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 60, 80);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    //计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

}
