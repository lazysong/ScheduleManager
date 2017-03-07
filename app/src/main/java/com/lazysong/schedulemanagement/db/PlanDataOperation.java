package com.lazysong.schedulemanagement.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lazysong.schedulemanagement.help.*;

/**
 * @author songhui
 * @category 这个类用来操作myPlan表
 *
 */
public class PlanDataOperation {
	private DatabaseHelper helper;//多态的使用，SQliteOpenHelper
	private SQLiteDatabase database;
	private Cursor cursor;
	private Context context;
	
	/**
	 * @param context 
	 * {@literal 上下文参数}
	 * @category
	 * {@literal 构造函数，初始化成员变量context,helper,database}
	 */
	public PlanDataOperation(Context context) {
		this.context = context;
		helper = new DatabaseHelper(context, "db2", null, 1);
		database = helper.getWritableDatabase();
	}
	
	
	/**
	 * 向计划表myPlan中插入一条记录，
	 * 其中difficulty,planPriority,completence,ability四个字段采用默认值
	 * 分别为1,5,0,0
	 * @param planName 计划的名称
	 * @param planDescription 计划描述
	 * @param startDate 开始日期
	 * @param startTime 开始时间
	 * @param endDate 结束日期
	 * @param endTime 结束时间
	 * @throws Exception 可能抛出的异常
	 */
	public void insertPlan(
			String planName, 	String planDescription, 
			String startDate, 	String startTime, 
			String endDate, 	String endTime
			) throws Exception{
		database.execSQL("insert into myPlan (" +
				"planName , 	planDescription, 	difficulty , " +
				"planPriority , startDate , 		startTime , " +
				"endDate , 		endTime , 			completence , state, ability ) " +
				"values(?,?,1,5,?,?,?,?,0,0,0);", 
				new String[] {	planName, planDescription, 
								startDate, startTime, 
								endDate,  endTime	});
	}
	
	/**
	 * 这个函数可以根据传入的planNo来检索对应的某条记录
	 * 并将记录的各个字段封装到一个MyPlan类的对象中
	 * 返回这个对象
	 * @param planNo 根据这个值来检索myPlan表中的planNo字段
	 * @return 返回一个MyPlan对象
	 */
	public MyPlan getPlanRecord(int planNo) {
		cursor = database.rawQuery("select * from myPlan where planNo = ?", new String[] {"" + planNo});
		MyPlan plan = new MyPlan();
		while(cursor.moveToNext()) {
			plan.setPlanNo(cursor.getInt(0));
			plan.setPlanName(cursor.getString(1));
			plan.setPlanDescription(cursor.getString(2));
			plan.setDifficulty(cursor.getFloat(3));
			plan.setPlanPriority(cursor.getFloat(4));
			plan.setStartDate(cursor.getString(5));
			plan.setStartTime(cursor.getString(6));
			plan.setEndDate(cursor.getString(7));
			plan.setEndTime(cursor.getString(8));
			plan.setCompletence(cursor.getFloat(9));
			plan.setState(cursor.getInt(10));
			plan.setAbility(cursor.getFloat(11));
		}
		return plan;
	}
	
	/**
	 * 该函数用于检索出近期所有的计划，
	 * 如果今天位于某条计划的开始日期、时间和结束日期、时间的中间，
	 * 那么该条计划即为近期计划
	 * @return 返回近期的所有计划
	 */
	public List<MyPlan> getRecentPlans() {
		//检索出计划表的所有记录
		cursor = database.rawQuery("select * from myPlan order by startDate desc;",null);
		//开始逐条记录判断是否是近期的计划
		List<MyPlan> allPlans = new ArrayList<MyPlan>();
		MyPlan plan = new MyPlan();
		CheckValid check = new CheckValid();
		while(cursor.moveToNext()) {
			//检查该计划是否已经开始，如果该计划还未开始，或者已经结束，则跳过该条计划
			if(check.checkCalendarValid(cursor.getString(5), cursor.getString(6))
				|| !check.checkCalendarValid(cursor.getString(7), cursor.getString(8)))
				continue;
			
			plan = new MyPlan();
			plan.setPlanNo(cursor.getInt(0));
			plan.setPlanName(cursor.getString(1));
			plan.setPlanDescription(cursor.getString(2));
			plan.setDifficulty(cursor.getFloat(3));
			plan.setPlanPriority(cursor.getFloat(4));
			plan.setStartDate(cursor.getString(5));
			plan.setStartTime(cursor.getString(6));
			plan.setEndDate(cursor.getString(7));
			plan.setEndTime(cursor.getString(8));
			plan.setCompletence(cursor.getFloat(9));
			plan.setState(cursor.getInt(10));
			plan.setAbility(cursor.getFloat(11));
			
			allPlans.add(plan);
		}
		return allPlans;
	}
	
