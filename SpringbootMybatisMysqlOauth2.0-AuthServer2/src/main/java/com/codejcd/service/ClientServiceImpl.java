package com.codejcd.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codejcd.common.CustomException;
import com.codejcd.common.CustomPasswordEncoder;
import com.codejcd.common.MessageProperties;
import com.codejcd.dao.ClientDao;
import com.codejcd.entity.Client;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientDao clientDao;
	
	@Autowired
	private CustomPasswordEncoder passwordEncoder;
	
	@Override
	public Client selectClient(String authorization) throws Exception {
		
   		String[] client = null;
		if (authorization == null || !authorization.toLowerCase().startsWith("basic")) {
			throw new CustomException(MessageProperties.prop("error.code.authorization.invalid")
					, MessageProperties.prop("error.message.authorization.invalid"));	
		}
		
		 // Authorization: Basic base64credentials
	    String base64Credentials = authorization.substring("Basic".length()).trim();
	    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
	    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
	    // credentials = username:password
	    client = credentials.split(":", 2);
	    Client resultClient = clientDao.selectClient(client[0], client[1]);
	    if (null != resultClient) {
			throw new CustomException(MessageProperties.prop("error.code.authorization.invalid")
					, MessageProperties.prop("error.message.authorization.invalid"));	
	    }
		return resultClient;
	}
	
	@Override
	public int registClient(String clientId, String clientSecret) throws Exception {
		clientSecret = passwordEncoder.encode(clientSecret);
		return clientDao.registClient(clientId, clientSecret);
	}
}
