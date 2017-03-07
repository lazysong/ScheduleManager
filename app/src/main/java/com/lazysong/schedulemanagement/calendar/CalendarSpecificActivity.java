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
		 * ���и������ݵĲ���
		 * */
		calendarNo = getIntent().getExtras().getString("calendarNo");
//		Toast.makeText(CalendarSpecificActivity.this, "�ճ̵ı����" + calendarNo, Toast.LENGTH_LONG).show();
		
		update();
		
		//ΪeditButton��ӵ����¼���������������ת���༭����
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
		
		//ΪdeleteButton��ӵ����¼�������������ִ��ɾ������
		deleteButton =(Button)findViewById(R.id.delete_check_calendar);
		deleteButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ִ��ɾ������
				CalendarDataOperation oper = new CalendarDataOperation(CalendarSpecificActivity.this);
				oper.delete(calendarNo);
				AlarmManage manage = new AlarmManage(CalendarSpecificActivity.this);
	    		manage.cancleAlarm(Integer.parseInt(calendarNo));
				oper.close();
				//�رձ�����
				CalendarSpecificActivity.this.finish();
				
				//������Ϣ
				Toast.makeText(CalendarSpecificActivity.this, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
			}
		});
		
		//ΪbackButton���ü�����
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
			calendarName = "δ�����ճ�";
		date = cal.getDate();
		time = cal.getTime();
		place = cal.getPlace();
		description = cal.getDescription();
		repetition = cal.getRepetition();
		advanceTime = cal.getAdvanceTime();
		
		//��ÿؼ�����
		textName = (TextView)findViewById(R.id.textView1_check_calendar);
		textDate = (TextView)findViewById(R.id.textView2_check_calendar);
		textTime = (TextView)findViewById(R.id.textView3_check_calendar);
		textPlace = (TextView)findViewById(R.id.textView4_check_calendar);
		textDescription = (TextView)findViewById(R.id.textView5_check_calendar);
		textRepetition = (TextView)findViewById(R.id.textView6_check_calendar);
		textAdvanceTime = (TextView)findViewById(R.id.textView7_check_calendar);
		
		
		//���ı���װ������
		textName.setText("" + calendarName);
		textDate.setText("ʱ�䣺" + date);
		textTime.setText("" + time);
		textPlace.setText("�ص㣺" + place);
		textDescription.setText(description);
		switch (repetition) {
		case "1":
			textRepetition.setText("������һ��");
			break;
		case "2":
			textRepetition.setText("ÿ������");
			break;
		case "3":
			textRepetition.setText("ÿ������");
			break;
		case "4":
			textRepetition.setText("ÿ������");
			break;
		case "5":
			textRepetition.setText("ÿ������");
			break;
		default:
			textRepetition.setText("");
			break;
		}
		switch (advanceTime) {
		case "1":
			textAdvanceTime.setText("׼ʱ����");
			break;
		case "2":
			textAdvanceTime.setText("��ǰ5����");
			break;
		case "3":
			textAdvanceTime.setText("��ǰ10����");
			break;
		case "4":
			textAdvanceTime.setText("��ǰ30����");
			break;
		case "5":
			textAdvanceTime.setText("��ǰ1Сʱ");
			break;
		case "6":
			textAdvanceTime.setText("��ǰ2Сʱ");
			break;
		case "7":
			textAdvanceTime.setText("��ǰ3Сʱ");
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
