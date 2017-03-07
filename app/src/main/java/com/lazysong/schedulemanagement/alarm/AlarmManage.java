package com.lazysong.schedulemanagement.alarm;

import java.util.Calendar;

import com.lazysong.schedulemanagement.NotificationActivity;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.MyTask;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmManage {
	private Intent intent;
	private PendingIntent pi;
	private Context context;
	private AlarmManager aManager;
	public static int NOTIFICATION_ID = 10001;
	
	public AlarmManage(Context context) {
		this.context = context;
		aManager = (AlarmManager)(context.getSystemService(Service.ALARM_SERVICE));
		//指明要跳转的闹钟界面
		intent = new Intent(context,AlarmActivity.class);
	}
	
	/**
	 * @author lazy song
	 * @category 此函数用于设置一个闹钟，通常在对日程表进行增加和修改的时候被调用
	 * @param alarmId 给定的编号用于区分不同的闹钟，和日程表中的calendarNo字段相关
	 * @param repetition 闹钟的重复方式，和日程表中的repetition字段相关
	 * @param advanceTime 闹钟的提前时间，和日程表中的advanceTime字段相关
	 * @param year 闹钟的年份
	 * @param month 闹钟的月份
	 * @param day 闹钟的天数
	 * @param hour 闹钟的时
	 * @param minute 闹钟的分
	 */
	public void setAlarm(int alarmId, 
						String repetition, 
						String advanceTime,
						int year, int month, int day,
						int hour, int minute) {
		//根据alarmId和时间、日期、提醒方式来设置闹钟
		intent.putExtra("calNo", alarmId);
    	pi = PendingIntent.getActivity(context, alarmId, intent, 0);
    	Calendar c = Calendar.getInstance();
//    	c.setTimeInMillis(System.currentTimeMillis());
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month);
    	c.set(Calendar.DAY_OF_MONTH, day);
    	c.set(Calendar.HOUR_OF_DAY, hour);
    	c.set(Calendar.MINUTE, minute);
    	c.set(Calendar.SECOND, 0);
    	
    	//根据提前的时间调整闹钟的触发点
    	switch (advanceTime) {
		case "1":
			;
			break;
		case "2":
			//提前5分钟
			c.set(Calendar.MINUTE, minute - 5);
			break;
		case "3":
			//提前10分钟
			c.set(Calendar.MINUTE, minute - 10);
			break;
		case "4":
			//提前30分钟
			c.set(Calendar.MINUTE, minute - 30);
			break;
		case "5":
			//提前1小时
			c.set(Calendar.HOUR_OF_DAY, hour - 1);
			break;
		case "6":
			//提前2小时
			c.set(Calendar.HOUR_OF_DAY, hour - 2);
			break;
		case "7":
			//提前3小时
			c.set(Calendar.HOUR_OF_DAY, hour - 3);
			break;
		default:
			;
			break;
    	}
    	
    	switch (repetition) {
		case "1":
			//仅提醒一次
			aManager.set(AlarmManager.RTC_WAKEUP, 
	    			c.getTimeInMillis(), 
	    			pi);
			break;
		case "2":
			//每天提醒
			aManager.setRepeating(
					AlarmManager.RTC_WAKEUP, 
					c.getTimeInMillis(), 
					(60*60*24*1000), 
					pi);
			break;
		case "3":
			//每周提醒
			aManager.setRepeating(
					AlarmManager.RTC_WAKEUP, 
					c.getTimeInMillis(), 
					(7*60*60*24*1000), 
					pi);
			break;
		case "4":
			//每月提醒
			aManager.setRepeating(
					AlarmManager.RTC_WAKEUP, 
					c.getTimeInMillis(), 
					(30*60*60*24*1000), 
					pi);
			break;
		case "5":
			//每年提醒
			aManager.setRepeating(
					AlarmManager.RTC_WAKEUP, 
					c.getTimeInMillis(), 
					(365*60*60*24*1000), 
					pi);
			break;
		default:
//			textRepetition.setText("");
			;
			break;
		}

    	Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
	}
	
	public void cancleAlarm(int alarmId) {
		pi = PendingIntent.getActivity(context, alarmId, intent, 0);
		aManager.cancel(pi);
	}
	
	public void setNotification(
			int nofiId, 
			int year, int month, int dayOfMonth, 
			int hour, int minute) {
		intent = new Intent(context,NotificationActivity.class);
		intent.putExtra("nofiId", nofiId);
//		Toast.makeText(context, "从alarmmanager中putextras :" + nofiId,
//				Toast.LENGTH_SHORT).show();
		
		pi = PendingIntent.getActivity(context, nofiId, intent, 0);
//		pi = PendingIntent.getActivity(context, 100, intent, 0);
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month);
    	c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    	c.set(Calendar.HOUR_OF_DAY, hour);
    	c.set(Calendar.MINUTE, minute);
    	c.set(Calendar.SECOND, 0);
    	
    	aManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
	}
	
	public void cancleNotification(int nofiId) {
		pi = PendingIntent.getActivity(context, nofiId, intent, 0);
//		pi = PendingIntent.getActivity(context, 100, intent, 0);
		aManager.cancel(pi);
	}
}
