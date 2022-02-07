package com.codejcd.service;

import com.codejcd.entity.User;

public interface UserService {
	/**
	 * 유저 조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User selectUserByUserId(String userId) throws Exception;
	
	/**
	 * 유저 패스워드 확인
	 * @param userId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User matchUserPassword(String userId, String password) throws Exception;
	
	/**
	 * 유저 등록
	 * @param userId
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public int registUser(String userId, String userName, String password) throws Exception; 
}