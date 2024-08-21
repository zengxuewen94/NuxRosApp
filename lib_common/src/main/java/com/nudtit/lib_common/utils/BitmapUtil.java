package com.nudtit.lib_common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author :  zengxuewen
 * @date :  2021/7/15
 * @desc :  Bitmap 工具类
 */
public class BitmapUtil {

    /**
     * 转换bitmap
     *
     * @param srcBitmap 源bitmap
     * @return 转换后的bitmap
     */
    public static Bitmap convertBitmap(Bitmap srcBitmap, int degrees) {
        if (srcBitmap == null) {
            return null;
        }
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(-1, 1);
        // 旋转角度
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * @param bitmap
     * @param path
     * @return
     * @desc 保存图片
     * @date 2020/09/11
     * @author zengxuewen
     */
    public static boolean saveBitmap(Bitmap bitmap, String path) {
        FileOutputStream fos = null;
        try {
            File file = new File(path);
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            fos = new FileOutputStream(file);
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return false;
    }

    /**
     * YUV图像格式转Bitmap
     *
     * @param data
     * @param width
     * @param height
     * @return
     */
    public static Bitmap YuvImageToBitmap(byte[] data, int width, int height) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] rawImage;
        Bitmap retBitmap;
        YuvImage yuvimage = new YuvImage(
                data,
                ImageFormat.NV21,
                width,
                height,
                null);
        // 80--JPG图片的质量[0-100],100最高
        yuvimage.compressToJpeg(new Rect(0, 0, width, height), 100, os);
        rawImage = os.toByteArray();
        //将rawImage转换成bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        retBitmap = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length, options);
        return retBitmap;
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {
        //声明将要创建的bitmap
        Bitmap bitmap = null;
        //获取图片宽度
        int width = 80;
        //获取图片高度
        int height = 80;
        //图片位深，PixelFormat.OPAQUE代表没有透明度，RGB_565就是没有透明度的位深，否则就用ARGB_8888。详细见下面图片编码知识。
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        //创建一个空的Bitmap
        bitmap = Bitmap.createBitmap(width, height, config);
        //在bitmap上创建一个画布
        Canvas canvas = new Canvas(bitmap);
        //设置画布的范围
        drawable.setBounds(0, 0, width, height);
        //将drawable绘制在canvas上
        drawable.draw(canvas);
        return bitmap;
    }

}
