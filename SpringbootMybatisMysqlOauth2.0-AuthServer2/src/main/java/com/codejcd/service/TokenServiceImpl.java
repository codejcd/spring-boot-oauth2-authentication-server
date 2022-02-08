package com.codejcd.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codejcd.common.CustomException;
import com.codejcd.common.MessageProperties;
import com.codejcd.dao.RefreshTokenDao;
import com.codejcd.entity.RefreshToken;
import com.codejcd.entity.User;
import com.codejcd.util.DateUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@PropertySource("classpath:jwt.properties")
public class TokenServiceImpl implements TokenService {
	
	@Value("${jwt.accessToken.secretKey}")
	private String ACCESS_TOKEN_SECRET_KEY;
	
	@Value("${jwt.accessToken.expireHour}")
	private String ACCESS_TOKEN_EXPIRE_DAY;
	
	@Value("${jwt.accessToken.issuer}")
	private String ACCESS_TOKEN_ISSUER;
	
	@Value("${jwt.accessToken.audience}")
	private String ACCESS_TOKEN_AUDIENCE;
	
	@Value("${jwt.accessToken.subject}")
	private String ACCESS_TOKEN_SUBJECT;
	
	@Value("${jwt.refreshToken.secretKey}")
	private String REFRESH_TOKEN_SECRET_KEY;
	
	@Value("${jwt.refreshToken.expireDay}")
	private String REFRESH_TOKEN_EXPIRE_DAY;
	
	@Value("${jwt.refreshToken.issuer}")
	private String REFRESH_TOKEN_ISSUER;
	
	@Value("${jwt.refreshToken.audience}")
	private String REFRESH_TOKEN_AUDIENCE;
	
	@Value("${jwt.refreshToken.subject}")
	private String REFRESH_TOKEN_SUBJECT;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RefreshTokenDao refreshTokenDao;
	
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public HashMap<String, Object> getTokens(User user) throws Exception {
		String accessToken = getAccessToken(user);
		String refreshToken = getRefreshToken(user);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("accessToken", accessToken);
		resultMap.put("refreshToken", refreshToken);
		return resultMap;
	}
	
	@Override
	public String getAccessToken(User user) throws Exception {
		if (null == user) {
			throw new CustomException(MessageProperties.prop("error.code.userId.invalid")
					, MessageProperties.prop("error.message.userId.invalid"));
		}
		
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(Base64.encodeBase64String(ACCESS_TOKEN_SECRET_KEY.getBytes()));
		Key signKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		String strExpiration = DateUtil.addHour(DateUtil.getNow(DateUtil.YYYYMMDDHHmm), DateUtil.YYYYMMDDHHmm, Integer.parseInt(ACCESS_TOKEN_EXPIRE_DAY));
		//String strExpiration = DateUtil.addMinute(DateUtil.getNow(DateUtil.YYYYMMDDHHmm), DateUtil.YYYYMMDDHHmm, 1);
		
		Date expiration = DateUtil.getDate(strExpiration, DateUtil.YYYYMMDDHHmm);
		//System.out.println("strExp : " + expiration);
		String userId = user.getUserId();
		
		if (null == userId && "".equals(userId)) {
			throw new CustomException(MessageProperties.prop("error.code.userId.invalid")
					, MessageProperties.prop("error.message.userId.invalid"));	
		}
		
	    JwtBuilder jwtBuilder = Jwts.builder()
	                .setId(userId) //jti(고유식별자)
	                .setAudience(ACCESS_TOKEN_AUDIENCE) // 토큰 대상자
	                .setIssuedAt(DateUtil.getNowDate()) //iat 발행날짜
	                .setSubject(ACCESS_TOKEN_SUBJECT) //sub 제목 
	                .setIssuer(ACCESS_TOKEN_ISSUER) // iss 발급자
	                .setExpiration(expiration) // exp 만료날짜
	                .signWith(signatureAlgorithm, signKey); // sign algo, key
		String temp =jwtBuilder.compact();
	    
	    checkAccessToken(temp);
	    
		return temp;
	}

