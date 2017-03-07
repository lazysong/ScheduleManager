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
		//设置状态字段为3
		TaskDataOperation oper = new TaskDataOperation(NotificationActivity.this);
		oper.setState(3, taskNo);
		oper.close();
//		Toast.makeText(NotificationActivity.this, "从notificationactivitt中getextras转换为taskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
		setNotiType(R.drawable.ic_launcher, "是时候评估一波完成度啦");

		
		this.finish();
	}
	
	/* 发出Notification的method */
	  public void setNotiType(int iconId, String text)
	  {
	    /* 建立新的Intent，作为点选Notification留言条时，
	     * 会执行的Activity */ 
	    Intent notifyIntent = new Intent();
	    //判断是否是独立任务
	    TaskDataOperation oper = new TaskDataOperation(NotificationActivity.this);
	    MyTask task = oper.getTaskRecord(taskNo);
	    
	    if( task.getPlanNo()== -1 )
	    	notifyIntent = new Intent(this,SingleTaskEvaluateActivity.class); 
	    else
	    	notifyIntent = new Intent(this,TaskEvaluateActivity.class); 
	    oper.close();

//	    taskNo = 20;
//	    Toast.makeText(NotificationActivity.this, "在notificationactivitt中putExtras之前,taskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
	    notifyIntent.putExtra("taskNo", taskNo);
//	    Toast.makeText(NotificationActivity.this, "从notificationactivitt中putExtras之后,taskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
	    notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
	    /* 建立PendingIntent作为设定递延执行的Activity */ 
	    PendingIntent appIntent=PendingIntent.getActivity(this,taskNo,
	                                                      notifyIntent,0);
	       
	    /* 建立Notication，并设定相关参数 */ 
	    Notification myNoti=new Notification();

	    /* 设定statusbar显示的icon */
	    myNoti.icon=iconId;
	    /* 设定statusbar显示的文字讯息 */
	    myNoti.tickerText=text;
	    /* 设定notification发生时同时发出预设声音 */
	    myNoti.defaults=Notification.DEFAULT_SOUND;
	    /* 设定Notification留言条的参数 */
	    myNoti.setLatestEventInfo(this,"计划助手",text,appIntent);
	    /* 送出Notification */
	    myNotiManager.notify(1,myNoti);
	  } 
}
