package com.eaibot.running.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;


import com.eaibot.running.constants.FileNameConstant;
import com.nudtit.lib_common.utils.FileUtil;
import com.nudtit.lib_common.utils.LogUtils;

import java.io.File;
import java.io.IOException;

public class GreenDaoContext extends ContextWrapper {
    public GreenDaoContext(Context base) {
        super(base);
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象对象
     *
     * @param name
     */
    @Override
    public File getDatabasePath(String name) {
        // 判断是否存在sd卡
        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if (!sdExist) {
            LogUtils.d("数据库","gggggg");
            return null;
        } else {// 如果存在
            // 获取sd卡路径
            String dbDir = FileUtil.getFilePath(FileNameConstant.MAP);
            dbDir += "/database";
            String dbPath = dbDir + "/" + name;
            // 判断目录是否存在，不存在则创建该目录
            File dirFile = new File(dbDir);
            if (!dirFile.exists()) {
                LogUtils.d("数据库",dbDir+"不存在");
                dirFile.mkdirs();
            }
            LogUtils.d("数据库",dbPath);
            // 数据库文件是否创建成功
            boolean isFileCreateSuccess = false;
            // 判断文件是否存在，不存在则创建该文件
            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                LogUtils.d("数据库",dbPath+"不存在");
                try {
                    isFileCreateSuccess = dbFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isFileCreateSuccess = true;

            }
            // 返回数据库文件对象
            if (isFileCreateSuccess) {
                return dbFile;

            } else {
                return super.getDatabasePath(name);

            }
        }
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }


    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler int,
     *                     SQLiteDatabase.CursorFactory,
     *                     DatabaseErrorHandler)
     */

    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    }

}