	@Override
	public boolean checkAccessToken(String accessToken) throws Exception {
		if (null == accessToken || "".equals(accessToken)) {
			throw new CustomException(MessageProperties.prop("error.code.token.invalid")
					, MessageProperties.prop("error.message.token.invalid"));
		}
		
		boolean result = false;
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(Base64.encodeBase64String(ACCESS_TOKEN_SECRET_KEY.getBytes()));
		Claims claims = null;
		claims = Jwts.parser()
					.setSigningKey(secretKeyBytes)
					.parseClaimsJws(accessToken).getBody();
		
		Date expitration;
		Date now = DateUtil.getNowDate();
		String id;
		String issuer;
		String audience;
		
		
		if (null == claims.getExpiration()) {
			throw new CustomException(MessageProperties.prop("error.code.token.expiration.invalid")
					, MessageProperties.prop("error.message.token.expiration.invalid"));	
		}
		expitration = claims.getExpiration();
		System.out.println(expitration);
		 
		if (DateUtil.isBefore(expitration, now)) {
			throw new CustomException(MessageProperties.prop("error.code.token.expiration")
					, MessageProperties.prop("error.message.token.expiration"));
		}
		
		if (null == claims.getIssuer() ||  "".equals(claims.getIssuer())) {
			throw new CustomException(MessageProperties.prop("error.code.token.issuer.invalid")
					, MessageProperties.prop("error.message.token.issuer.invalid"));		
		}
		issuer = claims.getIssuer();
		
		if (!ACCESS_TOKEN_ISSUER.equals(issuer)) {
			throw new CustomException(MessageProperties.prop("error.code.token.issuer.invalid")
					, MessageProperties.prop("error.message.token.issuer.invalid"));
		}
		
		if (null == claims.getAudience() ||  "".equals(claims.getAudience())) {
			throw new CustomException(MessageProperties.prop("error.code.token.audience.invalid")
					, MessageProperties.prop("error.message.token.audience.invalid"));		
		}
		audience = claims.getAudience();
		
		if (!ACCESS_TOKEN_AUDIENCE.equals(audience)) {
			throw new CustomException(MessageProperties.prop("error.code.token.audience.invalid")
					, MessageProperties.prop("error.message.token.audience.invalid"));
		}
		
		if (null == claims.getId() ||  "".equals(claims.getId())) {
			throw new CustomException(MessageProperties.prop("error.code.token.id.invalid")
					, MessageProperties.prop("error.message.token.id.invalid"));		
		}
		id = claims.getId();	
		User user = userService.selectUserByUserId(id);
		
		if (null == user) {
			throw new CustomException(MessageProperties.prop("error.code.token.id.invalid")
					, MessageProperties.prop("error.message.token.id.invalid"));
		}
		result = true;
		//claims.getIssuedAt();
		//claims.getSubject();
		
		return result;
	}
	
	
	@Override
	public String refreshAccessToken(String refreshToken) throws Exception {
		User user = checkRefreshToken(refreshToken); 
		return getAccessToken(user);
	}
	
