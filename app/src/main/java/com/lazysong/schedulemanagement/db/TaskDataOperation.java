package com.lazysong.schedulemanagement.db;

import java.util.ArrayList;
import java.util.List;

import com.lazysong.schedulemanagement.help.CheckValid;
import com.lazysong.schedulemanagement.help.Evaluate;
import com.lazysong.schedulemanagement.help.MyTask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 这个类用来对任务表的操作
 * @author songhui
 *
 */
public class TaskDataOperation {
	private DatabaseHelper helper;//多态的使用，SQliteOpenHelper
	private SQLiteDatabase database;
	private Cursor cursor;
	private Context context;
	
	public TaskDataOperation(Context context) {
		this.context = context;
		helper = new DatabaseHelper(context, "db2", null, 1);
		database = helper.getWritableDatabase();
	}
	
	
	/**
	 * 向任务表myTask中插入一条记录，完成度(completence)和执行能力(ability)字段的值设为0
	 * @param planNo 任务所属的计划的编号，如果编号为-1则代表该任务为独立任务
	 * @param taskName 任务的名称
	 * @param taskDescription 任务描述
	 * @param difficulty 任务难度
	 * @param taskPriority 任务优先级
	 * @param startDate 开始日期
	 * @param startTime 开始时间
	 * @param endDate 结束日期
	 * @param endTime 结束时间
	 * @throws Exception 可能抛出的异常
	 */
	public void insertTask(
			int planNo,
			String taskName, 	String taskDescription, 
			float difficulty,	float taskPriority,
			String startDate, 	String startTime, 
			String endDate, 	String endTime
			) throws Exception{
		database.execSQL("insert into myTask (" +
				"planNo, " + 
				"taskName , 	taskDescription, 	difficulty , " +
				"taskPriority , startDate , 		startTime , " +
				"endDate , 		endTime , 			completence, state, ability  ) " +
				"values(?,?,?,?,?,?,?,?,?,0,0,0);", 
				new String[] {	planNo + "", 
								taskName, taskDescription,
								difficulty + "", taskPriority + "",
								startDate, startTime, 
								endDate,  endTime });
	}
	
	/**
	 * 这个函数可以根据传入的taskNo来检索对应的某条记录
	 * 并将记录的各个字段封装到一个MyTask类的对象中
	 * 返回这个对象
	 * @param taskNo 根据这个值来检索myTask表中的taskNo字段
	 * @return 返回一个MyTask对象
	 */
	public MyTask getTaskRecord(int taskNo) {
		cursor = database.rawQuery("select * from myTask where taskNo = ?", new String[] {"" + taskNo});
		MyTask task = new MyTask();
		while(cursor.moveToNext()) {
			task.setTaskNo(cursor.getInt(0));
			task.setPlanNo(cursor.getInt(1));
			task.setTaskName(cursor.getString(2));
			task.setTaskDescription(cursor.getString(3));
			task.setDifficulty(cursor.getFloat(4));
			task.setTaskPriority(cursor.getFloat(5));
			task.setStartDate(cursor.getString(6));
			task.setStartTime(cursor.getString(7));
			task.setEndDate(cursor.getString(8));
			task.setEndTime(cursor.getString(9));
			task.setCompletence(cursor.getFloat(10));
			task.setState(cursor.getInt(11));
			task.setAbility(cursor.getFloat(12));
		}
		return task;
	}
	/**
	 * 得到一个计划所对应的所有任务
	 * */
	public List<MyTask> getTasksOfPlan(int planNo) {
		//检索给出的计划所对应的所有任务,独立任务的标志就是其planNo字段的值为-1
		cursor = database.rawQuery("select * from myTask where planNo = ? order by startDate desc;",new String[]{planNo + ""});
		List<MyTask> allTasks = new ArrayList<MyTask>();
		MyTask task = new MyTask();
		while(cursor.moveToNext()) {
			task = new MyTask();
			
			task.setTaskNo(cursor.getInt(0));
			task.setPlanNo(cursor.getInt(1));
			task.setTaskName(cursor.getString(2));
			task.setTaskDescription(cursor.getString(3));
			task.setDifficulty(cursor.getFloat(4));
			task.setTaskPriority(cursor.getFloat(5));
			task.setStartDate(cursor.getString(6));
			task.setStartTime(cursor.getString(7));
			task.setEndDate(cursor.getString(8));
			task.setEndTime(cursor.getString(9));
			task.setCompletence(cursor.getFloat(10));
			task.setState(cursor.getInt(11));
			task.setAbility(cursor.getFloat(12));
			
			allTasks.add(task);
		}
		return allTasks;
	}
	/**
	 * 该函数用于检索出近期所有的独立任务，
	 * 如果今天位于某条独立任务的开始日期、时间和结束日期、时间的中间，
	 * 那么该条任务即为近期独立任务
	 * @return 返回近期的所有独立任务
	 */
	public List<MyTask> getRecentSingleTasks() {
		//检索出任务表的所有独立任务记录，独立任务的标志就是其planNo字段的值为-1
		cursor = database.rawQuery("select * from myTask where planNo = -1 order by startDate desc;",null);
		//开始逐条记录判断是否是近期的独立任务
		List<MyTask> allTasks = new ArrayList<MyTask>();
		MyTask task = new MyTask();
		CheckValid check = new CheckValid();
		while(cursor.moveToNext()) {
			//检查该独立任务是否已经开始，如果该独立任务还未开始，或者已经结束，则跳过该条独立任务
			if(check.checkCalendarValid(cursor.getString(6), cursor.getString(7))
				|| !check.checkCalendarValid(cursor.getString(8), cursor.getString(9)))
				continue;
			
			task = new MyTask();
			task.setTaskNo(cursor.getInt(0));
			task.setPlanNo(cursor.getInt(1));
			task.setTaskName(cursor.getString(2));
			task.setTaskDescription(cursor.getString(3));
			task.setDifficulty(cursor.getFloat(4));
			task.setTaskPriority(cursor.getFloat(5));
			task.setStartDate(cursor.getString(6));
			task.setStartTime(cursor.getString(7));
			task.setEndDate(cursor.getString(8));
			task.setEndTime(cursor.getString(9));
			task.setCompletence(cursor.getFloat(10));
			task.setState(cursor.getInt(11));
			task.setAbility(cursor.getFloat(12));
			
			allTasks.add(task);
		}
		return allTasks;
	}
	
