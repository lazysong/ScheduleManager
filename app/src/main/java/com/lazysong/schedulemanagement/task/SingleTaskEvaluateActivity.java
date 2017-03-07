package com.lazysong.schedulemanagement.task;

import java.util.Calendar;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lazysong.schedulemanagement.NotificationActivity;
import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.MyTask;


public class SingleTaskEvaluateActivity extends Activity {

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
		setContentView(R.layout.activity_single_task_evaluate);
		
		//点击后取消状态栏通知
		NotificationManager manager = (NotificationManager) 
				getSystemService(NOTIFICATION_SERVICE); 
				 manager.cancel(1);
		
		taskNo = getIntent().getExtras().getInt("taskNo") + "";
//		Toast.makeText(SingleTaskEvaluateActivity.this, "从singleTaskEvaluate中getextras, taskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
		text1=(TextView)findViewById(R.id.conclusion_evaluate_singletask);
		text1.setText("任务"+taskNo);
		
		name=(TextView)findViewById(R.id.name_evaluate_singletask);
		des=(TextView)findViewById(R.id.des_evaluate_singletask);
		
		oper = new TaskDataOperation(this);
		task = new MyTask();
		
		//获取传过来的taskNo所对应的task的属性
		task = oper.getTaskRecord(Integer.parseInt(taskNo));
		name.setText(task.getTaskName());
		des.setText(task.getTaskDescription());
		
		seek=(SeekBar)findViewById(R.id.seekBar_evaluate_singletask);
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
		
		
		
		save = (Button)findViewById(R.id.save_evaluate_singletask);
		cancel = (Button)findViewById(R.id.cancel_evaluate_singletask);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//下面测试更新函数，时间改变
				taskName = name.getText().toString();
				taskDes = des.getText().toString();
				
				oper.setCompletence(Integer.parseInt(taskNo),processBar);
				oper.setState(4, Integer.parseInt(taskNo));
//				Toast.makeText(SingleTaskEvaluateActivity.this, "在singleTaskEvaluate中设置完成度和状态, taskNo :" + taskNo,
//						Toast.LENGTH_SHORT).show();

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
				

				SingleTaskEvaluateActivity.this.finish();
			}
		});
		
        cancel.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		//关闭数据库连接
        		oper.close();
        		//推迟提醒5分钟
        		Calendar cal = Calendar.getInstance();
        		AlarmManage manage = new AlarmManage(SingleTaskEvaluateActivity.this);
        		manage.setNotification(Integer.parseInt(taskNo) + 10000, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE) + 5);
				
        		SingleTaskEvaluateActivity.this.finish();
			}
        });

		
			
	}
}
