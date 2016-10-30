package com.xpown.mybatis;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.ReflectionUtils;

import com.xpown.utils.Page;



/**
 * Mybatis分页查询工具类,为分页查询增加传递:
 * startRow,endRow : 用于oracle分页使用,从1开始
 * offset,limit : 用于mysql 分页使用,从0开始
 * 
 */

@SuppressWarnings("unchecked")
public class MybatisDao extends SqlSessionDaoSupport {

        public <T> Page<T> selectPage(Page<T> page, String statementName, Object parameter) {
                String countStatementName = statementName + "Count";
                return selectPage(page, statementName, countStatementName, parameter);
        }

        public <T> Page<T> selectPage(Page<T> page, String statementName, String countStatementName, Object parameter) {

                Number totalItems = (Number) getSqlSession().selectOne(countStatementName, parameter);
        		//Number totalItems = 10;
                if (totalItems != null && totalItems.longValue() > 0) {
                        List list = getSqlSession().selectList(statementName, toParameterMap(parameter, page));
                        page.setResult(list);
                        page.setTotalCount(totalItems.intValue());
                }
                return page;
        }

        protected static Map toParameterMap(Object parameter, Page p) {
                Map map = toParameterMap(parameter);
                //map.put("startRow", p.getStartRow());
                //map.put("startRow", p.getOffset());
                //map.put("endRow", p.getEndRow());
                map.put("offset", p.getOffset());
                map.put("limit", p.getPageSize());
                return map;
        }

        protected static Map toParameterMap(Object parameter) {
                if (parameter instanceof Map) {
                        return (Map) parameter;
                } else {
                        try {
                                return PropertyUtils.describe(parameter);
                        } catch (Exception e) {
                                ReflectionUtils.handleReflectionException(e);
                                return null;
                        }
                }
        }
}