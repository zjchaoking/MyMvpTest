package com.kaicom.api.db;

import android.database.sqlite.SQLiteDatabase;

import com.kaicom.api.KaicomApplication;
import com.kaicom.api.log.KlLoger;

import java.util.Collections;
import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.identityscope.IdentityScope;
import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * <h4>greendao包装类</h4>
 * 封装基本的数据库CRUD操作
 * 
 * @author scj
 * @param <T> 实体对象
 * @param <K> 主键
 */
@SuppressWarnings("unchecked")
public class DaoWrapper<T, K> {

    private final AbstractDao<T, K> dao;

    public DaoWrapper(Class<?> entityClass) {
        this.dao = (AbstractDao<T, K>) KaicomApplication.app.getDaoSession()
                .getDao(entityClass);
    }

    /**
     * 获得SqliteDatabase对象
     * @return
     */
    public SQLiteDatabase getDatabase() {
        return dao.getDatabase();
    }

    public QueryBuilder<T> getQueryBuilder() {
        return dao.queryBuilder();
    }

    public AbstractDao<T, K> getDao() {
        return dao;
    }

    public IdentityScope<?, ?> getIdentityScope() {
        return dao.getDaoConfig().getIdentityScope();
    }

    /**
     * 清除dao对象的数据缓存
     */
    public void clearCache() {
        try {
            dao.getDaoConfig().getIdentityScope().clear();
        } catch (Exception e) {
            KlLoger.error("clearCache error", e);
        }
    }

    /**
     * 插入单条数据
     * @param entity
     * @return rowId 大于0表示插入成功
     * @throws RuntimeException
     */
    public long insert(T entity) throws RuntimeException {
        if (entity == null)
            throw new RuntimeException("entity为空");
        try {
            return dao.insert(entity);
        } catch (Exception e) {
            debug("插入单条数据出错", e);
            throw new RuntimeException("插入单条数据出错");
        }
    }

    /**
     * 插入多条数据
     * @param entities
     * @return 成功返回true
     * @throws RuntimeException
     */
    public boolean insertInTx(Iterable<T> entities) throws RuntimeException {
        if (entities == null || !entities.iterator().hasNext())
            throw new RuntimeException("数据为空");
        try {
            dao.insertInTx(entities);
            return true;
        } catch (Exception e) {
            debug("插入多条数据出错", e);
            throw new RuntimeException("插入多条数据出错");
        }
    }

    /**
     * 插入一条或多条数据
     * @param entities
     * @return
     * @throws RuntimeException
     */
    public boolean insertInTx(T... entities) throws RuntimeException {
        if (entities == null || entities.length == 0)
            throw new RuntimeException("数据为空");
        try {
            dao.insertInTx(entities);
            return true;
        } catch (Exception e) {
            debug("插入数据出错", e);
            throw new RuntimeException("插入数据出错");
        }
    }

    /**
     * 插入or替换一条数据
     * @param entity
     * @return
     * @throws RuntimeException
     */
    public long insertOrReplace(T entity) throws RuntimeException {
        if (entity == null)
            throw new RuntimeException("entity为空");
        try {
            return dao.insertOrReplace(entity);
        } catch (Exception e) {
            debug("插入或替换单条数据出错", e);
            throw new RuntimeException("插入或替换单条数据出错");
        }
    }

    /**
     * 插入or替换多条数据
     * @param entities
     * @return
     * @throws RuntimeException
     */
    public boolean insertOrReplaceInTx(Iterable<T> entities)
            throws RuntimeException {
        if (entities == null || !entities.iterator().hasNext())
            throw new RuntimeException("数据为空");
        try {
            dao.insertOrReplaceInTx(entities);
            return true;
        } catch (Exception e) {
            debug("插入或替换多条数据出错", e);
            throw new RuntimeException("插入或替换多条数据出错");
        }
    }

    /**
     * 插入or替换一条、多条数据
     * @param entities
     * @return
     * @throws RuntimeException
     */
    public boolean insertOrReplaceInTx(T... entities) throws RuntimeException {
        if (entities == null || entities.length == 0)
            throw new RuntimeException("数据为空");
        try {
            dao.insertInTx(entities);
            return true;
        } catch (Exception e) {
            debug("插入或替换数据出错", e);
            throw new RuntimeException("插入或替换数据出错");
        }
    }

