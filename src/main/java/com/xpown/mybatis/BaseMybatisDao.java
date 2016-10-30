package com.xpown.mybatis;
import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public abstract class BaseMybatisDao<E,PK extends Serializable> extends MybatisDao  implements EntityDao<E,PK> {
   
	private static Logger logger = LoggerFactory.getLogger(BaseMybatisDao.class);
	
    @SuppressWarnings("unchecked")
	public E getById(PK primaryKey) {
        return (E)getSqlSession().selectOne(getFindByPrimaryKeyStatement(), primaryKey);
    }
    
	public void deleteById(PK id) {
		getSqlSession().delete(getDeleteStatement(), id);
	}
	
    public void save(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		getSqlSession().insert(getInsertStatement(), entity);    	
    }
    
	public int update(E entity) {
		prepareObjectForSaveOrUpdate(entity);
		int affectCount = getSqlSession().update(getUpdateStatement(), entity);
		return affectCount;
	}
	
	
	/**
	 * 用于子类覆盖,在insert,update之前调用
	 * @param o
	 */
    protected void prepareObjectForSaveOrUpdate(E o) {
    			logger.info("prepareObjectForSaveOrUpdate...");
    			// System.out.println("kw...");
    }

    /*public String getMybatisMapperNamesapce() {
        throw new RuntimeException("not yet implement");
    }*/
    
    public abstract String getMybatisMapperNamesapce();
    
    public String getFindByPrimaryKeyStatement() {
        return getMybatisMapperNamesapce()+".getById";
    }

    public String getInsertStatement() {
        return getMybatisMapperNamesapce()+".insert";
    }

    public String getUpdateStatement() {
    		return getMybatisMapperNamesapce()+".updateById";
    }

    public String getDeleteStatement() {
    		return getMybatisMapperNamesapce()+".deleteById";
    }
    
    public String getFindStatement() {
    		return getMybatisMapperNamesapce()+".findAll";
    }
   
	public List<E> findAll() {
		// throw new UnsupportedOperationException();
		String statementName = getFindStatement();
		return getSqlSession().selectList(statementName);
	}

	
	public boolean isUnique(E entity, String uniquePropertyNames) {
		throw new UnsupportedOperationException();
	}
	
	public void flush() {
		//ignore
	}
}
