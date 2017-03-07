package com.lazysong.schedulemanagement.plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lazysong.schedulemanagement.PlanMainActivity;
import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyPlan;
import com.lazysong.schedulemanagement.help.MyTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class CheckAllPlanActivity extends Activity {
	private Button back;
	private Button add;
	private TextView numberOfPlansTextView;
	private ListView list;
	
	private PlanDataOperation planOper;
	private List<MyPlan> allPlans;
	private int planNo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_all_plan);
		
		//��ÿؼ�
		back = (Button)findViewById(R.id.back_check_all_plan);
		add = (Button)findViewById(R.id.add_check_all_plan);
		numberOfPlansTextView = (TextView)findViewById(R.id.textView2_check_all_plan);
		list = (ListView)findViewById(R.id.ListView1__check_all_plan);
		list.setCacheColorHint(Color.TRANSPARENT);
		updateData();
		
		//Ϊback�󶨼�����
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckAllPlanActivity.this.finish();
			}
		});
		
		//Ϊback�󶨼�����
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CheckAllPlanActivity.this, AddPlanActivity.class);
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
				intent.setClass(CheckAllPlanActivity.this, CheckPlanActivity.class);
				intent.putExtra("planNo", allPlans.get(position).getPlanNo());
//				Toast.makeText(CheckAllPlanActivity.this, "�ƻ����Ϊ" + allPlans.get(position).getPlanNo(), Toast.LENGTH_LONG).show();
				startActivity(intent);
			}
		});
		
		//
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				planNo = allPlans.get(position).getPlanNo();
//				Toast.makeText(CheckAllPlanActivity.this, "�ƻ����Ϊ" + planNo, Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		//
		 list.setOnCreateContextMenuListener(new AdapterView.OnCreateContextMenuListener() {  
             
	            @Override  
	            public void onCreateContextMenu(ContextMenu menu, View v,android.view.ContextMenu.ContextMenuInfo menuInfo) {  
	                menu.setHeaderTitle("�����ճ�");     
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

				intent.putExtra("planNo", planNo);
				intent.setClass(CheckAllPlanActivity.this, EditPlanActivity.class);

			startActivity(intent);
    	}
    	else {
    		//���ѡ�С�ɾ������ִ��ɾ������
    		//ȡ������
    		//ɾ����¼
    		planOper = new PlanDataOperation(this);
    		planOper.delete(planNo);
    		AlarmManage manage = new AlarmManage(CheckAllPlanActivity.this);
			List<MyTask> tasksOfPlan = planOper.getTasksOfPlan(planNo);
			for(int i = 0; i < tasksOfPlan.size(); i ++) {
				manage.cancleNotification(tasksOfPlan.get(i).getTaskNo() + 10000);
			}
    		planOper.close();
    		updateData();
    		//����
    		Toast.makeText(CheckAllPlanActivity.this, "��ɾ��", Toast.LENGTH_SHORT).show();
    	}
        return super.onContextItemSelected(item);  
    }
	
	public void updateData() {
		//��ȡ����м����ƻ��Ͷ����ƻ�
			planOper = new PlanDataOperation(this);
			allPlans = planOper.getAllRecords();
			int numberOfPlans = allPlans.size();

			for(int i = 0; i < allPlans.size(); i ++) {
				planOper.refreshData(allPlans.get(i).getPlanNo());
			}
			

			//�ڽ�������ʾ�м����ƻ�
			String text1 = "";
			if(numberOfPlans == 0)
				text1 += "����û�д����κμƻ�Ŷ���촴��һ���ɣ�";
			else
				text1 += "Ŀǰ������" + numberOfPlans + "��ƻ�\n";
			
			numberOfPlansTextView.setText(text1);
			

			
			//�ر���������
			planOper.close();
			
			/*
			 * ����Ϊlist������
			 * */
			List<Map<String, Object>> listItemPlan = new ArrayList<Map<String, Object>>();
			
			String[] planName = new String[allPlans.size()];
			String[] planState = new String[allPlans.size()];
			int[] iconState = new int[allPlans.size()];
			
			String[] planStateText = new String[]{"����������","��δ��ʼ","���ڽ���","δ����","������"};
			int[] icons = new int[]{0, R.drawable.not_start, R.drawable.going_on, R.drawable.not_evaluate, R.drawable.evaluated};
			
			for(int i = 0; i < allPlans.size(); i ++) {
				if(allPlans.get(i).getPlanName().length() == 0)
					planName[i] = "δ�����ƻ�";
				else
					planName[i] = allPlans.get(i).getPlanName();
				planState[i] = planStateText[allPlans.get(i).getState()];
//				iconState[i] = icons[allPlans.get(i).getState()];
			}
//			ImageView stateView = (ImageView)findViewById(R.id.stateImageView);
			
			
			for(int i = 0; i < planName.length; i ++) {
				Map<String, Object> listItem = new HashMap<String, Object>();
				listItem.put("planName", planName[i]);
				listItem.put("planState", planState[i]);

				listItemPlan.add(listItem);
			}
//				
			//����һ��SimpleAdapter
			SimpleAdapter simpleAdapterPlan = new SimpleAdapter(this, listItemPlan,
					R.layout.simple_item, 
					new String[]{"planName","planState"},
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
