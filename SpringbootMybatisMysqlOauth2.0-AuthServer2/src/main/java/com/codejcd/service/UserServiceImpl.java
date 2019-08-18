package com.codejcd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codejcd.common.CustomException;
import com.codejcd.common.CustomPasswordEncoder;
import com.codejcd.common.MessageProperties;
import com.codejcd.dao.UserDao;
import com.codejcd.entity.User;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private CustomPasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;
	
	@Override
	public User selectUserByUserId(String userId) throws Exception {
		return userDao.selectUserByUserId(userId);
	}
	
	@Override
	public User matchUserPassword(String userId, String password) throws Exception {
		User user = userDao.selectUserByUserId(userId);
		
		boolean result = passwordEncoder.matches(password, user.getPassword());
		if (!result) {
			throw new CustomException(MessageProperties.prop("error.code.userPassword.invalid")
					, MessageProperties.prop("error.message.userPassword.invalid"));	
		}
		
		return user; 
	}
	
	@Override
	public int registUser(String userId, String userName, String password) throws Exception {
		password = passwordEncoder.encode(password);
		return userDao.registUser(userId, userName, password);
	}
	
	
}
