package com.lazysong.schedulemanagement.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	public static final String CREATE_CALENDAR = "create table cal1(" +
			"calendarNo integer primary key autoincrement," +
			"calendarName text," +
			"calendarDate date," +
			"calendarTime time," +
			"place text," +
			"calDescription text," +
			"repetition integer," +
			"advanceTime integer," +
			"valid integer " +
			"constraint c1 check ( valid in (1,0) ));"; 
//	public static final String CREATE_CALENDAR = "create table cal1(calendarNo integer primary key,calendarName text,repetition integer,advanceTime integer);"; 
			 
	private Context mContext; 
	 
	public MyDatabaseHelper(Context context, String name, CursorFactory 
	factory, int version) { 
		super(context, name, factory, version); 
		mContext = context; 
	} 
	 
	 @Override 
	 public void onCreate(SQLiteDatabase db) { 
		 try {
		 db.execSQL(CREATE_CALENDAR); 
//		 Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show(); 
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	 } 
	 
	 @Override 
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { 
	 } 
}
