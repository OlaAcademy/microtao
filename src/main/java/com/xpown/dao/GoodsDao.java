package com.xpown.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xpown.model.Goods;
import com.xpown.mybatis.BaseMybatisDao;

@Repository
public class GoodsDao<E> extends BaseMybatisDao<Goods, String> {
	
	public String getMybatisMapperNamesapce() {
		return "com.xpown.model.GoodsMapper";
	}

	public List<Goods> getGoodsList(Map<String, Object> param) {
		return this.getSqlSession().selectList(this.getMybatisMapperNamesapce() + ".getGoodsList", param);
	}
}