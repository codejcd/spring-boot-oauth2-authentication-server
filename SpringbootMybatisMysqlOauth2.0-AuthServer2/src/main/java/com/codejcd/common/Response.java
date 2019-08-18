package com.codejcd.common;

/**
 * Customize Response
 * @author Jeon
 * 커스터마이징 응답 객체
 */
public class Response {

	private String responseCode;
	private String responseMessage;
	private Object result;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}
