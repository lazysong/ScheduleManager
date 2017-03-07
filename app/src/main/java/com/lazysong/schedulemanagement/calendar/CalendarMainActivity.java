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
		//ΪListView�󶨵����¼�������
		updateData();
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
				//�رձ��������������
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
//				Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				calendarNo = records.get(position).getCalendarNo();
//				Toast.makeText(CalendarMainActivity.this, calendarNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});

		 // ��ӳ����¼��ļ�����

        list1.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("�����ճ�");     
                menu.add(0, 0, 0, "ɾ��");  
                menu.add(0, 1, 0, "�༭");
            }  
        });
        
        //Ϊ�ճ�ͳ�Ʋ�����ӵ���¼���Ӧ
        TextView summaryText = (TextView)findViewById(R.id.textView1_add_plan);
        summaryText.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�رձ��������������
//				oper.close();
				Intent intent = new Intent();
				intent.setClass(CalendarMainActivity.this, CalendarTodayActivity.class);
				startActivity(intent);
			}
		});
        
        //Ϊ������ճ̰�ť������¼�������
        addButton = (Button)findViewById(R.id.button1_add_plan);
        addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�رձ��������������
//				oper.close();
				//ת������ճ̰�ť
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
     * �����˵���Ӧ����
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
//        setTitle("����˳����˵�����ĵ�"+item.getItemId()+"����Ŀ");   
    	if(item.getItemId() == 1) {
    		//���ѡ�С��༭������ת���༭����
    		//�رձ��������������
//			oper.close();
    		Intent intent = new Intent();
			intent.setClass(CalendarMainActivity.this, CalendarEditActivity.class);
			intent.putExtra("calendarNo", calendarNo);
			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		oper.delete(calendarNo);
    		AlarmManage manage = new AlarmManage(CalendarMainActivity.this);
    		manage.cancleAlarm(Integer.parseInt(calendarNo));
    		
    		//����listview
    		updateData();
    		//����
    		Toast.makeText(CalendarMainActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
    		
    	}
        return super.onContextItemSelected(item);  
    }  
    
    public void updateData() {
    	//����������Ϊ��ǰ���ڵ��ճ������
        oper = new CalendarDataOperation(this);
        
        
        //�������������ʾ����������
        records = oper.getTodayRecord();
        int todayCount = records.size();
        textView = (TextView)findViewById(R.id.textView1_add_plan);
        textView.setText("��������" + todayCount + "���ճ�");
        //���������е��ճ�������ƺ����ڣ�������ʾ���б�����
        records = oper.getAllRecord();
		//����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for(int i = 0; i < records.size(); i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
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
		
		list1 = (ListView)findViewById(R.id.list1);
		//ΪListView����Adapter
		list1.setAdapter(simpleAdapter);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,CLEAN_INVALID,0,"��չ����ճ�");
		menu.add(0,QUIT,0,"�˳�����");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
		case CLEAN_INVALID:
			//��������ճ�
			oper.deleteInvalid();
			updateData();
			break;
		case QUIT:
			//�˳�����
			 ExitApplication.exit(this);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
