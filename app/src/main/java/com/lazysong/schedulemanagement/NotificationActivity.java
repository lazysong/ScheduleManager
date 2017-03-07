package com.lazysong.schedulemanagement;

import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.task.SingleTaskEvaluateActivity;
import com.lazysong.schedulemanagement.task.TaskEvaluateActivity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class NotificationActivity extends Activity {
	private NotificationManager myNotiManager;
	private int taskNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		myNotiManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);				
		
		
		taskNo = getIntent().getExtras().getInt("nofiId") - 10000;
		//����״̬�ֶ�Ϊ3
		TaskDataOperation oper = new TaskDataOperation(NotificationActivity.this);
		oper.setState(3, taskNo);
		oper.close();
//		Toast.makeText(NotificationActivity.this, "��notificationactivitt��getextrasת��ΪtaskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
		setNotiType(R.drawable.ic_launcher, "��ʱ������һ����ɶ���");

		
		this.finish();
	}
	
	/* ����Notification��method */
	  public void setNotiType(int iconId, String text)
	  {
	    /* �����µ�Intent����Ϊ��ѡNotification������ʱ��
	     * ��ִ�е�Activity */ 
	    Intent notifyIntent = new Intent();
	    //�ж��Ƿ��Ƕ�������
	    TaskDataOperation oper = new TaskDataOperation(NotificationActivity.this);
	    MyTask task = oper.getTaskRecord(taskNo);
	    
	    if( task.getPlanNo()== -1 )
	    	notifyIntent = new Intent(this,SingleTaskEvaluateActivity.class); 
	    else
	    	notifyIntent = new Intent(this,TaskEvaluateActivity.class); 
	    oper.close();

//	    taskNo = 20;
//	    Toast.makeText(NotificationActivity.this, "��notificationactivitt��putExtras֮ǰ,taskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
	    notifyIntent.putExtra("taskNo", taskNo);
//	    Toast.makeText(NotificationActivity.this, "��notificationactivitt��putExtras֮��,taskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
	    notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
	    /* ����PendingIntent��Ϊ�趨����ִ�е�Activity */ 
	    PendingIntent appIntent=PendingIntent.getActivity(this,taskNo,
	                                                      notifyIntent,0);
	       
	    /* ����Notication�����趨��ز��� */ 
	    Notification myNoti=new Notification();

	    /* �趨statusbar��ʾ��icon */
	    myNoti.icon=iconId;
	    /* �趨statusbar��ʾ������ѶϢ */
	    myNoti.tickerText=text;
	    /* �趨notification����ʱͬʱ����Ԥ������ */
	    myNoti.defaults=Notification.DEFAULT_SOUND;
	    /* �趨Notification�������Ĳ��� */
	    myNoti.setLatestEventInfo(this,"�ƻ�����",text,appIntent);
	    /* �ͳ�Notification */
	    myNotiManager.notify(1,myNoti);
	  } 
}
