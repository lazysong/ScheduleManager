package com.lazysong.schedulemanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.calendar.*;
import com.lazysong.schedulemanagement.db.CalendarDataOperation;
import com.lazysong.schedulemanagement.db.MyCalendar;
import com.lazysong.schedulemanagement.db.MyDatabaseHelper;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.plan.*;
import com.lazysong.schedulemanagement.task.AddSingleTaskActivity;
import com.lazysong.schedulemanagement.task.CheckAllSingleTaskActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class MainActivity extends Activity {
	private List<MyCalendar> records;
	private Button addButton;
	private Button moreButton;
	private TextView todayCalendarTextView;
	private TextView recentPlanTextView;
	private TextView evaluatedTextView;
	private ListView list;
	private String calendarNo;
	private CalendarDataOperation oper;
	private PlanDataOperation planOper;
	private TaskDataOperation taskOper;
	private TextView textView;
	private Button openButton;
    private Button closeButton;
    private SlidingMenu mSlidingMenu;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mSlidingMenu = new SlidingMenu(this, LayoutInflater
                .from(this).inflate(R.layout.activity_main, null), LayoutInflater
                .from(this).inflate(R.layout.left_fragment, null));
        setContentView(mSlidingMenu);//注意setContentView需要换为我们的SlidingMenu
        openButton = (Button) findViewById(R.id.button_more_main);
        closeButton = (Button) findViewById(R.id.button_close);
        openButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSlidingMenu.open();
			}
		});
        closeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSlidingMenu.close();
			}
		});
		
//		button1 = (Button)findViewById(R.id.button1_add_plan);
//		button1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(MainActivity.this, CalendarMainActivity.class);
//				startActivity(intent);
//			}
//		});
//		
//		button2 = (Button)findViewById(R.id.button2_add_plan);
//		button2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(MainActivity.this, PlanMainActivity.class);
//				startActivity(intent);
//				ExitApplication.getInstance().addActivity(MainActivity.this);
//			}
//		});
		
		//数据库操作
//		MyDatabaseHelper helper = new MyDatabaseHelper(this,"db1.db",null,2);
//		SQLiteDatabase base = helper.getWritableDatabase();
//		CalendarDataOperation oper = new CalendarDataOperation(MainActivity.this);
//		base.execSQL("insert into cal1 values(1,'2015-05-2','2:30:25','9教204','某公司宣讲会',1,1);");
//		oper.addAffair("insert into cal1 values(?,?,?,?,?,?,?,?);",new String[]{"1","参加宣讲会","2015-04-15","2:30:25","9教204","某公司宣讲会","1","1"});
//		base.execSQL("insert into book values (100,\"author1\",20,100,\"android programming\")");

        //获取侧边栏控件
        Button buttonPlan = (Button)findViewById(R.id.item1_ce);
        Button buttonTask = (Button)findViewById(R.id.item2_ce);
        Button buttonCalendar = (Button)findViewById(R.id.item3_ce);
        Button buttonAbility = (Button)findViewById(R.id.item4_ce);
        Button buttonInformation = (Button)findViewById(R.id.item5_ce);
        Button buttonQuit = (Button)findViewById(R.id.item6_ce);
        
        buttonPlan.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CheckAllPlanActivity.class);
				startActivity(intent);
			}
		});
        
        buttonTask.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CheckAllSingleTaskActivity.class);
				startActivity(intent);
			}
		});
        
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CalendarTodayActivity.class);
				startActivity(intent);
			}
		});
        
        buttonAbility.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TotalCompletenceActivity.class);
				startActivity(intent);
			}
		});
        
        buttonInformation.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, InformationActivity.class);
				startActivity(intent);
			}
		});
        
        buttonQuit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();
			}
		});
        
		addButton = (Button)findViewById(R.id.add_main);
		moreButton = (Button)findViewById(R.id.button_more_main);
		todayCalendarTextView = (TextView)findViewById(R.id.textview2_main);
		recentPlanTextView = (TextView)findViewById(R.id.textview3_main);
		evaluatedTextView = (TextView)findViewById(R.id.textview4_main);
		list = (ListView)findViewById(R.id.listview_main);
		
		//为”添加“按钮设置监听器
		addButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this)
			 	.setTitle("添加")
			 	.setIcon(android.R.drawable.ic_dialog_info)                
			 	.setSingleChoiceItems(new String[] {"计划","独立任务","日程"}, 0, 
			 	  new DialogInterface.OnClickListener() {
			 	                              
			 	     public void onClick(DialogInterface dialog, int which) {
			 	    	 Intent intent = new Intent();
			 	    	 switch (which) {
			 	    	
			 	    	 case 0:
			 	    		 intent.setClass(MainActivity.this, AddPlanActivity.class);
			 	    		 startActivity(intent);
			 	    		 break;
			 	    	 case 1:
			 	    		 intent.setClass(MainActivity.this, AddSingleTaskActivity.class);
			 	    		 startActivity(intent);
			 	    		 break;
			 	    	case 2:
			 	    		 intent.setClass(MainActivity.this, CalendarAddActivity.class);
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
		
		//为todayCalendarTextView设置监听器
		todayCalendarTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CalendarTodayActivity.class);
				startActivity(intent);
			}
		});
		
		//为recentPlanTextView设置监听器
		recentPlanTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, PlanMainActivity.class);
				startActivity(intent);
			}
		});
		
		//为evaluatedTextView设置监听器
		evaluatedTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TotalCompletenceActivity.class);
				startActivity(intent);
			}
		});
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CalendarSpecificActivity.class);
				intent.putExtra("calendarNo", records.get(position).getCalendarNo());
				startActivity(intent);
			}
		});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				calendarNo = records.get(position).getCalendarNo();
