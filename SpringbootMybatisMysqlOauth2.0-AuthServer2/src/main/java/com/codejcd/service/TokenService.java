package com.codejcd.service;

import java.util.HashMap;

import com.codejcd.common.CustomException;
import com.codejcd.entity.User;

public interface TokenService {
	
	/**
	 * 액세스 및 리프레시 토큰 발행
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getTokens(User user) throws Exception;
	
	/**
	 * 액세스 토큰 생성
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken(User user) throws Exception;
	
	/**
	 * 액세스 토큰 검사
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public boolean checkAccessToken(String accessToken) throws Exception;
	
	/**
	 * 액세스 토큰 재발행
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String refreshAccessToken(String accessToken) throws Exception;
	
	/**
	 * 리프레시 토큰 생성
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String getRefreshToken(User user) throws Exception;
	
	/**
	 * 리프레시 토큰 검사
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public User checkRefreshToken(String refreshToken) throws CustomException, Exception;
	
}
