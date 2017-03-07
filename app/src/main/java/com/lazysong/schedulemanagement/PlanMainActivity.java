package com.lazysong.schedulemanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
import com.lazysong.schedulemanagement.plan.AddPlanActivity;
import com.lazysong.schedulemanagement.plan.CheckAllPlanActivity;
import com.lazysong.schedulemanagement.plan.CheckAllPlansAndTasksActivity;
import com.lazysong.schedulemanagement.plan.CheckPlanActivity;
import com.lazysong.schedulemanagement.plan.EditPlanActivity;
import com.lazysong.schedulemanagement.task.AddSingleTaskActivity;
import com.lazysong.schedulemanagement.task.CheckAllSingleTaskActivity;
import com.lazysong.schedulemanagement.task.CheckSingleTaskActivity;
import com.lazysong.schedulemanagement.task.EditSingleTaskActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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

public class PlanMainActivity extends Activity {
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private ListView list1;
	private ListView list2;
	private Button back;
	private Button add;
	
	private int planNo;
	private int taskNo;
	private PlanDataOperation planOper;
	private TaskDataOperation taskOper;
	private int selected;
	private float totalAbility;
	
	private List<MyPlan> recentPlans = new ArrayList<MyPlan>();
	private List<MyTask> recentSingleTasks = new ArrayList<MyTask>();
	private List<MyPlan> allPlans = new ArrayList<MyPlan>();
	private List<MyTask> allSingleTasks = new ArrayList<MyTask>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_main);
		
		/*
		 * ���濪ʼ�����µĴ�����
		 * */
		//������еĿؼ�
		textView1 = (TextView)findViewById(R.id.plan_conclusion_plan_main);
		textView2 = (TextView)findViewById(R.id.task_conclusion_plan_main);
		textView3 = (TextView)findViewById(R.id.total_ability_plan_main);
		list1 = (ListView)findViewById(R.id.ListView1_plan_main);
		list2 = (ListView)findViewById(R.id.ListView2_plan_main);
		back = (Button)findViewById(R.id.back_plan_main);
		add = (Button)findViewById(R.id.add_plan_main);
		
		list1.setCacheColorHint(Color.TRANSPARENT);
		list2.setCacheColorHint(Color.TRANSPARENT);
		
		//������Բ��뺯��
//		PlanDataOperation oper = new PlanDataOperation(this);
//		try{
//			oper.insertPlan("�¼ӵļƻ�", "planDescription1", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		try{
//			oper.insertPlan("plnaName1", "planDescription1", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		TaskDataOperation oper1 = new TaskDataOperation(this);
//		MyTask task = new MyTask();
//		
//		//���ڿ�ʼ���Բ�������ĺ���
//		try {
//			oper1.insertTask(18, "new Single Task", "taskDescription1", 3, 1, "2014-09-08", "02:09:00", "2015-12-09", "13:21:00");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		oper1.close();
//		oper1.setCompletence(3, 90);
//		try {
//			oper1.insertTask(2, "taskName1", "taskDescription1", 3, 1, "2014-09-08", "02:09:00", "2014-12-09", "13:21:00");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		oper1.setCompletence(4, 90);
		
		updateData();
		
		/*
		 * �󶨼�����
		 * */
		//Ϊlist1�󶨵����¼�������
		list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//								Toast.makeText(CalendarMainActivity.this, "����˵�"+(position+1)+"��", Toast.LENGTH_LONG).show();
				//�л�����ϸ���ճ�����
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, CheckPlanActivity.class);
				intent.putExtra("planNo", recentPlans.get(position).getPlanNo());
//				Toast.makeText(PlanMainActivity.this, "�ƻ����Ϊ" + recentPlans.get(position).getPlanNo(), Toast.LENGTH_LONG).show();
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
				intent.setClass(PlanMainActivity.this, CheckSingleTaskActivity.class);
				intent.putExtra("taskNo", recentSingleTasks.get(position).getTaskNo());
