package com.lazysong.schedulemanagement.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
	private Context context;
	private final String CREATE_PLAN = "create table myPlan(" +
			"planNo integer primary key autoincrement, " +
			"planName text, " +
			"planDescription text, " +
			"difficulty float, " +
			"planPriority float, " +
			"startDate date, " +
			"startTime time, " +
			"endDate date, " +
			"endTime time, " +
			"completence float, " +
			"state integer, " + 
			"ability float );";
	private final String CREATE_TASK = "create table myTask(" +
			"taskNo integer primary key autoincrement, " +
			"planNo integer, " +
			"taskName text, " +
			"taskDescription text, " +
			"difficulty float, " +
			"taskPriority float, " +
			"startDate date, " +
			"startTime time, " +
			"endDate date, " +
			"endTime time, " +
			"completence float, " +
			"state integer, " + 
			"ability float, " +
			"constraint foreignKey foreign key (planNo) references myPlan(planNo));";
	
	public DatabaseHelper(
			Context 		context, 	String name,
			CursorFactory 	factory, 	int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try {
		 db.execSQL(CREATE_PLAN); 
		 db.execSQL(CREATE_TASK);
//		 Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show(); 
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
