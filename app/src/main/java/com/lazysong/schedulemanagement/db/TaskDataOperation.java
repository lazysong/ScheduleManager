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
 * ����������������Ĳ���
 * @author songhui
 *
 */
public class TaskDataOperation {
	private DatabaseHelper helper;//��̬��ʹ�ã�SQliteOpenHelper
	private SQLiteDatabase database;
	private Cursor cursor;
	private Context context;
	
	public TaskDataOperation(Context context) {
		this.context = context;
		helper = new DatabaseHelper(context, "db2", null, 1);
		database = helper.getWritableDatabase();
	}
	
	
	/**
	 * �������myTask�в���һ����¼����ɶ�(completence)��ִ������(ability)�ֶε�ֵ��Ϊ0
	 * @param planNo ���������ļƻ��ı�ţ�������Ϊ-1����������Ϊ��������
	 * @param taskName ���������
	 * @param taskDescription ��������
	 * @param difficulty �����Ѷ�
	 * @param taskPriority �������ȼ�
	 * @param startDate ��ʼ����
	 * @param startTime ��ʼʱ��
	 * @param endDate ��������
	 * @param endTime ����ʱ��
	 * @throws Exception �����׳����쳣
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
	 * ����������Ը��ݴ����taskNo��������Ӧ��ĳ����¼
	 * ������¼�ĸ����ֶη�װ��һ��MyTask��Ķ�����
	 * �����������
	 * @param taskNo �������ֵ������myTask���е�taskNo�ֶ�
	 * @return ����һ��MyTask����
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
	 * �õ�һ���ƻ�����Ӧ����������
	 * */
	public List<MyTask> getTasksOfPlan(int planNo) {
		//���������ļƻ�����Ӧ����������,��������ı�־������planNo�ֶε�ֵΪ-1
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
	 * �ú������ڼ������������еĶ�������
	 * �������λ��ĳ����������Ŀ�ʼ���ڡ�ʱ��ͽ������ڡ�ʱ����м䣬
	 * ��ô��������Ϊ���ڶ�������
	 * @return ���ؽ��ڵ����ж�������
	 */
	public List<MyTask> getRecentSingleTasks() {
		//���������������ж��������¼����������ı�־������planNo�ֶε�ֵΪ-1
		cursor = database.rawQuery("select * from myTask where planNo = -1 order by startDate desc;",null);
		//��ʼ������¼�ж��Ƿ��ǽ��ڵĶ�������
		List<MyTask> allTasks = new ArrayList<MyTask>();
		MyTask task = new MyTask();
		CheckValid check = new CheckValid();
		while(cursor.moveToNext()) {
			//���ö��������Ƿ��Ѿ���ʼ������ö�������δ��ʼ�������Ѿ�������������������������
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
	 * �ú��������õ�һ��MyTask������б�����װ����������е����ж�������
	 * @return �������������ж��������¼
	 */
	public List<MyTask> getAllSingleTasks() {
		//���������������м�¼
		cursor = database.rawQuery("select * from myTask where planNo = -1 order by startDate desc;",null);
		
		//�����м�¼װ�ص�MyTask������б�allTasks��
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
		return allTasks;//�����б�
	}
	
	/**
	 * ���������������myTask������ݣ�
	 * ͨ���ڱ༭ĳ��������߶��������ʱ��ʹ���������
	 * �ڸ���ʱҪע����ǣ�
	 * ������ڻ�ʱ�����ݱ��ı��ˣ���Ҫ��completence��ability�ֶ���0
	 * ����Ͳ���Ҫ��0
	 * @param taskNo ���ڶ�λmyTask��ľ���ĳ����¼
	 * @param taskName �µ�������߶�����������
	 * @param taskDescription �µ�������߶�����������
	 * @param difficulty �µ�������߶��������Ѷ�
	 * @param taskPriority �µ�������߶����������ȼ�
	 * @param startDate �µĿ�ʼ����
	 * @param startTime �µĿ�ʼʱ��
	 * @param endDate �µĽ�������
	 * @param endTime �µĽ���ʱ��
	 */
	public void updateTaskRecord(
			int    taskNo,
			String taskName, 	String taskDescription, 
			float  difficulty,	float  taskPriority,
			String startDate, 	String startTime, 
			String endDate, 	String endTime) {
		/*�ж�һ�����ڡ�ʱ����û�иı�
		 * ������ڻ�ʱ��ı�Ļ���Ҫ����ɶ�completence��ability�ֶ���Ϊ0
		 * ������ڡ�ʱ���û�иı�Ļ�������Ҫ����completence��ability�ֶ�
		 * */
		MyTask task = getTaskRecord(taskNo);
		//������ڡ�ʱ���û�иı�Ļ�������Ҫ����completence��ability�ֶ�
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
		//������ڻ�ʱ��ı�Ļ���Ҫ����ɶ�completence��ability�ֶ���Ϊ0
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
	 * ����������������������ɶ�
	 * ͨ�����������֮���������������ʱ������������
	 * @param taskNo �����ţ����ڶ�λ��Ҫ����������
	 * @param completence �������ɶ�
	 */
	public void setCompletence(int taskNo, float completence) {
		database.execSQL("update myTask " +
				"set completence = ? " +
				"where taskNo = ?;", new String[]{
				completence + "", taskNo + ""});
	}
	
	/**
	 * ��������������������ִ������
	 * ͨ�������������������Ҫ���öԸ������ִ��������ʱ������������
	 * @param taskNo �����ţ����ڶ�λ��Ҫ����������
	 * @param ability �����ִ������
	 */
	public void setAbility(int taskNo, float ability) {
		database.execSQL("update myTask " +
				"set ability = ? " +
				"where taskNo = ?;", new String[]{
				ability + "", taskNo + ""});
	}
	
	/**
	 * ��������������㲢���¸��������ִ�������ֶ�
	 * ͨ������ɶ���������������㲢����ability�ֶε�ʱ������������
	 * ��������е�����setAbility(int, int)����
	 * @param taskNo
	 * @return ���ظ��º��ִ�������ֶ�
	 */
	public float updateSingleAbility(int taskNo) {
		MyTask task = getTaskRecord(taskNo);
		Evaluate evaluate = new Evaluate();
		float ability = evaluate.computeAbility(task.getDifficulty(), task.getTaskPriority(), task.getCompletence());
		setAbility(taskNo, ability);
		return ability;
	}
	/**
	 * �ú�������ɾ��ĳ������
	 * @param taskNo �����ţ����ڶ�λҪɾ���ļ�¼
	 */
	public void delete(int taskNo) {
		database.execSQL("delete from myTask where taskNo = ?", new String[]{taskNo + ""});
	}
	
	/**
	 * �ú�����������ĳ������ġ�״̬���ֶ�
	 * @param state �������õġ�״̬���ֶ�
	 * @param taskNo ������
	 * */
	public void setState(int state, int taskNo) {
		database.execSQL("update myTask " +
				"set state = ? " +
				"where taskNo = ?;", new String[]{
				state + "", taskNo + ""});
	}
	
	/**
	 * �˺�����������ĳ������ġ�״̬�ֶΡ�
	 * @param taskNo ������
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
	 * ������������رյ�ǰ��������ݿ�����
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
