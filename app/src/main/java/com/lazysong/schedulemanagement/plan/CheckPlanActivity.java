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
	private String[] mapDifficulty = new String[]{"非常简单", "简单", "一般", "困难", "非常困难"};
	private String[] mapPriority = new String[]{"最紧迫", "紧迫", "要紧", "不要紧", "真的不要紧"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_plan);
		
		//取出intent对象的的extras数据
		planNo = getIntent().getExtras().getInt("planNo");
		
		//获得控件的对象
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
		
		//设置监听器
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
//				Toast.makeText(CheckPlanActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				//关闭本界面的数据连接
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
//				Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				taskNo = tasks.get(position).getTaskNo();
//				Toast.makeText(CalendarMainActivity.this, calendarNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		 // 添加长按事件的监听器

        list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("管理任务");     
                menu.add(0, 0, 0, "删除");  
                menu.add(0, 1, 0, "编辑");
            }  
        });
	}
	
	/**
     * 长按菜单响应函数
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
//        setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");   
    	if(item.getItemId() == 1) {
    		//如果选中“编辑”，跳转到编辑界面
    		//关闭本界面的数据连接
//			oper.close();
    		Intent intent = new Intent();
			intent.setClass(CheckPlanActivity.this, EditTaskActivity.class);
			intent.putExtra("taskNo", taskNo);
			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		taskOper = new TaskDataOperation(this);
    		taskOper.delete(taskNo);
    		AlarmManage manage = new AlarmManage(CheckPlanActivity.this);
    		manage.cancleNotification(taskNo + 10000);
    		taskOper.close();
//    		AlarmManage manage = new AlarmManage(CheckPlanActivity.this);
//    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		
    		//更新listview
    		updateData();
    		//反馈
    		Toast.makeText(CheckPlanActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    		
    	}
        return super.onContextItemSelected(item);  
    }  
	
	public void updateData() {
		
		planOper = new PlanDataOperation(this);
		
		//刷新数据
//		taskOper = new TaskDataOperation(this);
//		for(int i = 0; i < tasks.size(); i ++)
//			taskOper.refreshData(tasks.get(i).getTaskNo());
		planOper.refreshData(planNo);
		tasks = planOper.getTasksOfPlan(planNo);
//		taskOper.close();
		
		MyPlan plan = planOper.getPlanRecord(planNo);
		
		//取出需要显示的字段
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
		

		
		//检查计划状态并显示信息
		String stateText = "";
		switch(state) {
		case 1:
			stateText += "该计划尚未开始";
			break;
		case 2:
			stateText += "该计划正在进行";
			break;
		case 3:
			stateText += "该计划已到截止日期，尚未评估";
			break;
		case 4:
			stateText += "该计划已完成评估,完成度为" + completence + "%，执行能力为：" + ability;
			break;
		default:
			stateText += "该计划尚无子任务";
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
		
		
		
		/*
		 * 下面为list1绑定数据
		 * */
		//获取该计划的所有任务
		tasks = planOper.getTasksOfPlan(planNo);
		
		List<Map<String, Object>> listItemTask = new ArrayList<Map<String, Object>>();
		
		String[] taskName = new String[tasks.size()];
		String[] taskState = new String[tasks.size()];
		
		String[] showState = new String[]{"sf","未开始","进行中","未评估","已评估","asdf"};
		
		for(int i = 0; i < tasks.size(); i ++) {
			if(tasks.get(i).getTaskName().length() == 0)
				taskName[i] = "未命名任务";
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
		
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemTask,
				R.layout.simple_item, 
				new String[]{"taskName","taskState"},
				new int[] {R.id.nameTextView, R.id.dateTextView });
		
		list = (ListView)findViewById(R.id.ListView1_check_plan);
		list.setCacheColorHint(Color.TRANSPARENT);
		//为list1设置Adapter
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
