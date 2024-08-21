package com.eaibot.running.db;

import android.content.Context;


import com.eaibot.running.db.dao.SlamLocationBean;

import com.nudtit.gkitr.greendao.gen.DaoMaster;
import com.nudtit.gkitr.greendao.gen.DaoSession;
import com.nudtit.gkitr.greendao.gen.SlamLocationBeanDao;
import com.nudtit.lib_common.utils.LogUtils;

import java.util.List;


/**
 * @author :
 * @date :  2022/1/13
 * @desc : 商用底盘手机端控制 数据库管理类
 */
public class SlamLocationDBManager {

    private static final String TAG = SlamLocationDBManager.class.getSimpleName();
    private static final String DB_NAME = "GKITR_SLAM_DB";
    private DaoMaster mDaoMaster;
    private SlamLocationDataBaseOpenHelper mHelper;
    private DaoSession mDaoSession;
    private SlamLocationBeanDao locationBeanDao;

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao(Context context) {
        if (mDaoMaster == null) {
            mHelper = new SlamLocationDataBaseOpenHelper(new GreenDaoContext(context), DB_NAME, null) {

            };
            mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        }
        if (mDaoSession == null) {
            mDaoSession = mDaoMaster.newSession();
        }
        locationBeanDao = mDaoSession.getSlamLocationBeanDao();
    }

    public static SlamLocationDBManager instance;

    public static SlamLocationDBManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SlamLocationDBManager.class) {
                if (instance == null) {
                    instance = new SlamLocationDBManager();
                    instance.initGreenDao(context);
                }
            }
        }
        return instance;
    }

    /**
     * 新增地点列表
     *
     * @param locationList
     */
    public void insertLocationList(List<SlamLocationBean> locationList) {
        locationBeanDao.insertInTx(locationList);
    }


    /**
     * 查询地点列表,查询出地图中的所有位置点信息
     *
     * @param mapName 地图名称
     * @return 地点列表
     */
    public List<SlamLocationBean> queryLocationListByMap(String mapName) {

        return locationBeanDao.queryBuilder().where(SlamLocationBeanDao.Properties.MapName.like(mapName)).list();
    }



    /**
     * 查询地点列表
     *
     * @param locationNo 地点编号
     * @return 地点列表
     */
    public List<SlamLocationBean> queryLocationListByNo(String locationNo) {
        return locationBeanDao.queryBuilder().where(SlamLocationBeanDao.Properties.LocationNumBer.eq(locationNo)).list();
    }

    /**
     * 查询地点列表，通过地点名称和地图名称确定唯一
     *
     * @param locationName 地点中文名称 mapName 表名字
     * @return 地点对象
     */
    public SlamLocationBean queryLocationListByLocationName(String locationName, String mapName) {
        List<SlamLocationBean> slamLocationBeans = locationBeanDao.queryBuilder().where(SlamLocationBeanDao.Properties.LocationNameChina.eq(locationName), SlamLocationBeanDao.Properties.MapName.eq(mapName)).list();
        if (slamLocationBeans != null && !slamLocationBeans.isEmpty()) {
            return slamLocationBeans.get(0);
        }
        return null;
    }

    /**
     * 更新地图数据(没有对应id不做处理)
     */
    public void updateLocationListToMap(List<SlamLocationBean> locationList) {
        locationBeanDao.updateInTx(locationList);
    }


    /**
     * 更新地图数据(没有对应id不做处理)
     */
    public void updateLocationToMap(SlamLocationBean locationBean) {
        locationBeanDao.updateInTx(locationBean);
    }

    /**
     * 插入数据
     */
    public long insert(SlamLocationBean locationBean) {
        try {
            return locationBeanDao.insert(locationBean);
        }catch (Exception e){
            LogUtils.d(TAG,"insertOrReplace"+e.getMessage());
            return -1;
        }

    }

    /**
     * 插入或更新数据
     */
    public long insertOrReplace(SlamLocationBean locationBean) {
        try {
            return locationBeanDao.insertOrReplace(locationBean);
        }catch (Exception e){
            LogUtils.d(TAG,"insertOrReplace"+e.getMessage());
            return -1;
        }

    }


    /**
     * 删除地图中的地点列表
     *
     * @param mapName 地图
     */
    public void deleteLocationListNyMapName(String mapName) {
        locationBeanDao.queryBuilder().where(SlamLocationBeanDao.Properties.MapName.eq(mapName)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除地图中的地点列表
     *
     * @param tableId 表id
     */
    public void deleteLocationListById(long tableId) {
        locationBeanDao.queryBuilder().where(SlamLocationBeanDao.Properties.TableId.eq(tableId)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    /**
     * 删除表中所有数据
     */
    public void deleteTableAllData() {
        locationBeanDao.deleteAll();
    }


    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
        instance = null;
        if (mDaoMaster != null) {
            mDaoMaster = null;
        }
    }

}
