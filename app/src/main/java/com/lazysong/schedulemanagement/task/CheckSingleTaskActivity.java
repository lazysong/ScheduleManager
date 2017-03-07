package com.lazysong.schedulemanagement.task;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
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

public class CheckSingleTaskActivity extends Activity {
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
		setContentView(R.layout.activity_check_single_task);
		
		
		//��ÿؼ��Ķ���
		back = (Button)findViewById(R.id.back_check_singletask);
		delete = (Button)findViewById(R.id.delete_check_singletask);
		edit = (Button)findViewById(R.id.edit_check_singletask);
		stateTextView = (TextView)findViewById(R.id.state_check_singletask);
		nameTextView = (TextView)findViewById(R.id.name_check_singletask);
		descriptionTextView = (TextView)findViewById(R.id.description_check_singletask);
		difficultyTextView = (TextView)findViewById(R.id.difficulty_check_singletask);
		priorityTextView = (TextView)findViewById(R.id.priority_check_singletask);
		startDateTextView = (TextView)findViewById(R.id.start_date_check_singletask);
		startTimeTextView = (TextView)findViewById(R.id.start_time_check_singletask);
		endDateTextView = (TextView)findViewById(R.id.end_date_check_singletask);
		endTimeTextView = (TextView)findViewById(R.id.end_time_check_singletask);
		
		
		updateData();
		
		//���ü�����
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckSingleTaskActivity.this.finish();
			}
		});
		
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ִ��ɾ������
				oper = new TaskDataOperation(CheckSingleTaskActivity.this);
				oper.delete(taskNo);
				AlarmManage manage = new AlarmManage(CheckSingleTaskActivity.this);
				manage.cancleNotification(taskNo + 10000);
				oper.close();
				//����
				Toast.makeText(CheckSingleTaskActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
				CheckSingleTaskActivity.this.finish();
			}
		});
		
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CheckSingleTaskActivity.this, EditSingleTaskActivity.class);
				intent.putExtra("taskNo", taskNo);
				startActivity(intent);
			}
		});
				
	}
	
	public void updateData() {
		//ȡ��intent����ĵ�extras����
		taskNo = getIntent().getExtras().getInt("taskNo");
		//�����ݿ��м�����task��¼
		oper = new TaskDataOperation(this);

		
		//ˢ������
		oper.refreshData(taskNo);
		
		MyTask task = oper.getTaskRecord(taskNo);
		
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
			stateText += "�ö���������δ��ʼ";
			break;
		case 2:
			stateText += "�ö����������ڽ���";
			break;
		case 3:
			stateText += "�ö��������ѵ���ֹ���ڣ���δ����";
			break;
		case 4:
			stateText += "�ö����������������,��ɶ�Ϊ" + completence + "%��ִ������Ϊ��" + ability;
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
		startDateTextView.setText("��ʼʱ�䣺" + startDate);
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
