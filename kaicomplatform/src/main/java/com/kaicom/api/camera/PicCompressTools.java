package com.kaicom.api.camera;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kaicom.api.util.BitmapUtil;

/**
 * 照片处理工具类
 * 
 * @author scj
 *
 */
public class PicCompressTools {

	private PicCompressTools() {
	}

	/**
	 * 按比例大小压缩图片
	 * 
	 * @param image
	 * @return
	 */
	public static byte[] compressByScale(byte[] data, int h, int w) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
				newOpts);
		newOpts.inJustDecodeBounds = false;
		int width = newOpts.outWidth;
		int height = newOpts.outHeight;
		int scale = 1;
		System.out.println("width:" + width + ", w:" + w);
		System.out.println("height:" + height + ", h:" + h);
		if (width > height && width > w) {
			scale = newOpts.outWidth / w;
		} else if (width < height && height > h) {
			scale = newOpts.outHeight / h;
		}
		if (scale <= 0)
			scale = 1;
		newOpts.inSampleSize = scale;
		System.out.println("scale:" + scale);
		// newOpts.inPreferredConfig = Config.RGB_565;
		bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, newOpts);
		Bitmap bmap = BitmapUtil.rotate(bitmap, 90);
		return compressImage(bmap, 100);
	}

	/**
	 * 按质量压缩图片
	 * 
	 * @param bitmap
	 * @param options
	 * @param byteSize
	 * @return
	 */
	public static byte[] compressImage(Bitmap bitmap, int byteSize) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > byteSize) {
			baos.reset();
			options -= 5;
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
		}
		return baos.toByteArray();
	}

}
