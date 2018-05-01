
package com.kaicom.mymvptest.datasource;

import com.kaicom.api.db.DaoWrapper;
import com.kaicom.mymvptest.datasource.entity.TraceRecordsEntity;
import com.kaicom.mymvptest.datasource.gen.TraceRecordsDao;

import java.util.Collections;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

import static com.kaicom.api.log.KlLoger.debug;


/**
 */
public class TraceRecordsOper  {
    protected DaoWrapper daoWrapper;

    public TraceRecordsOper() {
        daoWrapper = new DaoWrapper(TraceRecordsEntity.class);
    }

    public long insert(TraceRecordsEntity entity) {
        return daoWrapper.insert(entity);
    }

    public long replace(TraceRecordsEntity entity) {
        return daoWrapper.insertOrReplace(entity);
    }

    public boolean insertList(Iterable<TraceRecordsEntity> entities) {
        return daoWrapper.insertInTx(entities);
    }

    public boolean insertOrReplaceList(Iterable<TraceRecordsEntity> entities) {
        return daoWrapper.insertOrReplaceInTx(entities);
    }

    public List<TraceRecordsEntity> getListByQueryBuilder(QueryBuilder<TraceRecordsEntity> qb) {
        return daoWrapper.findDataList(qb);
    }

    public List<TraceRecordsEntity> getAll() {
        return daoWrapper.findAll();
    }
    /**
     * 查询所有记录倒序
     * @return 如果没有记录返回空集合
     * @throws RuntimeException
     */
    public List<TraceRecordsEntity> getAllDescByTime() throws RuntimeException {
        try {
            List<TraceRecordsEntity> dataList = daoWrapper.getQueryBuilder().orderDesc(TraceRecordsDao.Properties.StartTime).list();
            if (dataList == null)
                return Collections.emptyList();
            else
                return dataList;
        } catch (Exception e) {
            debug("查询所有数据出错", e);
            throw new RuntimeException("查询出错");
        }
    }

    public long count() {
        return daoWrapper.count();
    }

    public boolean deleteAll() {
        return daoWrapper.deleteAll();
    }

    public boolean deleteOneData(TraceRecordsEntity entity) {
        return daoWrapper.delete(entity);
    }

    public boolean deleteByKey(Long key) {
        return daoWrapper.deleteByKey(key);
    }

    public boolean deleteDatas(Iterable<TraceRecordsEntity> entitie) {
        return daoWrapper.deleteInTx(entitie);
    }
    public void update(List<TraceRecordsEntity> entities) {
        daoWrapper.updateInTx(entities);
    }

    public void refresh(TraceRecordsEntity entity) {
        daoWrapper.refresh(entity);
    }

    public boolean deleteOldRecord() {
        return false;
    }

    public boolean deleteDatas(List<TraceRecordsEntity> datas) {
        return daoWrapper.deleteInTx(datas);
    }

}
