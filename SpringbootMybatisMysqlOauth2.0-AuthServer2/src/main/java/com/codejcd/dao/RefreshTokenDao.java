package com.codejcd.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.codejcd.entity.RefreshToken;

@Repository
public class RefreshTokenDao {
	private final static String NAMESPACE = "com.codejcd.mapper.RefreshTokenMapper.";
	
	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;
	
	public int countRefreshToken(RefreshToken refreshToken) {
		return sqlSession.selectOne(NAMESPACE+ "countRefreshTokenByToken", refreshToken);
	}
	
	public RefreshToken selectRefreshToken(RefreshToken refreshToken) {
		return sqlSession.selectOne(NAMESPACE+ "selectRefreshTokenByAuthentication", refreshToken);
	}
	
	public int insertRefreshToken(RefreshToken refreshToken) {
		return sqlSession.insert(NAMESPACE + "insertRefreshToken", refreshToken);
	}
	
	public int deleteRefreshToken(RefreshToken refreshToken) {
		return sqlSession.delete(NAMESPACE+ "deleteRefreshTokenByAuthentication", refreshToken);
	}
	
	public int updateRefreshToken(RefreshToken refreshToken) {
		return sqlSession.delete(NAMESPACE+ "updateRefreshTokenByAuthentication", refreshToken);
	}
}
