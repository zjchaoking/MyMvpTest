package com.kaicom.api.db;

import static com.kaicom.api.log.KlLoger.debug;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.kaicom.api.KaicomApplication;
import com.kaicom.api.util.ReflectUtil;

/**
 * 封装原生sqlite的数据库操作
 * @author scj
 *
 */
@SuppressLint("NewApi") 
public class SqliteWrapper {

    private SQLiteDatabase db;

    private SqliteWrapper() {
        this.db = KaicomApplication.app.getSqliteDatabase();
    }

    public static SqliteWrapper getInstance() {
        return SqliteWrapperHolder.instance;
    }

    private static class SqliteWrapperHolder {
        private static SqliteWrapper instance = new SqliteWrapper();
    }

    /**
     * 插入单条数据
     * @param <T>
     * @param ContentValues
     * @param tableName
     * @param builder 不能为空，传入dao实现类对象(this)即可
     * @return rowId 
     * @throws RuntimeException 
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> long insert(String tableName, T entity, BaseBuilder builder)
            throws RuntimeException {
        if (TextUtils.isEmpty(tableName))
            throw new RuntimeException("tableName为空");
        if (entity == null)
            throw new RuntimeException("entity为空");
        if (builder == null)
            throw new RuntimeException("builder为空");
        try {
            return db.insert(tableName, null,
                    builder.buildContentValues(entity));
        } catch (Exception e) {
            debug(tableName + "数据插入失败", e);
            throw new RuntimeException("插入数据失败", e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> long insertOrReplace(String tableName, T entity,
            BaseBuilder builder) throws RuntimeException {
        if (TextUtils.isEmpty(tableName))
            throw new RuntimeException("tableName为空");
        if (entity == null)
            throw new RuntimeException("entity为空");
        if (builder == null)
            throw new RuntimeException("builder为空");
        try {
            return db.replace(tableName, null,
                    builder.buildContentValues(entity));
        } catch (Exception e) {
            debug(tableName + "数据插入or替换失败", e);
            throw new RuntimeException("插入数据失败", e);
        }
    }

    /**
     * 插入多条数据
     * @param <T> 实体对象采用自增长 必须定义成long id(注：setId方法名固定)
     * @param tableName
     * @param values
     * @param builder 不能为空，传入dao实现类对象(this)即可
     * @return
     * @throws RuntimeException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> boolean insertList(String tableName, Iterable<T> values,
            BaseBuilder builder) throws RuntimeException {
        if (TextUtils.isEmpty(tableName))
            throw new RuntimeException("tableName为空");
        if (builder == null)
            throw new RuntimeException("builder为空");
        if (values == null || !values.iterator().hasNext())
            throw new RuntimeException("values为空");

        Method setIdMethod = ReflectUtil.getMethod(values.iterator().next()
                .getClass(), "setId", long.class);
        db.beginTransaction();
        try {
            for (T entity : values) {
                if (entity == null)
                    continue;
                long id = db.insert(tableName, null,
                        builder.buildContentValues(entity));
                if (setIdMethod != null && id > 0)
                    setIdMethod.invoke(entity, id);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            debug(tableName + "数据 insertList error", e);
            throw new RuntimeException("保存数据失败", e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 插入或者替代多条数据
     * @param <T> 实体对象采用自增长 必须定义成long id(注：setId方法名固定)
     * @param tableName
     * @param values
     * @param builder 不能为空，传入dao实现类对象(this)即可
     * @return
     * @throws RuntimeException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> boolean insertOrReplaceList(String tableName,
            Iterable<T> values, BaseBuilder builder) throws RuntimeException {
        if (TextUtils.isEmpty(tableName))
            throw new RuntimeException("tableName为空");
        if (builder == null)
            throw new RuntimeException("builder为空");
        if (values == null || !values.iterator().hasNext())
            throw new RuntimeException("values为空");

        Method setIdMethod = ReflectUtil.getMethod(values.iterator().next()
                .getClass(), "setId", long.class);
        db.beginTransaction();
        try {
            for (T entity : values) {
                if (entity == null)
                    continue;
                long id = db.replace(tableName, null,
                        builder.buildContentValues(entity));
                if (setIdMethod != null && id > 0)
                    setIdMethod.invoke(entity, id);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            debug(tableName + "数据替换失败", e);
            throw new RuntimeException("保存数据失败", e);
        } finally {
            db.endTransaction();
        }
    }

    /**
     * 删除某张表的所有数据
     * 
     * @param tableName
     * @return 删除成功返回true
     */
    public boolean deleteAll(String tableName) throws RuntimeException {
        if (TextUtils.isEmpty(tableName))
            throw new RuntimeException("tableName为空");
        long res = -1;
        try {
            res = db.delete(tableName, null, null);
        } catch (Exception e) {
            debug("删除所有数据失败", e);
            throw new RuntimeException("删除数据失败", e);
        }
        return res > 0;
    }

    /**
     * 删除指定的数据
     * 
     * @param tableName
     * @param whereClause
     * @param whereArgs
     * @return 
     */
    public int delete(String tableName, String whereClause, String[] whereArgs)
            throws RuntimeException {
        if (TextUtils.isEmpty(tableName))
            throw new RuntimeException("tableName为空");
        int res = 0;
        try {
            res = db.delete(tableName, whereClause, whereArgs);
        } catch (Exception e) {
            debug("删除数据失败", e);
            throw new RuntimeException("删除数据失败", e);
        }
        return res;
    }

