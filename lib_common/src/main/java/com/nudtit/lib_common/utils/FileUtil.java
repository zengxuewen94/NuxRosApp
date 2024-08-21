package com.nudtit.lib_common.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author :  zengxuewen
 * @date :  2021/6/17
 * @desc :  文件操作工具类
 */
public class FileUtil {


    /**
     * 检查SD卡是否可用
     */
    public static boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡根目录
     */
    public static String getSDRootFile() {
        if (isSdCardAvailable()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 创建文件夹
     * 谷歌推荐使用getExternalFilesDir()方法创建文件夹，我们的应用是定制化，
     * 目前默认使用getExternalStorageDirectory()方法创建"/storage/emulated/0/nudtit"
     *
     * @param context  上下文对象
     * @param fileName 文件夹名称
     * @return 创建的文件夹目录
     */
    public static String createTmpDir(Context context, String fileName) {
        String tmpDir;
        if (isSdCardAvailable()) {
            tmpDir = getFilePath(fileName);
            /**
             tmpDir = context.getExternalFilesDir(fileName).toString();
             if (!makeDir(tmpDir)) {
             tmpDir = context.getExternalFilesDir(fileName).getAbsolutePath();
             }*/

        } else {
            // 如果没有sdk,就是使用机身内存
            tmpDir = context.getFilesDir().getAbsolutePath();
        }

        if (!makeDir(tmpDir)) {
            throw new RuntimeException("文件夹创建失败 :" + tmpDir);
        }
        return tmpDir;
    }


    /**
     * 判断文件夹是否存在
     *
     * @param dirPath
     * @return
     */
    public static boolean makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return file.mkdirs();
        } else {
            return true;
        }
    }


    /**
     * @param path
     * @return 是否是word 文档
     */
    public static boolean isWordFile(String path) {
        try {
            if (!TextUtils.isEmpty(path)) {
                String name = path.substring(path.lastIndexOf("."));
                if (".doc".equalsIgnoreCase(name) || ".docx".equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param path
     * @return 是否是图片
     */
    public static boolean isPictureFile(String path) {
        try {
            if (!TextUtils.isEmpty(path) && path.contains(".")) {
                String name = path.substring(path.lastIndexOf("."));
                if (".png".equalsIgnoreCase(name) || ".jpg".equalsIgnoreCase(name) || ".jpeg".equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * @param path
     * @return 是否是视频
     */
    public static boolean isVideoFile(String path) {
        try {
            if (!TextUtils.isEmpty(path) && path.contains(".")) {
                String name = path.substring(path.lastIndexOf("."));
                if (".mp4".equalsIgnoreCase(name) || ".mkv".equalsIgnoreCase(name) || ".wmv".equalsIgnoreCase(name)
                        || ".flv".equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param path
     * @return 是否是音频
     */
    public static boolean isAudioFile(String path) {
        try {
            if (!TextUtils.isEmpty(path) && path.contains(".")) {
                String name = path.substring(path.lastIndexOf("."));
                if (".mp3".equalsIgnoreCase(name) || ".midi".equalsIgnoreCase(name) || ".wmv".equalsIgnoreCase(name)
                        || ".3gpp".equalsIgnoreCase(name) || ".wma".equalsIgnoreCase(name) || ".wav".equalsIgnoreCase(name)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param fileName 文件夹名称（不存在就新建）
     * @return 文件夹路径,
     */
    public static String getFilePath(String fileName) {
        String filePath = getSDRootFile().concat(File.separator).concat(fileName);
        makeDir(filePath);
        return filePath;
        //return context.getExternalFilesDir(filePath).getAbsolutePath();
    }


    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean status;
        SecurityManager checker = new SecurityManager();
        if (!TextUtils.isEmpty(filePath)) {
            File newPath = new File(filePath);
            checker.checkDelete(newPath.toString());
            if (newPath.isFile()) {
                try {
                    newPath.delete();
                    status = true;
                } catch (SecurityException se) {
                    se.printStackTrace();
                    status = false;
                }
            } else {
                status = false;
            }
        } else {
            status = false;
        }
        return status;
    }


    /**
     * 获取文件夹目录下所有文件名称
     *
     * @param path 文件夹路径
     * @return
     */
    public static List<String> getFilesAllName(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            LogUtils.e("error", "空目录");
            return null;
        }
        List<String> s = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            s.add(files[i].getAbsolutePath());
        }
        return s;
    }





}
