package com.lazysong.schedulemanagement.task;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.calendar.CalendarAddActivity;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.CheckValid;
import com.lazysong.schedulemanagement.help.MyTask;

public class EditTaskActivity extends Activity {

	private EditText name,des,diff,priority;
	private Button buttonsDate,buttonsTime,buttoneDate,buttoneTime,save,cancel;
	private String sdate,stime,edate,etime,taskNo,taskName,taskDes;
	private int syear,smonth,sday,shour,sminute,ssecond,
	eyear,emonth,eday,ehour,eminute,esecond,tdiff=0,tpriority=0;
	private Calendar c;
	private TaskDataOperation oper;
	private MyTask task;
	private Spinner spinnerDiff;
	private Spinner spinnerPriority;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_edit_task);
		Intent intent=getIntent();
		taskNo=intent.getExtras().getInt("taskNo") + "";
		
		
		name=(EditText)findViewById(R.id.name_edit_task);
		des=(EditText)findViewById(R.id.description_edit_task);
		spinnerDiff = (Spinner)findViewById(R.id.spinner1_edit_task);
		spinnerPriority = (Spinner)findViewById(R.id.spinner2_edit_task);
		
		buttonsDate = (Button)findViewById(R.id.start_date_edit_task);
		buttonsTime = (Button)findViewById(R.id.start_time_edit_task);
		buttoneDate = (Button)findViewById(R.id.end_date_edit_task);
		buttoneTime = (Button)findViewById(R.id.end_time_edit_task);
		//��ȡ��������taskNo����Ӧ��plan������
		
		oper = new TaskDataOperation(this);
		task = new MyTask();
		
		//��ȡ��������planNo����Ӧ��plan������
		task = oper.getTaskRecord(Integer.parseInt(taskNo));
		Log.v("checkPlan", "��ѯ����--" + task.getInformation());


		taskName = task.getTaskName();
//		planDes = plan.getPlanDescription();
		name.setText(task.getTaskName());
		des.setText(task.getTaskDescription());
		sdate = task.getStartDate();
		stime = task.getStartTime();
		edate = task.getEndDate();
		etime = task.getEndTime();
		tdiff = (int)task.getDifficulty();
		tpriority = (int) task.getTaskPriority();
		
		
		
//		Toast.makeText(getApplicationContext(), taskName,
//				Toast.LENGTH_SHORT).show();
		
		initSpinnerDiff();
		initSpinnerPriority();
		c = Calendar.getInstance();
		initDate();//��������
		initTime();//����ʱ��
		
		save = (Button)findViewById(R.id.save_edit_task);
		cancel = (Button)findViewById(R.id.cancle_edit_task);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//update(planNo);���¼ƻ�����
				//������Ը��º�����ʱ��ı�
				taskName = name.getText().toString();
				taskDes = des.getText().toString();
//				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�noΪ--" +Integer.parseInt(taskNo));
//				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�diffΪ--" +tdiff);
//				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�prioΪ--" +tpriority);
				
				oper.updateTaskRecord(Integer.parseInt(taskNo), taskName, 
						taskDes,tdiff,tpriority, sdate, stime, edate, etime);
				task = oper.getTaskRecord(Integer.parseInt(taskNo));
				Log.v("checkPlan", "���²���ִ����ϣ�task�����ݶ�Ϊ--" + task.getInformation());
//				tag = "MyLog";
//				Log.v(tag, "MainActivity��OnCreate()����������");
				Toast.makeText(getApplicationContext(), "����ɹ�",
						Toast.LENGTH_SHORT).show();
				
				//�ر����ݿ�����
				oper.close();
				
				AlarmManage manage = new AlarmManage(EditTaskActivity.this);
				CheckValid check = new CheckValid();