//				Toast.makeText(CalendarMainActivity.this, calendarNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		 // 添加长按事件的监听器

        list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("管理日程");     
                menu.add(0, 0, 0, "删除");  
                menu.add(0, 1, 0, "编辑");
            }  
        });
        list.setCacheColorHint(Color.TRANSPARENT);
        
	}
     
	
	public void updateDate() {
		
		//刷新计划表和任务表
		planOper = new PlanDataOperation(this);
		taskOper = new TaskDataOperation(this);
		
		List<MyPlan> allPlans = planOper.getAllRecords();
		List<MyTask> allSingleTasks = taskOper.getAllSingleTasks();

		//计算并更新计划和独立任务的相应字段
		for(int i = 0; i < allPlans.size(); i ++)
			planOper.refreshData(allPlans.get(i).getPlanNo());
		for(int i = 0; i < allSingleTasks.size(); i ++)
			taskOper.refreshData(allSingleTasks.get(i).getTaskNo());
		
		//检索出近期的任务和计划
		int recentPlanCount = planOper.getRecentPlans().size();
		int recentSingleTaskCount = taskOper.getRecentSingleTasks().size();
		String recentText = "";
		if(recentPlanCount > 0)
			recentText += "近期计划：" + recentPlanCount;
		else
			recentText += "近期无计划";
		if(recentSingleTaskCount > 0)
			recentText += "近期独立任务：" + recentSingleTaskCount;
		else
			recentText += "近期无独立任务";
		recentPlanTextView.setText(recentText);
		
		
		
		//重新获得所有计划和独立任务的列表
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//计算综合执行能力
		Evaluate evaluate = new Evaluate();
		float totalAbility = evaluate.computeTotalAbility(allPlans, allSingleTasks);
		String text2 = "您目前的综合执行能力为" + totalAbility;
		evaluatedTextView.setText(text2);
		
		
		
    	//检索出日期为当前日期的日程项并保存
        oper = new CalendarDataOperation(this);
        records = oper.getTodayRecord();
        
        String todayText = "";
        if(records.size() < 0 || records.size() == 0)
        	todayText += "今天您没有需要进行的日程，点击查看所有日程";
        else
        	todayText += "今天您有" + records.size() + "项日程，点击查看所有日程";
        todayCalendarTextView.setText(todayText);
        

		//创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < records.size(); i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			if(records.get(i).getCalendarName().length() == 0)
				listItem.put("name", "未命名日程");
			else
				listItem.put("name", records.get(i).getCalendarName());
			listItem.put("date", records.get(i).getDate());
			listItem.put("calendarNo", records.get(i).getCalendarNo());
			listItems.add(listItem);
		}
		
		//创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.simple_item, 
				new String[]{"name","date"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		
		//为ListView设置Adapter
		list.setAdapter(simpleAdapter);
		
		//关闭数据连接
		oper.close();
    }
	
	/**
     * 长按菜单响应函数
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
//        setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");   
    	if(item.getItemId() == 1) {
    		//如果选中“编辑”，跳转到编辑界面
    		Intent intent = new Intent();
			intent.setClass(MainActivity.this, CalendarEditActivity.class);
			intent.putExtra("calendarNo", calendarNo);
			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		oper = new CalendarDataOperation(this);
    		oper.delete(calendarNo);
    		AlarmManage manage = new AlarmManage(MainActivity.this);
    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		//更新本界面listview
    		updateDate();
    		//更新日程主界面的lsitview
//    		CalendarMainActivity.this.updateDate();
    		//反馈
    		Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    		oper.close();
    	}
        return super.onContextItemSelected(item);  
    }  
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateDate();
		super.onResume();
	}
}
