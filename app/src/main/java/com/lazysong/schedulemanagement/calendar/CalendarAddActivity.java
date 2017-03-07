package com.lazysong.schedulemanagement.calendar;

import java.util.Calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.CalendarDataOperation;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class CalendarAddActivity extends Activity {
	private EditText textName;
	private Button buttonDate;
	private Button buttonTime;
	private EditText textPlace;
	private EditText textDescription;
	private Spinner spinnerRepetition;
	private Spinner spinnerAdvanceTime;
	private Button save;
	private Button cancle;
	
	private String calendarNo;
	private String calendarName;
	private String date;
	private String time;
	private String place;
	private String description;
	private String repetition;
	private String advanceTime;
	
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;
	
	private Calendar c;
	private CalendarDataOperation oper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_add);
		
		//����������
		textName = (EditText)findViewById(R.id.editText1_add_calendar);
		buttonDate = (Button)findViewById(R.id.button1_add_calendar);
		buttonTime = (Button)findViewById(R.id.button2_add_calendar);
		textPlace = (EditText)findViewById(R.id.editText2_add_calendar);
		textDescription = (EditText)findViewById(R.id.editText3_add_calendar);
		spinnerRepetition = (Spinner)findViewById(R.id.spinner1_add_calendar);
		spinnerAdvanceTime = (Spinner)findViewById(R.id.spinner2_add_calendar);
		save = (Button)findViewById(R.id.save_add_calendar);
		cancle =(Button)findViewById(R.id.cancle_add_calendar);
		
		

		initSpinnerRepetition();
		initSpinnerPromition();
		c = Calendar.getInstance();
		initDate();
		initTime();
		
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				insert();
				/**ͨ��Calendar����cal����������ڡ�ʱ��ľ������ݸ�ֵ��
				 * year,month,day,hour,minute
				 * ���������ԭ����ֵ�ǵ�������ڡ�ʱ��*/
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				hour = c.get(Calendar.HOUR_OF_DAY);
				minute = c.get(Calendar.MINUTE);
				
				//ͨ�����ڡ�ʱ��ֵ����������
//				Toast.makeText(CalendarAddActivity.this, "������˱��Ϊ" + CalendarDataOperation.recordCount + "������", Toast.LENGTH_SHORT).show();
				AlarmManage manager = new AlarmManage(CalendarAddActivity.this);
				manager.setAlarm(CalendarDataOperation.recordCount ++, repetition, advanceTime, year, month, day, hour, minute);
//				Intent intent = new Intent();
//				intent.setClass(CalendarAddActivity.this, CalendarMainActivity.class);
//				startActivity(intent);
				CalendarAddActivity.this.finish();
			}
		});
		
		cancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CalendarAddActivity.this.finish();
			}
		});

	}
	
	public void initSpinnerRepetition() {
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_repeat,android.R.layout.simple_spinner_item); 
		//����Spinnerÿ����Ŀ����ʾ��ʽ 
		//3.����һ��ArrayAdapter����ȡ������������Spinner��ʾ����Ϣ�� 
		//��Ӧ����˵����1.�����ı�����2.Ҫ��ʾ���ַ���Array��3.Spinner����ʾ��ʽ 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//Ϊspinner������������д������Ϣ���󶨼����¼��� 
		spinnerRepetition.setAdapter(adapter); 
		spinnerRepetition.setPrompt("�ظ�����"); 
		
		spinnerRepetition.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
				//��ȡ��ѡ����ظ�����
				repetition = arg2 + 1 + "";
//				Toast.makeText(CalendarAddActivity.this, "ѡ����" + (int)(arg2+1), Toast.LENGTH_LONG).show();
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
//				Toast.makeText(CalendarAddActivity.this, "δѡ��", Toast.LENGTH_LONG).show();
			}
		}); 
	}
	
	public void initSpinnerPromition() {
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, 
				R.array.spinner_promote,
				android.R.layout.simple_spinner_item); 
		//����Spinnerÿ����Ŀ����ʾ��ʽ 
		//3.����һ��ArrayAdapter����ȡ������������Spinner��ʾ����Ϣ�� 
		//��Ӧ����˵����1.�����ı�����2.Ҫ��ʾ���ַ���Array��3.Spinner����ʾ��ʽ 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//Ϊspinner������������д������Ϣ���󶨼����¼��� 
		spinnerAdvanceTime.setAdapter(adapter); 
		spinnerAdvanceTime.setPrompt("��ǰʱ��"); 
		
		spinnerAdvanceTime.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
				//��ȡ��ѡ����ظ�����
				advanceTime = arg2 + 1 + "";
