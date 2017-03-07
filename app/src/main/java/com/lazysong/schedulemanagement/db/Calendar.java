package com.lazysong.schedulemanagement.db;

public class Calendar {
	private String calendarNo;
	private String calendarName;
	private String date;
	private String time;
	private String place;
	private String description;
	private String repetition;
	private String advanceTime;
	private String valid;
	
	
	public String getCalendarNo() {
		return calendarNo;
	}
	public void setCalendarNo(String calendarNo) {
		this.calendarNo = calendarNo;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRepetition() {
		return repetition;
	}
	public void setRepetition(String repetition) {
		this.repetition = repetition;
	}
	public String getAdvanceTime() {
		return advanceTime;
	}
	public void setAdvanceTime(String advanceTime) {
		this.advanceTime = advanceTime;
	}
	public String isValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
}