	/**
	 * 该函数用来得到一个MyPlan对象的列表，里面装载着计划表中的所有记录
	 * @return 返回计划表的所有记录，即所有的计划
	 */
	public List<MyPlan> getAllRecords() {
		//检索出计划表的所有记录
		cursor = database.rawQuery("select * from myPlan order by startDate desc;",null);
		
		//将所有记录装载到MyPlan对象的列表allPlans中
		List<MyPlan> allPlans = new ArrayList<MyPlan>();
		MyPlan plan = new MyPlan();
		while(cursor.moveToNext()) {
			plan = new MyPlan();
			
			plan.setPlanNo(cursor.getInt(0));
			plan.setPlanName(cursor.getString(1));
			plan.setPlanDescription(cursor.getString(2));
			plan.setDifficulty(cursor.getFloat(3));
			plan.setPlanPriority(cursor.getFloat(4));
			plan.setStartDate(cursor.getString(5));
			plan.setStartTime(cursor.getString(6));
			plan.setEndDate(cursor.getString(7));
			plan.setEndTime(cursor.getString(8));
			plan.setCompletence(cursor.getFloat(9));
			plan.setState(cursor.getInt(10));
			plan.setAbility(cursor.getFloat(11));
			
			allPlans.add(plan);
		}
		return allPlans;//返回列表
	}
	
