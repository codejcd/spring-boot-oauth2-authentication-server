package com.codejcd.entity;

public class TokenInfo {

	private int tokenInfoSeq;
	private String tokenType;
	private String secretkey;
	private int expireDay;
	private int expireHour;
	private String issuer;
	private String audience;
	private String subject;
	
	public int getTokenInfoSeq() {
		return tokenInfoSeq;
	}
	public void setTokenInfoSeq(int tokenInfoSeq) {
		this.tokenInfoSeq = tokenInfoSeq;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	public int getExpireDay() {
		return expireDay;
	}
	public void setExpireDay(int expireDay) {
		this.expireDay = expireDay;
	}
	public int getExpireHour() {
		return expireHour;
	}
	public void setExpireHour(int expireHour) {
		this.expireHour = expireHour;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
		
}
