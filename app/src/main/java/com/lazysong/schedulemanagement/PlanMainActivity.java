package com.lazysong.schedulemanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.plan.AddPlanActivity;
import com.lazysong.schedulemanagement.plan.CheckAllPlanActivity;
import com.lazysong.schedulemanagement.plan.CheckAllPlansAndTasksActivity;
import com.lazysong.schedulemanagement.plan.CheckPlanActivity;
import com.lazysong.schedulemanagement.plan.EditPlanActivity;
import com.lazysong.schedulemanagement.task.AddSingleTaskActivity;
import com.lazysong.schedulemanagement.task.CheckAllSingleTaskActivity;
import com.lazysong.schedulemanagement.task.CheckSingleTaskActivity;
import com.lazysong.schedulemanagement.task.EditSingleTaskActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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

public class PlanMainActivity extends Activity {
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private ListView list1;
	private ListView list2;
	private Button back;
	private Button add;
	
	private int planNo;
	private int taskNo;
	private PlanDataOperation planOper;
	private TaskDataOperation taskOper;
	private int selected;
	private float totalAbility;
	
	private List<MyPlan> recentPlans = new ArrayList<MyPlan>();
	private List<MyTask> recentSingleTasks = new ArrayList<MyTask>();
	private List<MyPlan> allPlans = new ArrayList<MyPlan>();
	private List<MyTask> allSingleTasks = new ArrayList<MyTask>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_main);
		
		/*
		 * 下面开始就是新的代码了
		 * */
		//获得所有的控件
		textView1 = (TextView)findViewById(R.id.plan_conclusion_plan_main);
		textView2 = (TextView)findViewById(R.id.task_conclusion_plan_main);
		textView3 = (TextView)findViewById(R.id.total_ability_plan_main);
		list1 = (ListView)findViewById(R.id.ListView1_plan_main);
		list2 = (ListView)findViewById(R.id.ListView2_plan_main);
		back = (Button)findViewById(R.id.back_plan_main);
		add = (Button)findViewById(R.id.add_plan_main);
		
		list1.setCacheColorHint(Color.TRANSPARENT);
		list2.setCacheColorHint(Color.TRANSPARENT);
		
		//下面测试插入函数
//		PlanDataOperation oper = new PlanDataOperation(this);
//		try{
//			oper.insertPlan("新加的计划", "planDescription1", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try{
//			oper.insertPlan("plnaName1", "planDescription1", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		TaskDataOperation oper1 = new TaskDataOperation(this);
//		MyTask task = new MyTask();
//		
//		//现在开始测试插入任务的函数
//		try {
//			oper1.insertTask(18, "new Single Task", "taskDescription1", 3, 1, "2014-09-08", "02:09:00", "2015-12-09", "13:21:00");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		oper1.close();
//		oper1.setCompletence(3, 90);
//		try {
//			oper1.insertTask(2, "taskName1", "taskDescription1", 3, 1, "2014-09-08", "02:09:00", "2014-12-09", "13:21:00");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		oper1.setCompletence(4, 90);
		
		updateData();
		
		/*
		 * 绑定监听器
		 * */
		//为list1绑定单击事件监听器
		list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//								Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, CheckPlanActivity.class);
				intent.putExtra("planNo", recentPlans.get(position).getPlanNo());
//				Toast.makeText(PlanMainActivity.this, "计划编号为" + recentPlans.get(position).getPlanNo(), Toast.LENGTH_LONG).show();
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
				intent.setClass(PlanMainActivity.this, CheckSingleTaskActivity.class);
				intent.putExtra("taskNo", recentSingleTasks.get(position).getTaskNo());
//				Toast.makeText(PlanMainActivity.this, "任务编号为" + recentSingleTasks.get(position).getTaskNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		list1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				planNo = recentPlans.get(position).getPlanNo();
				selected = 1;
//				Toast.makeText(PlanMainActivity.this, "计划编号为" + planNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		list2.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				taskNo = recentSingleTasks.get(position).getTaskNo();
				selected = 2;
