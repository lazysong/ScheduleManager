package com.lazysong.schedulemanagement.calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.alarm.AlarmManage;
import com.lazysong.schedulemanagement.db.CalendarDataOperation;
import com.lazysong.schedulemanagement.db.MyCalendar;
import com.lazysong.schedulemanagement.plan.CheckPlanActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarSpecificActivity extends Activity {
	private Button editButton;
	private Button deleteButton;
	private Button backButton;
	private TextView textName;
	private TextView textDate;
	private TextView textTime;
	private TextView textPlace;
	private TextView textDescription;
	private TextView textRepetition;
	private TextView textAdvanceTime;
	
	private String calendarNo;
	private String calendarName;
	private String date;
	private String time;
	private String place;
	private String description;
	private String repetition;
	private String advanceTime;
	
	private MyCalendar cal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar_specific);
		
		/**
		 * 进行更新数据的操作
		 * */
		calendarNo = getIntent().getExtras().getString("calendarNo");
//		Toast.makeText(CalendarSpecificActivity.this, "日程的编号是" + calendarNo, Toast.LENGTH_LONG).show();
		
		update();
		
		//为editButton添加单击事件监听器，单击跳转到编辑界面
		editButton = (Button)findViewById(R.id.edit_check_calendar);
		editButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(CalendarSpecificActivity.this, CalendarEditActivity.class);
				intent.putExtra("calendarNo", calendarNo);
				startActivity(intent);
			}
		});
		
		//为deleteButton添加单击事件监听器，单击执行删除操作
		deleteButton =(Button)findViewById(R.id.delete_check_calendar);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//执行删除操作
				CalendarDataOperation oper = new CalendarDataOperation(CalendarSpecificActivity.this);
				oper.delete(calendarNo);
				AlarmManage manage = new AlarmManage(CalendarSpecificActivity.this);
	    		manage.cancleAlarm(Integer.parseInt(calendarNo));
				oper.close();
				//关闭本界面
				CalendarSpecificActivity.this.finish();
				
				//反馈信息
				Toast.makeText(CalendarSpecificActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
			}
		});
		
		//为backButton设置监听器
		backButton = (Button)findViewById(R.id.back_check_calendar);
		backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CalendarSpecificActivity.this.finish();
			}
		});
		
	}
	
	public void update() {
		CalendarDataOperation oper = new CalendarDataOperation(CalendarSpecificActivity.this);
		cal = oper.getRecord2(calendarNo);
		String tag = "onResume";
		Log.v(tag, "onResume is called");
		calendarName = cal.getCalendarName();
		if(calendarName.length() == 0)
			calendarName = "未命名日程";
		date = cal.getDate();
		time = cal.getTime();
		place = cal.getPlace();
		description = cal.getDescription();
		repetition = cal.getRepetition();
		advanceTime = cal.getAdvanceTime();
		
		//获得控件对象
		textName = (TextView)findViewById(R.id.textView1_check_calendar);
		textDate = (TextView)findViewById(R.id.textView2_check_calendar);
		textTime = (TextView)findViewById(R.id.textView3_check_calendar);
		textPlace = (TextView)findViewById(R.id.textView4_check_calendar);
		textDescription = (TextView)findViewById(R.id.textView5_check_calendar);
		textRepetition = (TextView)findViewById(R.id.textView6_check_calendar);
		textAdvanceTime = (TextView)findViewById(R.id.textView7_check_calendar);
		
		
		//向文本域装载数据
		textName.setText("" + calendarName);
		textDate.setText("时间：" + date);
		textTime.setText("" + time);
		textPlace.setText("地点：" + place);
		textDescription.setText(description);
		switch (repetition) {
		case "1":
			textRepetition.setText("仅提醒一次");
			break;
		case "2":
			textRepetition.setText("每天提醒");
			break;
		case "3":
			textRepetition.setText("每周提醒");
			break;
		case "4":
			textRepetition.setText("每月提醒");
			break;
		case "5":
			textRepetition.setText("每年提醒");
			break;
		default:
			textRepetition.setText("");
			break;
		}
		switch (advanceTime) {
		case "1":
			textAdvanceTime.setText("准时提醒");
			break;
		case "2":
			textAdvanceTime.setText("提前5分钟");
			break;
		case "3":
			textAdvanceTime.setText("提前10分钟");
			break;
		case "4":
			textAdvanceTime.setText("提前30分钟");
			break;
		case "5":
			textAdvanceTime.setText("提前1小时");
			break;
		case "6":
			textAdvanceTime.setText("提前2小时");
			break;
		case "7":
			textAdvanceTime.setText("提前3小时");
			break;
		default:
			textAdvanceTime.setText("");
			break;
		}	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		update();
		super.onResume();
	}
}
