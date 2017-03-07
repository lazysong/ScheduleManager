package com.lazysong.schedulemanagement.plan;

import java.util.Calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.help.MyPlan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditPlanActivity extends Activity {

	private EditText name,des;
	private Button buttonsDate,buttonsTime,buttoneDate,buttoneTime,save,cancel;
	private String sdate,stime,edate,etime,planNo,planName;
	private int syear,smonth,sday,shour,sminute,ssecond,
	eyear,emonth,eday,ehour,eminute,esecond;
	private Calendar c;
	private PlanDataOperation oper;
	private MyPlan plan;
	private String planDes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_plan);
		
		Intent intent=getIntent();
		planNo=intent.getExtras().getInt("planNo") + "";
//		TextView text1=(TextView)findViewById(R.id.conclusion_total_completence);
//		text1.setText(planNo);
		
		oper = new PlanDataOperation(this);
		
		name=(EditText)findViewById(R.id.editText1_edit_plan);
		des=(EditText)findViewById(R.id.editText2_edit_plan);
		buttonsDate = (Button)findViewById(R.id.button1_edit_plan);
		buttonsTime = (Button)findViewById(R.id.button2_edit_plan);
		buttoneDate = (Button)findViewById(R.id.button3_edit_plan);
		buttoneTime = (Button)findViewById(R.id.button4_edit_plan);
		//��ȡ��������planNo����Ӧ��plan������
		plan = oper.getPlanRecord(Integer.parseInt(planNo));
		Log.v("checkPlan", "1--" + plan.getInformation());

		planName = plan.getPlanName();
//		planDes = plan.getPlanDescription();
		name.setText(plan.getPlanName());
		des.setText(plan.getPlanDescription());
		sdate = plan.getStartDate();
		stime = plan.getStartTime();
		edate = plan.getEndDate();
		etime = plan.getEndTime();

		
//		Toast.makeText(getApplicationContext(), planName,
//				Toast.LENGTH_SHORT).show();
		
		c = Calendar.getInstance();
		initDate();//��������
		initTime();//����ʱ��
		
		save = (Button)findViewById(R.id.button5_edit_plan);
		cancel = (Button)findViewById(R.id.button6_edit_plan);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//update(planNo);���¼ƻ�����
				//������Ը��º�����ʱ��ı�
				planName = name.getText().toString();
				planDes = des.getText().toString();
				oper.updatePlanRecord(Integer.parseInt(planNo), planName, planDes, sdate, stime, edate, etime);
				plan = oper.getPlanRecord(Integer.parseInt(planNo));
				Log.v("checkPlan", "���²���ִ����ϣ�plan�����ݶ�Ϊ--" + plan.getInformation());
//				tag = "MyLog";
//				Log.v(tag, "MainActivity��OnCreate()����������");
				Toast.makeText(getApplicationContext(), "����ɹ�",
						Toast.LENGTH_SHORT).show();
				
				//�ر����ݿ�����
				oper.close();
				
				/**ͨ��Calendar����cal����������ڡ�ʱ��ľ������ݸ�ֵ��
				 * year,month,day,hour,minute
				 * ���������ԭ����ֵ�ǵ�������ڡ�ʱ��*/
//				year = c.get(Calendar.YEAR);
//				month = c.get(Calendar.MONTH);
//				day = c.get(Calendar.DAY_OF_MONTH);
//				hour = c.get(Calendar.HOUR_OF_DAY);
//				minute = c.get(Calendar.MINUTE);
				
				//ͨ�����ڡ�ʱ��ֵ����������
//				Toast.makeText(AddPlanActivity.this, "������˱��Ϊ" + CalendarDataOperation.recordCount + "������", Toast.LENGTH_SHORT).show();
//				AlarmManage manager = new AlarmManage(CalendarAddActivity.this);
//				manager.setAlarm(CalendarDataOperation.recordCount ++, repetition, advanceTime, year, month, day, hour, minute);
				EditPlanActivity.this.finish();
			}
		});
		
        cancel.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		//�ر����ݿ�����
        		oper.close();
				EditPlanActivity.this.finish();
			}
        });
		
		//������Բ��뺯��
//		try{
//			oper.insertPlan("plnaName1", "planDescription1", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "2" + plan.getInformation());
//		
//		
		//�����������ִ�������ĺ���
//		oper.setAbility(1, 90);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "����ִ����������ִ����ϣ�plan�����ݶ�Ϊ--" + plan.getInformation());
		
		//������Ը��º�����ʱ��δ�ı�
//		oper.updatePlanRecord(1, "planName1_new", "planDescription1_new", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "���²���ִ����ϣ�plan�����ݶ�Ϊ--" + plan.getInformation());
//		
//		
//		//������Ը��º�����ʱ��ı�
//		oper.updatePlanRecord(1, "planName1_new", "planDescription1_new", "2012-01-10", "12:23:19", "2013-02-02", "13:00:00");
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "���²���ִ����ϣ�plan�����ݶ�Ϊ--" + plan.getInformation());
////		tag = "MyLog";
////		Log.v(tag, "MainActivity��OnCreate()����������");
		
