package com.lazysong.schedulemanagement.task;

import java.util.Calendar;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.MyTask;


public class TaskEvaluateActivity extends Activity {

	private TextView text1,name,des;
	private String taskNo,taskName,taskDes;
	private TaskDataOperation oper;
	private MyTask task;
	private Button save ,cancel;
	private SeekBar seek;//���϶��Ĺ�����
	private int processBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_evaluate);
		
		NotificationManager manager = (NotificationManager) 
				getSystemService(NOTIFICATION_SERVICE); 
				 manager.cancel(1);
		
		Intent intent=getIntent();
		taskNo=intent.getExtras().getInt("taskNo") + "";
		text1=(TextView)findViewById(R.id.conclusion_total_completence);
		text1.setText("����"+taskNo);
		
		name=(TextView)findViewById(R.id.name);
		des=(TextView)findViewById(R.id.des);
		
		oper = new TaskDataOperation(this);
		task = new MyTask();
		
		//��ȡ��������planNo����Ӧ��plan������
		task = oper.getTaskRecord(Integer.parseInt(taskNo));
		Log.v("checkPlan", "��ѯ����--" + task.getInformation());
		
		//taskName = task.getTaskName();
//		planDes = plan.getPlanDescription();
		name.setText(task.getTaskName());
		des.setText(task.getTaskDescription());
		processBar = (int)task.getCompletence();
		
		seek=(SeekBar)findViewById(R.id.seekBar);
		seek.setProgress(processBar);  
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
	            
	            @Override
	            public void onStopTrackingTouch(SeekBar seekBar) {
	                // TODO Auto-generated method stub
	                text1.setText("ֹͣ������ɶ�  "+processBar);
	            }
	            
	            @Override
	            public void onStartTrackingTouch(SeekBar seekBar) {
	                // TODO Auto-generated method stub
	            	text1.setText("��ʼ������ɶ�  ");
	            }
	            
	            @Override
	            public void onProgressChanged(SeekBar seekBar, int progress,
	                    boolean fromUser) {
	                // TODO Auto-generated method stub
	                text1.setText("��ǰֵ Ϊ:"+progress);
	                processBar = progress;
	            }
	        });
		
		
		
		save = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.cancel);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//update(planNo);���¼ƻ�����
				//������Ը��º�����ʱ��ı�
				taskName = name.getText().toString();
				taskDes = des.getText().toString();
//				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�noΪ--" +Integer.parseInt(taskNo));
//				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�diffΪ--" +tdiff);
//				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�prioΪ--" +tpriority);
				
				//oper.updateTaskRecord(Integer.parseInt(taskNo), taskName, );
				oper.setCompletence(Integer.parseInt(taskNo),processBar);
				oper.setState(4, Integer.parseInt(taskNo));
				task = oper.getTaskRecord(Integer.parseInt(taskNo));
				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�Ϊ--" + task.getInformation());
//				tag = "MyLog";
//				Log.v(tag, "MainActivity��OnCreate()����������");
//				Toast.makeText(getApplicationContext(), "������³ɹ�",
//						Toast.LENGTH_SHORT).show();
				
				//�ر����ݿ�����
				oper.close();
				
				/**ͨ��Calendar����cal����������ڡ�ʱ��ľ������ݸ�ֵ��
				 * year,month,day,hour,minute
				 * ���������ԭ����ֵ�ǵ�������ڡ�ʱ��*/
//				year = c.get(Calendar.YEAR);
//				month = c.get(Calendar.MONTH);
//				day = c.get(Calendar.DAY_OF_MONTH);
//				hour = c.get(Calendar.HOUR_OF_DAY);
//				minute = c.get(Calendar.MINUTE);
				
				//ͨ�����ڡ�ʱ��ֵ����������
//				Toast.makeText(AddPlanActivity.this, "������˱��Ϊ" + CalendarDataOperation.recordCount + "������", Toast.LENGTH_SHORT).show();
//				AlarmManage manager = new AlarmManage(CalendarAddActivity.this);
//				manager.setAlarm(CalendarDataOperation.recordCount ++, repetition, advanceTime, year, month, day, hour, minute);
//				Intent intent = new Intent();
//				intent.setClass(TaskEvaluateActivity.this, com.lazysong.schedulemanagement.MainActivity.class);
//				startActivity(intent);
				TaskEvaluateActivity.this.finish();
			}
		});
		
        cancel.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		//�ر����ݿ�����
        		oper.setState(3, Integer.parseInt(taskNo));
        		oper.close();
//				Intent intent = new Intent();
//				intent.setClass(TaskEvaluateActivity.this, com.lazysong.schedulemanagement.MainActivity.class);
//				startActivity(intent);
        		
        		//�����֮��������
        		Calendar cal = Calendar.getInstance();
        		AlarmManage manage = new AlarmManage(TaskEvaluateActivity.this);
        		manage.setNotification(Integer.parseInt(taskNo) + 10000, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE) + 5);
				TaskEvaluateActivity.this.finish();
			}
        });

			
	}
}
