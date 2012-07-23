package com.robertboloc.presence.pojo;

import java.util.Date;

public class Interval {
	
	private long timestart;
	private long timestop;
	private Integer timediff;
	
	public long getTimestart() {
		return timestart;
	}
	public void setTimestart(long timestart) {
		this.timestart = timestart;
	}
	public long getTimestop() {
		return timestop;
	}
	public void setTimestop(long timestop) {
		this.timestop = timestop;
	}
	public Integer getTimediff() {
		return timediff;
	}
	public void setTimediff(Integer timediff) {
		this.timediff = timediff;
	}
	
	public String toString(){ 
		Date startDate = new Date(timestart*1000);  
		Date stopDate = new Date(timestop*1000);  
		return startDate.toGMTString()+"\n"+stopDate.toGMTString();
	}

}
