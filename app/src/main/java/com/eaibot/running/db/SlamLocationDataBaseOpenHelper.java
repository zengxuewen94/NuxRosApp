package com.eaibot.running.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.nudtit.gkitr.greendao.gen.DaoMaster;


public class SlamLocationDataBaseOpenHelper extends DaoMaster.OpenHelper {


    public SlamLocationDataBaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // favorite表新增1个字段
        if (oldVersion == 1 && newVersion == 2) {
//            //在1版本的数据库 升级到 2版本的数据库
//            // ANSWER_PROGRAM_HISTORY_MATH_GO  增加 ProblemNodeId 字段
//            String sql1 = "ALTER TABLE "+AnswerProgramHistoryMathGoDao.TABLENAME+" ADD COLUMN PROBLEM_NODE_ID TEXT";
//            db.execSQL(sql1);
//            // PROGRAM_MATH_GO  增加 COURSE_ID 字段
//            String sql2 = "ALTER TABLE "+ProgramMathGoDao.TABLENAME+" ADD COLUMN COURSE_ID TEXT";
//            db.execSQL(sql2);
//            //增加新表 NodeDismastlingMathGoDao
//            NodeDismastlingMathGoDao.createTable(db, true);
//            //数据库升级之后，下载的状态要重置，基础数据需要重新下载
//            DownLoadStatusHistoryMathGoDao.dropTable(db,true);
//            DownLoadStatusHistoryMathGoDao.createTable(db,true);
//            CourseMathGoDao.createTable(db,true);
        }
    }


}