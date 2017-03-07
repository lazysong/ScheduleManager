package com.lazysong.schedulemanagement.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.plan.CheckPlanActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class CheckAllSingleTaskActivity extends Activity {

	private Button back;
	private Button add;
	private TextView numberOfSingleTasksTextView;
	private ListView list;
	
	private TaskDataOperation taskOper;
	private List<MyTask> allSingleTasks;
	private int taskNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_all_single_task);
		
		//获得控件
		back = (Button)findViewById(R.id.back_check_all_singletask);
		add = (Button)findViewById(R.id.add_check_all_singletask);
		numberOfSingleTasksTextView = (TextView)findViewById(R.id.textView2_check_all_singletask);
		list = (ListView)findViewById(R.id.ListView1_check_all_singletask);
		list.setCacheColorHint(Color.TRANSPARENT);
		
		updateData();
		
		//为back绑定监听器
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckAllSingleTaskActivity.this.finish();
			}
		});
		
		//为back绑定监听器
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CheckAllSingleTaskActivity.this, AddSingleTaskActivity.class);
				startActivity(intent);
			}
		});
		
		//
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//								Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				Intent intent = new Intent();
				intent.setClass(CheckAllSingleTaskActivity.this, CheckSingleTaskActivity.class);
				intent.putExtra("taskNo", allSingleTasks.get(position).getTaskNo());
//				Toast.makeText(CheckAllSingleTaskActivity.this, "计划编号为" + allSingleTasks.get(position).getTaskNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		//
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				taskNo = allSingleTasks.get(position).getTaskNo();
//				Toast.makeText(CheckAllSingleTaskActivity.this, "计划编号为" + taskNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		//
		 list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
             
	            @Override  
	            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
	                menu.setHeaderTitle("管理独立任务");     
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
    	if(item.getItemId() == 1) {
    		//如果选中“编辑”，跳转到编辑界面
    		Intent intent = new Intent();

				intent.putExtra("taskNo", taskNo);
				intent.setClass(CheckAllSingleTaskActivity.this, EditTaskActivity.class);

			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		//取消提醒
    		//删除记录
    		taskOper = new TaskDataOperation(this);
    		taskOper.delete(taskNo);
    		AlarmManage manage = new AlarmManage(CheckAllSingleTaskActivity.this);
    		manage.cancleNotification(taskNo + 10000);
    		taskOper.close();
    		updateData();
    		//反馈
    		Toast.makeText(CheckAllSingleTaskActivity.this, "已删除", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }
	
	public void updateData() {
		//获取最近有几个计划和独立计划
		taskOper = new TaskDataOperation(this);
		allSingleTasks = taskOper.getAllSingleTasks();
		int numberOfSingleTasks = allSingleTasks.size();

		for(int i = 0; i < allSingleTasks.size(); i ++) {
			taskOper.refreshData(allSingleTasks.get(i).getTaskNo());
		}
		
		//在界面上显示有几个计划
		String text = " ";
		if(numberOfSingleTasks == 0)
			text += "您还没有创建任何独立任务哦，赶快创建一个吧！";
		else
			text += "目前您共有" + numberOfSingleTasks + "项独立任务\n";
		numberOfSingleTasksTextView.setText(text);
		

		
		//关闭数据连接
		taskOper.close();
		
		/*
		 * 下面为list绑定数据
		 * */
		List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
		
		String[] taskName = new String[allSingleTasks.size()];
		String[] taskState = new String[allSingleTasks.size()];
		int[] iconState = new int[allSingleTasks.size()];
		
		String[] taskStateText = new String[]{"","尚未开始","正在进行","未评估","已评估"};
		int[] icons = new int[]{R.drawable.not_start, R.drawable.going_on, R.drawable.not_evaluate, R.drawable.evaluated};
		
		for(int i = 0; i < allSingleTasks.size(); i ++) {
			if(allSingleTasks.get(i).getTaskName().length() == 0) {
				
				taskName[i] = "未命名任务";
			}
			else
				taskName[i] = allSingleTasks.get(i).getTaskName();
			taskState[i] = taskStateText[allSingleTasks.get(i).getState()];
//				iconState[i] = icons[allSingleTasks.get(i).getState()];
		}
		for(int i = 0; i < taskName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("taskName", taskName[i]);
			listItem.put("taskState", taskState[i]);
//					listItem.put("icon", icons[i]);
			listItemPlan.add(listItem);
		}
//				
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
				R.layout.simple_item, 
				new String[]{"taskName","taskState"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		//为list1设置Adapter
		list.setAdapter(simpleAdapterPlan);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateData();
		super.onResume();
	}
}
