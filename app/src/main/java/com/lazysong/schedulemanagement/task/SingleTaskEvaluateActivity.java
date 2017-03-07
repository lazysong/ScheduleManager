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
	private SeekBar seek;//���϶��Ĺ�����
	private int processBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_task_evaluate);
		
		//�����ȡ��״̬��֪ͨ
		NotificationManager manager = (NotificationManager) 
				getSystemService(NOTIFICATION_SERVICE); 
				 manager.cancel(1);
		
		taskNo = getIntent().getExtras().getInt("taskNo") + "";
//		Toast.makeText(SingleTaskEvaluateActivity.this, "��singleTaskEvaluate��getextras, taskNo :" + taskNo,
//				Toast.LENGTH_SHORT).show();
		text1=(TextView)findViewById(R.id.conclusion_evaluate_singletask);
		text1.setText("����"+taskNo);
		
		name=(TextView)findViewById(R.id.name_evaluate_singletask);
		des=(TextView)findViewById(R.id.des_evaluate_singletask);
		
		oper = new TaskDataOperation(this);
		task = new MyTask();
		
		//��ȡ��������taskNo����Ӧ��task������
		task = oper.getTaskRecord(Integer.parseInt(taskNo));
		name.setText(task.getTaskName());
		des.setText(task.getTaskDescription());
		
		seek=(SeekBar)findViewById(R.id.seekBar_evaluate_singletask);
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
		
		
		
		save = (Button)findViewById(R.id.save_evaluate_singletask);
		cancel = (Button)findViewById(R.id.cancel_evaluate_singletask);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//������Ը��º�����ʱ��ı�
				taskName = name.getText().toString();
				taskDes = des.getText().toString();
				
				oper.setCompletence(Integer.parseInt(taskNo),processBar);
				oper.setState(4, Integer.parseInt(taskNo));
//				Toast.makeText(SingleTaskEvaluateActivity.this, "��singleTaskEvaluate��������ɶȺ�״̬, taskNo :" + taskNo,
//						Toast.LENGTH_SHORT).show();

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
				

				SingleTaskEvaluateActivity.this.finish();
			}
		});
		
        cancel.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		//�ر����ݿ�����
        		oper.close();
        		//�Ƴ�����5����
        		Calendar cal = Calendar.getInstance();
        		AlarmManage manage = new AlarmManage(SingleTaskEvaluateActivity.this);
        		manage.setNotification(Integer.parseInt(taskNo) + 10000, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE) + 5);
				
        		SingleTaskEvaluateActivity.this.finish();
			}
        });

		
			
	}
}
