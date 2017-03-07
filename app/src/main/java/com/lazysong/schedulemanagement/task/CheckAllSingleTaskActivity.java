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
		
		//��ÿؼ�
		back = (Button)findViewById(R.id.back_check_all_singletask);
		add = (Button)findViewById(R.id.add_check_all_singletask);
		numberOfSingleTasksTextView = (TextView)findViewById(R.id.textView2_check_all_singletask);
		list = (ListView)findViewById(R.id.ListView1_check_all_singletask);
		list.setCacheColorHint(Color.TRANSPARENT);
		
		updateData();
		
		//Ϊback�󶨼�����
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckAllSingleTaskActivity.this.finish();
			}
		});
		
		//Ϊback�󶨼�����
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
//								Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
				Intent intent = new Intent();
				intent.setClass(CheckAllSingleTaskActivity.this, CheckSingleTaskActivity.class);
				intent.putExtra("taskNo", allSingleTasks.get(position).getTaskNo());
//				Toast.makeText(CheckAllSingleTaskActivity.this, "�ƻ����Ϊ" + allSingleTasks.get(position).getTaskNo(), Toast.LENGTH_LONG).show();
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
//				Toast.makeText(CheckAllSingleTaskActivity.this, "�ƻ����Ϊ" + taskNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		//
		 list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
             
	            @Override  
	            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
	                menu.setHeaderTitle("�����������");     
	                menu.add(0, 0, 0, "ɾ��");  
	                menu.add(0, 1, 0, "�༭");
	            }  
	        });
	}
	
	/**
     * �����˵���Ӧ����
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
    	if(item.getItemId() == 1) {
    		//���ѡ�С��༭������ת���༭����
    		Intent intent = new Intent();

				intent.putExtra("taskNo", taskNo);
				intent.setClass(CheckAllSingleTaskActivity.this, EditTaskActivity.class);

			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		//ȡ������
    		//ɾ����¼
    		taskOper = new TaskDataOperation(this);
    		taskOper.delete(taskNo);
    		AlarmManage manage = new AlarmManage(CheckAllSingleTaskActivity.this);
    		manage.cancleNotification(taskNo + 10000);
    		taskOper.close();
    		updateData();
    		//����
    		Toast.makeText(CheckAllSingleTaskActivity.this, "��ɾ��", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }
	
	public void updateData() {
		//��ȡ����м����ƻ��Ͷ����ƻ�
		taskOper = new TaskDataOperation(this);
		allSingleTasks = taskOper.getAllSingleTasks();
		int numberOfSingleTasks = allSingleTasks.size();

		for(int i = 0; i < allSingleTasks.size(); i ++) {
			taskOper.refreshData(allSingleTasks.get(i).getTaskNo());
		}
		
		//�ڽ�������ʾ�м����ƻ�
		String text = " ";
		if(numberOfSingleTasks == 0)
			text += "����û�д����κζ�������Ŷ���Ͽ촴��һ���ɣ�";
		else
			text += "Ŀǰ������" + numberOfSingleTasks + "���������\n";
		numberOfSingleTasksTextView.setText(text);
		

		
		//�ر���������
		taskOper.close();
		
		/*
		 * ����Ϊlist������
		 * */
		List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
		
		String[] taskName = new String[allSingleTasks.size()];
		String[] taskState = new String[allSingleTasks.size()];
		int[] iconState = new int[allSingleTasks.size()];
		
		String[] taskStateText = new String[]{"","��δ��ʼ","���ڽ���","δ����","������"};
		int[] icons = new int[]{R.drawable.not_start, R.drawable.going_on, R.drawable.not_evaluate, R.drawable.evaluated};
		
		for(int i = 0; i < allSingleTasks.size(); i ++) {
			if(allSingleTasks.get(i).getTaskName().length() == 0) {
				
				taskName[i] = "δ��������";
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
		//����һ��SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
				R.layout.simple_item, 
				new String[]{"taskName","taskState"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		//Ϊlist1����Adapter
		list.setAdapter(simpleAdapterPlan);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateData();
		super.onResume();
	}
}
