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
        setContentView(mSlidingMenu);//ע��setContentView��Ҫ��Ϊ���ǵ�SlidingMenu
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
		
		//���ݿ����
//		MyDatabaseHelper helper = new MyDatabaseHelper(this,"db1.db",null,2);
//		SQLiteDatabase base = helper.getWritableDatabase();
//		CalendarDataOperation oper = new CalendarDataOperation(MainActivity.this);
//		base.execSQL("insert into cal1 values(1,'2015-05-2','2:30:25','9��204','ĳ��˾������',1,1);");
//		oper.addAffair("insert into cal1 values(?,?,?,?,?,?,?,?);",new String[]{"1","�μ�������","2015-04-15","2:30:25","9��204","ĳ��˾������","1","1"});
//		base.execSQL("insert into book values (100,\"author1\",20,100,\"android programming\")");

        //��ȡ������ؼ�
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
		
		//Ϊ����ӡ���ť���ü�����
		addButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(MainActivity.this)
			 	.setTitle("���")
			 	.setIcon(android.R.drawable.ic_dialog_info)                
			 	.setSingleChoiceItems(new String[] {"�ƻ�","��������","�ճ�"}, 0, 
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
			 	.setNegativeButton("ȡ��", null)
			 	.show();
			}
		});
		
		//ΪtodayCalendarTextView���ü�����
		todayCalendarTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, CalendarTodayActivity.class);
				startActivity(intent);
			}
		});
		
		//ΪrecentPlanTextView���ü�����
		recentPlanTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, PlanMainActivity.class);
				startActivity(intent);
			}
		});
		
		//ΪevaluatedTextView���ü�����
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
//				Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
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
//				Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				calendarNo = records.get(position).getCalendarNo();
//				Toast.makeText(CalendarMainActivity.this, calendarNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		 // ��ӳ����¼��ļ�����

        list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("�����ճ�");     
                menu.add(0, 0, 0, "ɾ��");  
                menu.add(0, 1, 0, "�༭");
            }  
        });
        list.setCacheColorHint(Color.TRANSPARENT);
        
	}
     
	
	public void updateDate() {
		
		//ˢ�¼ƻ���������
		planOper = new PlanDataOperation(this);
		taskOper = new TaskDataOperation(this);
		
		List<MyPlan> allPlans = planOper.getAllRecords();
		List<MyTask> allSingleTasks = taskOper.getAllSingleTasks();

		//���㲢���¼ƻ��Ͷ����������Ӧ�ֶ�
		for(int i = 0; i < allPlans.size(); i ++)
			planOper.refreshData(allPlans.get(i).getPlanNo());
		for(int i = 0; i < allSingleTasks.size(); i ++)
			taskOper.refreshData(allSingleTasks.get(i).getTaskNo());
		
		//���������ڵ�����ͼƻ�
		int recentPlanCount = planOper.getRecentPlans().size();
		int recentSingleTaskCount = taskOper.getRecentSingleTasks().size();
		String recentText = "";
		if(recentPlanCount > 0)
			recentText += "���ڼƻ���" + recentPlanCount;
		else
			recentText += "�����޼ƻ�";
		if(recentSingleTaskCount > 0)
			recentText += "���ڶ�������" + recentSingleTaskCount;
		else
			recentText += "�����޶�������";
		recentPlanTextView.setText(recentText);
		
		
		
		//���»�����мƻ��Ͷ���������б�
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//�����ۺ�ִ������
		Evaluate evaluate = new Evaluate();
		float totalAbility = evaluate.computeTotalAbility(allPlans, allSingleTasks);
		String text2 = "��Ŀǰ���ۺ�ִ������Ϊ" + totalAbility;
		evaluatedTextView.setText(text2);
		
		
		
    	//����������Ϊ��ǰ���ڵ��ճ������
        oper = new CalendarDataOperation(this);
        records = oper.getTodayRecord();
        
        String todayText = "";
        if(records.size() < 0 || records.size() == 0)
        	todayText += "������û����Ҫ���е��ճ̣�����鿴�����ճ�";
        else
        	todayText += "��������" + records.size() + "���ճ̣�����鿴�����ճ�";
        todayCalendarTextView.setText(todayText);
        

		//����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < records.size(); i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			if(records.get(i).getCalendarName().length() == 0)
				listItem.put("name", "δ�����ճ�");
			else
				listItem.put("name", records.get(i).getCalendarName());
			listItem.put("date", records.get(i).getDate());
			listItem.put("calendarNo", records.get(i).getCalendarNo());
			listItems.add(listItem);
		}
		
		//����һ��SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
				R.layout.simple_item, 
				new String[]{"name","date"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		
		//ΪListView����Adapter
		list.setAdapter(simpleAdapter);
		
		//�ر���������
		oper.close();
    }
	
	/**
     * �����˵���Ӧ����
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
//        setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ");   
    	if(item.getItemId() == 1) {
    		//���ѡ�С��༭������ת���༭����
    		Intent intent = new Intent();
			intent.setClass(MainActivity.this, CalendarEditActivity.class);
			intent.putExtra("calendarNo", calendarNo);
			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		oper = new CalendarDataOperation(this);
    		oper.delete(calendarNo);
    		AlarmManage manage = new AlarmManage(MainActivity.this);
    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		//���±�����listview
    		updateDate();
    		//�����ճ��������lsitview
//    		CalendarMainActivity.this.updateDate();
    		//����
    		Toast.makeText(MainActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
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
