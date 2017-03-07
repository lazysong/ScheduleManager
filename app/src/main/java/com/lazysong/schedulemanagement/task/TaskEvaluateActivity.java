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
	private SeekBar seek;//可拖动的滚动条
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
		text1.setText("任务"+taskNo);
		
		name=(TextView)findViewById(R.id.name);
		des=(TextView)findViewById(R.id.des);
		
		oper = new TaskDataOperation(this);
		task = new MyTask();
		
		//获取传过来的planNo所对应的plan的属性
		task = oper.getTaskRecord(Integer.parseInt(taskNo));
		Log.v("checkPlan", "查询任务--" + task.getInformation());
		
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
	                text1.setText("停止调节完成度  "+processBar);
	            }
	            
	            @Override
	            public void onStartTrackingTouch(SeekBar seekBar) {
	                // TODO Auto-generated method stub
	            	text1.setText("开始调节完成度  ");
	            }
	            
	            @Override
	            public void onProgressChanged(SeekBar seekBar, int progress,
	                    boolean fromUser) {
	                // TODO Auto-generated method stub
	                text1.setText("当前值 为:"+progress);
	                processBar = progress;
	            }
	        });
		
		
		
		save = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.cancel);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//update(planNo);更新计划数据
				//下面测试更新函数，时间改变
				taskName = name.getText().toString();
				taskDes = des.getText().toString();
//				Log.v("checkPlan", "更新操作执行完毕，task表数据段no为--" +Integer.parseInt(taskNo));
//				Log.v("checkPlan", "更新操作执行完毕，task表数据段diff为--" +tdiff);
//				Log.v("checkPlan", "更新操作执行完毕，task表数据段prio为--" +tpriority);
				
				//oper.updateTaskRecord(Integer.parseInt(taskNo), taskName, );
				oper.setCompletence(Integer.parseInt(taskNo),processBar);
				oper.setState(4, Integer.parseInt(taskNo));
				task = oper.getTaskRecord(Integer.parseInt(taskNo));
				Log.v("checkPlan", "更新操作执行完毕，task表数据段为--" + task.getInformation());
//				tag = "MyLog";
//				Log.v(tag, "MainActivity的OnCreate()函数被调用");
//				Toast.makeText(getApplicationContext(), "任务更新成功",
//						Toast.LENGTH_SHORT).show();
				
				//关闭数据库连接
				oper.close();
				
				/**通过Calendar对象cal将输入的日期、时间的具体数据赋值给
				 * year,month,day,hour,minute
				 * 这五个变量原来的值是当天的日期、时间*/
//				year = c.get(Calendar.YEAR);
//				month = c.get(Calendar.MONTH);
//				day = c.get(Calendar.DAY_OF_MONTH);
//				hour = c.get(Calendar.HOUR_OF_DAY);
//				minute = c.get(Calendar.MINUTE);
				
				//通过日期、时间值来设置闹钟
//				Toast.makeText(AddPlanActivity.this, "已添加了编号为" + CalendarDataOperation.recordCount + "的闹钟", Toast.LENGTH_SHORT).show();
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
        		//关闭数据库连接
        		oper.setState(3, Integer.parseInt(taskNo));
        		oper.close();
//				Intent intent = new Intent();
//				intent.setClass(TaskEvaluateActivity.this, com.lazysong.schedulemanagement.MainActivity.class);
//				startActivity(intent);
        		
        		//五分钟之后再提醒
        		Calendar cal = Calendar.getInstance();
        		AlarmManage manage = new AlarmManage(TaskEvaluateActivity.this);
        		manage.setNotification(Integer.parseInt(taskNo) + 10000, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE) + 5);
				TaskEvaluateActivity.this.finish();
			}
        });

			
	}
}
