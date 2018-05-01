/**
 * 
 */
package com.kaicom.mymvptest.datasource;


import com.kaicom.mymvptest.datasource.entity.AbstractBizEntity;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 数据库dao操作接口
 * 
 *
 */
public interface IBizDao<T> {

	/** 修改实体类 */
	public abstract void update(List<T> entities);

	long replace(T entity);

	/** 某个时间段 的 数据 */
	List<T> someTimebucketbizEntities(String beginTime, String endTime);

	long insert(T entity);

	boolean insertList(Iterable<T> entities);

	boolean insertOrReplaceList(Iterable<T> entities);

	List<T> getAll();

	long count();

	boolean deleteAll();

	public void refresh(T entity);



	/**
	 * 删除过期数据
	 * @return
	 */
	boolean deleteOldRecord();

	/**
	 * 删除一条数据
	 * @param entity
	 * @return
     */
	public boolean deleteOneData(AbstractBizEntity entity);

	boolean deleteDatas(Iterable<T> entitie);

	List<T> getListByQueryBuilder(QueryBuilder<T> qb);
}
