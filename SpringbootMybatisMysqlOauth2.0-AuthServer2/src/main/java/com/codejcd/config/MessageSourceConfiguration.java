package com.codejcd.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 메시지 설정
 * @author Jeon
 *
 */
@Configuration
public class MessageSourceConfiguration {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();

	    source.setBasename("classpath:/messages/message"); // 메시지 파일 위치
	    source.setDefaultEncoding("UTF-8");
	    source.setCacheSeconds(10);
	    source.setUseCodeAsDefaultMessage(true);
	    Locale.setDefault(Locale.KOREA); // 기본 Locale
	   
	    return source;
	}


}
