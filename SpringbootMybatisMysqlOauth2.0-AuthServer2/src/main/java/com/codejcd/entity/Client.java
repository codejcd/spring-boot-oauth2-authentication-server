package com.codejcd.entity;

public class Client {
	private int clientSeq;
	private String clientId;
	private String clientSecret;
	private String status;
	private String regDate;
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	public int getClientSeq() {
		return clientSeq;
	}
	public void setClientSeq(int clientSeq) {
		this.clientSeq = clientSeq;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	
}
