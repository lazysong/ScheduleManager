package com.lazysong.schedulemanagement.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.PlanMainActivity;
import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.calendar.CalendarEditActivity;
import com.lazysong.schedulemanagement.calendar.CalendarMainActivity;
import com.lazysong.schedulemanagement.calendar.CalendarSpecificActivity;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.CheckValid;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.task.AddTaskActivity;
import com.lazysong.schedulemanagement.task.CheckTaskActivity;
import com.lazysong.schedulemanagement.task.EditTaskActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class CheckPlanActivity extends Activity {
	private int planNo;
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
	private Button add;
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
	private ListView list;
	
	private TaskDataOperation taskOper;
	private PlanDataOperation planOper;
	private List<MyTask> tasks;
	private int taskNo;
	private String[] mapDifficulty = new String[]{"�ǳ���", "��", "һ��", "����", "�ǳ�����"};
	private String[] mapPriority = new String[]{"�����", "����", "Ҫ��", "��Ҫ��", "��Ĳ�Ҫ��"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_plan);
		
		//ȡ��intent����ĵ�extras����
		planNo = getIntent().getExtras().getInt("planNo");
		
		//��ÿؼ��Ķ���
		back = (Button)findViewById(R.id.back_check_plan);
		delete = (Button)findViewById(R.id.delete_check_plan);
		add = (Button)findViewById(R.id.add_check_plan);
		edit = (Button)findViewById(R.id.edit_check_plan);
		stateTextView = (TextView)findViewById(R.id.state_check_plan);
		nameTextView = (TextView)findViewById(R.id.name_check_plan);
		descriptionTextView = (TextView)findViewById(R.id.description_check_plan);
		difficultyTextView = (TextView)findViewById(R.id.difficulty_check_plan);
		priorityTextView = (TextView)findViewById(R.id.priority_check_plan);
		startDateTextView = (TextView)findViewById(R.id.start_date_check_plan);
		startTimeTextView = (TextView)findViewById(R.id.start_time_check_plan);
		endDateTextView = (TextView)findViewById(R.id.end_date_check_plan);
		endTimeTextView = (TextView)findViewById(R.id.end_time_check_plan);
		list = (ListView)findViewById(R.id.ListView1_check_plan);
		
		updateData();
		
		//���ü�����
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckPlanActivity.this.finish();
			}
		});
		edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CheckPlanActivity.this, EditPlanActivity.class);
				intent.putExtra("planNo", planNo);
				startActivity(intent);
			}
		});
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CheckPlanActivity.this, AddTaskActivity.class);
				intent.putExtra("planNo", planNo);
				startActivity(intent);
			}
		});
		delete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				planOper = new PlanDataOperation(CheckPlanActivity.this);
				planOper.delete(planNo);
				AlarmManage manage = new AlarmManage(CheckPlanActivity.this);
				List<MyTask> tasksOfPlan = planOper.getTasksOfPlan(planNo);
				for(int i = 0; i < tasksOfPlan.size(); i ++) {
					manage.cancleNotification(tasksOfPlan.get(i).getTaskNo() + 10000);
				}
				planOper.close();
				CheckPlanActivity.this.finish();
			}
		});
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(CheckPlanActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
				//�رձ��������������
				Intent intent = new Intent();
				intent.setClass(CheckPlanActivity.this, CheckTaskActivity.class);
				intent.putExtra("taskNo", tasks.get(position).getTaskNo());
				startActivity(intent);
			}
		});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				taskNo = tasks.get(position).getTaskNo();
//				Toast.makeText(CalendarMainActivity.this, calendarNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		 // ��ӳ����¼��ļ�����

        list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("��������");     
                menu.add(0, 0, 0, "ɾ��");  
                menu.add(0, 1, 0, "�༭");
            }  
        });
	}
	
	/**
     * �����˵���Ӧ����
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
//        setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ");   
    	if(item.getItemId() == 1) {
    		//���ѡ�С��༭������ת���༭����
    		//�رձ��������������
//			oper.close();
    		Intent intent = new Intent();
			intent.setClass(CheckPlanActivity.this, EditTaskActivity.class);
			intent.putExtra("taskNo", taskNo);
			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		taskOper = new TaskDataOperation(this);
    		taskOper.delete(taskNo);
    		AlarmManage manage = new AlarmManage(CheckPlanActivity.this);
    		manage.cancleNotification(taskNo + 10000);
    		taskOper.close();
//    		AlarmManage manage = new AlarmManage(CheckPlanActivity.this);
//    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		
    		//����listview
    		updateData();
    		//����
    		Toast.makeText(CheckPlanActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
    		
    	}
        return super.onContextItemSelected(item);  
    }  
	
	public void updateData() {
		
		planOper = new PlanDataOperation(this);
		
		//ˢ������
//		taskOper = new TaskDataOperation(this);
//		for(int i = 0; i < tasks.size(); i ++)
//			taskOper.refreshData(tasks.get(i).getTaskNo());
		planOper.refreshData(planNo);
		tasks = planOper.getTasksOfPlan(planNo);
//		taskOper.close();
		
		MyPlan plan = planOper.getPlanRecord(planNo);
		
		//ȡ����Ҫ��ʾ���ֶ�
		name = plan.getPlanName();
		description = plan.getPlanDescription();
		difficulty = plan.getDifficulty();
		priority = plan.getPlanPriority();
		startDate = plan.getStartDate();
		startTime = plan.getStartTime();
		endDate = plan.getEndDate();
		endTime = plan.getEndTime();
		completence = plan.getCompletence();
		state = plan.getState();
		ability = plan.getAbility();
		

		
		//���ƻ�״̬����ʾ��Ϣ
		String stateText = "";
		switch(state) {
		case 1:
			stateText += "�üƻ���δ��ʼ";
			break;
		case 2:
			stateText += "�üƻ����ڽ���";
			break;
		case 3:
			stateText += "�üƻ��ѵ���ֹ���ڣ���δ����";
			break;
		case 4:
			stateText += "�üƻ����������,��ɶ�Ϊ" + completence + "%��ִ������Ϊ��" + ability;
			break;
		default:
			stateText += "�üƻ�����������";
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
		
		
		
		/*
		 * ����Ϊlist1������
		 * */
		//��ȡ�üƻ�����������
		tasks = planOper.getTasksOfPlan(planNo);
		
		List<Map<String, Object>> listItemTask = new ArrayList<Map<String, Object>>();
		
		String[] taskName = new String[tasks.size()];
		String[] taskState = new String[tasks.size()];
		
		String[] showState = new String[]{"sf","δ��ʼ","������","δ����","������","asdf"};
		
		for(int i = 0; i < tasks.size(); i ++) {
			if(tasks.get(i).getTaskName().length() == 0)
				taskName[i] = "δ��������";
			else
				taskName[i] = tasks.get(i).getTaskName();
			taskState[i] = showState[tasks.get(i).getState()];
		}
		for(int i = 0; i < taskName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("taskName", taskName[i]);
			listItem.put("taskState", taskState[i]);
			listItemTask.add(listItem);
		}
		
		//����һ��SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemTask,
				R.layout.simple_item, 
				new String[]{"taskName","taskState"},
				new int[] {R.id.nameTextView, R.id.dateTextView });
		
		list = (ListView)findViewById(R.id.ListView1_check_plan);
		list.setCacheColorHint(Color.TRANSPARENT);
		//Ϊlist1����Adapter
		list.setAdapter(simpleAdapterPlan);
		planOper.close();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateData();
		super.onResume();
	}
	
}
