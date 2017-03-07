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
	private String[] mapDifficulty = new String[]{"非常简单", "简单", "一般", "困难", "非常困难"};
	private String[] mapPriority = new String[]{"最紧迫", "紧迫", "要紧", "不要紧", "真的不要紧"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_single_task);
		
		
		//获得控件的对象
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
		
		//设置监听器
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
				//执行删除操作
				oper = new TaskDataOperation(CheckSingleTaskActivity.this);
				oper.delete(taskNo);
				AlarmManage manage = new AlarmManage(CheckSingleTaskActivity.this);
				manage.cancleNotification(taskNo + 10000);
				oper.close();
				//反馈
				Toast.makeText(CheckSingleTaskActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
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
		//取出intent对象的的extras数据
		taskNo = getIntent().getExtras().getInt("taskNo");
		//从数据库中检索出task记录
		oper = new TaskDataOperation(this);

		
		//刷新数据
		oper.refreshData(taskNo);
		
		MyTask task = oper.getTaskRecord(taskNo);
		
		//取出需要显示的字段
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
		
		
		//检查任务状态并显示信息
		String stateText = "";
		switch(state) {
		case 1:
			stateText += "该独立任务尚未开始";
			break;
		case 2:
			stateText += "该独立任务正在进行";
			break;
		case 3:
			stateText += "该独立任务已到截止日期，尚未评估";
			break;
		case 4:
			stateText += "该独立任务已完成评估,完成度为" + completence + "%，执行能力为：" + ability;
			break;
		default:
			break;
		}
		stateTextView.setText(stateText);
		
		//显示其他信息
		nameTextView.setText("任务名称：" + name);
		descriptionTextView.setText("任务描述\n" + description);
		difficultyTextView.setText("难度:" + mapDifficulty[(int)difficulty - 1]);
		priorityTextView.setText("优先级:" + mapPriority[(int)priority - 1]);
		startDateTextView.setText("开始时间：" + startDate);
		startTimeTextView.setText("" + startTime);
		endDateTextView.setText("结束时间：" + endDate);
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
