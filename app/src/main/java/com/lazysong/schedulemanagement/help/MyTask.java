package com.lazysong.schedulemanagement.help;

public class MyTask {
	private int taskNo;
	private int planNo;
	private String taskName;
	private String taskDescription;
	private float difficulty;
	private float taskPriority;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private float completence;
	private int state;
	private float ability;
	
	public MyTask() {
	}
	
	public MyTask(	int taskNo, int planNo, String taskName, 
					String plnaDescription, float difficulty,
					float taskPriority, String startDate, 
					String startTime, String endDate, 
					String endTime, float completence, 
					float ability) {
		
		this.taskNo = taskNo;
		this.planNo = planNo;
		this.taskName = taskName;
		this.taskDescription = plnaDescription;
		this.difficulty = difficulty;
		this.taskPriority = taskPriority;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.completence = completence;
		this.ability = ability;
	}

	public int getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(int taskNo) {
		this.taskNo = taskNo;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	public float getTaskPriority() {
		return taskPriority;
	}

	public void setTaskPriority(float taskPriority) {
		this.taskPriority = taskPriority;
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

	public int getPlanNo() {
		return planNo;
	}

	public void setPlanNo(int planNo) {
		this.planNo = planNo;
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
		information = "taskNo: " + taskNo + "planNo: " + planNo + "taskName: " + taskName + 
				"taskDescription: " + taskDescription + "difficulty: " + difficulty + 
				"taskProirity: " + taskPriority + "startDate: " + startDate
				+ "startTime: " + startTime + "endDate: " + endDate + "endTime: " + endTime + 
				"completence: " + completence + completence + "state:" + state + "ability: " + ability;
		return information;
	}
}
