package com.lazysong.schedulemanagement.calendar;

import java.util.Calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.CalendarDataOperation;
import com.lazysong.schedulemanagement.db.MyCalendar;
import com.lazysong.schedulemanagement.help.CheckValid;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class CalendarEditActivity extends Activity {
	private MyCalendar cal;
	private Calendar c;
	private CalendarDataOperation oper;
	
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_edit);
		
		//��intent�л�ȡcalendarNo�����������༭���ճ̼�¼�ı��
		calendarNo = getIntent().getExtras().getString("calendarNo");
//		Toast.makeText(CalendarEditActivity.this, calendarNo, Toast.LENGTH_SHORT).show();
		
		/**����calendarNo�����������ݿ�
		 * ��ø����ճ̼�¼
		 * ���صļ�¼����MyCalendar���󣬼�cal��
		 * */
		CalendarDataOperation oper = new CalendarDataOperation(CalendarEditActivity.this);
		cal = oper.getRecord2(calendarNo);
		
		//��ȡcal�����е����ݶΣ����ڶ�Ӧ�ĳ�Ա������
//		calendarNo = cal.getCalendarNo();
		calendarName = cal.getCalendarName();
		date = cal.getDate();
		time = cal.getTime();
		place = cal.getPlace();
		description = cal.getDescription();
		repetition = cal.getRepetition();
		advanceTime = cal.getAdvanceTime();
		
		//��ÿؼ�����
		textName = (EditText)findViewById(R.id.editText1_edit_calendar);
		buttonDate = (Button)findViewById(R.id.button1_edit_calendar);
		buttonTime = (Button)findViewById(R.id.button2_edit_calendar);
		textPlace = (EditText)findViewById(R.id.editText2_edit_calendar);
		textDescription = (EditText)findViewById(R.id.editText3_edit_calendar);
		spinnerRepetition = (Spinner)findViewById(R.id.spinner1_edit_calendar);
		spinnerAdvanceTime = (Spinner)findViewById(R.id.spinner2_edit_calendar);
		save = (Button)findViewById(R.id.save_edit_calendar);
		cancle =(Button)findViewById(R.id.cancle_edit_calendar);
		
		//���ı���װ������
		textName.setText(calendarName);
		textPlace.setText(place);
		textDescription.setText(description);
		
		//��ʼ��repetition���ݶζ�Ӧ��spinner
		initSpinnerRepetition();
		//��ʼ��advanctTime���ݶζ�Ӧ��spinner
		initSpinnerPromition();
		
		//��ʼ�����ں�ʱ��
		c = Calendar.getInstance();
		initDate();
		initTime();
		
		//Ϊ�����桱��ť�󶨼�����
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update();
				/**ͨ��Calendar����cal����������ڡ�ʱ��ľ������ݸ�ֵ��
				 * year,month,day,hour,minute
				 * ���������ԭ����ֵ�Ǹ��ճ̼�¼֮ǰ�����ڡ�ʱ��*/
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				hour = c.get(Calendar.HOUR_OF_DAY);
				minute = c.get(Calendar.MINUTE);
				
				//��������
				AlarmManage manage = new AlarmManage(CalendarEditActivity.this);
				manage.setAlarm(Integer.parseInt(calendarNo), repetition, advanceTime, year, month, day, hour, minute);
//				Intent intent = new Intent();
//				intent.setClass(CalendarAddActivity.this, CalendarMainActivity.class);
//				startActivity(intent);
				CalendarEditActivity.this.finish();
			}
		});
		
		cancle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CalendarEditActivity.this.finish();
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
//				Toast.makeText(CalendarEditActivity.this, "δѡ��", Toast.LENGTH_LONG).show();
			}
		}); 
	}
	
	public void initDate() {
//		//��õ�ǰ���ڣ�����date�ֶβ���ʾ�����ڰ�ť��
//		year = c.get(Calendar.YEAR);
//		month = c.get(Calendar.MONTH) + 1;
//		day = c.get(Calendar.DAY_OF_MONTH);
//		
//		if(month < 10)
//			date = "" + year + "-0" + month;
//		else
//			date = "" + year + "-" + month;
//		if (day < 10)
//			date += "-" + "0" + day;
//		else
//			date += "-" + day;
//		buttonDate.setText("" + year + "��" + month + "��" + day + "��");		
////        date = "" + c.get(Calendar.YEAR) + "-" + (int)(c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
////		Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
////        buttonDate.setText("" + c.get(Calendar.YEAR) + "��" + (int)(c.get(Calendar.MONTH) + 1) + "��" + c.get(Calendar.DAY_OF_MONTH) + "��");
        
		
		//�Ӹ�ʽ������������ȡ���ꡢ�¡���
		CheckValid check = new CheckValid();
		year = check.getYearFromDate(date);
		month = check.getMonthFromDate(date);
		day = check.getDayFromDate(date);
		
		//����ȡ��������ʾ��buttonDate��
		buttonDate.setText(year + "��" + month + "��" + day + "��");
		
		buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CalendarEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        //���ꡢ�¡��հ���'yyyy-mm-dd'��ʽ��װ��date
                        if(monthOfYear < 10)
                			date = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			date = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			date += "-" + "0" + dayOfMonth;
                		else
                			date += "-" + dayOfMonth;
                		//��buttonDate����ʾ�ո�ѡ�������
                        buttonDate.setText("" + year + "��" + (int)(monthOfYear + 1) + "��" + dayOfMonth + "��");
                    }
                }, year, month, day);
                dialog.show();
            }
        });
	}
	
	public void initTime() {
//		//��õ�ǰʱ�䣬����time�ֶ�
//		hour = c.get(Calendar.HOUR_OF_DAY);
//		minute = c.get(Calendar.MINUTE);
//		second = c.get(Calendar.SECOND);
//		
//		//��ʽ��ʱ��
//		if(hour < 10)
//			time = "0" + hour + ":";
//		else
//			time = "" + hour + ":";
//		if(minute < 10)
//			time += "0"+ minute + ":";
//		else
//			time += minute + ":";
//		if(second < 10)
//			time += "0" + second;
//		else
//			time += second;
//		
//		//�ڰ�ť����ʾʱ��
//        buttonTime.setText("" + hour + ":" + minute);
		//�Ӹ�ʽ����ʱ������ȡ��ʱ���֡���
		CheckValid check = new CheckValid();
		hour = check.getHourFromTime(time);
		minute = check.getMinuteFromTime(time);
//		second = check.getSecondFromTime(time);
				
		//����ȡ��ʱ����ʾ��buttonTime��
		buttonTime.setText(hour + ":" + minute);
		
        //Ϊ��ť�󶨼�����
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(CalendarEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
                    	c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	c.set(Calendar.MINUTE, minute);
                    	//����ȡ��ʱ���ְ���'hh:mm:00'�ĸ�ʽ��װ��time
                    	if(hourOfDay < 10)
                    		time = "0" + hourOfDay + ":";
                		else
                			time = "" + hourOfDay + ":";
                		if(minute < 10)
                			time += "0"+ minute + ":";
                		else
                			time += minute + ":";
                		time += "00";
                		//��buttonTime����ʾ�ո�ѡ���ʱ��
                    	buttonTime.setText("" + hourOfDay + ":" + minute);
        			}
                }, hour, minute,true);
                dialog.show();
            }
        });
	}
	
	public void update() {
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
		oper.updateDate(calendarNo, calendarName, date, time, place, description, repetition, advanceTime);
		//�رձ��������������
		oper.close();
	}
}
