package com.lazysong.schedulemanagement.help;

import java.util.Calendar;

/**
 * @author lazysong
 * @category ����
 * ���ܰ�����
 * 1.�õ���ǰ�����ں�ʱ�䣬��"2012-2-9","21:34:98"
 * 2.�õ�������ꡢ�¡��պ�ʱ���֡��룬��2012,2,9,21,34,98
 * 3.�Ƚϸ��������ں͵�ǰ���ڵĴ�С
 * 4.�Ƚϸ�����ʱ��͵�ǰʱ��Ĵ�С
 * 5.�������ĵ����ճ̼�¼�Ƿ���ڣ������¼�¼�Ƿ���ڵ�����λ
 * 6.������ݿ��������ճ̼�¼�Ƿ���ڣ������¼�¼�Ƿ���ڵ�����λ
 *
 */

public class CheckValid {
//	private Context mContext;
	private Calendar c;
	private String currentTime;
	private String currentDate;
	private int currentYear;
	private int currentMonth;
	private int currentDay;
	private int currentHour;
	private int currentMinute;
	private int currentSecond;
	
	/**
	 * ���������õ���ǰ�����ڡ�ʱ����Ϣ�������װ����صĳ�Ա������
	 */
	public CheckValid() {
//		mContext = context;
		c = Calendar.getInstance();
		currentYear = c.get(Calendar.YEAR);
		currentMonth = c.get(Calendar.MONTH) + 1;
		currentDay = c.get(Calendar.DAY_OF_MONTH);
		currentHour = c.get(Calendar.HOUR_OF_DAY);
		currentMinute = c.get(Calendar.MINUTE);
		currentSecond = c.get(Calendar.SECOND);
		if(currentMonth < 10)
			currentDate = "" + currentYear + "-0" + currentMonth;
		else
			currentDate = "" + currentYear + "-" + currentMonth;
		if (currentDay < 10)
			currentDate += "-" + "0" + currentDay;
		else
			currentDate += "-" + currentDay;
		
		if(currentHour < 10)
			currentTime = "0" + currentHour + ":";
		else
			currentTime = "" + currentHour + ":";
		if(currentMinute < 10)
			currentTime += "0"+ currentMinute + ":";
		else
			currentTime += currentMinute + ":";
		if(currentSecond < 10)
			currentTime += "0" + currentSecond;
		else
			currentTime += currentSecond;
//		currentDate = "" + currentYear + "-" + currentMonth + "-" + currentDay;
//		currentTime = "" + currentHour + ":" + currentMinute + ":" + currentSecond;
	}
	
	//�����ǳ�Ա�����ķ�װ��
	public int getCurrentYear() {
		return currentYear;
	}
	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}
	public int getCurrentMonth() {
		return currentMonth;
	}
	public void setCurrentMonth(int currentMonth) {
		this.currentMonth = currentMonth;
	}
	public int getCurrentDay() {
		return currentDay;
	}
	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}
	public int getCurrentHour() {
		return currentHour;
	}
	public void setCurrentHour(int currentHour) {
		this.currentHour = currentHour;
	}
	public int getCurrentMinute() {
		return currentMinute;
	}
	public void setCurrentMinute(int currentMinute) {
		this.currentMinute = currentMinute;
	}
	public int getCurrentSecond() {
		return currentSecond;
	}
	public void setCurrentSecond(int currentSecond) {
		this.currentSecond = currentSecond;
	}
	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	
	/**
	 * �������ĵ����ճ̼�¼�Ƿ����
	 * �����ڷ���false����δ�����򷵻�true
	 * 
	 * */
	public boolean checkCalendarValid(String date, String time) {
//		//���ݸ������ճ̱�Ŵ����ݿ���ȡ�����ں�ʱ������
//		String date = "2014-03-05";
//		String time = "20:09:28";
//		
//		//��ȡ��������ʱ����
//		String year = "2015";
//		String month = "04";
//		String day = "16";
//		String hour = "20";
//		String minute = "59";
//		String second = "28";
		
		//��������ʱ����ת��Ϊint��ֵ
		int intYear = getYearFromDate(date);
		int intMonth = getMonthFromDate(date);
		int intDay = getDayFromDate(date);
		int intHour = getHourFromTime(time);
		int intMinute = getMinuteFromTime(time);
		int intSecond = getSecondFromTime(time);
		
		//�ж��ճ��Ƿ����
		if(intYear > currentYear)
		return true;
		else if(intYear < currentYear)
			return false;
		else if(intMonth > currentMonth)
			return true;
		else if(intMonth < currentMonth)
			return false;
		else if(intDay > currentDay)
			return true;
		else if(intDay < currentDay)
			return false;
		else if(intHour > currentHour)
			return true;
		else if(intHour < currentHour)
			return false;
		else if(intMinute > currentMinute)
			return true;
		else if(intMinute < currentMinute)
			return false;
		else if(intSecond > currentSecond)
			return true;
		else if(intSecond < currentSecond)
			return false;
		else
			return true;
	}
	
	/**
	 * �������������ʱ���Ƿ���ڵ�ǰ�����ں�ʱ��
	 * @param date ��ʽ��������
	 * @param time ��ʽ����ʱ��
	 * */
	public boolean laterThanCurrent(String date, String time) {
		return checkCalendarValid(date, time);
	}
	/**
	 * ������ݿ������е��ճ̼�¼�Ƿ���ڣ�������������ճ��Ƿ���ڵ�����λ
	 * */
	public void checkCalendarsValid() {
		
	}

	/**
	 * ����'yyyy-mm-dd'��ʽ�����ڣ��õ����
	 */
	public int getYearFromDate(String date) {
		return Integer.parseInt(date.substring(0, 4));
	}
	/**
	 * ����'yyyy-mm-dd'��ʽ�����ڣ��õ��·�
	 */
	public int getMonthFromDate(String date) {
		return Integer.parseInt(date.substring(5, 7));
	}
	/**
	 * ����'yyyy-mm-dd'��ʽ�����ڣ��õ�����
	 */
	public int getDayFromDate(String date) {
		return Integer.parseInt(date.substring(8, 10));
	}
	/**
	 * ����'hh-mm-ss'��ʽ��ʱ�䣬�õ�Сʱ
	 */
	public int getHourFromTime(String time) {
		return Integer.parseInt(time.substring(0, 2));
	}
	/**
	 * ����'hh-mm-ss'��ʽ��ʱ�䣬�õ�����
	 */
	public int getMinuteFromTime(String time) {
		return Integer.parseInt(time.substring(3, 5));
	}
	/**
	 * ����'hh-mm-ss'��ʽ��ʱ�䣬�õ�����
	 */
	public int getSecondFromTime(String time) {
		return Integer.parseInt(time.substring(6, 8));
	}
}
