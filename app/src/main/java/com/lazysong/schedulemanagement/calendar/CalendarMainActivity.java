package com.lazysong.schedulemanagement.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.ExitApplication;
import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.CalendarDataOperation;
import com.lazysong.schedulemanagement.db.MyCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarMainActivity extends Activity {
	private List<MyCalendar> records;
	private Button addButton;
	private String calendarNo;
	private CalendarDataOperation oper ;
	private ListView list1;
	private TextView textView;
	
	final int CLEAN_INVALID = 0x112;
	final int QUIT = 0x1113;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_main);
		ExitApplication.getInstance().addActivity(CalendarMainActivity.this);
		//为ListView绑定单击事件监听器
		updateData();
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				//关闭本界面的数据连接
//				oper.close();
				Intent intent = new Intent();
				intent.setClass(CalendarMainActivity.this, CalendarSpecificActivity.class);
				intent.putExtra("calendarNo", records.get(position).getCalendarNo());
				startActivity(intent);
			}
		});
		
		list1.setOnItemLongClickListener(new OnItemLongClickListener() {

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

        list1.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("管理日程");     
                menu.add(0, 0, 0, "删除");  
                menu.add(0, 1, 0, "编辑");
            }  
        });
        
        //为日程统计部分添加点击事件响应
        TextView summaryText = (TextView)findViewById(R.id.textView1_add_plan);
        summaryText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//关闭本界面的数据连接
//				oper.close();
				Intent intent = new Intent();
				intent.setClass(CalendarMainActivity.this, CalendarTodayActivity.class);
				startActivity(intent);
			}
		});
        
        //为“添加日程按钮”添加事件监听器
        addButton = (Button)findViewById(R.id.button1_add_plan);
        addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//关闭本界面的数据连接
//				oper.close();
				//转到添加日程按钮
				Intent intent = new Intent();
				intent.setClass(CalendarMainActivity.this, CalendarAddActivity.class);
				startActivity(intent);
			}
		});
    }  
	
	
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
    	updateData();
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		oper.close();
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		oper.close();
		super.onStop();
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
			intent.setClass(CalendarMainActivity.this, CalendarEditActivity.class);
			intent.putExtra("calendarNo", calendarNo);
			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		oper.delete(calendarNo);
    		AlarmManage manage = new AlarmManage(CalendarMainActivity.this);
    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		
    		//更新listview
    		updateData();
    		//反馈
    		Toast.makeText(CalendarMainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    		
    	}
        return super.onContextItemSelected(item);  
    }  
    
    public void updateData() {
    	//检索出日期为当前日期的日程项并保存
        oper = new CalendarDataOperation(this);
        
        
        //将结果的名称显示在综述部分
        records = oper.getTodayRecord();
        int todayCount = records.size();
        textView = (TextView)findViewById(R.id.textView1_add_plan);
        textView.setText("今天您有" + todayCount + "项日程");
        //检索出所有的日程项的名称和日期，逐条显示在列表项中
        records = oper.getAllRecord();
		//创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < records.size(); i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
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
		
		list1 = (ListView)findViewById(R.id.list1);
		//为ListView设置Adapter
		list1.setAdapter(simpleAdapter);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,CLEAN_INVALID,0,"清空过期日程");
		menu.add(0,QUIT,0,"退出程序");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case CLEAN_INVALID:
			//清除过期日程
			oper.deleteInvalid();
			updateData();
			break;
		case QUIT:
			//退出程序
			 ExitApplication.exit(this);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