//		//�������ɾ������
//		oper.delete(1);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "ɾ������ִ����ϣ�plan�����ݶ�Ϊ--" + plan.getInformation());*/
//		
		//������Ը����Ѷȡ����ȼ�����ɶ������ֶεĺ���
//		oper.setEvidence(1, 2, 3, 90);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "����ƾ���ֶβ���ִ����ϣ�plan�����ݶ�Ϊ--" + plan.getInformation());
		
		//������Եõ�ͨ���ƻ��õ���������������ĺ���
//		List<MyTask> tasks = oper.getTasksOfPlan(1);
//		for(int i = 0; i < tasks.size(); i ++) {
//			Log.v("checkPlan", "��������" + i + "����¼��" + tasks.get(i).getInformation() + "\n");
//		}
		
		//������Լ��㲢����ִ�������ĺ���
//		oper.updateEvidenceAndAbility(1);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "���㲢����ִ�������ֶβ���ִ����ϣ�plan�����ݶ�Ϊ--" + plan.getInformation());
//		
//		//������Է���ȫ���ƻ��ĺ���
//		List<MyPlan> allPlans = new ArrayList<MyPlan>();
//		allPlans = oper.getAllRecords();
//		for(int i = 0; i < allPlans.size(); i ++) {
//			Log.v("checkPlan", "��������" + i + "����¼��" + allPlans.get(i).getInformation() + "\n");
//		}
//		
//
		//������Է��ؽ���ִ���мƻ��ĺ���
//		List<MyPlan> allPlans = new ArrayList<MyPlan>();
//		allPlans = oper.getRecentPlans();
//		for(int i = 0; i < allPlans.size(); i ++) {
//			Log.v("checkPlan", "��������" + i + "����¼��" + allPlans.get(i).getInformation() + "\n");
//		}
		
		//�ر����ݿ�����
//		oper.close();
		
//		TaskDataOperation oper = new TaskDataOperation(this);
//		MyTask task = new MyTask();
//		
		//���ڿ�ʼ���Բ�������ĺ���
//		try {
//			oper.insertTask(1, "taskName1", "taskDescription1", 3, 1, "2014-09-08", "02:09:00", "2014-12-09", "13:21:00");
//			oper.setCompletence(i + 14, 80);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//���ڿ�ʼ���Բ�ѯĳ�������¼�ĺ���
//		task = oper.getTaskRecord(1);
//		Log.v("checkTask", "���������ɣ���һ������Ϊ��" + task.getInformation());
		
		//������Է��ؽ���ִ���ж�������ĺ���
//		List<MyTask> recentSingleTasks = new ArrayList<MyTask>();
//		recentSingleTasks = oper.getRecentSingleTasks();
//		for(int i = 0; i < recentSingleTasks.size(); i ++) {
//			Log.v("checkTask", "��������" + i + "����¼��" + recentSingleTasks.get(i).getInformation() + "\n");
//		}
		
		//������Է���ȫ����������ĺ���
//		List<MyTask> allSingleTasks = new ArrayList<MyTask>();
//		allSingleTasks = oper.getAllSingleTasks();
//		for(int i = 0; i < allSingleTasks.size(); i ++) {
//			Log.v("checkTask", "��������" + i + "����¼��" + allSingleTasks.get(i).getInformation() + "\n");
//		}
		
		//�����������ִ�������ֶεĺ���
//		oper.setAbility(4, 80);
		
		//������Ը���������߶�������ĺ��������ڱ䶯��
//		oper.updateTaskRecord(4, "taskName_new", "taskDescription_new", 3, 3, "2014-09-08", "02:09:00", "2015-12-09", "13:21:00");
		//������Ը���������߶�������ĺ��������ڱ䶯��
//		oper.updateTaskRecord(5, "taskName_new", "taskDescription_new", 3, 3, "2014-09-08", "02:09:00", "2014-12-09", "13:21:00");
		
		//������������������ɶȺ���
//		oper.setCompletence(8, 80);
		
		//�������ɾ������ĺ���
//		oper.delete(7);
		
		//������Եõ�ĳ���ƻ�����������ĺ���
//		List<MyTask> allTasks = new ArrayList<MyTask>();
//		allTasks = oper.getTasksOfPlan(1);
//		for(int i = 0; i < allTasks.size(); i ++) {
//			Log.v("checkTask", "��������" + i + "����¼��" + allTasks.get(i).getInformation() + "\n");
//		}
		
		//������Լ��㲢����ִ�������ֶεĺ���
//		oper.updateSingleAbility(8);
		
		//�ر����ݿ�����
//		oper.close();
		
		//����ִ������
