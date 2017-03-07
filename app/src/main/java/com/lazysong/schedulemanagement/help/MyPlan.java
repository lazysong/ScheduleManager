package com.lazysong.schedulemanagement.help;

public class MyPlan {
	private int planNo;
	private String planName;
	private String planDescription;
	private float difficulty;
	private float planPriority;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private float completence;
	private int state;
	private float ability;
	
	public MyPlan() {
		
	}
	
	public MyPlan(	int planNo, String planName, String plnaDescription, 
					float difficulty, float planPriority, String startDate, 
					String startTime, String endDate, String endTime, 
					float completence, float ability) {
		
		this.planNo = planNo;
		this.planName = planName;
		this.planDescription = plnaDescription;
		this.difficulty = difficulty;
		this.planPriority = planPriority;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.completence = completence;
		this.ability = ability;
	}
	
	public int getPlanNo() {
		return planNo;
	}
	public void setPlanNo(int planNo) {
		this.planNo = planNo;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanDescription() {
		return planDescription;
	}
	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}
	public float getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}
	public float getPlanPriority() {
		return planPriority;
	}
	public void setPlanPriority(float planPriority) {
		this.planPriority = planPriority;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public float getCompletence() {
		return completence;
	}
	public void setCompletence(float completence) {
		this.completence = completence;
	}
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getAbility() {
		return ability;
	}
	public void setAbility(float ability) {
		this.ability = ability;
	}

	/**
	 * 用于输出plan对象的成员变量的值
	 */
	public String getInformation() {
		String information;
		information = "planNo: " + planNo + "planName: " + planName + "planDescription: " + planDescription
				+ "difficulty: " + difficulty + "planProirity: " + planPriority + "startDate: " + startDate
				+ "startTime: " + startTime + "endDate: " + endDate + "endTime: " + endTime + 
				"completence: " + completence + "state:" + state + "ability: " + ability;
		return information;
	}
}
