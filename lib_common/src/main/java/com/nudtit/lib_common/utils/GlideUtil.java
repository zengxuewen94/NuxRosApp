package com.nudtit.lib_common.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.nudtit.lib_common.manager.ThreadPoolManager;

import java.io.File;

/**
 * @author :  zengxuewen
 * @date :  2021/6/30
 * @desc :  Glide加载图片视频工具类
 */
public class GlideUtil {

    /**
     * 限制Glide内存
     */
    public static void setGlideBuilder() {
        new GlideBuilder().setMemoryCache(new LruResourceCache(40 * 1024 * 1024)).setBitmapPool(new LruBitmapPool(3));
    }

    /**
     * 加载普通图片（.jpg/.png）
     *
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public static void loadImg(Context context, ImageView imageView, String imgUrl) {
        Glide.with(context).load(imgUrl)
//                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                // 避免刷新闪烁的问题
                .skipMemoryCache(false)
                .into(imageView);
    }

    /**
     * 加载圆角图片
     * @param context
     * @param imageView
     * @param imgUrl
     */
    public static void loadRadiusImg(Context context, ImageView imageView, String imgUrl){
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载普通图片（.jpg/.png）
     *
     * @param context
     * @param imageView
     * @param uri
     */
    public static void loadImg(Context context, ImageView imageView, Uri uri) {
        Glide.with(context).load(uri)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                // 避免刷新闪烁的问题
                .skipMemoryCache(false)
                .into(imageView);
    }
    /**
     * 加载GIF 图片
     *
     * @param context
     * @param imageView
     * @param resId
     */
    public static void loadGif(Context context, ImageView imageView, int resId) {
        Glide.with(context).asGif().diskCacheStrategy(DiskCacheStrategy.RESOURCE).load(resId).into(imageView);
    }


    /**
     * 加载缩略图
     *
     * @param context
     * @param filePath
     * @param imageView
     */
    public static void showVideoThumbnail(Context context, ImageView imageView, String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                Glide.with(context).load(Uri.fromFile(new File(filePath))).into(imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 停止加载
     *
     * @param context
     */
    public static void pauseRequests(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 继续加载
     *
     * @param context
     */
    public static void resumeRequests(Context context) {
        Glide.with(context).resumeRequests();
    }


    /**
     * 清理内存
     *
     * @param context
     */
    public static void clearMemory(Context context) {

        Glide.get(context).clearMemory();
        //及时调用GC清理内存
        Runtime.getRuntime().gc();


    }


}