    /**
     * 删除所有数据
     * @throws RuntimeException
     */
    public boolean deleteAll() throws RuntimeException {
        try {
            dao.deleteAll();
            return true;
        } catch (Exception e) {
            debug("删除所有数据", e);
            throw new RuntimeException("删除所有数据出错");
        }
    }

    /**
     * 删除一条记录
     * @param entity
     * @return
     * @throws RuntimeException
     */
    public boolean delete(T entity) throws RuntimeException {
        if (entity == null)
            throw new RuntimeException("entity为空");
        try {
            dao.delete(entity);
            return true;
        } catch (Exception e) {
            debug("删除单条记录出错", e);
            throw new RuntimeException("删除单条记录出错", e);
        }
    }

    public boolean deleteInTx(Iterable<T> entities) throws RuntimeException {
        if (entities == null || !entities.iterator().hasNext())
            throw new RuntimeException("数据为空");
        try {
            dao.deleteInTx(entities);
            return true;
        } catch (Exception e) {
            debug("删除多条数据出错", e);
            throw new RuntimeException("删除多条数据出错", e);
        }
    }

    public boolean deleteInTx(T... entities) throws RuntimeException {
        if (entities == null || entities.length == 0)
            throw new RuntimeException("数据为空");
        try {
            dao.deleteInTx(entities);
            return true;
        } catch (Exception e) {
            debug("删除多条数据出错", e);
            throw new RuntimeException("删除多条数据出错", e);
        }
    }

    public boolean deleteByKey(K key) throws RuntimeException {
        if (key == null)
            throw new RuntimeException("主键为空");
        try {
            dao.deleteByKey(key);
            return true;
        } catch (Exception e) {
            debug("按主键删除单条数据出错", e);
            throw new RuntimeException("删除数据出错");
        }
    }

    public boolean deleteByKey(Iterable<K> keys) throws RuntimeException {
        if (keys == null || !keys.iterator().hasNext())
            throw new RuntimeException("主键为空");
        try {
            dao.deleteByKeyInTx(keys);
            return true;
        } catch (Exception e) {
            debug("按主键删除多条数据出错", e);
            throw new RuntimeException("删除数据出错");
        }
    }
    
    public boolean delete(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            throw new RuntimeException("QueryBuilder为空");
        try {
        	qb.buildDelete().executeDeleteWithoutDetachingEntities();
        	return true;
        } catch (Exception e) {
            debug("按条件删除数据出错", e);
            throw new RuntimeException("按条件删除数据出错");
        }
    }

    @Deprecated
    public boolean delelte(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            throw new RuntimeException("QueryBuilder为空");
        try {
        	qb.buildDelete().executeDeleteWithoutDetachingEntities();
        	return true;
        } catch (Exception e) {
            debug("按条件删除数据出错", e);
            throw new RuntimeException("按条件删除数据出错");
        }
    }
    
    public int deleteWithResult(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            throw new RuntimeException("QueryBuilder为空");
        try {
        	return qb.buildDelete().executeDeleteWithoutDetachingEntities();
        } catch (Exception e) {
            debug("按条件删除数据出错", e);
            throw new RuntimeException("按条件删除数据出错");
        }
    }

    /**
     * 更新对象
     * @param entity
     * @throws RuntimeException
     */
    public void refresh(T entity) throws RuntimeException {
        if (entity == null)
            throw new RuntimeException("entity为空");
        try {
            dao.refresh(entity);
        } catch (Exception e) {
            debug("refresh error", e);
            throw new RuntimeException("refresh error");
        }
    }

    /**
     * 查询记录总数
     * @return
     */
    public long count() throws RuntimeException {
        try {
            return dao.count();
        } catch (Exception e) {
            debug("查询记录总数出错", e);
            throw new RuntimeException("查询记录总数出错");
        }
    }

    /**
     * 查询数量
     * @param qb 
     * @return 如果QueryBuilder，查询记录总数
     * @throws RuntimeException
     */
    public long count(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            return dao.count();
        try {
            return qb.buildCount().count();
        } catch (Exception e) {
            debug("查询数量出错", e);
            throw new RuntimeException("查询数量出错");
        }
    }

    /**
     * 查询所有记录
     * @return 如果没有记录返回空集合
     * @throws RuntimeException
     */
    public List<T> findAll() throws RuntimeException {
        try {
            List<T> dataList = dao.loadAll();
            if (dataList == null)
                return Collections.emptyList();
            else
                return dataList;
        } catch (Exception e) {
            debug("查询所有数据出错", e);
            throw new RuntimeException("查询出错");
        }
    }

