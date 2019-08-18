package com.codejcd.common;

import java.text.MessageFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

/**
 * Message Component
 * 커스터마이징 메시지 
 * @author Jeon
 *
 */
@Component
public class MessageProperties implements MessageSourceAware{
	
    private static MessageSource messageSource;

    public void setMessageSource (MessageSource messageSource) {
        MessageProperties.messageSource = messageSource;
    }

    public static String prop(String key) {
        return messageSource.getMessage(key, null, Locale.KOREA);
    }

    public static String prop(String key, HttpServletRequest request) {
        return MessageProperties.msg(key, request);
    }

    public static String prop(String key, Locale locale) {
        return MessageProperties.msg(key, locale);
    }

    public static String propFormat(String key, HttpServletRequest request, Object...objects) {
        return MessageFormat.format(MessageProperties.msg(key, request), objects);
    }

    public static String propFormat(String key, Locale locale, Object...objects) {
        return MessageFormat.format(MessageProperties.msg(key, locale),objects);
    }

     public static String msg(String key, HttpServletRequest request){
         return messageSource.getMessage(key, null, Locale.KOREA);
     }

     public static String msg(String key, Locale locale){
         return messageSource.getMessage(key, null, locale);
     }

}
