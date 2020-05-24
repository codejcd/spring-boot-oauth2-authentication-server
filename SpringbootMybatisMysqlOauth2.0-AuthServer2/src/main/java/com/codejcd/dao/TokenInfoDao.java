package com.codejcd.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.codejcd.entity.TokenInfo;

@Repository
public class TokenInfoDao {
	private final static String NAMESPACE = "com.codejcd.mapper.TokenInfoMapper.";
	
	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;
	
	public TokenInfo selectTokenInfoByTokenType(TokenInfo tokenInfo) {
		return sqlSession.selectOne(NAMESPACE + "selectTokenInfoByTokenType", tokenInfo);
	}

}
