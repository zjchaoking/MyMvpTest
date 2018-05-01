package com.kaicom.api.net.download;

import java.util.List;

import com.kaicom.api.biz.BizException;
import com.kaicom.api.db.BaseDaoOperator;
import com.kaicom.api.db.DaoWrapper;

public class DownloadDaoOperator<T, K> implements BaseDaoOperator<T> {
    
    protected DaoWrapper<T, K> daoWrapper;
    
    public DownloadDaoOperator(Class<T> entityClass) {
        daoWrapper = new DaoWrapper<T, K>(entityClass);
    }

    @Override
    public long insert(T entity) throws BizException {
        return daoWrapper.insert(entity);
    }

    @Override
    public boolean insertList(Iterable<T> entities) throws BizException {
        return daoWrapper.insertInTx(entities);
    }

    @Override
    public boolean insertOrReplaceList(Iterable<T> entities) throws BizException {
        return daoWrapper.insertOrReplaceInTx(entities);
    }

    @Override
    public List<T> findAll() throws BizException {
        return daoWrapper.findAll();
    }

    @Override
    public long count() throws BizException {
        return daoWrapper.count();
    }

    @Override
    public boolean deleteAll() throws BizException {
        return daoWrapper.deleteAll();
    }

    @Override
    public void refresh(T entity) throws BizException {
        daoWrapper.refresh(entity);
    }
    
}
