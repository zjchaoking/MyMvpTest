package com.kaicom.mymvptest.datasource.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.kaicom.mymvptest.datasource.entity.TraceRecordsEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table TRACE_RECORDS_TABLE.
*/
public class TraceRecordsDao extends AbstractDao<TraceRecordsEntity, Long> {

    public static final String TABLENAME = "TRACE_RECORDS_TABLE";

    /**
     * Properties of entity TraceRecordsEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property StartTime = new Property(1, String.class, "startTime", false, "start_time");
        public final static Property EndTime = new Property(2, String.class, "endTime", false, "end_time");
        public final static Property FilePath = new Property(3, String.class, "filePath", false, "file_path");
    };


    public TraceRecordsDao(DaoConfig config) {
        super(config);
    }
    
    public TraceRecordsDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'TRACE_RECORDS_TABLE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'start_time' TEXT," + // 1: startTime
                "'end_time' TEXT," + // 2: endTime
                "'file_path' TEXT);"); // 3: filePath
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'TRACE_RECORDS_TABLE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, TraceRecordsEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(2, startTime);
        }
 
        String endTime = entity.getEndTime();
        if (endTime != null) {
            stmt.bindString(3, endTime);
        }
 
        String filePath = entity.getFilePath();
        if (filePath != null) {
            stmt.bindString(4, filePath);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public TraceRecordsEntity readEntity(Cursor cursor, int offset) {
        TraceRecordsEntity entity = new TraceRecordsEntity();
        readEntity(cursor, entity, offset);
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, TraceRecordsEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setStartTime(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEndTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setFilePath(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(TraceRecordsEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(TraceRecordsEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