	@Override
	public String getRefreshToken(User user) throws Exception {
		if (null == user) {
			throw new CustomException(MessageProperties.prop("error.code.userId.invalid")
					, MessageProperties.prop("error.message.userId.invalid"));
		}
		
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(Base64.encodeBase64String(REFRESH_TOKEN_SECRET_KEY.getBytes()));
		Key signKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		String strExpiration = DateUtil.addDate(DateUtil.getNow(DateUtil.YYYYMMDDHHmm), DateUtil.YYYYMMDDHHmm, Integer.parseInt(REFRESH_TOKEN_EXPIRE_DAY));
		Date expiration = DateUtil.getDate(strExpiration, DateUtil.YYYYMMDDHHmm);
		
		String userId = user.getUserId();
		
		if (null == userId && "".equals(userId)) {
			throw new CustomException(MessageProperties.prop("error.code.userId.invalid")
					, MessageProperties.prop("error.message.userId.invalid"));	
		}
		
	    JwtBuilder jwtBuilder = Jwts.builder()
	                .setId(userId) //jti(고유식별자)
	                .setAudience(REFRESH_TOKEN_AUDIENCE) // 토큰 대상자
	                .setIssuedAt(DateUtil.getNowDate()) //iat 발행날짜
	                .setSubject(REFRESH_TOKEN_SUBJECT) //sub 제목 
	                .setIssuer(REFRESH_TOKEN_ISSUER) // iss 발급자
	                .setExpiration(expiration) // exp 만료날짜
	                .signWith(signatureAlgorithm, signKey); // sign algo, key
	    
	    final String token =  jwtBuilder.compact();
	    
	    RefreshToken refreshToken = new RefreshToken();
	    refreshToken.setAuthentication(String.valueOf(user.getUserSeq()));
	    refreshToken.setToken(token);
	    
	    RefreshToken duplicationRefreshToken = refreshTokenDao.selectRefreshToken(refreshToken);
	    
	    int result = 0;
	    
	    if (null != duplicationRefreshToken) {
	    	refreshToken.setRefreshTokenSeq(duplicationRefreshToken.getRefreshTokenSeq());
	    	result = refreshTokenDao.updateRefreshToken(refreshToken);
	    } else {
	   	  	result = refreshTokenDao.insertRefreshToken(refreshToken);
	    }
	    
	    if (0 == result) {
	    	throw new CustomException(MessageProperties.prop("error.code.common.occured.exception")
					, MessageProperties.prop("error.message.common.occured.exception"));	
	    }
	    
		return token;
	}
	
	@Override
	public User checkRefreshToken(String refreshToken) throws Exception {
		if (null == refreshToken || "".equals(refreshToken)) {
			throw new CustomException(MessageProperties.prop("error.code.token.invalid")
					, MessageProperties.prop("error.message.token.invalid"));
		}
		
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(Base64.encodeBase64String(REFRESH_TOKEN_SECRET_KEY.getBytes()));
		Claims claims = null;
		claims = Jwts.parser()
					.setSigningKey(secretKeyBytes)
					.parseClaimsJws(refreshToken).getBody();
		
		Date expitration;
		Date now = DateUtil.getNowDate();
		String id;
		String issuer;
		String audience;
		
		
		if (null == claims.getExpiration()) {
			throw new CustomException(MessageProperties.prop("error.code.token.expiration.invalid")
					, MessageProperties.prop("error.message.token.expiration.invalid"));	
		}
		expitration = claims.getExpiration();
		 
		if (DateUtil.isBefore(expitration, now)) {
			throw new CustomException(MessageProperties.prop("error.code.token.expiration")
					, MessageProperties.prop("error.message.token.expiration"));
		}
		
		if (null == claims.getIssuer() ||  "".equals(claims.getIssuer())) {
			throw new CustomException(MessageProperties.prop("error.code.token.issuer.invalid")
					, MessageProperties.prop("error.message.token.issuer.invalid"));		
		}
		issuer = claims.getIssuer();
		
		if (!REFRESH_TOKEN_ISSUER.equals(issuer)) {
			throw new CustomException(MessageProperties.prop("error.code.token.issuer.invalid")
					, MessageProperties.prop("error.message.token.issuer.invalid"));
		}
		
		if (null == claims.getAudience() ||  "".equals(claims.getAudience())) {
			throw new CustomException(MessageProperties.prop("error.code.token.audience.invalid")
					, MessageProperties.prop("error.message.token.audience.invalid"));		
		}
		audience = claims.getAudience();
		
		if (!REFRESH_TOKEN_AUDIENCE.equals(audience)) {
			throw new CustomException(MessageProperties.prop("error.code.token.audience.invalid")
					, MessageProperties.prop("error.message.token.audience.invalid"));
		}
		
		if (null == claims.getId() ||  "".equals(claims.getId())) {
			throw new CustomException(MessageProperties.prop("error.code.token.id.invalid")
					, MessageProperties.prop("error.message.token.id.invalid"));		
		}
		id = claims.getId();	
		User user = userService.selectUserByUserId(id);
		
		if (null == user) {
			throw new CustomException(MessageProperties.prop("error.code.token.id.invalid")
					, MessageProperties.prop("error.message.token.id.invalid"));
		}	
		return user;
	}
}