//				if(check.laterThanCurrent(edate, etime)) {
					manage.setNotification(10000 +  Integer.parseInt(taskNo), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
//					Toast.makeText(EditTaskActivity.this, "δ����taskNo :" ,
//							Toast.LENGTH_SHORT).show();
//				}
//				else{
//					manage.cancleNotification(10000 + Integer.parseInt(taskNo)); 
////					manage.setNotification(10000 +  Integer.parseInt(taskNo), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
//					Toast.makeText(EditTaskActivity.this, "����taskNo :" ,
//							Toast.LENGTH_SHORT).show();
//				}
//					manage.setNotification(10000 +  Integer.parseInt(taskNo), c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
					
//				Toast.makeText(EditTaskActivity.this, "��ǰ��NOTIFICATION_IDֵΪ" + AlarmManage.NOTIFICATION_ID + "taskNo:" + taskNo, Toast.LENGTH_SHORT).show();
				
				/**ͨ��Calendar����cal����������ڡ�ʱ��ľ������ݸ�ֵ��
				 * year,month,day,hour,minute
				 * ���������ԭ����ֵ�ǵ�������ڡ�ʱ��*/
				EditTaskActivity.this.finish();
			}
		});
		
        cancel.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		//�ر����ݿ�����
        		oper.close();
				EditTaskActivity.this.finish();
			}
        });
		
	}
	
	public void initSpinnerDiff() {
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_difficulty,android.R.layout.simple_spinner_item); 
		//����Spinnerÿ����Ŀ����ʾ��ʽ 
		//3.����һ��ArrayAdapter����ȡ������������Spinner��ʾ����Ϣ�� 
		//��Ӧ����˵����1.�����ı�����2.Ҫ��ʾ���ַ���Array��3.Spinner����ʾ��ʽ 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//Ϊspinner������������д������Ϣ���󶨼����¼��� 
		spinnerDiff.setAdapter(adapter); 
		spinnerDiff.setPrompt("�����Ѷ�"); 
		spinnerDiff.setSelection(tdiff-1,true);
		
		spinnerDiff.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				//��ȡ��ѡ��������Ѷ�
				tdiff = arg2 + 1;
				//Toast.makeText(EditTaskActivity.this, "ѡ����" + (int)(arg2+1), Toast.LENGTH_LONG).show();
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
//				Toast.makeText(CalendarAddActivity.this, "δѡ��", Toast.LENGTH_LONG).show();
			}
		}); 
	}
	
	public void initSpinnerPriority() {
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_priority,
				android.R.layout.simple_spinner_item); 
		//����Spinnerÿ����Ŀ����ʾ��ʽ 
		//3.����һ��ArrayAdapter����ȡ������������Spinner��ʾ����Ϣ�� 
		//��Ӧ����˵����1.�����ı�����2.Ҫ��ʾ���ַ���Array��3.Spinner����ʾ��ʽ 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//Ϊspinner������������д������Ϣ���󶨼����¼��� 
		spinnerPriority.setAdapter(adapter); 
		spinnerPriority.setPrompt("�������ȼ�");
		spinnerPriority.setSelection(tpriority-1,true);
		
		spinnerPriority.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
				//��ȡ��ѡ����ظ�����
				tpriority = arg2+1;
//				Toast.makeText(CalendarAddActivity.this, "ѡ����" + (int)(arg2+1), Toast.LENGTH_LONG).show();
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
				//Toast.makeText(EditTaskActivity.this, "δѡ��", Toast.LENGTH_LONG).show();
			}
		}); 
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
                DatePickerDialog dialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear < 10)
                			sdate = "" + year + "-0" + (int)(monthOfYear);
                		else
                			sdate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			sdate += "-" + "0" + dayOfMonth;
                		else
                			sdate += "-" + dayOfMonth;
//                        date = year + "-" + (int)(monthOfYear + 1) + "-" + dayOfMonth;
                        buttonsDate.setText("" + year + "��" + (int)(monthOfYear) + "��" + dayOfMonth + "��");
//                        Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
                    }
                }, syear,smonth,sday);
                dialog.show();
            }
        });

		buttoneDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        if(monthOfYear < 10)
                			edate = "" + year + "-0" + (int)(monthOfYear);
                		else
                			edate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			edate += "-" + "0" + dayOfMonth;
                		else
                			edate += "-" + dayOfMonth;
//                        date = year + "-" + (int)(monthOfYear + 1) + "-" + dayOfMonth;
                        buttoneDate.setText("" + year + "��" + (int)(monthOfYear) + "��" + dayOfMonth + "��");
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
		
        
//		buttonsTime.setText("" + shour + ":" + sminute);
//		buttoneTime.setText("" + ehour + ":" + eminute);
        //Ϊ��ť�󶨼�����
		buttonsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            	TimePickerDialog dialog = new TimePickerDialog(EditTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
