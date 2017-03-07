package com.lazysong.schedulemanagement.help;

import java.util.Calendar;

/**
 * @author lazysong
 * @category 测试
 * 功能包括：
 * 1.得到当前的日期和时间，如"2012-2-9","21:34:98"
 * 2.得到具体的年、月、日和时、分、秒，如2012,2,9,21,34,98
 * 3.比较给定的日期和当前日期的大小
 * 4.比较给定的时间和当前时间的大小
 * 5.检查给定的单条日程记录是否过期，并更新记录是否过期的数据位
 * 6.检查数据库中所有日程记录是否过期，并更新记录是否过期的数据位
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
	 * 构造器，得到当前的日期、时间信息并将其封装到相关的成员变量中
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
	
	//下面是成员变量的封装器
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
	 * 检查给定的单条日程记录是否过期
	 * 若过期返回false，若未过期则返回true
	 * 
	 * */
	public boolean checkCalendarValid(String date, String time) {
//		//根据给定的日程编号从数据库中取出日期和时间数据
//		String date = "2014-03-05";
//		String time = "20:09:28";
//		
//		//提取出年月日时分秒
//		String year = "2015";
//		String month = "04";
//		String day = "16";
//		String hour = "20";
//		String minute = "59";
//		String second = "28";
		
		//将年月日时分秒转换为int型值
		int intYear = getYearFromDate(date);
		int intMonth = getMonthFromDate(date);
		int intDay = getDayFromDate(date);
		int intHour = getHourFromTime(time);
		int intMinute = getMinuteFromTime(time);
		int intSecond = getSecondFromTime(time);
		
		//判断日程是否过期
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
	 * 检查所给的日期时间是否大于当前的日期和时间
	 * @param date 格式化的日期
	 * @param time 格式化的时间
	 * */
	public boolean laterThanCurrent(String date, String time) {
		return checkCalendarValid(date, time);
	}
	/**
	 * 检查数据库中所有的日程记录是否过期，并更新其代表日程是否过期的数据位
	 * */
	public void checkCalendarsValid() {
		
	}

	/**
	 * 给定'yyyy-mm-dd'格式的日期，得到年份
	 */
	public int getYearFromDate(String date) {
		return Integer.parseInt(date.substring(0, 4));
	}
	/**
	 * 给定'yyyy-mm-dd'格式的日期，得到月份
	 */
	public int getMonthFromDate(String date) {
		return Integer.parseInt(date.substring(5, 7));
	}
	/**
	 * 给定'yyyy-mm-dd'格式的日期，得到天数
	 */
	public int getDayFromDate(String date) {
		return Integer.parseInt(date.substring(8, 10));
	}
	/**
	 * 给定'hh-mm-ss'格式的时间，得到小时
	 */
	public int getHourFromTime(String time) {
		return Integer.parseInt(time.substring(0, 2));
	}
	/**
	 * 给定'hh-mm-ss'格式的时间，得到分钟
	 */
	public int getMinuteFromTime(String time) {
		return Integer.parseInt(time.substring(3, 5));
	}
	/**
	 * 给定'hh-mm-ss'格式的时间，得到秒数
	 */
	public int getSecondFromTime(String time) {
		return Integer.parseInt(time.substring(6, 8));
	}
}
