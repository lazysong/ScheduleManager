package com.lazysong.schedulemanagement.db;

import java.util.ArrayList;
import java.util.List;

import com.lazysong.schedulemanagement.help.CheckValid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class CalendarDataOperation {
	private SQLiteOpenHelper helper;
	private SQLiteDatabase database;
	private Context mContext;
	private Cursor cursor;
	public static int recordCount = 1;
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	
	public CalendarDataOperation(Context context) {
		mContext = context;
		helper = new MyDatabaseHelper(mContext, "db1", null, 1);
		database = helper.getWritableDatabase();
//		Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show(); 
	}
	
	//��Ӽ�¼
	public  void addAffair(String sql,String[] args) {
		database.execSQL(sql, args);
	}
	
	//��������ǰ���ڵı�š����ƺ�����
	public List<MyCalendar> getAllRecord() {
		//��ѯ������ļ�¼
//		Cursor cursor = database.rawQuery("select * from cal1 where calendarDate = ?", new String[]{currentDate});
		//��ѯ�����еļ�¼
		cursor = database.rawQuery("select * from cal1 order by calendarDate desc;",null);
		MyCalendar cal = new MyCalendar();
		List<MyCalendar> records = new ArrayList<MyCalendar>();
		while (cursor.moveToNext()) {
			cal = new MyCalendar();
			cal.setCalendarNo(cursor.getString(0));//��ȡ��һ�е�ֵ,��һ�е�������0��ʼ
			cal.setCalendarName(cursor.getString(1));//��ȡ�ڶ��е�ֵ
			cal.setDate(cursor.getString(2));//��ȡ�����е�ֵ
			cal.setTime(cursor.getString(3));
			cal.setPlace(cursor.getString(4));
			cal.setDescription(cursor.getString(5));
			cal.setRepetition(cursor.getString(6));
			cal.setAdvanceTime(cursor.getString(7));
			cal.setValid(cursor.getString(8));
			records.add(cal);
		}
//		Toast.makeText(mContext, cal.getCalendarName(), Toast.LENGTH_SHORT).show();
		return records;
	}
	
	//��������ǰ���ڵı�š����ƺ�����
	public List<MyCalendar> getTodayRecord() {
		CheckValid check = new CheckValid();
		String currentDate = check.getCurrentDate();
		//��ѯ������ļ�¼
		cursor = database.rawQuery("select * from cal1 where calendarDate = ?", new String[]{currentDate});
		//��ѯ�����еļ�¼
//		cursor = database.rawQuery("select * from cal1 order by calendarDate desc;",null);
		MyCalendar cal = new MyCalendar();
		List<MyCalendar> records = new ArrayList<MyCalendar>();
		while (cursor.moveToNext()) {
			cal = new MyCalendar();
			cal.setCalendarNo(cursor.getString(0));//��ȡ��һ�е�ֵ,��һ�е�������0��ʼ
			cal.setCalendarName(cursor.getString(1));//��ȡ�ڶ��е�ֵ
			cal.setDate(cursor.getString(2));//��ȡ�����е�ֵ
			cal.setTime(cursor.getString(3));
			cal.setPlace(cursor.getString(4));
			cal.setDescription(cursor.getString(5));
			cal.setRepetition(cursor.getString(6));
			cal.setAdvanceTime(cursor.getString(7));
			cal.setValid(cursor.getString(8));
			records.add(cal);
		}
//		Toast.makeText(mContext, cal.getCalendarName(), Toast.LENGTH_SHORT).show();
		return records;
	}
	
	public void delete(String calendarNo) {
		database.execSQL("delete from cal1 where calendarNo = ?",new String[]{calendarNo});
	}
	
	public MyCalendar getRecord2(String calendarNo) {
		cursor = database.rawQuery("select * from cal1 where calendarNo = ?",new String[]{calendarNo});
		MyCalendar cal = new MyCalendar();
		while (cursor.moveToNext()) {
			cal.setCalendarNo(cursor.getString(0));//��ȡ��һ�е�ֵ,��һ�е�������0��ʼ
			cal.setCalendarName(cursor.getString(1));//��ȡ�ڶ��е�ֵ
			cal.setDate(cursor.getString(2));//��ȡ�����е�ֵ
			cal.setTime(cursor.getString(3));
			cal.setPlace(cursor.getString(4));
			cal.setDescription(cursor.getString(5));
			cal.setRepetition(cursor.getString(6));
			cal.setAdvanceTime(cursor.getString(7));
			cal.setValid(cursor.getString(8));
		}
		return cal;
	}
	
	public void updateDate(String calendarNo, String calendarName, String date, String time, String place, String description, String repetition, String advanceTime) {
		database.execSQL("update cal1 set calendarName = ?, calendarDate = ?, calendarTime = ?, place = ?, calDescription = ?, repetition = ?, advanceTime = ? where calendarNo = ?",new String[]{calendarName, date, time, place, description, repetition, advanceTime,calendarNo});
		close();
	}
	
	public void deleteInvalid() {
		CheckValid check = new CheckValid();
		//��ѯ�����еļ�¼
		cursor = database.rawQuery("select * from cal1 order by calendarDate desc;",null);
		while (cursor.moveToNext()) {
			//ȡ��������¼�ı�š����ڡ�ʱ��
			String calendarNo = cursor.getString(0);
			String date = cursor.getString(2);
			String time = cursor.getString(3);
			//�жϸ�����¼�Ƿ���ڣ�������ڣ�ɾ���ü�¼
			if(!check.checkCalendarValid(date, time)) {
				database.execSQL("delete from cal1 " +
						"where calendarNo = ?", 
						new String[]{calendarNo});//ɾ����¼
//				Toast.makeText(mContext, date + time + "�жϹ��ڣ���ɾ��", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	public void close() {
		if(helper != null)
			helper.close();
		if(cursor != null)
			cursor.close();
		if(database != null)
			database.close();
	}
}
