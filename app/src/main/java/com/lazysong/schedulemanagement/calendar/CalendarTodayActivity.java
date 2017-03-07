package com.lazysong.schedulemanagement.calendar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.CalendarDataOperation;
import com.lazysong.schedulemanagement.db.MyCalendar;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class CalendarTodayActivity extends Activity {
	private List<MyCalendar> records;
	private Button addButton;
	private String calendarNo;
	private CalendarDataOperation oper;
	private TextView textView;
	private Button backButton;
	private ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_today);
		
		textView = (TextView)findViewById(R.id.textView2_check_today_calendar);
		backButton = (Button)findViewById(R.id.back_check_today_calendar);
		
		updateDate();
		
		//为“添加日程按钮”添加事件监听器
        addButton = (Button)findViewById(R.id.add_check_today_calendar);
        addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//转到添加日程按钮
//				//关闭本界面的数据连接
//				oper.close1();
				Intent intent = new Intent();
				intent.setClass(CalendarTodayActivity.this, CalendarAddActivity.class);
				startActivity(intent);
			}
		});
        
        backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CalendarTodayActivity.this.finish();
			}
		});
        
        list.setCacheColorHint(Color.TRANSPARENT);
        
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(CalendarMainActivity.this, "点击了第"+(position+1)+"项", Toast.LENGTH_LONG).show();
				//切换到详细的日常界面
				Intent intent = new Intent();
				intent.setClass(CalendarTodayActivity.this, CalendarSpecificActivity.class);
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
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateDate();
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
//		oper.close();
		super.onPause();
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
			intent.setClass(CalendarTodayActivity.this, CalendarEditActivity.class);
			intent.putExtra("calendarNo", calendarNo);
			startActivity(intent);
    	}
    	else {
    		//如果选中“删除”，执行删除操作
    		oper.delete(calendarNo);
    		AlarmManage manage = new AlarmManage(CalendarTodayActivity.this);
    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		//更新本界面listview
    		updateDate();
    		//更新日程主界面的lsitview
//    		CalendarMainActivity.this.updateDate();
    		//反馈
    		Toast.makeText(CalendarTodayActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
    		
    	}
        return super.onContextItemSelected(item);  
    }  
    
    public void updateDate() {
    	//所有的日程
        oper = new CalendarDataOperation(this);
        records = oper.getAllRecord();
        
        //将结果的名称显示在综述部分
        String conclusionText = "";
        if(records.size() == 0)
        	conclusionText += "目前您还没有添加任何日程哦，赶快添加吧！";
        else
        	conclusionText += "目前您共有" + records.size() + "项日程";
        textView.setText(conclusionText);
        
        //检索出所有的日程项的名称和日期，逐条显示在列表项中
		
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
		
		list = (ListView)findViewById(R.id.ListView1__check_today_calendar);
		//为ListView设置Adapter
		list.setAdapter(simpleAdapter);
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		oper.close();
		super.onPause();
	}
}
