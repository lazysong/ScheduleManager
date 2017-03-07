package com.lazysong.schedulemanagement.plan;

import java.util.Calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.calendar.CalendarAddActivity;
import com.lazysong.schedulemanagement.db.PlanDataOperation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class AddPlanActivity extends Activity {
	private String planName;
	private String planDescription;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	
	private EditText nameEditText;
	private EditText descriptionEditText;
	private Button startDateButton;
	private Button startTimeButton;
	private Button endDateButton;
	private Button endTimeButton;
	private Button save;
	private Button cancle;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	private Calendar cal;
	private Calendar current;
	private String date;
	
	private PlanDataOperation oper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_plan);
		
		nameEditText = (EditText)findViewById(R.id.editText1_add_plan);
		descriptionEditText = (EditText)findViewById(R.id.editText2_add_plan);
		startDateButton = (Button)findViewById(R.id.button1_add_plan);
		startTimeButton = (Button)findViewById(R.id.button2_add_plan);
		endDateButton = (Button)findViewById(R.id.button3_add_plan);
		endTimeButton = (Button)findViewById(R.id.button4_add_plan);
		save = (Button)findViewById(R.id.button5_add_plan);
		cancle = (Button)findViewById(R.id.button6_add_plan);
		
		current = Calendar.getInstance();
		cal = Calendar.getInstance();
		initStartDate();
		initStartTime();
		initEndDate();
		initEndTime();
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				oper = new PlanDataOperation(AddPlanActivity.this);
				planName = nameEditText.getText().toString();
				planDescription = descriptionEditText.getText().toString();
				try {
					oper.insertPlan(planName, planDescription, startDate, startTime, endDate, endTime);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				oper.close();
				AddPlanActivity.this.finish();
			}
		});
		
		cancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddPlanActivity.this.finish();
			}
		});
	}
	
	public void initStartDate() {
		//��õ�ǰ���ڣ�����date�ֶβ���ʾ�����ڰ�ť��
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		
		if(month < 10)
			startDate = "" + year + "-0" + month;
		else
			startDate = "" + year + "-" + month;
		if (day < 10)
			startDate += "-" + "0" + day;
		else
			startDate += "-" + day;
		startDateButton.setText("��ʼ����\n" + year + "��" + month + "��" + day + "��");		
		startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        if(monthOfYear < 10)
                			startDate = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			startDate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			startDate += "-" + "0" + dayOfMonth;
                		else
                			startDate += "-" + dayOfMonth;
                		startDateButton.setText("��ʼ����\n" + year + "��" + (int)(monthOfYear + 1) + "��" + dayOfMonth + "��");
                    }
                }, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
	}
	
	public void initStartTime() {
		//��õ�ǰʱ�䣬����time�ֶ�
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
		
		//��ʽ��ʱ��
		if(hour < 10)
			startTime = "0" + hour + ":";
		else
			startTime = "" + hour + ":";
		if(minute < 10)
			startTime += "0"+ minute + ":";
		else
			startTime += minute + ":";
		if(second < 10)
			startTime += "0" + second;
		else
			startTime += second;
		
		//�ڰ�ť����ʾʱ��
        startTimeButton.setText("��ʼʱ��\n" + hour + ":" + minute);
        
        //Ϊ��ť�󶨼�����
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(AddPlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//                    	c.set(hourOfDay, minute);
                    	cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	cal.set(Calendar.MINUTE, minute);
                    	if(hourOfDay < 10)
                    		startTime = "0" + hourOfDay + ":";
                		else
                			startTime = "" + hourOfDay + ":";
                		if(minute < 10)
                			startTime += "0"+ minute + ":";
                		else
                			startTime += minute + ":";
                		startTime += "00";
                		startTimeButton.setText("��ʼʱ��\n" + hourOfDay + ":" + minute);
        			}
                }, current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });
	}
        
    public void initEndDate() {
		//��õ�ǰ���ڣ�����date�ֶβ���ʾ�����ڰ�ť��
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		
		if(month < 10)
			endDate = "" + year + "-0" + month;
		else
			endDate = "" + year + "-" + month;
		if (day < 10)
			endDate += "-" + "0" + day;
		else
			endDate += "-" + day;
		endDateButton.setText("��������\n" + year + "��" + month + "��" + day + "��");		
		endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        if(monthOfYear < 10)
                        	endDate = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			endDate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			endDate += "-" + "0" + dayOfMonth;
                		else
                			endDate += "-" + dayOfMonth;
                		endDateButton.setText("��������\n" + year + "��" + (int)(monthOfYear + 1) + "��" + dayOfMonth + "��");
                    }
                }, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
	}
	
	public void initEndTime() {
		//��õ�ǰʱ�䣬����time�ֶ�
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);
		
		//��ʽ��ʱ��
		if(hour < 10)
			endTime = "0" + hour + ":";
		else
			endTime = "" + hour + ":";
		if(minute < 10)
			endTime += "0"+ minute + ":";
		else
			endTime += minute + ":";
		if(second < 10)
			endTime += "0" + second;
		else
			endTime += second;
		
		//�ڰ�ť����ʾʱ��
        endTimeButton.setText("����ʱ��\n" + hour + ":" + minute);
        
        //Ϊ��ť�󶨼�����
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(AddPlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//                        	c.set(hourOfDay, minute);
                    	cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	cal.set(Calendar.MINUTE, minute);
                    	if(hourOfDay < 10)
                    		endTime = "0" + hourOfDay + ":";
                		else
                			endTime = "" + hourOfDay + ":";
                		if(minute < 10)
                			endTime += "0"+ minute + ":";
                		else
                			endTime += minute + ":";
                		endTime += "00";
                		endTimeButton.setText("����ʱ��\n" + hourOfDay + ":" + minute);
        			}
                }, current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });
	}
}
