package com.lazysong.schedulemanagement.alarm;


import java.util.Calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.calendar.CalendarSpecificActivity;
import com.lazysong.schedulemanagement.db.CalendarDataOperation;
import com.lazysong.schedulemanagement.db.MyCalendar;
import com.lazysong.schedulemanagement.task.TaskEvaluateActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends Activity {
	private MediaPlayer alarmMusic;
	private int calendarNo;
//	private NotificationManager myNotiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		
		calendarNo = getIntent().getExtras().getInt("calNo");
		CalendarDataOperation oper = new CalendarDataOperation(this);
		MyCalendar calendar = oper.getRecord2(calendarNo + "");
		String calendarName = calendar.getCalendarName();
		if(calendarName.length() == 0)
			calendarName = "δ�����ճ�";
		
		alarmMusic = MediaPlayer.create(this, R.raw.qingfeideyi);
		alarmMusic.setLooping(true);
		//��������
		alarmMusic.start();
		new AlertDialog.Builder(AlarmActivity.this).setTitle("����")
		.setMessage(calendarName)
		.setPositiveButton("ȷ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				alarmMusic.stop();
				Intent intent = new Intent();
				intent.setClass(AlarmActivity.this, CalendarSpecificActivity.class);
				intent.putExtra("calendarNo", calendarNo + "");
				startActivity(intent);
				AlarmActivity.this.finish();
			}
		}).show();
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		Log.v("alarmTag", "�����¼��Ѵ�����ʱ�� ��" + cal.getTime());
//		this.finish(); 
		

//		// TODO Auto-generated method stub
//		myNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);				
//		setNotiType(R.drawable.ic_launcher, "sdf");
//		this.finish();

	}
	
//	/* ����Notification��method */
//	  private void setNotiType(int iconId, String text)
//	  {
//	    /* �����µ�Intent����Ϊ��ѡNotification������ʱ��
//	     * ��ִ�е�Activity */ 
//	    Intent notifyIntent=new Intent(this,TaskEvaluateActivity.class);  
//	    notifyIntent.putExtra("taskNo", 1);
//	    notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//	    /* ����PendingIntent��Ϊ�趨����ִ�е�Activity */ 
//	    PendingIntent appIntent=PendingIntent.getActivity(this,0,
//	                                                      notifyIntent,0);
//	       
//	    /* ����Notication�����趨��ز��� */ 
//	    Notification myNoti=new Notification();
////	    Notification myNoti = new Notification(
////				R.drawable.ic_launcher, "This is ticker text",
////				System.currentTimeMillis());
//	    /* �趨statusbar��ʾ��icon */
//	    myNoti.icon=iconId;
//	    /* �趨statusbar��ʾ������ѶϢ */
//	    myNoti.tickerText=text;
//	    /* �趨notification����ʱͬʱ����Ԥ������ */
//	    myNoti.defaults=Notification.DEFAULT_SOUND;
//	    /* �趨Notification�������Ĳ��� */
//	    myNoti.setLatestEventInfo(this,"MSN����״̬",text,appIntent);
//	    /* �ͳ�Notification */
//	    myNotiManager.notify(1,myNoti);
//	  } 
}
