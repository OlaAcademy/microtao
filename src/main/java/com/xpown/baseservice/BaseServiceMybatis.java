package com.xpown.baseservice;

import java.io.Serializable;
import java.util.List;

import org.springframework.dao.DataAccessException;

/**
 * 保存实体对象接口
 * 
 * 命名规则：
 * 
 * 获取单个对象 get*** 查找对象 find*** 修改对象 update*** 保存对象 save*** 删除对象 remove*** 或
 * delete***
 * 
 * @author hujihong
 *
 * @param <E>
 * @param <PK>
 */

public interface BaseServiceMybatis<E, PK extends Serializable> {

	public abstract E getById(PK id) throws DataAccessException;

	public abstract List<E> findAll() throws DataAccessException;

	/** 根据id检查是否插入或是更新数据 */
	// public abstract void saveOrUpdate(E entity) throws DataAccessException;

	/** 插入数据 */
	public abstract void save(E entity) throws DataAccessException;

	public abstract void removeById(PK id) throws DataAccessException;

	public abstract void update(E entity) throws DataAccessException;

	public abstract boolean isUnique(E entity, String uniquePropertyNames)
			throws DataAccessException;

}