    /**
     * 按主键查询记录
     * @param key
     * @return 
     * @throws RuntimeException
     */
    public T findByKey(K key) throws RuntimeException {
        if (key == null)
            throw new RuntimeException("key为空");
        try {
            T entity = dao.load(key);
            return entity;
        } catch (Exception e) {
            debug("按主键查询数据出错", e);
            throw new RuntimeException("查询出错");
        }
    }

    public List<T> findData(String where, String... selectionArg)
            throws RuntimeException {
        try {
            return dao.queryRaw(where, selectionArg);
        } catch (Exception e) {
            debug("按主键查询数据出错", e);
            throw new RuntimeException("查询出错");
        }
    }

    /**
     * 查询单条记录
     * @param qb
     * @return 未查到记录会抛出异常
     * @throws RuntimeException
     */
    public T findSingleData(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            throw new RuntimeException("QueryBuilder为空");
        try {
            T entity = qb.unique();
            return entity;
        } catch (Exception e) {
            debug("查询数量出错", e);
            throw new RuntimeException("查询数量出错");
        }
    }

    /**
     * 查询多条记录
     * @param qb
     * @return 查不到记录返回空集合
     * @throws RuntimeException
     */
    public synchronized List<T> findDataList(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            throw new RuntimeException("QueryBuilder为空");
        try {
            List<T> dataList = qb.list();
            if (dataList == null)
                return Collections.emptyList();
            else
                return dataList;
        } catch (Exception e) {
            debug("查询数量出错", e);
            throw new RuntimeException("查询数量出错");
        }
    }
    
    /**
     * 使用懒加载的方式查询数据<br>
     * 注:使用完后需要调用close方法关闭
     * @param qb
     * @return 未查到数据时，返回空
     * @throws RuntimeException
     */
    public synchronized LazyList<T> lazyDataList(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            throw new RuntimeException("QueryBuilder为空");
        try {
        	LazyList<T> dataList = qb.listLazy();
            if (dataList == null)
                return null;
            else
                return dataList;
        } catch (Exception e) {
            debug("查询数量出错", e);
            throw new RuntimeException("查询数量出错");
        }
    }

    /**
     * 根据主键判断对象是否存在
     * @param key
     * @return
     * @throws RuntimeException
     */
    public boolean exists(K key) throws RuntimeException {
        if (key == null)
            throw new RuntimeException("主键为空");
        try {
            T entity = (T) dao.load(key);
            return entity != null;
        } catch (Exception e) {
            debug("按主键查询出错", e);
            throw new RuntimeException("查询出错");
        }
    }

    /**
     * 根据条件查询数据是否已存在
     * @param qb
     * @return
     * @throws RuntimeException
     */
    public boolean exists(QueryBuilder<T> qb) throws RuntimeException {
        if (qb == null)
            throw new RuntimeException("QueryBuilder为空");
        try {
            return qb.buildCount().count() > 0;
        } catch (Exception e) {
            debug("查询数量出错", e);
            throw new RuntimeException("查询数量出错");
        }
    }

    public boolean update(T entity) throws RuntimeException {
        if (entity == null)
            throw new RuntimeException("entity为空");
        try {
            dao.update(entity);
            return true;
        } catch (Exception e) {
            debug("更新数据出错", e);
            throw new RuntimeException("更新数据出错");
        }
    }

    public synchronized boolean updateInTx(Iterable<T> entities) throws RuntimeException {
        if (entities == null || !entities.iterator().hasNext())
            throw new RuntimeException("数据为空");
        try {
            dao.updateInTx(entities);
            return true;
        } catch (Exception e) {
            debug("更新数据出错", e);
            throw new RuntimeException("更新数据出错");
        }
    }

    public synchronized boolean updateInTx(T... entities) throws RuntimeException {
        if (entities == null || entities.length == 0)
            throw new RuntimeException("数据为空");
        try {
            dao.updateInTx(entities);
            return true;
        } catch (Exception e) {
            debug("更新数据出错", e);
            throw new RuntimeException("更新数据出错");
        }
    }

    protected void debug(String message) {
        KlLoger.debug(dao.getClass().getName() + message);
    }

    protected void debug(String message, Exception e) {
        KlLoger.debug(dao.getClass().getName() + message, e);
    }

}
