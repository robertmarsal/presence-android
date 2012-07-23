package com.robertboloc.presence.pojo;

import java.sql.Timestamp;

public class Status {

	private String status = "";
	private long timestamp;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
