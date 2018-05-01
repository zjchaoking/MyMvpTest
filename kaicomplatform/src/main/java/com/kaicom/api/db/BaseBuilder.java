package com.kaicom.api.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

/**
 * 用于构建ContentValues和实体对象
 * 
 * @author scj
 */
public interface BaseBuilder<T> {

    /**
     * 创建ContentValues
     * @param entity
     * @return
     * @throws DaoException
     */
    public ContentValues buildContentValues(T entity);
    
    /**
     * 从cursor中获取数据
     * @param cursor
     * @return
     * @throws DaoException
     */
    public T getDataFromCursor(Cursor cursor);
    
}
