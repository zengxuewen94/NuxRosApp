package com.nudtit.gkitr.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.eaibot.running.db.dao.SlamLocationBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SLAM_LOCATION_BEAN".
*/
public class SlamLocationBeanDao extends AbstractDao<SlamLocationBean, Long> {

    public static final String TABLENAME = "SLAM_LOCATION_BEAN";

    /**
     * Properties of entity SlamLocationBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TableId = new Property(0, Long.class, "tableId", true, "_id");
        public final static Property MapName = new Property(1, String.class, "mapName", false, "MAP_NAME");
        public final static Property LocationNumBer = new Property(2, String.class, "locationNumBer", false, "LOCATION_NUM_BER");
        public final static Property LocationLevel = new Property(3, String.class, "locationLevel", false, "LOCATION_LEVEL");
        public final static Property LocationNameEnglish = new Property(4, String.class, "locationNameEnglish", false, "LOCATION_NAME_ENGLISH");
        public final static Property LocationNameChina = new Property(5, String.class, "locationNameChina", false, "LOCATION_NAME_CHINA");
        public final static Property Content = new Property(6, String.class, "content", false, "CONTENT");
        public final static Property X = new Property(7, float.class, "x", false, "X");
        public final static Property Y = new Property(8, float.class, "y", false, "Y");
        public final static Property Yaw = new Property(9, float.class, "yaw", false, "YAW");
        public final static Property Type = new Property(10, int.class, "type", false, "TYPE");
        public final static Property SensorStatus = new Property(11, int.class, "sensorStatus", false, "SENSOR_STATUS");
        public final static Property StartX = new Property(12, float.class, "startX", false, "START_X");
        public final static Property StartY = new Property(13, float.class, "startY", false, "START_Y");
        public final static Property EndX = new Property(14, float.class, "endX", false, "END_X");
        public final static Property EndY = new Property(15, float.class, "endY", false, "END_Y");
        public final static Property Time = new Property(16, long.class, "time", false, "TIME");
    }


    public SlamLocationBeanDao(DaoConfig config) {
        super(config);
    }
    
    public SlamLocationBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SLAM_LOCATION_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: tableId
                "\"MAP_NAME\" TEXT NOT NULL ," + // 1: mapName
                "\"LOCATION_NUM_BER\" TEXT," + // 2: locationNumBer
                "\"LOCATION_LEVEL\" TEXT," + // 3: locationLevel
                "\"LOCATION_NAME_ENGLISH\" TEXT," + // 4: locationNameEnglish
                "\"LOCATION_NAME_CHINA\" TEXT NOT NULL ," + // 5: locationNameChina
                "\"CONTENT\" TEXT," + // 6: content
                "\"X\" REAL NOT NULL ," + // 7: x
                "\"Y\" REAL NOT NULL ," + // 8: y
                "\"YAW\" REAL NOT NULL ," + // 9: yaw
                "\"TYPE\" INTEGER NOT NULL ," + // 10: type
                "\"SENSOR_STATUS\" INTEGER NOT NULL ," + // 11: sensorStatus
                "\"START_X\" REAL NOT NULL ," + // 12: startX
                "\"START_Y\" REAL NOT NULL ," + // 13: startY
                "\"END_X\" REAL NOT NULL ," + // 14: endX
                "\"END_Y\" REAL NOT NULL ," + // 15: endY
                "\"TIME\" INTEGER NOT NULL );"); // 16: time
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SLAM_LOCATION_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SlamLocationBean entity) {
        stmt.clearBindings();
 
        Long tableId = entity.getTableId();
        if (tableId != null) {
            stmt.bindLong(1, tableId);
        }
        stmt.bindString(2, entity.getMapName());
 
        String locationNumBer = entity.getLocationNumBer();
        if (locationNumBer != null) {
            stmt.bindString(3, locationNumBer);
        }
 
        String locationLevel = entity.getLocationLevel();
        if (locationLevel != null) {
            stmt.bindString(4, locationLevel);
        }
 
        String locationNameEnglish = entity.getLocationNameEnglish();
        if (locationNameEnglish != null) {
            stmt.bindString(5, locationNameEnglish);
        }
        stmt.bindString(6, entity.getLocationNameChina());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        stmt.bindDouble(8, entity.getX());
        stmt.bindDouble(9, entity.getY());
        stmt.bindDouble(10, entity.getYaw());
        stmt.bindLong(11, entity.getType());
        stmt.bindLong(12, entity.getSensorStatus());
        stmt.bindDouble(13, entity.getStartX());
        stmt.bindDouble(14, entity.getStartY());
        stmt.bindDouble(15, entity.getEndX());
        stmt.bindDouble(16, entity.getEndY());
        stmt.bindLong(17, entity.getTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SlamLocationBean entity) {
        stmt.clearBindings();
 
        Long tableId = entity.getTableId();
        if (tableId != null) {
            stmt.bindLong(1, tableId);
        }
        stmt.bindString(2, entity.getMapName());
 
        String locationNumBer = entity.getLocationNumBer();
        if (locationNumBer != null) {
            stmt.bindString(3, locationNumBer);
        }
 
        String locationLevel = entity.getLocationLevel();
        if (locationLevel != null) {
            stmt.bindString(4, locationLevel);
        }
 
        String locationNameEnglish = entity.getLocationNameEnglish();
        if (locationNameEnglish != null) {
            stmt.bindString(5, locationNameEnglish);
        }
        stmt.bindString(6, entity.getLocationNameChina());
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(7, content);
        }
        stmt.bindDouble(8, entity.getX());
        stmt.bindDouble(9, entity.getY());
        stmt.bindDouble(10, entity.getYaw());
        stmt.bindLong(11, entity.getType());
        stmt.bindLong(12, entity.getSensorStatus());
        stmt.bindDouble(13, entity.getStartX());
        stmt.bindDouble(14, entity.getStartY());
        stmt.bindDouble(15, entity.getEndX());
        stmt.bindDouble(16, entity.getEndY());
        stmt.bindLong(17, entity.getTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public SlamLocationBean readEntity(Cursor cursor, int offset) {
        SlamLocationBean entity = new SlamLocationBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // tableId
            cursor.getString(offset + 1), // mapName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // locationNumBer
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // locationLevel
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // locationNameEnglish
            cursor.getString(offset + 5), // locationNameChina
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // content
            cursor.getFloat(offset + 7), // x
            cursor.getFloat(offset + 8), // y
            cursor.getFloat(offset + 9), // yaw
            cursor.getInt(offset + 10), // type
            cursor.getInt(offset + 11), // sensorStatus
            cursor.getFloat(offset + 12), // startX
            cursor.getFloat(offset + 13), // startY
            cursor.getFloat(offset + 14), // endX
            cursor.getFloat(offset + 15), // endY
            cursor.getLong(offset + 16) // time
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SlamLocationBean entity, int offset) {
        entity.setTableId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMapName(cursor.getString(offset + 1));
        entity.setLocationNumBer(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setLocationLevel(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setLocationNameEnglish(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setLocationNameChina(cursor.getString(offset + 5));
        entity.setContent(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setX(cursor.getFloat(offset + 7));
        entity.setY(cursor.getFloat(offset + 8));
        entity.setYaw(cursor.getFloat(offset + 9));
        entity.setType(cursor.getInt(offset + 10));
        entity.setSensorStatus(cursor.getInt(offset + 11));
        entity.setStartX(cursor.getFloat(offset + 12));
        entity.setStartY(cursor.getFloat(offset + 13));
        entity.setEndX(cursor.getFloat(offset + 14));
        entity.setEndY(cursor.getFloat(offset + 15));
        entity.setTime(cursor.getLong(offset + 16));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(SlamLocationBean entity, long rowId) {
        entity.setTableId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(SlamLocationBean entity) {
        if(entity != null) {
            return entity.getTableId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(SlamLocationBean entity) {
        return entity.getTableId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}