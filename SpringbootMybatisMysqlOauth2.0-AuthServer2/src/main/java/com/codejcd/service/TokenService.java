package com.codejcd.service;

import com.codejcd.entity.User;

public interface TokenService {
	/**
	 * 토큰 생성
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String getAccessToken(User user) throws Exception;
	
	/**
	 * 토큰 검사
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public boolean checkAccessToken(String accessToken) throws Exception;
	
	/**
	 * 토큰 연장
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public String refreshAccessToken(String accessToken) throws Exception;
}
