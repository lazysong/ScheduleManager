package com.lazysong.schedulemanagement.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.id;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.task.CheckSingleTaskActivity;
import com.lazysong.schedulemanagement.task.EditSingleTaskActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class CheckAllPlansAndTasksActivity extends Activity {
	private ListView list1;
	private ListView list2;
	private Button back;
	
	private int planNo;
	private int taskNo;
	private PlanDataOperation planOper;
	private TaskDataOperation taskOper;
	private int selected;
	
	private List<MyPlan> allPlans = new ArrayList<MyPlan>();
	private List<MyTask> allSingleTasks = new ArrayList<MyTask>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_all_plans_and_tasks);
		
		list1 = (ListView)findViewById(R.id.ListView1__check_all_plans_and_tasks);
		list2 = (ListView)findViewById(R.id.ListView2__check_all_plans_and_tasks);
		back = (Button)findViewById(R.id.back_check_all_plans_and_tasks);
		
		updateData();
		
		list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//								Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				Intent intent = new Intent();
				intent.setClass(CheckAllPlansAndTasksActivity.this, CheckPlanActivity.class);
				intent.putExtra("planNo", allPlans.get(position).getPlanNo());
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "计划编号为" + allPlans.get(position).getPlanNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		//为list2绑定单击事件监听器
		list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//										Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				Intent intent = new Intent();
				intent.setClass(CheckAllPlansAndTasksActivity.this, CheckSingleTaskActivity.class);
				intent.putExtra("taskNo", allSingleTasks.get(position).getTaskNo());
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "任务编号为" + allSingleTasks.get(position).getTaskNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		list1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				planNo = allPlans.get(position).getPlanNo();
				selected = 1;
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "计划编号为" + planNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		list2.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				taskNo = allSingleTasks.get(position).getTaskNo();
				selected = 2;
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "任务编号为" + taskNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		 // 添加长按事件的监听器

        list1.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("管理日程");     
                menu.add(0, 0, 0, "删除");  
                menu.add(0, 1, 0, "编辑");
            }  
        });
        
        list2.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
            
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("管理日程");     
                menu.add(0, 0, 0, "删除");  
                menu.add(0, 1, 0, "编辑");
            }  
        });
        
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckAllPlansAndTasksActivity.this.finish();
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
			if(selected == 1){
				intent.putExtra("planNo", planNo);
				intent.setClass(CheckAllPlansAndTasksActivity.this, EditPlanActivity.class);
			}
			if(selected == 2){
				intent.putExtra("taskNo", taskNo);
				intent.setClass(CheckAllPlansAndTasksActivity.this, EditSingleTaskActivity.class);
			}
			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		//取消提醒
    		//删除记录
    		planOper = new PlanDataOperation(this);
    		taskOper = new TaskDataOperation(this);
    		if(selected == 1)
//    			Toast.makeText(CheckAllPlansAndTasksActivity.this, "选中的是计划", Toast.LENGTH_SHORT).show();
    			planOper.delete(planNo);
    		if(selected == 2)
//    			Toast.makeText(CheckAllPlansAndTasksActivity.this, "选中的是独立任务", Toast.LENGTH_SHORT).show();
    			taskOper.delete(taskNo);
    		planOper.close();
    		taskOper.close();
    		updateData();
    		//反馈
    		Toast.makeText(CheckAllPlansAndTasksActivity.this, "已删除", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }
	
	public void updateData() {
		
		//获取所有计划和独立计划
		planOper = new PlanDataOperation(this);
		allPlans = planOper.getAllRecords();
		
		taskOper = new TaskDataOperation(this);
		allSingleTasks = taskOper.getAllSingleTasks();
		
		
		
		/*
		 * 下面为list1绑定数据
		 * */
		List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
		
		String[] planName = new String[allPlans.size()];
		String[] planCompletence = new String[allPlans.size()];
		
		for(int i = 0; i < allPlans.size(); i ++) {
			planName[i] = allPlans.get(i).getPlanName();
			planCompletence[i] = allPlans.get(i).getCompletence() + "";
		}
		for(int i = 0; i < planName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("planName", planName[i]);
			listItem.put("planCompletence", planCompletence[i]);
			listItemPlan.add(listItem);
		}
		
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
				R.layout.simple_item, 
				new String[]{"planName","planCompletence"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		//为list1设置Adapter
		list1.setAdapter(simpleAdapterPlan);
				
		/*
		 * 下面为list2绑定数据
		 * */
		List<Map<String, Object>> listItemTask = new ArrayList<Map<String, Object>>();
		String[] taskName = new String[allSingleTasks.size()];
		String[] taskCompletence = new String[allSingleTasks.size()];
		
		for(int i = 0; i < allSingleTasks.size(); i ++) {
			taskName[i] = allSingleTasks.get(i).getTaskName();
			taskCompletence[i] = allSingleTasks.get(i).getCompletence() + "";
		}
		for(int i = 0; i < taskName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("taskName", taskName[i]);
			listItem.put("taskCompletence", taskCompletence[i]);
			listItemTask.add(listItem);
		}
		
		SimpleAdapter simpleAdapterTask = new SimpleAdapter(this, listItemTask,
				R.layout.simple_item, 
				new String[]{"taskName","taskCompletence"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		//为list2设置Adapter
		list2.setAdapter(simpleAdapterTask);
		
		//关闭数据连接
		planOper.close();
		taskOper.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateData();
		super.onResume();
	}
	
}
