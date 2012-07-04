package com.robertboloc.presence.pojo;

import java.sql.Timestamp;

public class Status {

	private String status = "";
	private Timestamp timestamp;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
