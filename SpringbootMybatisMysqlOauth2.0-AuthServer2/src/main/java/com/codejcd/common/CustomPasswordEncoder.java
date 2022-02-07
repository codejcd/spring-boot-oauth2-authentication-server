package com.codejcd.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * BCrypt Passwodencoder Component
 * BCrypt 의존성이 주입된 passwordencoder
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
	private PasswordEncoder passwordEncoder;
	
	public CustomPasswordEncoder() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	@Override
	public String encode(CharSequence rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}
	
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	

}
