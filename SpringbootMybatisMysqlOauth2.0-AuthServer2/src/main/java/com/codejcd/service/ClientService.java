package com.codejcd.service;

public interface ClientService {
	/**
	 * 클라이언트 조회
	 * @param authorization
	 * @return
	 * @throws Exception
	 */
	public void selectClientByClientId(String authorization) throws Exception;
	
	/**
	 * 클라이언트 등록
	 * @param clientId
	 * @param clientSecret
	 * @return
	 * @throws Exception
	 */
	public int registClient(String clientId, String clientSecret) throws Exception; 
}
