package com.robertboloc.presence.pojo;

import java.sql.Timestamp;

public class Token {
	
	private String token;
	private Timestamp timestamp;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
