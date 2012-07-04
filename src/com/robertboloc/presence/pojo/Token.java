package com.robertboloc.presence.pojo;

import java.sql.Timestamp;

public class Token {
	
	private String token;
	private Timestamp timeexpires;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Timestamp getTimeexpires() {
		return timeexpires;
	}
	public void setTimeexpires(Timestamp timeexpires) {
		this.timeexpires = timeexpires;
	}

}
