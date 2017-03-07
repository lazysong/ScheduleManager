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
		
		//从intent中获取calendarNo参数，即待编辑的日程记录的编号
		calendarNo = getIntent().getExtras().getString("calendarNo");
//		Toast.makeText(CalendarEditActivity.this, calendarNo, Toast.LENGTH_SHORT).show();
		
		/**根据calendarNo参数检索数据库
		 * 获得该条日程记录
		 * 返回的记录存在MyCalendar对象，即cal中
		 * */
		CalendarDataOperation oper = new CalendarDataOperation(CalendarEditActivity.this);
		cal = oper.getRecord2(calendarNo);
		
		//提取cal对象中的数据段，存在对应的成员变量中
//		calendarNo = cal.getCalendarNo();
		calendarName = cal.getCalendarName();
		date = cal.getDate();
		time = cal.getTime();
		place = cal.getPlace();
		description = cal.getDescription();
		repetition = cal.getRepetition();
		advanceTime = cal.getAdvanceTime();
		
		//获得控件对象
		textName = (EditText)findViewById(R.id.editText1_edit_calendar);
		buttonDate = (Button)findViewById(R.id.button1_edit_calendar);
		buttonTime = (Button)findViewById(R.id.button2_edit_calendar);
		textPlace = (EditText)findViewById(R.id.editText2_edit_calendar);
		textDescription = (EditText)findViewById(R.id.editText3_edit_calendar);
		spinnerRepetition = (Spinner)findViewById(R.id.spinner1_edit_calendar);
		spinnerAdvanceTime = (Spinner)findViewById(R.id.spinner2_edit_calendar);
		save = (Button)findViewById(R.id.save_edit_calendar);
		cancle =(Button)findViewById(R.id.cancle_edit_calendar);
		
		//向文本域装载数据
		textName.setText(calendarName);
		textPlace.setText(place);
		textDescription.setText(description);
		
		//初始化repetition数据段对应的spinner
		initSpinnerRepetition();
		//初始化advanctTime数据段对应的spinner
		initSpinnerPromition();
		
		//初始化日期和时间
		c = Calendar.getInstance();
		initDate();
		initTime();
		
		//为“保存”按钮绑定监听器
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				update();
				/**通过Calendar对象cal将输入的日期、时间的具体数据赋值给
				 * year,month,day,hour,minute
				 * 这五个变量原来的值是该日程记录之前的日期、时间*/
				year = c.get(Calendar.YEAR);
				month = c.get(Calendar.MONTH);
				day = c.get(Calendar.DAY_OF_MONTH);
				hour = c.get(Calendar.HOUR_OF_DAY);
				minute = c.get(Calendar.MINUTE);
				
				//设置闹钟
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
		//设置Spinner每个条目的显示样式 
		//3.声明一个ArrayAdapter并获取对象，用于配置Spinner显示的信息。 
		//对应参数说明：1.上下文变量；2.要显示的字符串Array；3.Spinner的显示样式 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//为spinner绑定适配器，书写标题信息，绑定监听事件。 
		spinnerRepetition.setAdapter(adapter); 
		spinnerRepetition.setPrompt("重复次数"); 
		
		spinnerRepetition.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
				//获取所选择的重复次数
				repetition = arg2 + 1 + "";
//				Toast.makeText(CalendarAddActivity.this, "选择了" + (int)(arg2+1), Toast.LENGTH_LONG).show();
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
//				Toast.makeText(CalendarAddActivity.this, "未选中", Toast.LENGTH_LONG).show();
			}
		}); 
	}
	
	public void initSpinnerPromition() {
		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, 
				R.array.spinner_promote,
				android.R.layout.simple_spinner_item); 
		//设置Spinner每个条目的显示样式 
		//3.声明一个ArrayAdapter并获取对象，用于配置Spinner显示的信息。 
		//对应参数说明：1.上下文变量；2.要显示的字符串Array；3.Spinner的显示样式 
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		//为spinner绑定适配器，书写标题信息，绑定监听事件。 
		spinnerAdvanceTime.setAdapter(adapter); 
		spinnerAdvanceTime.setPrompt("提前时间"); 
		
		spinnerAdvanceTime.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
				//获取所选择的重复次数
				advanceTime = arg2 + 1 + "";
