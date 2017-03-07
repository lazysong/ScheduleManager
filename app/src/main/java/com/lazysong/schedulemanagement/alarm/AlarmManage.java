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
		//ָ��Ҫ��ת�����ӽ���
		intent = new Intent(context,AlarmActivity.class);
	}
	
	/**
	 * @author lazy song
	 * @category �˺�����������һ�����ӣ�ͨ���ڶ��ճ̱�������Ӻ��޸ĵ�ʱ�򱻵���
	 * @param alarmId �����ı���������ֲ�ͬ�����ӣ����ճ̱��е�calendarNo�ֶ����
	 * @param repetition ���ӵ��ظ���ʽ�����ճ̱��е�repetition�ֶ����
	 * @param advanceTime ���ӵ���ǰʱ�䣬���ճ̱��е�advanceTime�ֶ����
	 * @param year ���ӵ����
	 * @param month ���ӵ��·�
	 * @param day ���ӵ�����
	 * @param hour ���ӵ�ʱ
	 * @param minute ���ӵķ�
	 */
	public void setAlarm(int alarmId, 
						String repetition, 
						String advanceTime,
						int year, int month, int day,
						int hour, int minute) {
		//����alarmId��ʱ�䡢���ڡ����ѷ�ʽ����������
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
    	
    	//������ǰ��ʱ��������ӵĴ�����
    	switch (advanceTime) {
		case "1":
			;
			break;
		case "2":
			//��ǰ5����
			c.set(Calendar.MINUTE, minute - 5);
			break;
		case "3":
			//��ǰ10����
			c.set(Calendar.MINUTE, minute - 10);
			break;
		case "4":
			//��ǰ30����
			c.set(Calendar.MINUTE, minute - 30);
			break;
		case "5":
			//��ǰ1Сʱ
			c.set(Calendar.HOUR_OF_DAY, hour - 1);
			break;
		case "6":
			//��ǰ2Сʱ
			c.set(Calendar.HOUR_OF_DAY, hour - 2);
			break;
		case "7":
			//��ǰ3Сʱ
			c.set(Calendar.HOUR_OF_DAY, hour - 3);
			break;
		default:
			;
			break;
    	}
    	
    	switch (repetition) {
		case "1":
			//������һ��
			aManager.set(AlarmManager.RTC_WAKEUP, 
	    			c.getTimeInMillis(), 
	    			pi);
			break;
		case "2":
			//ÿ������
			aManager.setRepeating(
					AlarmManager.RTC_WAKEUP, 
					c.getTimeInMillis(), 
					(60*60*24*1000), 
					pi);
			break;
		case "3":
			//ÿ������
			aManager.setRepeating(
					AlarmManager.RTC_WAKEUP, 
					c.getTimeInMillis(), 
					(7*60*60*24*1000), 
					pi);
			break;
		case "4":
			//ÿ������
			aManager.setRepeating(
					AlarmManager.RTC_WAKEUP, 
					c.getTimeInMillis(), 
					(30*60*60*24*1000), 
					pi);
			break;
		case "5":
			//ÿ������
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

    	Toast.makeText(context, "���óɹ�", Toast.LENGTH_SHORT).show();
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
//		Toast.makeText(context, "��alarmmanager��putextras :" + nofiId,
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