    /**
     * 更新数据
     * @param tableName
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public int update(String tableName, ContentValues values,
            String whereClause, String[] whereArgs) throws RuntimeException {
        if (TextUtils.isEmpty(tableName))
            throw new RuntimeException("tableName为空");
        if (values == null)
            throw new RuntimeException("ContentValues为空");
        int res = -1;
        try {
            res = db.update(tableName, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新数据失败", e);
        } finally {
            values.clear();
        }
        return res;
    }

    /**
     * 根据sql查询，获得Cursor对象
     * 
     * @param sql
     * @param selectionArgs
     * @return cursor 需要自己关闭cursor
     */
    public Cursor queryCursor(String sql, String[] selectionArgs)
            throws RuntimeException {
        if (TextUtils.isEmpty(sql))
            throw new RuntimeException("sql为空");
        try {
            return db.rawQuery(sql, selectionArgs);
        } catch (Exception e) {
            debug(sql + "查询出错", e);
            throw new RuntimeException("查询出错", e);
        }
    }

    /**
     * 根据sql查询，并从cursor对象中获取指定数据
     * @param <V>
     * @param sql
     * @param selectionArgs
     * @param callBack
     * @return
     * @throws RuntimeException
     */
    public <V> V queryResult(String sql, String[] selectionArgs,
            CursorCallBack<V> callBack) throws RuntimeException {
        Cursor cursor = null;
        try {
            try {
                cursor = db.rawQuery(sql, selectionArgs);
            } catch (Exception e) {
                debug(sql + "数据查询出错", e);
            }
            if (cursor != null && cursor.moveToFirst()) {
                return callBack.getResult(cursor);
            } else {
                throw new RuntimeException("未查到相应记录");
            }
        } finally {
            closeCursor(cursor);
        }
    }

    /**
     * 查询单条记录
     * @param sql
     * @param selectionArgs
     * @param builder 不能为空，传入dao实现类对象(this)即可
     * @return 
     * @throws RuntimeException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T querySingleData(String sql, String[] selectionArgs,
            BaseBuilder builder) throws RuntimeException {
        Cursor cursor = null;
        try {
            try {
                cursor = db.rawQuery(sql, selectionArgs);
            } catch (Exception e) {
                debug(sql + "数据查询出错", e);
            }
            if (cursor != null && cursor.moveToFirst()) {
                return (T) builder.getDataFromCursor(cursor);
            } else {
                throw new RuntimeException("未查到相应记录");
            }
        } finally {
            closeCursor(cursor);
        }
    }

    /**
     * 查询多条记录
     * @param sql
     * @param selectionArgs
     * @param builder 不能为空，传入dao实现类对象(this)即可
     * @return 没查到数据时会提示"未查到相应记录"
     * @throws RuntimeException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> List<T> queryManyData(String sql, String[] selectionArgs,
            BaseBuilder builder) throws RuntimeException {
        Cursor cursor = null;
        try {
            try {
                cursor = db.rawQuery(sql, selectionArgs);
            } catch (Exception e) {
                debug(sql + "数据查询出错", e);
            }
            if (cursor != null && cursor.moveToFirst()) {
                List<T> lists = new ArrayList<T>();
                T entity = null;
                do {
                    entity = (T) builder.getDataFromCursor(cursor);
                    lists.add(entity);
                } while (cursor.moveToNext());
                return lists;
            } else {
                throw new RuntimeException("未查到相应记录");
            }
        } finally {
            closeCursor(cursor);
        }
    }

    /**
     * 判断数据是否存在
     * @param sql
     * @param selectionArgs
     * @return
     */
    public boolean exists(String sql, String[] selectionArgs) {
        boolean isExists = false;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                isExists = (count > 0);
            }
        } catch (Exception e) {
            debug(sql + "查询是否存在出错", e);
        } finally {
            closeCursor(cursor);
        }
        return isExists;
    }

    /**
     * 查询某表记录总数
     * 
     * @param tableName
     * @return
     */
    public int count(String tableName) throws RuntimeException {
        String sql = "select count(*) from " + tableName;
        return queryCount(sql, null);
    }

    /**
     * 根据sql语句查询记录数量
     * @param sql
     * @param selectionArgs
     * @return
     * @throws RuntimeException
     */
    public int queryCount(String sql, String[] selectionArgs)
            throws RuntimeException {
        int count = 0;
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            debug(sql + "查询记录数量出错", e);
            throw new RuntimeException("查询记录数量出错", e);
        } finally {
            closeCursor(cursor);
        }
        return count;
    }

    /**
     * 关闭游标
     * 
     * @param cursor
     *            游标
     */
    public void closeCursor(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    /**
     * 回调接口
     * @author scj
     * @param <V>
     *
     */
    public interface CursorCallBack<V> {

        /**
         * 从cursor中获取想要的数据
         * @param cursor
         * @return
         */
        public V getResult(Cursor cursor);
    }

}
