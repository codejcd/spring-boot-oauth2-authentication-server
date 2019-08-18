package com.codejcd.service;

import com.codejcd.entity.Client;

public interface ClientService {
	/**
	 * 클라이언트 조회
	 * @param authorization
	 * @return
	 * @throws Exception
	 */
	public Client selectClient(String authorization) throws Exception;
	
	/**
	 * 클라이언트 등록
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws Exception
	 */
	public int registClient(String clientId, String clientSecret) throws Exception; 
}
