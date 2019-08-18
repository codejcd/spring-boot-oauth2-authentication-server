package com.codejcd.dao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.codejcd.entity.User;

@Repository
public class UserDao {
	private final static String NAMESPACE = "com.codejcd.mapper.UserMapper.";
	
	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;
	
	   public User selectUserByUserId(String userId) {
	    	return sqlSession.selectOne(NAMESPACE + "selectUserByUserId", userId);
	   }
	   
	   public User selectUser(String userId, String password) {
		   User user = new User();
		   user.setUserId(userId);
		   user.setPassword(password);
		   
		   return sqlSession.selectOne(NAMESPACE + "selectUser", user);
	   }
	   
		public int registUser(String userId, String userName, String password) {
			User user = new User();
			
			user.setUserId(userId);
			user.setName(userName);
			user.setPassword(password);
			
			return sqlSession.insert(NAMESPACE + "registUser", user); 
		}

}
