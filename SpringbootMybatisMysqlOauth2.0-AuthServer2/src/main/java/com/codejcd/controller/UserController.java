package com.codejcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codejcd.common.CustomException;
import com.codejcd.common.MessageProperties;
import com.codejcd.common.Response;
import com.codejcd.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * 유저 등록
	 * @param userId
	 * @param userName
	 * @param password
	 * @return
	 */
    @RequestMapping("/user/regist")
    public Response clientRegist(@RequestParam(value="userId", defaultValue="") String userId,
    	@RequestParam(value="userName", defaultValue="") String userName,
    	@RequestParam(value="password", defaultValue="") String password) {
    	
    	Response response = new Response();
    	
    	try {
    		userService.registUser(userId, userName, password);
        	response.setResponseCode(MessageProperties.prop("error.code.common.success"));
    		response.setResponseMessage(MessageProperties.prop("error.message.common.success"));
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
}
