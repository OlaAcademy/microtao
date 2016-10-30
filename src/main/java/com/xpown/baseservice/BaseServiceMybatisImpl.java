package com.xpown.baseservice;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.xpown.mybatis.EntityDao;

@Transactional
public abstract class BaseServiceMybatisImpl <E,PK extends Serializable> implements BaseServiceMybatis<E, PK>{
	
		private static Logger logger = LoggerFactory.getLogger(BaseServiceMybatisImpl.class);

		protected abstract EntityDao getEntityDao();


		@Transactional(readOnly=true)
		public E getById(PK id) throws DataAccessException{
			return (E)getEntityDao().getById(id);
		}
		
		@Transactional(readOnly=true)
		public List<E> findAll() throws DataAccessException{
			return getEntityDao().findAll();
		}
		
		public void save(E entity) throws DataAccessException{
			logger.info("保存实体对象：{}", entity);
			getEntityDao().save(entity);
		}
		
		
		public void removeById(PK id) throws DataAccessException{
			getEntityDao().deleteById(id);
		}
		
		
		public void update(E entity) throws DataAccessException{
			getEntityDao().update(entity);
		}
		
		@Transactional(readOnly=true)
		public boolean isUnique(E entity, String uniquePropertyNames) throws DataAccessException {
			return getEntityDao().isUnique(entity, uniquePropertyNames);
		}
		
	}