//				Toast.makeText(CalendarAddActivity.this, "ѡ����" + (int)(arg2+1), Toast.LENGTH_LONG).show();
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
//				Toast.makeText(CalendarAddActivity.this, "δѡ��", Toast.LENGTH_LONG).show();
			}
		}); 
	}
	
	public void initDate() {
		//��õ�ǰ���ڣ�����date�ֶβ���ʾ�����ڰ�ť��
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);
		
		if(month < 10)
			date = "" + year + "-0" + month;
		else
			date = "" + year + "-" + month;
		if (day < 10)
			date += "-" + "0" + day;
		else
			date += "-" + day;
		buttonDate.setText("" + year + "��" + month + "��" + day + "��");		
//        date = "" + c.get(Calendar.YEAR) + "-" + (int)(c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
//		Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
//        buttonDate.setText("" + c.get(Calendar.YEAR) + "��" + (int)(c.get(Calendar.MONTH) + 1) + "��" + c.get(Calendar.DAY_OF_MONTH) + "��");
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CalendarAddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        if(monthOfYear < 10)
                			date = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			date = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			date += "-" + "0" + dayOfMonth;
                		else
                			date += "-" + dayOfMonth;
//                        date = year + "-" + (int)(monthOfYear + 1) + "-" + dayOfMonth;
                        buttonDate.setText("" + year + "��" + (int)(monthOfYear + 1) + "��" + dayOfMonth + "��");
//                        Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
	}
	
	public void initTime() {
		//��õ�ǰʱ�䣬����time�ֶ�
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		second = c.get(Calendar.SECOND);
		
		//��ʽ��ʱ��
		if(hour < 10)
			time = "0" + hour + ":";
		else
			time = "" + hour + ":";
		if(minute < 10)
			time += "0"+ minute + ":";
		else
			time += minute + ":";
		if(second < 10)
			time += "0" + second;
		else
			time += second;
		
		//�ڰ�ť����ʾʱ��
        buttonTime.setText("" + hour + ":" + minute);
        
        //Ϊ��ť�󶨼�����
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(CalendarAddActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//                    	c.set(hourOfDay, minute);
                    	c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	c.set(Calendar.MINUTE, minute);
                    	if(hourOfDay < 10)
                    		time = "0" + hourOfDay + ":";
                		else
                			time = "" + hourOfDay + ":";
                		if(minute < 10)
                			time += "0"+ minute + ":";
                		else
                			time += minute + ":";
                		time += "00";
//                    	time = hourOfDay + ":" + minute + ":" + 00;
                    	buttonTime.setText("" + hourOfDay + ":" + minute);
//                      Toast.makeText(CalendarAddActivity.this, time, Toast.LENGTH_LONG).show();
        			}
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),true);
                dialog.show();
            }
        });
	}
	
	public void insert() {
		//���������Ϣ��װΪ�ֶ�
				calendarName = textName.getText().toString();
//				��ȡ������Ϣ 
//				date = "2014-10-10";
//				��ȡʱ����Ϣ
//				time = "20:23:45";
				place = textPlace.getText().toString();
				description = textDescription.getText().toString();
//				��ȡ�����б�spinner������
//				repetition = "1";
//				��ȡ�����б�spinner������
//				advanceTime = "1";
		oper = new CalendarDataOperation(this);
		oper.addAffair("insert into cal1 " +
				"(calendarName," +
				"calendarDate," +
				"calendarTime," +
				"place," +
				"calDescription," +
				"repetition," +
				"advanceTime, " +
				"valid )" +
				"values(?,?,?,?,?,?,?,?);", new String[]{calendarName,date,time,place,description,repetition,advanceTime,"1"});
		//�رձ��������������
		oper.close();
	}
}
