package com.codejcd.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.codejcd.common.CustomException;
import com.codejcd.common.MessageProperties;
import com.codejcd.common.Response;
import com.codejcd.entity.User;
import com.codejcd.service.ClientService;
import com.codejcd.service.TokenService;
import com.codejcd.service.UserService;

@RestController
public class TokenController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClientService clientService;
	
	/**
	 * 토큰 발급
	 * token generation
	 * @param authorization
	 * @param userId
	 * @param password
	 * @return
	 */
    @RequestMapping("/oauth2/tokens")
    public Response generateToken(@RequestHeader(value="Authorization") String authorization,
    			@RequestParam(value="userId", defaultValue="") String userId,
    			@RequestParam(value="password", defaultValue="") String password) {
    	
    	Response response = new Response();
    	HashMap<String, Object> result = new HashMap<>(); 
    	
    	try {   
    		// Basic Auth 검증 
    		clientService.checkClientByClientId(authorization);
    		// User 검증
    		User user = userService.matchUserPassword(userId, password);
    		// 토큰 생성
        	result = tokenService.getTokens(user);
        	response.setResponseCode(MessageProperties.prop("error.code.common.success"));
    		response.setResponseMessage(MessageProperties.prop("error.message.common.success"));
    		response.setResult(result);
    	} catch(CustomException e) {
    		response.setResponseCode(MessageProperties.prop(e.getErrorCode()));
    		response.setResponseMessage(MessageProperties.prop(e.getErrorMessage()));
    	} catch(Exception e) {
            response.setResponseCode(MessageProperties.prop("error.code.common.occured.exception"));
            response.setResponseMessage(MessageProperties.prop("error.message.common.occured.exception"));
            e.printStackTrace();
    	} finally {
    		
    	}
    	return response;
    }
    
    /**
     * 토큰 연장
     * token refresh
     * @param authorization
     * @param refreshToken
     * @return
     */
    @RequestMapping("/oauth2/accessToken/refresh")
    public Response refreshToken(@RequestHeader(value="Authorization") String authorization,
			@RequestParam(value="refreshToken", defaultValue="") String refreshToken) {
    	
    	Response response = new Response();
    	HashMap<String, Object> result = new HashMap<>();
    	String accessToken = "";
    	try {
    		clientService.checkClientByClientId(authorization);
    		accessToken = tokenService.refreshAccessToken(refreshToken);
    		
        	response.setResponseCode(MessageProperties.prop("error.code.common.success"));
    		response.setResponseMessage(MessageProperties.prop("error.message.common.success"));
    		result.put("accessToken", accessToken);
    		response.setResult(result);
      	} catch(CustomException e) {
    		response.setResponseCode(e.getErrorCode());
    		response.setResponseMessage(e.getErrorMessage());
    	} catch(Exception e) {
            response.setResponseCode(MessageProperties.prop("error.code.common.occured.exception"));
            response.setResponseMessage(MessageProperties.prop("error.message.common.occured.exception"));
    	} finally {
    		
    	}
    	return response;
    }
}
