package com.lazysong.schedulemanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.plan.CheckPlanActivity;
import com.lazysong.schedulemanagement.plan.EditPlanActivity;
import com.lazysong.schedulemanagement.task.CheckSingleTaskActivity;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class TotalCompletenceActivity extends Activity {
	private float totalAbility;
	private int selected;
	private int taskNo;
	private int planNo;
	
	private TextView conclusion;
	private ListView list1;
	private ListView list2;
	private Button back;
	
	private TaskDataOperation taskOper;
	private PlanDataOperation planOper;
	private List<MyPlan> allPlans;
	private List<MyTask> allSingleTasks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_total_completence);
		

		conclusion = (TextView)findViewById(R.id.conclusion_total_completence);
		list1 = (ListView)findViewById(R.id.ListView1_total_completence);
		list2 = (ListView)findViewById(R.id.ListView2_total_completence);
		back = (Button)findViewById(R.id.back_total_completence);
		
		list1.setCacheColorHint(Color.TRANSPARENT);
		list2.setCacheColorHint(Color.TRANSPARENT);
		
		updateData();
		
		//
		conclusion.setText("��Ŀǰ���ۺ�ִ������Ϊ��" + totalAbility);
		
		list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//								Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
				Intent intent = new Intent();
				intent.setClass(TotalCompletenceActivity.this, CheckPlanActivity.class);
				intent.putExtra("planNo", allPlans.get(position).getPlanNo());
//				Toast.makeText(TotalCompletenceActivity.this, "�ƻ����Ϊ" + allPlans.get(position).getPlanNo(), Toast.LENGTH_LONG).show();
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
				intent.setClass(TotalCompletenceActivity.this, CheckSingleTaskActivity.class);
				intent.putExtra("taskNo", allSingleTasks.get(position).getTaskNo());
//				Toast.makeText(TotalCompletenceActivity.this, "������Ϊ" + allSingleTasks.get(position).getTaskNo(), Toast.LENGTH_LONG).show();
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
//				Toast.makeText(TotalCompletenceActivity.this, "�ƻ����Ϊ" + planNo, Toast.LENGTH_LONG).show();
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
//				Toast.makeText(TotalCompletenceActivity.this, "������Ϊ" + taskNo, Toast.LENGTH_LONG).show();
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
		
		//�󶨼�����
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TotalCompletenceActivity.this.finish();
			}
		});
	}
	
	public void updateData() {
		
		planOper = new PlanDataOperation(this);
		taskOper = new TaskDataOperation(this);
		
		
		/*��ʾ�ۺ�ִ������*/
		Evaluate evaluate = new Evaluate();
		
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//���㲢���¼ƻ��Ͷ����������Ӧ�ֶ�
		for(int i = 0; i < allPlans.size(); i ++)
			planOper.refreshData(allPlans.get(i).getPlanNo());
		for(int i = 0; i < allSingleTasks.size(); i ++)
			taskOper.refreshData(allSingleTasks.get(i).getTaskNo());
		
		//���»�����мƻ��Ͷ���������б�
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//�����ۺ�ִ������
		totalAbility = evaluate.computeTotalAbility(allPlans, allSingleTasks);
		String text2 = "��Ŀǰ���ۺ�ִ������Ϊ" + totalAbility;
		conclusion.setText(text2);
		
		//�ر���������
		planOper.close();
		taskOper.close();		
		
		/*
		 * ����Ϊlist1������
		 * */
		List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
		
		String[] planName = new String[allPlans.size()];
		String[] planAbility = new String[allPlans.size()];
		
		for(int i = 0; i < allPlans.size(); i ++) {
			if(allPlans.get(i).getPlanName().length() == 0)
				planName[i] = "δ�����ƻ�";
			else
				planName[i] = allPlans.get(i).getPlanName();
			planAbility[i] = allPlans.get(i).getAbility() + "";
		}
		for(int i = 0; i < planName.length; i ++) {
			
			Map<String, Object> listItem = new HashMap<String, Object>();
			if(allPlans.get(i).getState() == 4) {
				listItem.put("planName", planName[i]);
				listItem.put("planAbility", planAbility[i]);
				listItemPlan.add(listItem);
			}
		}
		
		//����һ��SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
				R.layout.simple_item, 
				new String[]{"planName","planAbility"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		//Ϊlist1����Adapter
		list1.setAdapter(simpleAdapterPlan);
				
		/*
		 * ����Ϊlist2������
		 * */
		List<Map<String, Object>> listItemTask = new ArrayList<Map<String, Object>>();
		String[] taskName = new String[allSingleTasks.size()];
		String[] taskAbility = new String[allSingleTasks.size()];
		
		for(int i = 0; i < allSingleTasks.size(); i ++) {
			if(allSingleTasks.get(i).getTaskName().length() == 0)
				taskName[i] = "δ��������";
			else
				taskName[i] = allSingleTasks.get(i).getTaskName();
			taskAbility[i] = allSingleTasks.get(i).getAbility() + "";
		}
		for(int i = 0; i < taskName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			if(allSingleTasks.get(i).getState() == 4) {
				listItem.put("taskName", taskName[i]);
				listItem.put("taskAbility", taskAbility[i]);
				listItemTask.add(listItem);
			}
		}
		
		SimpleAdapter simpleAdapterTask = new SimpleAdapter(this, listItemTask,
				R.layout.simple_item, 
				new String[]{"taskName","taskAbility"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
		//Ϊlist2����Adapter
		list2.setAdapter(simpleAdapterTask);


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
				intent.setClass(TotalCompletenceActivity.this, EditPlanActivity.class);
			}
			if(selected == 2){
				intent.putExtra("taskNo", taskNo);
				intent.setClass(TotalCompletenceActivity.this, EditSingleTaskActivity.class);
			}
			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		//ȡ������
    		//ɾ����¼
    		planOper = new PlanDataOperation(this);
    		taskOper = new TaskDataOperation(this);
    		AlarmManage manage = new AlarmManage(TotalCompletenceActivity.this);
    		if(selected == 1) {
//    			Toast.makeText(TotalCompletenceActivity.this, "ѡ�е��Ǽƻ�", Toast.LENGTH_SHORT).show();
    			planOper.delete(planNo);
    			List<MyTask> tasksOfPlan = planOper.getTasksOfPlan(planNo);
    			for(int i = 0; i < tasksOfPlan.size(); i ++) {
    				manage.cancleNotification(tasksOfPlan.get(i).getTaskNo() + 10000);
    			}
    		}
    		if(selected == 2) {
//    			Toast.makeText(TotalCompletenceActivity.this, "ѡ�е��Ƕ�������", Toast.LENGTH_SHORT).show();
    			taskOper.delete(taskNo);
    			manage.cancleNotification(taskNo + 10000);
    		}
    		planOper.close();
    		taskOper.close();
    		updateData();
    		//����
    		Toast.makeText(TotalCompletenceActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		updateData();
		super.onResume();
	}
	
	
}