	/**
	 * 得到一个计划所对应的所有任务
	 * */
	public List<MyTask> getTasksOfPlan(int planNo) {
		//检索给出的计划所对应的所有任务,独立任务的标志就是其planNo字段的值为0
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
	 * 这函数用来设置计划的困难度（difficulty）、优先级（planPriority）、完成度（completence）三个字段
	 * 通常这个函数不是在用户编辑计划的时候调用
	 * 而是在一些需要查看或使用这三个字段的场合下才会被调用
	 * 比如查看计划的困难度、优先级、完成度
	 * 或者是计算用户对该计划的综合执行能力时
	 * @param planNo 计划的编号，用于定位该条计划
	 * @param difficulty 计划的困难度，通过综合该计划的所属的所有任务(task)的difficulty字段得出
	 * @param planPriority 计划的优先级，通过综合该计划的所属的所有任务(task)的planPriority字段得出
	 * @param completence 计划的完成度，通过综合该计划的所属的所有任务(task)的completence字段得出
	 */
	public void setEvidence(int planNo, float difficulty, float planPriority, float completence) {
		database.execSQL("update myPlan " +
				"set difficulty = ?, planPriority = ?,  completence = ? " +
				"where planNo = ?;", 
				new String[]{difficulty + "", planPriority + "", completence + "", planNo + ""});
	}
	
	/**
	 * 这个函数用来设置计划的执行能力(ability)字段
	 * 通常在需要计算该计划的执行能力的时候调用这个函数
	 * @param planNo 计划编号，用于定位需要评估的计划
	 * @param ability 计划的执行能力
	 */
	public void setAbility(int planNo, float ability) {
		database.execSQL("update myPlan " +
				"set ability = ? " +
				"where planNo = ?;", new String[]{
				ability + "", planNo + ""});
	}
	
	/**
	 * 这个函数用来计算并设置计划的难度、优先级、完成度和执行能力(ability)字段
	 * 函数实现了：
	 * 1.检索出该计划所属的所有任务
	 * 2.通过所有任务的难度、优先级、完成度字段计算计划的难度、优先级、完成度字段
	 * 3.设置计划的难度、优先级、完成度字段，即调用setEvidence(int,float,float,float)
	 * 4.通过计划的难度、优先级、完成度字段来计算计划的执行能力字段
	 * 5.设置计划的执行能力字段，即调用setAbility(int planNo, float ability)
	 * @param planNo
	 * @return 返回更新后的执行能力字段
	 */
	public float updateEvidenceAndAbility(int planNo) {
		List<MyTask> tasks = new ArrayList<MyTask>();
		tasks = getTasksOfPlan(planNo);
		if(tasks.size()== 0) {
			setEvidence(planNo, 1, 1, 0);
			setState(planNo, 0);
			return 0;
		}
		Evaluate evaluate = new Evaluate();
		
		/*计算并更新该计划的difficulty,priority,cmpletence字段
		 * 通过该计划的所有子任务的diffciculty,priority,completenct字段的平均值来得到*/
		float difficulty = evaluate.computeDifficulty(tasks);
		float priority = evaluate.computePriority(tasks);
		float completence = evaluate.computeCompletence(tasks);
		setEvidence(planNo, difficulty, priority, completence);
		
		//计算并更新该条计划的state字段
		updateState(planNo);
		
		//筛选出该条计划的所有状态字段为4的子任务，存入finishedTasks中
		List<MyTask> finishedTasks = new ArrayList<MyTask>();
		for(int i = 0; i < tasks.size(); i ++) {
			if(tasks.get(i).getState() == 4) {
				finishedTasks.add(tasks.get(i));
				Log.v("add", "添加了一个为4的");
			}
			
			Log.v("add", "筛选了第" + i + "个, " + "各个字段为：" + tasks.get(i).getInformation() + "\n");
		}

		//计算并更新该计划的执行能力字段
		float ability = evaluate.computeAbility(finishedTasks);
		setAbility(planNo, ability);
		
		return ability;
	}
	
	/**
	 * 这个函数用来更新myPlan表的数据，
	 * 通常在编辑某个计划的时候使用这个函数
	 * 在更新时要注意的是：
	 * 如果日期或时间数据被改变了，需要将completence和ability字段置0
	 * 否则就不需要置0
	 * @param planNo 用于定位myPlan表的具体某条记录
	 * @param planName 新的计划名称
	 * @param planDescription 新的计划描述
	 * @param startDate 新的开始日期
	 * @param startTime 新的开始时间
	 * @param endDate 新的结束日期
	 * @param endTime 新的结束时间
	 */
	public void updatePlanRecord(
			int    planNo, 
			String planName, 	String planDescription, 
			String startDate, 	String startTime, 
			String endDate, 	String endTime) {
		/*判断一下日期、时间有没有改变
		 * 如果日期或时间改变的话，要将完成度completence和ability字段设为0
		 * 如果日期、时间均没有改变的话，则不需要重置completence和ability字段
		 * */
		MyPlan plan = getPlanRecord(planNo);
		//如果日期、时间均没有改变的话，则不需要重置completence和ability字段
		if(	startDate.equals(plan.getStartDate()) && 	startTime.equals(plan.getStartTime())
			 && endDate.equals(plan.getEndDate()) && 	endTime.equals(plan.getEndTime()))
			database.execSQL(
					"update myPlan " +
					"set planName = ?, " +
					"planDescription = ? " +
					"where planNo = ?;",new String[]{
							planName, planDescription, planNo + ""});
		//如果日期或时间改变的话，要将完成度completence和ability字段设为0
		else
			database.execSQL("update myPlan set planName = ?, planDescription = ?, " +
					"startDate = ?, startTime = ?, " +
					"endDate = ?, endTime = ?, " +
					"completence = 0, " +
					"ability = 0 " + 
					"where planNo = ?;", new String[]{planName, planDescription, 
							startDate, startTime, endDate, endTime, planNo + ""});
	}
	
	/**
	 * 这个函数用来设置某条计划记录的“状态”字段
	 * @param planNo 计划记录的编号
	 * @param state 计划记录的“状态”字段
	 */
	public void setState(int planNo, int state) {
		database.execSQL("update myPlan " +
				"set state = ? " +
				"where planNo = ?;", new String[]{
				state + "", planNo + ""});
	}
	
	public void updateState(int planNo) {
		//得到该条计划的所有子任务
		List<MyTask> tasks = getTasksOfPlan(planNo);
		//根据时间来判断
		MyPlan plan = getPlanRecord(planNo);
		String startDate = plan.getStartDate();
		String startTime = plan.getStartTime();
		String endDate = plan.getEndDate();
		String endTime = plan.getEndTime();
		CheckValid check = new CheckValid();
		if(check.laterThanCurrent(startDate, startTime))
			setState(1, planNo);
		else if (!check.laterThanCurrent(startDate, startTime) 
				&& check.laterThanCurrent(endDate, endTime))
			setState(2, planNo);
		else {
//			TaskDataOperation oper = new TaskDataOperation();
			for(int i = 0; i < tasks.size(); i ++) {
//				oper.updateState(tasks.get(i).getTaskNo());
				
				if(tasks.get(i).getState() != 4) {
					setState(planNo, 3);
					return;
				}
			}
//			oper.close();
			setState(planNo, 4);
		}
		
	}
	
	public void refreshData(int planNo) {
		
		//刷新该条计划下面每条子任务的状态值
		TaskDataOperation taskOper = new TaskDataOperation(context);
		
		List<MyTask> tasks = this.getTasksOfPlan(planNo);
		for(int i = 0; i < tasks.size(); i ++) {
			taskOper.refreshData(tasks.get(i).getTaskNo());
		}
		taskOper.close();
		
		//更新计划的各个字段
		updateEvidenceAndAbility(planNo);
	}
	
	/**
	 * 该函数用来删除某条计划
	 * @param planNo 计划编号，用于定位要删除的记录
	 */
	public void delete(int planNo) {
		/*注意：在删除myPlan表的某条记录之前，
		 *要先删除myTask表中从属于该条记录的所有任务记录
		 **/
		database.execSQL("delete from myTask where taskNo = ?", new String[]{planNo + ""});
		database.execSQL("delete from myPlan where planNo = ?", new String[]{planNo + ""});
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
