package com.kaicom.api.db;

import java.util.List;

import com.kaicom.api.biz.BizException;

public interface BaseDaoOperator<T> {

    long insert(T entity) throws BizException;
    
    boolean insertList(Iterable<T> entities) throws BizException;
    
    boolean insertOrReplaceList(Iterable<T> entities) throws BizException;
    
    List<T> findAll() throws BizException;
    
    long count() throws BizException;
    
    boolean deleteAll() throws BizException;
    
    public void refresh(T entity) throws BizException;
    
}