//				Toast.makeText(CalendarAddActivity.this, "选择了" + (int)(arg2+1), Toast.LENGTH_LONG).show();
			}
	
			public void onNothingSelected(AdapterView<?> arg0) {
//				Toast.makeText(CalendarEditActivity.this, "未选中", Toast.LENGTH_LONG).show();
			}
		}); 
	}
	
	public void initDate() {
//		//获得当前日期，存入date字段并显示在日期按钮上
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
//		buttonDate.setText("" + year + "年" + month + "月" + day + "日");		
////        date = "" + c.get(Calendar.YEAR) + "-" + (int)(c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
////		Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
////        buttonDate.setText("" + c.get(Calendar.YEAR) + "年" + (int)(c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日");
        
		
		//从格式化的日期中提取出年、月、日
		CheckValid check = new CheckValid();
		year = check.getYearFromDate(date);
		month = check.getMonthFromDate(date);
		day = check.getDayFromDate(date);
		
		//将获取的日期显示在buttonDate上
		buttonDate.setText(year + "年" + month + "月" + day + "日");
		
		buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(CalendarEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        //将年、月、日按照'yyyy-mm-dd'格式组装成date
                        if(monthOfYear < 10)
                			date = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			date = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			date += "-" + "0" + dayOfMonth;
                		else
                			date += "-" + dayOfMonth;
                		//在buttonDate上显示刚刚选择的日期
                        buttonDate.setText("" + year + "年" + (int)(monthOfYear + 1) + "月" + dayOfMonth + "日");
                    }
                }, year, month, day);
                dialog.show();
            }
        });
	}
	
	public void initTime() {
//		//获得当前时间，存入time字段
//		hour = c.get(Calendar.HOUR_OF_DAY);
//		minute = c.get(Calendar.MINUTE);
//		second = c.get(Calendar.SECOND);
//		
//		//格式化时间
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
//		//在按钮上显示时间
//        buttonTime.setText("" + hour + ":" + minute);
		//从格式化的时间中提取出时、分、秒
		CheckValid check = new CheckValid();
		hour = check.getHourFromTime(time);
		minute = check.getMinuteFromTime(time);
//		second = check.getSecondFromTime(time);
				
		//将获取的时间显示在buttonTime上
		buttonTime.setText(hour + ":" + minute);
		
        //为按钮绑定监听器
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(CalendarEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
                    	c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	c.set(Calendar.MINUTE, minute);
                    	//将获取的时、分按照'hh:mm:00'的格式组装成time
                    	if(hourOfDay < 10)
                    		time = "0" + hourOfDay + ":";
                		else
                			time = "" + hourOfDay + ":";
                		if(minute < 10)
                			time += "0"+ minute + ":";
                		else
                			time += minute + ":";
                		time += "00";
                		//在buttonTime上显示刚刚选择的时间
                    	buttonTime.setText("" + hourOfDay + ":" + minute);
        			}
                }, hour, minute,true);
                dialog.show();
            }
        });
	}
	
	public void update() {
		//将组件的信息封装为字段
				calendarName = textName.getText().toString();
//				提取日期信息 
//				date = "2014-10-10";
//				提取时间信息
//				time = "20:23:45";
				place = textPlace.getText().toString();
				description = textDescription.getText().toString();
//				提取下拉列表spinner的数据
//				repetition = "1";
//				提取下拉列表spinner的数据
//				advanceTime = "1";
		oper = new CalendarDataOperation(this);
		oper.updateDate(calendarNo, calendarName, date, time, place, description, repetition, advanceTime);
		//关闭本界面的数据连接
		oper.close();
	}
}
