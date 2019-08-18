package com.codejcd.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.codejcd.common.CustomException;
import com.codejcd.common.MessageProperties;
import com.codejcd.entity.User;
import com.codejcd.util.DateUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@PropertySource("classpath:jwt.properties")
public class TokenServiceImpl implements TokenService {
	
	@Value("${jwt.secretKey}")
	private String SECRET_KEY;
	
	@Value("${jwt.expireDay}")
	private String EXPIRE_DAY;
	
	@Value("${jwt.issuer}")
	private String ISSUER;
	
	@Value("${jwt.audience}")
	private String AUDIENCE;
	
	@Value("${jwt.subject}")
	private String SUBJECT;
	
	@Autowired
	private UserService userService;
	
	@Override
	public String getAccessToken(User user) throws Exception {
		if (null == user) {
			throw new CustomException(MessageProperties.prop("error.code.userId.invalid")
					, MessageProperties.prop("error.message.userId.invalid"));
		}
		
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(Base64.encodeBase64String(SECRET_KEY.getBytes()));
		Key signKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
		
		String strExpiration = DateUtil.addDate(DateUtil.getNow(DateUtil.YYYYMMDDHHmm), DateUtil.YYYYMMDDHHmm, Integer.parseInt(EXPIRE_DAY));
		Date expiration = DateUtil.getDate(strExpiration, DateUtil.YYYYMMDDHHmm);
		
		String userId = user.getUserId();
		
		if (null == userId && "".equals(userId)) {
			throw new CustomException(MessageProperties.prop("error.code.userId.invalid")
					, MessageProperties.prop("error.message.userId.invalid"));	
		}
		
	    JwtBuilder jwtBuilder = Jwts.builder()
	                .setId(userId) //jti(고유식별자)
	                .setAudience(AUDIENCE) // 토큰 대상자
	                .setIssuedAt(DateUtil.getNowDate()) //iat 발행날짜
	                .setSubject(AUDIENCE) //sub 제목 
	                .setIssuer(ISSUER) // iss 발급자
	                .setExpiration(expiration) // exp 만료날짜
	                .signWith(signatureAlgorithm, signKey); // sign algo, key
		
		return jwtBuilder.compact();
	}

	@Override
	public boolean checkAccessToken(String accessToken) throws Exception {
		if (null == accessToken || "".equals(accessToken)) {
			throw new CustomException(MessageProperties.prop("error.code.token.invalid")
					, MessageProperties.prop("error.message.token.invalid"));
		}
		
		boolean result = false;
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(Base64.encodeBase64String(SECRET_KEY.getBytes()));
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
		 
		if (DateUtil.isBefore(expitration, now)) {
			throw new CustomException(MessageProperties.prop("error.code.token.expiration")
					, MessageProperties.prop("error.message.token.expiration"));
		}
		
		if (null == claims.getIssuer() ||  "".equals(claims.getIssuer())) {
			throw new CustomException(MessageProperties.prop("error.code.token.issuer.invalid")
					, MessageProperties.prop("error.message.token.issuer.invalid"));		
		}
		issuer = claims.getIssuer();
		
		if (!ISSUER.equals(issuer)) {
			throw new CustomException(MessageProperties.prop("error.code.token.issuer.invalid")
					, MessageProperties.prop("error.message.token.issuer.invalid"));
		}
		
		if (null == claims.getAudience() ||  "".equals(claims.getAudience())) {
			throw new CustomException(MessageProperties.prop("error.code.token.audience.invalid")
					, MessageProperties.prop("error.message.token.audience.invalid"));		
		}
		audience = claims.getAudience();
		
		if (!AUDIENCE.equals(audience)) {
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
	public String refreshAccessToken(String accessToken) throws Exception {
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(Base64.encodeBase64String(SECRET_KEY.getBytes()));
		Claims claims = null;
		
		if (!this.checkAccessToken(accessToken)) {
			throw new CustomException(MessageProperties.prop("error.code.token.invalid")
					, MessageProperties.prop("error.message.token.invalid"));
		}
		
		claims = Jwts.parser()
				.setSigningKey(secretKeyBytes)
				.parseClaimsJws(accessToken).getBody();
	
		String strExpiration = DateUtil.addDate(DateUtil.getNow(DateUtil.YYYYMMDDHHmm), DateUtil.YYYYMMDDHHmm, Integer.parseInt(EXPIRE_DAY));
		Date expiration = DateUtil.getDate(strExpiration, DateUtil.YYYYMMDDHHmm);
		
		claims.setExpiration(expiration);
	    JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims);
	    
		return jwtBuilder.compact();
	}
}
