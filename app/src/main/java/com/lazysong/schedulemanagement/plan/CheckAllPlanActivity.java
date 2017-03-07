package com.lazysong.schedulemanagement.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.PlanMainActivity;
import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.task.EditSingleTaskActivity;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class CheckAllPlanActivity extends Activity {
	private Button back;
	private Button add;
	private TextView numberOfPlansTextView;
	private ListView list;
	
	private PlanDataOperation planOper;
	private List<MyPlan> allPlans;
	private int planNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_all_plan);
		
		//获得控件
		back = (Button)findViewById(R.id.back_check_all_plan);
		add = (Button)findViewById(R.id.add_check_all_plan);
		numberOfPlansTextView = (TextView)findViewById(R.id.textView2_check_all_plan);
		list = (ListView)findViewById(R.id.ListView1__check_all_plan);
		list.setCacheColorHint(Color.TRANSPARENT);
		updateData();
		
		//为back绑定监听器
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckAllPlanActivity.this.finish();
			}
		});
		
		//为back绑定监听器
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CheckAllPlanActivity.this, AddPlanActivity.class);
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
				intent.setClass(CheckAllPlanActivity.this, CheckPlanActivity.class);
				intent.putExtra("planNo", allPlans.get(position).getPlanNo());
//				Toast.makeText(CheckAllPlanActivity.this, "计划编号为" + allPlans.get(position).getPlanNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		//
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				planNo = allPlans.get(position).getPlanNo();
//				Toast.makeText(CheckAllPlanActivity.this, "计划编号为" + planNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		//
		 list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
             
	            @Override  
	            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
	                menu.setHeaderTitle("管理日程");     
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

				intent.putExtra("planNo", planNo);
				intent.setClass(CheckAllPlanActivity.this, EditPlanActivity.class);

			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		//取消提醒
    		//删除记录
    		planOper = new PlanDataOperation(this);
    		planOper.delete(planNo);
    		AlarmManage manage = new AlarmManage(CheckAllPlanActivity.this);
			List<MyTask> tasksOfPlan = planOper.getTasksOfPlan(planNo);
			for(int i = 0; i < tasksOfPlan.size(); i ++) {
				manage.cancleNotification(tasksOfPlan.get(i).getTaskNo() + 10000);
			}
    		planOper.close();
    		updateData();
    		//反馈
    		Toast.makeText(CheckAllPlanActivity.this, "已删除", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }
	
	public void updateData() {
		//获取最近有几个计划和独立计划
			planOper = new PlanDataOperation(this);
			allPlans = planOper.getAllRecords();
			int numberOfPlans = allPlans.size();

			for(int i = 0; i < allPlans.size(); i ++) {
				planOper.refreshData(allPlans.get(i).getPlanNo());
			}
			

			//在界面上显示有几个计划
			String text1 = "";
			if(numberOfPlans == 0)
				text1 += "您还没有创建任何计划哦，快创建一个吧！";
			else
				text1 += "目前您共有" + numberOfPlans + "项计划\n";
			
			numberOfPlansTextView.setText(text1);
			

			
			//关闭数据连接
			planOper.close();
			
			/*
			 * 下面为list绑定数据
			 * */
			List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
			
			String[] planName = new String[allPlans.size()];
			String[] planState = new String[allPlans.size()];
			int[] iconState = new int[allPlans.size()];
			
			String[] planStateText = new String[]{"尚无子任务","尚未开始","正在进行","未评估","已评估"};
			int[] icons = new int[]{0, R.drawable.not_start, R.drawable.going_on, R.drawable.not_evaluate, R.drawable.evaluated};
			
			for(int i = 0; i < allPlans.size(); i ++) {
				if(allPlans.get(i).getPlanName().length() == 0)
					planName[i] = "未命名计划";
				else
					planName[i] = allPlans.get(i).getPlanName();
				planState[i] = planStateText[allPlans.get(i).getState()];
//				iconState[i] = icons[allPlans.get(i).getState()];
			}
//			ImageView stateView = (ImageView)findViewById(R.id.stateImageView);
			
			
			for(int i = 0; i < planName.length; i ++) {
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("planName", planName[i]);
				listItem.put("planState", planState[i]);

				listItemPlan.add(listItem);
			}
//				
			//创建一个SimpleAdapter
			SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
					R.layout.simple_item, 
					new String[]{"planName","planState"},
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
