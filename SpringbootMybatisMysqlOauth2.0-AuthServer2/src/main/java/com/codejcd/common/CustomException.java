package com.codejcd.common;

/**
 * Customize Exception 
 * 커스터마이징 예외 처리 
 * @author Jeon
 *
 */
public class CustomException extends Exception {
	private static final long serialVersionUID = -3642478745758696786L;
	private String errorCode;
	private String errorMessage;
	private String errorDescription;
	
	public CustomException() {
	
	}
	
	public CustomException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public CustomException(String errorCode, String errorMessage, String errorDescription) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
