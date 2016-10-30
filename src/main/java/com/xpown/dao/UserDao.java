package com.xpown.dao;

import org.springframework.stereotype.Repository;

import com.xpown.model.User;
import com.xpown.mybatis.BaseMybatisDao;


@Repository("userDao")
public class UserDao<E> extends BaseMybatisDao<User, Integer> {

	@Override
	public String getMybatisMapperNamesapce() {
		return "com.xpown.model.UserMapper";
	}
	
}
