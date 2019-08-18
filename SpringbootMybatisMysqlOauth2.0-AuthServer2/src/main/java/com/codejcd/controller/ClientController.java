package com.codejcd.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codejcd.common.CustomException;
import com.codejcd.common.MessageProperties;
import com.codejcd.common.Response;
import com.codejcd.service.ClientService;

/**
 * Client 컨트롤러
 * @author Jeon
 *
 */
@RestController
public class ClientController {

	@Autowired
	private ClientService clientService;
	
	/**
	 * Client 등록
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
    @RequestMapping("/client/regist")
    public Response clientRegist(@RequestParam(value="clientId", defaultValue="") String clientId,
    	@RequestParam(value="clientSecret", defaultValue="") String clientSecret) {
    	
    	Response response = new Response();
    	
    	try {
    		clientService.registClient(clientId, clientSecret);
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
