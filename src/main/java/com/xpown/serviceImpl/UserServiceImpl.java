package com.xpown.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xpown.baseservice.BaseServiceMybatisImpl;
import com.xpown.dao.UserDao;
import com.xpown.model.User;
import com.xpown.mybatis.EntityDao;
import com.xpown.service.UserService;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceMybatisImpl<User, String>
		implements UserService {

	@Resource
	private UserDao<?> userDao;

	@Override
	protected EntityDao<User, Integer> getEntityDao() {
		return userDao;
	}

}
