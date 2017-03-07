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
		
		//Ϊ������ճ̰�ť������¼�������
        addButton = (Button)findViewById(R.id.add_check_today_calendar);
        addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ת������ճ̰�ť
//				//�رձ��������������
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
//				Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
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
     * �����˵���Ӧ����
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
//        setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ");   
    	if(item.getItemId() == 1) {
    		//���ѡ�С��༭������ת���༭����
    		Intent intent = new Intent();
			intent.setClass(CalendarTodayActivity.this, CalendarEditActivity.class);
			intent.putExtra("calendarNo", calendarNo);
			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		oper.delete(calendarNo);
    		AlarmManage manage = new AlarmManage(CalendarTodayActivity.this);
    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		//���±�����listview
    		updateDate();
    		//�����ճ��������lsitview
//    		CalendarMainActivity.this.updateDate();
    		//����
    		Toast.makeText(CalendarTodayActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
    		
    	}
        return super.onContextItemSelected(item);  
    }  
    
    public void updateDate() {
    	//���е��ճ�
        oper = new CalendarDataOperation(this);
        records = oper.getAllRecord();
        
        //�������������ʾ����������
        String conclusionText = "";
        if(records.size() == 0)
        	conclusionText += "Ŀǰ����û������κ��ճ�Ŷ���Ͽ���Ӱɣ�";
        else
        	conclusionText += "Ŀǰ������" + records.size() + "���ճ�";
        textView.setText(conclusionText);
        
        //���������е��ճ�������ƺ����ڣ�������ʾ���б�����
		
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
		
		list = (ListView)findViewById(R.id.ListView1__check_today_calendar);
		//ΪListView����Adapter
		list.setAdapter(simpleAdapter);
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		oper.close();
		super.onPause();
	}
}
