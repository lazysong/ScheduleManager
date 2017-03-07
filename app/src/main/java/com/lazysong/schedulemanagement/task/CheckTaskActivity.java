package com.lazysong.schedulemanagement.task;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.CheckValid;
import com.lazysong.schedulemanagement.help.MyTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckTaskActivity extends Activity {

	private int taskNo;
	private String name;
	private String description;
	private float difficulty;
	private float priority;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private float completence;
	private int state;
	private float ability;
	
	private Button back;
	private Button delete;
	private Button edit;
	private TextView stateTextView;
	private TextView nameTextView;
	private TextView descriptionTextView;
	private TextView difficultyTextView;
	private TextView priorityTextView;
	private TextView startDateTextView;
	private TextView startTimeTextView;
	private TextView endDateTextView;
	private TextView endTimeTextView;
	
	private TaskDataOperation oper;
	private String[] mapDifficulty = new String[]{"�ǳ���", "��", "һ��", "����", "�ǳ�����"};
	private String[] mapPriority = new String[]{"�����", "����", "Ҫ��", "��Ҫ��", "��Ĳ�Ҫ��"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_task);
		
		//ȡ��intent����ĵ�extras����
		taskNo = getIntent().getExtras().getInt("taskNo");
		
		//��ÿؼ��Ķ���
		back = (Button)findViewById(R.id.back_check_task);
		delete = (Button)findViewById(R.id.delete_check_task);
		edit = (Button)findViewById(R.id.edit_check_task);
		stateTextView = (TextView)findViewById(R.id.state_check_task);
		nameTextView = (TextView)findViewById(R.id.name_check_task);
		descriptionTextView = (TextView)findViewById(R.id.description_check_task);
		difficultyTextView = (TextView)findViewById(R.id.difficulty_check_task);
		priorityTextView = (TextView)findViewById(R.id.priority_check_task);
		startDateTextView = (TextView)findViewById(R.id.start_date_check_task);
		startTimeTextView = (TextView)findViewById(R.id.start_time_check_task);
		endDateTextView = (TextView)findViewById(R.id.end_date_check_task);
		endTimeTextView = (TextView)findViewById(R.id.end_time_check_task);
		
		updateData();
		
		//���ü�����
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckTaskActivity.this.finish();
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ִ��ɾ������
				oper = new TaskDataOperation(CheckTaskActivity.this);
				oper.delete(taskNo);
				AlarmManage manage = new AlarmManage(CheckTaskActivity.this);
				manage.cancleNotification(taskNo + 10000);
				oper.close();
				//����
				Toast.makeText(CheckTaskActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
				CheckTaskActivity.this.finish();
			}
		});
		
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CheckTaskActivity.this, EditTaskActivity.class);
				intent.putExtra("taskNo", taskNo);
				startActivity(intent);
			}
		});
				
	}
	
	public void updateData() {
		
		//�����ݿ��м�����task��¼
		oper = new TaskDataOperation(this);
		MyTask task = oper.getTaskRecord(taskNo);
		
		oper.refreshData(taskNo);
		
		//ȡ����Ҫ��ʾ���ֶ�
		name = task.getTaskName();
		description = task.getTaskDescription();
		difficulty = task.getDifficulty();
		priority = task.getTaskPriority();
		startDate = task.getStartDate();
		startTime = task.getStartTime();
		endDate = task.getEndDate();
		endTime = task.getEndTime();
		completence = task.getCompletence();
		state = task.getState();
		ability = task.getAbility();
		
		
		//�������״̬����ʾ��Ϣ
		String stateText = "";
		switch(state) {
		case 1:
			stateText += "��������δ��ʼ";
			break;
		case 2:
			stateText += "���������ڽ���";
			break;
		case 3:
			stateText += "�������ѵ���ֹ���ڣ���δ����";
			break;
		case 4:
			stateText += "���������������,��ɶ�Ϊ" + completence + "%��ִ������Ϊ��" + ability;
			break;
		default:
			break;
		}
		stateTextView.setText(stateText);
		
		//��ʾ������Ϣ
		nameTextView.setText("�������ƣ�" + name);
		descriptionTextView.setText("��������\n" + description);
		difficultyTextView.setText("�Ѷ�:" + mapDifficulty[(int)difficulty - 1]);
		priorityTextView.setText("���ȼ�:" + mapPriority[(int)priority - 1]);
		startDateTextView.setText("��ʼ���ڣ�" + startDate);
		startTimeTextView.setText("" + startTime);
		endDateTextView.setText("����ʱ�䣺" + endDate);
		endTimeTextView.setText("" + endTime);
		
		oper.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateData();
		super.onResume();
	}
}