//				Toast.makeText(PlanMainActivity.this, "������Ϊ" + recentSingleTasks.get(position).getTaskNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		list1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				planNo = recentPlans.get(position).getPlanNo();
				selected = 1;
//				Toast.makeText(PlanMainActivity.this, "�ƻ����Ϊ" + planNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		list2.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				taskNo = recentSingleTasks.get(position).getTaskNo();
				selected = 2;
//				Toast.makeText(PlanMainActivity.this, "������Ϊ" + taskNo, Toast.LENGTH_LONG).show();
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
        
        textView1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, CheckAllPlanActivity.class);
				startActivity(intent);
			}
		});
        
        textView2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, CheckAllSingleTaskActivity.class);
				startActivity(intent);
			}
		});
        
        
        
        textView3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(PlanMainActivity.this, TotalCompletenceActivity.class);
				intent.putExtra("totalAbility", totalAbility);
				startActivity(intent);
			}
		});
        
        back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PlanMainActivity.this.finish();
			}
		});
        add.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(PlanMainActivity.this)
			 	.setTitle("��ѡ��")
			 	.setIcon(android.R.drawable.ic_dialog_info)                
			 	.setSingleChoiceItems(new String[] {"�ƻ�","��������"}, 0, 
			 	  new DialogInterface.OnClickListener() {
			 	                              
			 	     public void onClick(DialogInterface dialog, int which) {
			 	    	 Intent intent = new Intent();
			 	    	 switch (which) {
			 	    	
			 	    	 case 0:
			 	    		 intent.setClass(PlanMainActivity.this, AddPlanActivity.class);
			 	    		 startActivity(intent);
			 	    		 break;
			 	    	 case 1:
			 	    		 intent.setClass(PlanMainActivity.this, AddSingleTaskActivity.class);
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
        
	}
		 
	/**
	 * ��������
	 * ͨ��ÿ�����·��ص��������ʱ�򶼻���ô˺���
	 * */
	public void updateData() {
		
		//��ȡ����м����ƻ��Ͷ����ƻ�
		planOper = new PlanDataOperation(this);
		recentPlans = planOper.getRecentPlans();
		int numberOfPlans = recentPlans.size();
		
		taskOper = new TaskDataOperation(this);
		recentSingleTasks = taskOper.getRecentSingleTasks();
		int numberOfSingleTasks = recentSingleTasks.size();
		
		
		//�ڽ�������ʾ����м����ƻ��Ͷ�������
		String text1 = "";
		if(numberOfPlans == 0)
			text1 += "������û��Ҫִ�еļƻ�\n";
		else
			text1 += "��������" + numberOfPlans + "��ƻ�\n";
		
		textView1.setText(text1);
		
		//�ڽ�������ʾ����м����ƻ��Ͷ�������
		String text2 = "";
		if(numberOfSingleTasks == 0)
			text2 += "������û��Ҫִ�еĶ�������";
		else
			text2 += "��������" + numberOfSingleTasks + "���������\n";
		
		textView2.setText(text2);
		
		
		/*��ʾ�ۺ�ִ������*/
		Evaluate evaluate = new Evaluate();
		
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//���㲢���¼ƻ��Ͷ����������Ӧ�ֶ�
		for(int i = 0; i < allPlans.size(); i ++)
			planOper.refreshData(allPlans.get(i).getPlanNo());
		for(int i = 0; i < allSingleTasks.size(); i ++)
			taskOper.refreshData(allSingleTasks.get(i).getTaskNo());
		
		//���»�ý��ڼƻ��Ͷ���������б�
		allPlans = planOper.getAllRecords();
		allSingleTasks = taskOper.getAllSingleTasks();
		
		//�����ۺ�ִ������
		totalAbility = evaluate.computeTotalAbility(allPlans, allSingleTasks);
		String text3 = "��Ŀǰ���ۺ�ִ������Ϊ" + totalAbility;
		textView3.setText(text3);
		
		recentPlans = planOper.getRecentPlans();
		recentSingleTasks = taskOper.getRecentSingleTasks();
		
		//�ر���������
		planOper.close();
		taskOper.close();
		/*
		 * ����Ϊlist1������
		 * */
		List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
		
		String[] planName = new String[recentPlans.size()];
		String[] planState = new String[recentPlans.size()];
		
		String[] showState = new String[]{"sf","δ��ʼ","������","δ����","������","asdf"};
		
		for(int i = 0; i < recentPlans.size(); i ++) {
			if(recentPlans.get(i).getPlanName().length() == 0)
				planName[i] = "δ�����ƻ�";
			else
				planName[i] = recentPlans.get(i).getPlanName();
			planState[i] = showState[recentPlans.get(i).getState()];
		}
		for(int i = 0; i < planName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("planName", planName[i]);
			listItem.put("planState", planState[i]);
			listItemPlan.add(listItem);
		}
		
		//����һ��SimpleAdapter
		SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
				R.layout.simple_item, 
				new String[]{"planName","planState"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
//		list1 = (ListView)findViewById(R.id.ListView1_plan_main);
		//Ϊlist1����Adapter
		list1.setAdapter(simpleAdapterPlan);
				
		/*
		 * ����Ϊlist2������
		 * */
		List<Map<String, Object>> listItemTask = new ArrayList<Map<String, Object>>();
		String[] taskName = new String[recentSingleTasks.size()];
		String[] taskState = new String[recentSingleTasks.size()];
		
		for(int i = 0; i < recentSingleTasks.size(); i ++) {
			if(recentSingleTasks.get(i).getTaskName().length() == 0)
				taskName[i] = "δ��������";
			else
				taskName[i] = recentSingleTasks.get(i).getTaskName();
			taskState[i] = showState[recentSingleTasks.get(i).getState()];
		}
		for(int i = 0; i < taskName.length; i ++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("taskName", taskName[i]);
			listItem.put("taskState", taskState[i]);
			listItemTask.add(listItem);
		}
		
		SimpleAdapter simpleAdapterTask = new SimpleAdapter(this, listItemTask,
				R.layout.simple_item, 
				new String[]{"taskName","taskState"},
				new int[] {R.id.nameTextView, R.id.dateTextView} );
		
//		list2 = (ListView)findViewById(R.id.ListView2_plan_main);
		//Ϊlist2����Adapter
		list2.setAdapter(simpleAdapterTask);
		
		
		
		
	}
	
//	
//    @Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		// TODO Auto-generated method stub
//		super.onCreateContextMenu(menu, v, menuInfo);
//	}


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
				intent.setClass(PlanMainActivity.this, EditPlanActivity.class);
			}
			if(selected == 2){
				intent.putExtra("taskNo", taskNo);
				intent.setClass(PlanMainActivity.this, EditSingleTaskActivity.class);
			}
			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		//ȡ������
    		//ɾ����¼
    		planOper = new PlanDataOperation(this);
    		taskOper = new TaskDataOperation(this);
    		
    		AlarmManage manage = new AlarmManage(PlanMainActivity.this);
    		
    		if(selected == 1){
//    			Toast.makeText(PlanMainActivity.this, "ѡ�е��Ǽƻ�", Toast.LENGTH_SHORT).show();
    			planOper.delete(planNo);
    			List<MyTask> tasksOfPlan = planOper.getTasksOfPlan(planNo);
    			for(int i = 0; i < tasksOfPlan.size(); i ++) {
    				manage.cancleNotification(tasksOfPlan.get(i).getTaskNo() + 10000);
    			}
    		}
    		if(selected == 2) {
//    			Toast.makeText(PlanMainActivity.this, "ѡ�е��Ƕ�������", Toast.LENGTH_SHORT).show();
    			taskOper.delete(taskNo);
    			manage.cancleNotification(taskNo + 10000);
    		}
    		planOper.close();
    		taskOper.close();
    		updateData();
    		//����
    		Toast.makeText(PlanMainActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateData();
	}  
    
}
