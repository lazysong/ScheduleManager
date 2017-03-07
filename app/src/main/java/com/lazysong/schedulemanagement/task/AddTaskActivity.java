package com.lazysong.schedulemanagement.task;

import java.util.Calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.TaskDataOperation;
import com.lazysong.schedulemanagement.help.CheckValid;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class AddTaskActivity extends Activity {
	private int planNo;
	private String taskName;
	private String taskDescription;
	private float difficulty;
	private float priority;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	
	private EditText taskNameEditText;
	private EditText taskDescriptionEditText;
	private Spinner spinnerDifficulty;
	private Spinner spinnerPriority;
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
	
	private Calendar cal_start;
	private Calendar cal_end;
	private Calendar current;
	
	private TaskDataOperation oper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		//��ÿؼ�����
		taskNameEditText = (EditText)findViewById(R.id.name_add_task);
		taskDescriptionEditText = (EditText)findViewById(R.id.description_add_task);
		spinnerDifficulty = (Spinner)findViewById(R.id.spinner1_add_task);
		spinnerPriority = (Spinner)findViewById(R.id.spinner2_add_task);
		startDateButton = (Button)findViewById(R.id.start_date_add_task);
		startTimeButton = (Button)findViewById(R.id.start_time_add_task);
		endDateButton = (Button)findViewById(R.id.end_date_add_task);
		endTimeButton = (Button)findViewById(R.id.end_time_add_task);
		save = (Button)findViewById(R.id.save_add_task);
		cancle = (Button)findViewById(R.id.cancle_add_task);
		
		
		cal_start = Calendar.getInstance();
		cal_end = Calendar.getInstance();
		current = Calendar.getInstance();
		
		initSpinnerDifficulty();
		initSpinnerPriority();
		initStartDate();
		initStartTime();
		initEndDate();
		initEndTime();
		
		
		//ע�������
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				taskName = taskNameEditText.getText().toString();
				taskDescription = taskDescriptionEditText.getText().toString();
				planNo = getIntent().getExtras().getInt("planNo");
				oper = new TaskDataOperation(AddTaskActivity.this);
				try {
					oper.insertTask(planNo, taskName, taskDescription, difficulty, priority, startDate, startTime, endDate, endTime);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				oper.close();
				
				
				CheckValid check = new CheckValid();
				AlarmManage manage = new AlarmManage(AddTaskActivity.this);
//				if(check.laterThanCurrent(endDate, endTime)) {
					manage.setNotification(
							AlarmManage.NOTIFICATION_ID, 
							cal_end.get(Calendar.YEAR), cal_end.get(Calendar.MONTH), cal_end.get(Calendar.DAY_OF_MONTH), 
							cal_end.get(Calendar.HOUR_OF_DAY), cal_end.get(Calendar.MINUTE));
//				}
//				else
//					manage.setNotification(
//							AlarmManage.NOTIFICATION_ID, 
//							cal_end.get(Calendar.YEAR), cal_end.get(Calendar.MONTH), cal_end.get(Calendar.DAY_OF_MONTH), 
//							cal_end.get(Calendar.HOUR_OF_DAY), cal_end.get(Calendar.MINUTE));
				
//				Toast.makeText(AddTaskActivity.this, "��ǰ��NOTIFICATION_IDֵΪ" + AlarmManage.NOTIFICATION_ID  , Toast.LENGTH_SHORT).show();
				AlarmManage.NOTIFICATION_ID ++;
				
				
				AddTaskActivity.this.finish();
			}
		});
		cancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AddTaskActivity.this.finish();
			}
		});
	}
	
	public void initSpinnerDifficulty() {
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_difficulty,android.R.layout.simple_spinner_item); 
		//����Spinnerÿ����Ŀ����ʾ��ʽ 
		//3.����һ��ArrayAdapter����ȡ������������Spinner��ʾ����Ϣ�� 
		//��Ӧ����˵����1.�����ı�����2.Ҫ��ʾ���ַ���Array��3.Spinner����ʾ��ʽ 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//Ϊspinner������������д������Ϣ���󶨼����¼��� 
		spinnerDifficulty.setAdapter(adapter); 
		spinnerDifficulty.setPrompt("�����Ѷ�"); 
		
		spinnerDifficulty.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
				//��ȡ��ѡ����ظ�����
				difficulty = arg2 + 1;
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		}); 
	}
	
	public void initSpinnerPriority() {
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, 
				R.array.spinner_priority,
				android.R.layout.simple_spinner_item); 
		/*
		 * ����Spinnerÿ����Ŀ����ʾ��ʽ 
		 *����һ��ArrayAdapter����ȡ������������Spinner��ʾ����Ϣ�� 
		 *��Ӧ����˵����1.�����ı�����2.Ҫ��ʾ���ַ���Array��3.Spinner����ʾ��ʽ 
		 **/
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//Ϊspinner������������д������Ϣ���󶨼����¼��� 
		spinnerPriority.setAdapter(adapter); 
		spinnerPriority.setPrompt("��ǰʱ��"); 
		
		spinnerPriority.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
				//��ȡ��ѡ����ظ�����
				priority = arg2 + 1;
//					Toast.makeText(CalendarAddActivity.this, "ѡ����" + (int)(arg2+1), Toast.LENGTH_LONG).show();
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
//					Toast.makeText(CalendarAddActivity.this, "δѡ��", Toast.LENGTH_LONG).show();
			}
		}); 
	}
	
	public void initStartDate() {
		//��õ�ǰ���ڣ�����date�ֶβ���ʾ�����ڰ�ť��
		year = cal_start.get(Calendar.YEAR);
		month = cal_start.get(Calendar.MONTH) + 1;
		day = cal_start.get(Calendar.DAY_OF_MONTH);
		
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
                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    	cal_start.set(year, monthOfYear, dayOfMonth);
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
		hour = cal_start.get(Calendar.HOUR_OF_DAY);
		minute = cal_start.get(Calendar.MINUTE);
		second = cal_start.get(Calendar.SECOND);
		
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
        startTimeButton.setText("��ʼʱ��\n��" + hour + ":" + minute);
        
        //Ϊ��ť�󶨼�����
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//	                    	c.set(hourOfDay, minute);
                    	cal_start.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	cal_start.set(Calendar.MINUTE, minute);
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
		year = cal_end.get(Calendar.YEAR);
		month = cal_end.get(Calendar.MONTH) + 1;
		day = cal_end.get(Calendar.DAY_OF_MONTH);
		
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
                DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    	cal_end.set(year, monthOfYear, dayOfMonth);
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
		hour = cal_end.get(Calendar.HOUR_OF_DAY);
		minute = cal_end.get(Calendar.MINUTE);
		second = cal_end.get(Calendar.SECOND);
		
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
            	TimePickerDialog dialog = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//	                        	c.set(hourOfDay, minute);
                    	cal_end.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	cal_end.set(Calendar.MINUTE, minute);
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