//				Toast.makeText(PlanMainActivity.this, "任务编号为" + taskNo, Toast.LENGTH_LONG).show();
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
        
        textView1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, CheckAllPlanActivity.class);
				startActivity(intent);
			}
		});
        
        textView2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, CheckAllSingleTaskActivity.class);
				startActivity(intent);
			}
		});
        
        
        
        textView3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, TotalCompletenceActivity.class);
				intent.putExtra("totalAbility", totalAbility);
				startActivity(intent);
			}
		});
        
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PlanMainActivity.this.finish();
			}
		});
        add.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(PlanMainActivity.this)
			 	.setTitle("请选择")
			 	.setIcon(android.R.drawable.ic_dialog_info)                
			 	.setSingleChoiceItems(new String[] {"计划","独立任务"}, 0, 
			 	  new DialogInterface.OnClickListener() {
			 	                              
			 	     public void onClick(DialogInterface dialog, int which) {
			 	    	 Intent intent = new Intent();
			 	    	 switch (which) {
			 	    	
			 	    	 case 0:
			 	    		 intent.setClass(PlanMainActivity.this, AddPlanActivity.class);
			 	    		 startActivity(intent);
			 	    		 break;
			 	    	 case 1:
			 	    		 intent.setClass(PlanMainActivity.this, AddSingleTaskActivity.class);
			 	    		 startActivity(intent);
			 	    		 break;
			 	    	 default:
			 	    			 break;
			 	    	 }
			 	        dialog.dismiss();
			 	     }
			 	  }
			 	)
			 	.setNegativeButton("取消", null)
			 	.show();
			}
		});
        
	}
		 
	/**
	 * 更新数据
	 * 通常每次重新返回到本界面的时候都会调用此函数
	 * */
	public void updateData() {
		
		//获取最近有几个计划和独立计划
		planOper = new PlanDataOperation(this);
		recentPlans = planOper.getRecentPlans();
		int numberOfPlans = recentPlans.size();
		
		taskOper = new TaskDataOperation(this);
		recentSingleTasks = taskOper.getRecentSingleTasks();
		int numberOfSingleTasks = recentSingleTasks.size();
		
		
		//在界面上显示最近有几个计划和独立任务
		String text1 = "";
		if(numberOfPlans == 0)
			text1 += "近期您没有要执行的计划\n";
		else
			text1 += "近期您有" + numberOfPlans + "项计划\n";
		
		textView1.setText(text1);
		
		//在界面上显示最近有几个计划和独立任务
		String text2 = "";
		if(numberOfSingleTasks == 0)
			text2 += "近期您没有要执行的独立任务";
		else
			text2 += "近期您有" + numberOfSingleTasks + "项独立任务\n";
		
		textView2.setText(text2);
		
		
		/*显示综合执行能力*/
		Evaluate evaluate = new Evaluate();
		
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//计算并更新计划和独立任务的相应字段
		for(int i = 0; i < allPlans.size(); i ++)
			planOper.refreshData(allPlans.get(i).getPlanNo());
		for(int i = 0; i < allSingleTasks.size(); i ++)
			taskOper.refreshData(allSingleTasks.get(i).getTaskNo());
		
		//重新获得近期计划和独立任务的列表
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//计算综合执行能力
		totalAbility = evaluate.computeTotalAbility(allPlans, allSingleTasks);
		String text3 = "您目前的综合执行能力为" + totalAbility;
		textView3.setText(text3);
		
		recentPlans = planOper.getRecentPlans();
		recentSingleTasks = taskOper.getRecentSingleTasks();
		
		//关闭数据连接
		planOper.close();
		taskOper.close();
		/*
		 * 下面为list1绑定数据
		 * */
		List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
		
		String[] planName = new String[recentPlans.size()];
		String[] planState = new String[recentPlans.size()];
		
		String[] showState = new String[]{"sf","未开始","进行中","未评估","已评估","asdf"};
		
		for(int i = 0; i < recentPlans.size(); i ++) {
			if(recentPlans.get(i).getPlanName().length() == 0)
				planName[i] = "未命名计划";
			else
				planName[i] = recentPlans.get(i).getPlanName();
			planState[i] = showState[recentPlans.get(i).getState()];
		}
		for(int i = 0; i < planName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("planName", planName[i]);
			listItem.put("planState", planState[i]);
			listItemPlan.add(listItem);
		}
		
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
				R.layout.simple_item, 
				new String[]{"planName","planState"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
//		list1 = (ListView)findViewById(R.id.ListView1_plan_main);
		//为list1设置Adapter
		list1.setAdapter(simpleAdapterPlan);
				
		/*
		 * 下面为list2绑定数据
		 * */
		List<Map<String, Object>> listItemTask = new ArrayList<Map<String, Object>>();
		String[] taskName = new String[recentSingleTasks.size()];
		String[] taskState = new String[recentSingleTasks.size()];
		
		for(int i = 0; i < recentSingleTasks.size(); i ++) {
			if(recentSingleTasks.get(i).getTaskName().length() == 0)
				taskName[i] = "未命名任务";
			else
				taskName[i] = recentSingleTasks.get(i).getTaskName();
			taskState[i] = showState[recentSingleTasks.get(i).getState()];
		}
		for(int i = 0; i < taskName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("taskName", taskName[i]);
			listItem.put("taskState", taskState[i]);
			listItemTask.add(listItem);
		}
		
		SimpleAdapter simpleAdapterTask = new SimpleAdapter(this, listItemTask,
				R.layout.simple_item, 
				new String[]{"taskName","taskState"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
//		list2 = (ListView)findViewById(R.id.ListView2_plan_main);
		//为list2设置Adapter
		list2.setAdapter(simpleAdapterTask);
		
		
		
		
	}
	
//	
//    @Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		// TODO Auto-generated method stub
//		super.onCreateContextMenu(menu, v, menuInfo);
//	}


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
				intent.setClass(PlanMainActivity.this, EditPlanActivity.class);
			}
			if(selected == 2){
				intent.putExtra("taskNo", taskNo);
				intent.setClass(PlanMainActivity.this, EditSingleTaskActivity.class);
			}
			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		//取消提醒
    		//删除记录
    		planOper = new PlanDataOperation(this);
    		taskOper = new TaskDataOperation(this);
    		
    		AlarmManage manage = new AlarmManage(PlanMainActivity.this);
    		
    		if(selected == 1){
//    			Toast.makeText(PlanMainActivity.this, "选中的是计划", Toast.LENGTH_SHORT).show();
    			planOper.delete(planNo);
    			List<MyTask> tasksOfPlan = planOper.getTasksOfPlan(planNo);
    			for(int i = 0; i < tasksOfPlan.size(); i ++) {
    				manage.cancleNotification(tasksOfPlan.get(i).getTaskNo() + 10000);
    			}
    		}
    		if(selected == 2) {
//    			Toast.makeText(PlanMainActivity.this, "选中的是独立任务", Toast.LENGTH_SHORT).show();
    			taskOper.delete(taskNo);
    			manage.cancleNotification(taskNo + 10000);
    		}
    		planOper.close();
    		taskOper.close();
    		updateData();
    		//反馈
    		Toast.makeText(PlanMainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateData();
	}  
    
}