	/**
	 * 该函数用来得到一个MyTask对象的列表，里面装载着任务表中的所有独立任务
	 * @return 返回任务表的所有独立任务记录
	 */
	public List<MyTask> getAllSingleTasks() {
		//检索出任务表的所有记录
		cursor = database.rawQuery("select * from myTask where planNo = -1 order by startDate desc;",null);
		
		//将所有记录装载到MyTask对象的列表allTasks中
		List<MyTask> allTasks = new ArrayList<MyTask>();
		MyTask task = new MyTask();
		while(cursor.moveToNext()) {
			task = new MyTask();
			
			task.setTaskNo(cursor.getInt(0));
			task.setPlanNo(cursor.getInt(1));
			task.setTaskName(cursor.getString(2));
			task.setTaskDescription(cursor.getString(3));
			task.setDifficulty(cursor.getFloat(4));
			task.setTaskPriority(cursor.getFloat(5));
			task.setStartDate(cursor.getString(6));
			task.setStartTime(cursor.getString(7));
			task.setEndDate(cursor.getString(8));
			task.setEndTime(cursor.getString(9));
			task.setCompletence(cursor.getFloat(10));
			task.setState(cursor.getInt(11));
			task.setAbility(cursor.getFloat(12));
			
			allTasks.add(task);
		}
		return allTasks;//返回列表
	}
	
