package com.codejcd.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.codejcd.entity.Client;

@Repository
public class ClientDao {
	private final static String NAMESPACE = "com.codejcd.mapper.ClientMapper.";
	
	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;
	
	public Client selectClientByClientId(String clientId) {
		Client client = new Client();
		client.setClientId(clientId);
		
		return sqlSession.selectOne(NAMESPACE + "selectClientByClientId", client); 
	}
	
	public int registClient(String clientId, String clientSecret) {
		Client client = new Client();
		client.setClientId(clientId);
		client.setClientSecret(clientSecret);
		
		return sqlSession.insert(NAMESPACE + "registClient", client); 
	}
}