//		Evaluate evaluate = new Evaluate();
//		Log.v("evaluate", "ִ������Ϊ��" + evaluate.computeAbility(5, 1, 100));

		
	}
    public void initDate() {
//		//��õ�ǰ���ڣ�����date�ֶβ���ʾ�����ڰ�ť��
//		year = c.get(Calendar.YEAR);
//		month = c.get(Calendar.MONTH) + 1;
//		day = c.get(Calendar.DAY_OF_MONTH);
		//����ַ���
		String[] sd =sdate.split("-");
		syear = Integer.parseInt(sd[0]);
		smonth = Integer.parseInt(sd[1]);
		sday = Integer.parseInt(sd[2]);
		
		//����ַ���
		String[] ed =edate.split("-");
		eyear = Integer.parseInt(ed[0]);
		emonth = Integer.parseInt(ed[1]);
		eday = Integer.parseInt(ed[2]);
		
		if(smonth < 10)
			sdate = "" + syear + "-0" + smonth;
		else
			sdate = "" + syear + "-" + smonth;
		if (sday < 10)
			sdate += "-" + "0" + sday;
		else
			sdate += "-" + sday;
		
		if(emonth < 10)
			edate = "" + eyear + "-0" + emonth;
		else
			edate = "" + eyear + "-" + emonth;
		if (eday < 10)
			edate += "-" + "0" + eday;
		else
			edate += "-" + eday;
		
		
		buttonsDate.setText("" + syear + "��" + smonth + "��" + sday + "��");
		buttoneDate.setText("" + eyear + "��" + emonth + "��" + eday + "��");
		
		buttonsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EditPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear < 10)
                			sdate = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			sdate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			sdate += "-" + "0" + dayOfMonth;
                		else
                			sdate += "-" + dayOfMonth;
//                        date = year + "-" + (int)(monthOfYear + 1) + "-" + dayOfMonth;
                        buttonsDate.setText("" + year + "��" + (int)(monthOfYear + 1) + "��" + dayOfMonth + "��");
//                        Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
                    }
                }, syear,smonth,sday);
                dialog.show();
            }
        });


		buttoneDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EditPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        if(monthOfYear < 10)
                			edate = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			edate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			edate += "-" + "0" + dayOfMonth;
                		else
                			edate += "-" + dayOfMonth;
//                        date = year + "-" + (int)(monthOfYear + 1) + "-" + dayOfMonth;
                        buttoneDate.setText("" + year + "��" + (int)(monthOfYear + 1) + "��" + dayOfMonth + "��");
//                        Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
                    }
                }, eyear,emonth,eday);
                dialog.show();
            }
        });

		
           
	}
	
	public void initTime() {
		//����ַ���
		String[] st =stime.split(":");
		shour = Integer.parseInt(st[0]);
		sminute = Integer.parseInt(st[1]);
		ssecond = Integer.parseInt(st[2]);
		
		String[] et =etime.split(":");
		ehour = Integer.parseInt(et[0]);
		eminute = Integer.parseInt(et[1]);
		esecond = Integer.parseInt(et[2]);
		
		//��ʽ��ʱ��
		if(shour < 10)
			stime = "0" + shour + ":";
		else
			stime = "" + shour + ":";
		if(sminute < 10)
			stime += "0"+ sminute + ":";
		else
			stime += sminute + ":";
		if(ssecond < 10)
			stime += "0" + ssecond;
		else
			stime += ssecond;
		
		if(ehour < 10)
			etime = "0" + ehour + ":";
		else
			etime = "" + ehour + ":";
		if(eminute < 10)
			etime += "0"+ eminute + ":";
		else
			etime += eminute + ":";
		if(esecond < 10)
			etime += "0" + esecond;
		else
			etime += esecond;
		
		//�ڰ�ť����ʾʱ��
		if(sminute == 0)
			buttonsTime.setText("" + shour + ":" + sminute+"0");
		else
			buttonsTime.setText("" + shour + ":" + sminute);
        
		if(eminute == 0)
			buttoneTime.setText("" + ehour + ":" + eminute+"0");
		else
			buttoneTime.setText("" + ehour + ":" + eminute);
        
        //Ϊ��ť�󶨼�����
		buttonsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(EditPlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//                    	c.set(hourOfDay, minute);
                    	if(hourOfDay < 10)
                    		stime = "0" + hourOfDay + ":";
                		else
                			stime = "" + hourOfDay + ":";
                		if(minute < 10)
                			stime += "0"+ minute + ":";
                		else
                			stime += minute + ":";
                		stime += "00";
//                    	time = hourOfDay + ":" + minute + ":" + 00;
                    	buttonsTime.setText("" + hourOfDay + ":" + minute);
//                      Toast.makeText(CalendarAddActivity.this, time, Toast.LENGTH_LONG).show();
        			}
                }, shour,sminute,true);
                dialog.show();
            }
        });
		
	      
	    buttoneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(EditPlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//                    	c.set(hourOfDay, minute);
                    	c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	c.set(Calendar.MINUTE, minute);
                    	if(hourOfDay < 10)
                    		etime = "0" + hourOfDay + ":";
                		else
                			etime = "" + hourOfDay + ":";
                		if(minute < 10)
                			etime += "0"+ minute + ":";
                		else
                			etime += minute + ":";
                		etime += "00";
//                    	time = hourOfDay + ":" + minute + ":" + 00;
                    	buttoneTime.setText("" + hourOfDay + ":" + minute);
//                      Toast.makeText(CalendarAddActivity.this, time, Toast.LENGTH_LONG).show();
        			}
                }, ehour,eminute,true);
                dialog.show();
            }
        });
	
	
	}
}