	/**
	 * 这个函数用来更新myTask表的数据，
	 * 通常在编辑某个任务或者独立任务的时候使用这个函数
	 * 在更新时要注意的是：
	 * 如果日期或时间数据被改变了，需要将completence和ability字段置0
	 * 否则就不需要置0
	 * @param taskNo 用于定位myTask表的具体某条记录
	 * @param taskName 新的任务或者独立任务名称
	 * @param taskDescription 新的任务或者独立任务描述
	 * @param difficulty 新的任务或者独立任务难度
	 * @param taskPriority 新的任务或者独立任务优先级
	 * @param startDate 新的开始日期
	 * @param startTime 新的开始时间
	 * @param endDate 新的结束日期
	 * @param endTime 新的结束时间
	 */
	public void updateTaskRecord(
			int    taskNo,
			String taskName, 	String taskDescription, 
			float  difficulty,	float  taskPriority,
			String startDate, 	String startTime, 
			String endDate, 	String endTime) {
		/*判断一下日期、时间有没有改变
		 * 如果日期或时间改变的话，要将完成度completence和ability字段设为0
		 * 如果日期、时间均没有改变的话，则不需要重置completence和ability字段
		 * */
		MyTask task = getTaskRecord(taskNo);
		//如果日期、时间均没有改变的话，则不需要重置completence和ability字段
		if(	startDate.equals(task.getStartDate()) && 	startTime.equals(task.getStartTime())
			 && endDate.equals(task.getEndDate()) && 	endTime.equals(task.getEndTime()))
			database.execSQL(
					"update myTask " +
					"set taskName = ?, " +
					"taskDescription = ?, " +
					"difficulty = ?, " + 
					"taskPriority = ? " + 
					"where taskNo = ?;",new String[]{
							taskName, taskDescription,
							difficulty + "", taskPriority + "", 
							taskNo + ""});
		//如果日期或时间改变的话，要将完成度completence和ability字段设为0
		else
			database.execSQL("update myTask set taskName = ?, taskDescription = ?, " +
					"difficulty = ?, taskPriority = ?, " + 
					"startDate = ?, startTime = ?, " +
					"endDate = ?, endTime = ?, " +
					"completence = 0, " +
					"ability = 0 " + 
					"where taskNo = ?;", new String[]{
							taskName, taskDescription, 
							difficulty + "", taskPriority + "", 
							startDate, startTime, 
							endDate, endTime, 
							taskNo + ""});
	}
	
	/**
	 * 这个函数用来设置任务的完成度
	 * 通常在任务结束之后进行任务评估的时候调用这个函数
	 * @param taskNo 任务编号，用于定位需要评估的任务
	 * @param completence 任务的完成度
	 */
	public void setCompletence(int taskNo, float completence) {
		database.execSQL("update myTask " +
				"set completence = ? " +
				"where taskNo = ?;", new String[]{
				completence + "", taskNo + ""});
	}
	
	/**
	 * 这个函数用来设置任务的执行能力
	 * 通常在完成任务评估后需要设置对该任务的执行能力的时候调用这个函数
	 * @param taskNo 任务编号，用于定位需要评估的任务
	 * @param ability 任务的执行能力
	 */
	public void setAbility(int taskNo, float ability) {
		database.execSQL("update myTask " +
				"set ability = ? " +
				"where taskNo = ?;", new String[]{
				ability + "", taskNo + ""});
	}
	
	/**
	 * 这个函数用来计算并更新给定任务的执行能力字段
	 * 通常在完成独立任务评估后计算并设置ability字段的时候调用这个函数
	 * 这个函数中调用了setAbility(int, int)函数
	 * @param taskNo
	 * @return 返回更新后的执行能力字段
	 */
	public float updateSingleAbility(int taskNo) {
		MyTask task = getTaskRecord(taskNo);
		Evaluate evaluate = new Evaluate();
		float ability = evaluate.computeAbility(task.getDifficulty(), task.getTaskPriority(), task.getCompletence());
		setAbility(taskNo, ability);
		return ability;
	}
	/**
	 * 该函数用来删除某条任务
	 * @param taskNo 任务编号，用于定位要删除的记录
	 */
	public void delete(int taskNo) {
		database.execSQL("delete from myTask where taskNo = ?", new String[]{taskNo + ""});
	}
	
	/**
	 * 该函数用来设置某条任务的”状态“字段
	 * @param state 用来设置的”状态“字段
	 * @param taskNo 任务编号
	 * */
	public void setState(int state, int taskNo) {
		database.execSQL("update myTask " +
				"set state = ? " +
				"where taskNo = ?;", new String[]{
				state + "", taskNo + ""});
	}
	
	/**
	 * 此函数用来更新某条任务的“状态字段”
	 * @param taskNo 任务编号
	 */
	public void updateState(int taskNo) {
		MyTask task = getTaskRecord(taskNo);
		String startDate = task.getStartDate();
		String startTime = task.getStartTime();
		String endDate = task.getEndDate();
		String endTime = task.getEndTime();
		CheckValid check = new CheckValid();
		if(check.laterThanCurrent(startDate, startTime))
			setState(1, taskNo);
		else if(!check.laterThanCurrent(startDate, startTime) 
				&& check.laterThanCurrent(endDate, endTime))
			setState(2, taskNo);
	}
	
	public void refreshData(int taskNo) {
		updateState(taskNo);
		updateSingleAbility(taskNo);
	}
	
	/**
	 * 这个函数用来关闭当前对象的数据库连接
	 */
	public void close() {
		if(helper != null)
			helper.close();
		if(cursor != null)
			cursor.close();
		if(database != null)
			database.close();
	}

}
