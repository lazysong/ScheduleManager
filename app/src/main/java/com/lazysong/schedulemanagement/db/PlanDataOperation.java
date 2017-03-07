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
 * @category �������������myPlan��
 *
 */
public class PlanDataOperation {
	private DatabaseHelper helper;//��̬��ʹ�ã�SQliteOpenHelper
	private SQLiteDatabase database;
	private Cursor cursor;
	private Context context;
	
	/**
	 * @param context 
	 * {@literal �����Ĳ���}
	 * @category
	 * {@literal ���캯������ʼ����Ա����context,helper,database}
	 */
	public PlanDataOperation(Context context) {
		this.context = context;
		helper = new DatabaseHelper(context, "db2", null, 1);
		database = helper.getWritableDatabase();
	}
	
	
	/**
	 * ��ƻ���myPlan�в���һ����¼��
	 * ����difficulty,planPriority,completence,ability�ĸ��ֶβ���Ĭ��ֵ
	 * �ֱ�Ϊ1,5,0,0
	 * @param planName �ƻ�������
	 * @param planDescription �ƻ�����
	 * @param startDate ��ʼ����
	 * @param startTime ��ʼʱ��
	 * @param endDate ��������
	 * @param endTime ����ʱ��
	 * @throws Exception �����׳����쳣
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
	 * ����������Ը��ݴ����planNo��������Ӧ��ĳ����¼
	 * ������¼�ĸ����ֶη�װ��һ��MyPlan��Ķ�����
	 * �����������
	 * @param planNo �������ֵ������myPlan���е�planNo�ֶ�
	 * @return ����һ��MyPlan����
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
	 * �ú������ڼ������������еļƻ���
	 * �������λ��ĳ���ƻ��Ŀ�ʼ���ڡ�ʱ��ͽ������ڡ�ʱ����м䣬
	 * ��ô�����ƻ���Ϊ���ڼƻ�
	 * @return ���ؽ��ڵ����мƻ�
	 */
	public List<MyPlan> getRecentPlans() {
		//�������ƻ�������м�¼
		cursor = database.rawQuery("select * from myPlan order by startDate desc;",null);
		//��ʼ������¼�ж��Ƿ��ǽ��ڵļƻ�
		List<MyPlan> allPlans = new ArrayList<MyPlan>();
		MyPlan plan = new MyPlan();
		CheckValid check = new CheckValid();
		while(cursor.moveToNext()) {
			//���üƻ��Ƿ��Ѿ���ʼ������üƻ���δ��ʼ�������Ѿ������������������ƻ�
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
	 * �ú��������õ�һ��MyPlan������б�����װ���żƻ����е����м�¼
	 * @return ���ؼƻ�������м�¼�������еļƻ�
	 */
	public List<MyPlan> getAllRecords() {
		//�������ƻ�������м�¼
		cursor = database.rawQuery("select * from myPlan order by startDate desc;",null);
		
		//�����м�¼װ�ص�MyPlan������б�allPlans��
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
		return allPlans;//�����б�
	}
	
	/**
	 * �õ�һ���ƻ�����Ӧ����������
	 * */
	public List<MyTask> getTasksOfPlan(int planNo) {
		//���������ļƻ�����Ӧ����������,��������ı�־������planNo�ֶε�ֵΪ0
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
	 * �⺯���������üƻ������Ѷȣ�difficulty�������ȼ���planPriority������ɶȣ�completence�������ֶ�
	 * ͨ����������������û��༭�ƻ���ʱ�����
	 * ������һЩ��Ҫ�鿴��ʹ���������ֶεĳ����²Żᱻ����
	 * ����鿴�ƻ������Ѷȡ����ȼ�����ɶ�
	 * �����Ǽ����û��Ըüƻ����ۺ�ִ������ʱ
	 * @param planNo �ƻ��ı�ţ����ڶ�λ�����ƻ�
	 * @param difficulty �ƻ������Ѷȣ�ͨ���ۺϸüƻ�����������������(task)��difficulty�ֶεó�
	 * @param planPriority �ƻ������ȼ���ͨ���ۺϸüƻ�����������������(task)��planPriority�ֶεó�
	 * @param completence �ƻ�����ɶȣ�ͨ���ۺϸüƻ�����������������(task)��completence�ֶεó�
	 */
	public void setEvidence(int planNo, float difficulty, float planPriority, float completence) {
		database.execSQL("update myPlan " +
				"set difficulty = ?, planPriority = ?,  completence = ? " +
				"where planNo = ?;", 
				new String[]{difficulty + "", planPriority + "", completence + "", planNo + ""});
	}
	
	/**
	 * ��������������üƻ���ִ������(ability)�ֶ�
	 * ͨ������Ҫ����üƻ���ִ��������ʱ������������
	 * @param planNo �ƻ���ţ����ڶ�λ��Ҫ�����ļƻ�
	 * @param ability �ƻ���ִ������
	 */
	public void setAbility(int planNo, float ability) {
		database.execSQL("update myPlan " +
				"set ability = ? " +
				"where planNo = ?;", new String[]{
				ability + "", planNo + ""});
	}
	
	/**
	 * ��������������㲢���üƻ����Ѷȡ����ȼ�����ɶȺ�ִ������(ability)�ֶ�
	 * ����ʵ���ˣ�
	 * 1.�������üƻ���������������
	 * 2.ͨ������������Ѷȡ����ȼ�����ɶ��ֶμ���ƻ����Ѷȡ����ȼ�����ɶ��ֶ�
	 * 3.���üƻ����Ѷȡ����ȼ�����ɶ��ֶΣ�������setEvidence(int,float,float,float)
	 * 4.ͨ���ƻ����Ѷȡ����ȼ�����ɶ��ֶ�������ƻ���ִ�������ֶ�
	 * 5.���üƻ���ִ�������ֶΣ�������setAbility(int planNo, float ability)
	 * @param planNo
	 * @return ���ظ��º��ִ�������ֶ�
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
		
		/*���㲢���¸üƻ���difficulty,priority,cmpletence�ֶ�
		 * ͨ���üƻ��������������diffciculty,priority,completenct�ֶε�ƽ��ֵ���õ�*/
		float difficulty = evaluate.computeDifficulty(tasks);
		float priority = evaluate.computePriority(tasks);
		float completence = evaluate.computeCompletence(tasks);
		setEvidence(planNo, difficulty, priority, completence);
		
		//���㲢���¸����ƻ���state�ֶ�
		updateState(planNo);
		
		//ɸѡ�������ƻ�������״̬�ֶ�Ϊ4�������񣬴���finishedTasks��
		List<MyTask> finishedTasks = new ArrayList<MyTask>();
		for(int i = 0; i < tasks.size(); i ++) {
			if(tasks.get(i).getState() == 4) {
				finishedTasks.add(tasks.get(i));
				Log.v("add", "�����һ��Ϊ4��");
			}
			
			Log.v("add", "ɸѡ�˵�" + i + "��, " + "�����ֶ�Ϊ��" + tasks.get(i).getInformation() + "\n");
		}

		//���㲢���¸üƻ���ִ�������ֶ�
		float ability = evaluate.computeAbility(finishedTasks);
		setAbility(planNo, ability);
		
		return ability;
	}
	
	/**
	 * ���������������myPlan������ݣ�
	 * ͨ���ڱ༭ĳ���ƻ���ʱ��ʹ���������
	 * �ڸ���ʱҪע����ǣ�
	 * ������ڻ�ʱ�����ݱ��ı��ˣ���Ҫ��completence��ability�ֶ���0
	 * ����Ͳ���Ҫ��0
	 * @param planNo ���ڶ�λmyPlan��ľ���ĳ����¼
	 * @param planName �µļƻ�����
	 * @param planDescription �µļƻ�����
	 * @param startDate �µĿ�ʼ����
	 * @param startTime �µĿ�ʼʱ��
	 * @param endDate �µĽ�������
	 * @param endTime �µĽ���ʱ��
	 */
	public void updatePlanRecord(
			int    planNo, 
			String planName, 	String planDescription, 
			String startDate, 	String startTime, 
			String endDate, 	String endTime) {
		/*�ж�һ�����ڡ�ʱ����û�иı�
		 * ������ڻ�ʱ��ı�Ļ���Ҫ����ɶ�completence��ability�ֶ���Ϊ0
		 * ������ڡ�ʱ���û�иı�Ļ�������Ҫ����completence��ability�ֶ�
		 * */
		MyPlan plan = getPlanRecord(planNo);
		//������ڡ�ʱ���û�иı�Ļ�������Ҫ����completence��ability�ֶ�
		if(	startDate.equals(plan.getStartDate()) && 	startTime.equals(plan.getStartTime())
			 && endDate.equals(plan.getEndDate()) && 	endTime.equals(plan.getEndTime()))
			database.execSQL(
					"update myPlan " +
					"set planName = ?, " +
					"planDescription = ? " +
					"where planNo = ?;",new String[]{
							planName, planDescription, planNo + ""});
		//������ڻ�ʱ��ı�Ļ���Ҫ����ɶ�completence��ability�ֶ���Ϊ0
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
	 * ���������������ĳ���ƻ���¼�ġ�״̬���ֶ�
	 * @param planNo �ƻ���¼�ı��
	 * @param state �ƻ���¼�ġ�״̬���ֶ�
	 */
	public void setState(int planNo, int state) {
		database.execSQL("update myPlan " +
				"set state = ? " +
				"where planNo = ?;", new String[]{
				state + "", planNo + ""});
	}
	
	public void updateState(int planNo) {
		//�õ������ƻ�������������
		List<MyTask> tasks = getTasksOfPlan(planNo);
		//����ʱ�����ж�
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
		
		//ˢ�¸����ƻ�����ÿ���������״ֵ̬
		TaskDataOperation taskOper = new TaskDataOperation(context);
		
		List<MyTask> tasks = this.getTasksOfPlan(planNo);
		for(int i = 0; i < tasks.size(); i ++) {
			taskOper.refreshData(tasks.get(i).getTaskNo());
		}
		taskOper.close();
		
		//���¼ƻ��ĸ����ֶ�
		updateEvidenceAndAbility(planNo);
	}
	
	/**
	 * �ú�������ɾ��ĳ���ƻ�
	 * @param planNo �ƻ���ţ����ڶ�λҪɾ���ļ�¼
	 */
	public void delete(int planNo) {
		/*ע�⣺��ɾ��myPlan���ĳ����¼֮ǰ��
		 *Ҫ��ɾ��myTask���д����ڸ�����¼�����������¼
		 **/
		database.execSQL("delete from myTask where taskNo = ?", new String[]{planNo + ""});
		database.execSQL("delete from myPlan where planNo = ?", new String[]{planNo + ""});
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
