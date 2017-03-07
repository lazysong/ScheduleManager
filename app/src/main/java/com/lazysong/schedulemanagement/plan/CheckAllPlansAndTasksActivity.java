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
//								Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
				Intent intent = new Intent();
				intent.setClass(CheckAllPlansAndTasksActivity.this, CheckPlanActivity.class);
				intent.putExtra("planNo", allPlans.get(position).getPlanNo());
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "�ƻ����Ϊ" + allPlans.get(position).getPlanNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		//Ϊlist2�󶨵����¼�������
		list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//										Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
				Intent intent = new Intent();
				intent.setClass(CheckAllPlansAndTasksActivity.this, CheckSingleTaskActivity.class);
				intent.putExtra("taskNo", allSingleTasks.get(position).getTaskNo());
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "������Ϊ" + allSingleTasks.get(position).getTaskNo(), Toast.LENGTH_LONG).show();
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
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "�ƻ����Ϊ" + planNo, Toast.LENGTH_LONG).show();
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
//				Toast.makeText(CheckAllPlansAndTasksActivity.this, "������Ϊ" + taskNo, Toast.LENGTH_LONG).show();
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
        
        list2.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
            
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
                menu.setHeaderTitle("�����ճ�");     
                menu.add(0, 0, 0, "ɾ��");  
                menu.add(0, 1, 0, "�༭");
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
     * �����˵���Ӧ����
     *   */
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
    	if(item.getItemId() == 1) {
    		//���ѡ�С��༭������ת���༭����
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
    		//���ѡ�С�ɾ������ִ��ɾ������
    		//ȡ������
    		//ɾ����¼
    		planOper = new PlanDataOperation(this);
    		taskOper = new TaskDataOperation(this);
    		if(selected == 1)
//    			Toast.makeText(CheckAllPlansAndTasksActivity.this, "ѡ�е��Ǽƻ�", Toast.LENGTH_SHORT).show();
    			planOper.delete(planNo);
    		if(selected == 2)
//    			Toast.makeText(CheckAllPlansAndTasksActivity.this, "ѡ�е��Ƕ�������", Toast.LENGTH_SHORT).show();
    			taskOper.delete(taskNo);
    		planOper.close();
    		taskOper.close();
    		updateData();
    		//����
    		Toast.makeText(CheckAllPlansAndTasksActivity.this, "��ɾ��", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }
	
	public void updateData() {
		
		//��ȡ���мƻ��Ͷ����ƻ�
		planOper = new PlanDataOperation(this);
		allPlans = planOper.getAllRecords();
		
		taskOper = new TaskDataOperation(this);
		allSingleTasks = taskOper.getAllSingleTasks();
		
		
		
		/*
		 * ����Ϊlist1������
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
		
		//����һ��SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
				R.layout.simple_item, 
				new String[]{"planName","planCompletence"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		//Ϊlist1����Adapter
		list1.setAdapter(simpleAdapterPlan);
				
		/*
		 * ����Ϊlist2������
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
		
		//Ϊlist2����Adapter
		list2.setAdapter(simpleAdapterTask);
		
		//�ر���������
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
