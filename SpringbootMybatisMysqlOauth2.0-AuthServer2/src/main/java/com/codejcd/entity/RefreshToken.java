package com.codejcd.entity;

public class RefreshToken extends Token {

	private int tokenId;
	private int refreshTokenSeq;
	private String authentication;
	
	public int getRefreshTokenSeq() {
		return refreshTokenSeq;
	}
	public void setRefreshTokenSeq(int refreshTokenSeq) {
		this.refreshTokenSeq = refreshTokenSeq;
	}
	public String getAuthentication() {
		return authentication;
	}
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}


}
