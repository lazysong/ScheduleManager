package com.lazysong.schedulemanagement.help;



import java.util.ArrayList;
import java.util.List;

public class Evaluate {
	
	public Evaluate() {
		
	}
	
	/**
	 * 根据三个数据段计算某项计划计划或者任务的执行能力
	 * */
	public float computeAbility(float difficulty, float priority, float completence) {
		float[] transform_difficulty = new float[]{60, 70, 85, 95, 100};
		float[] transform_priority = new float[]{100, 90, 80, 70, 60};
		
		float ability = 0.7f * transform_difficulty[(int)difficulty -1] / 100;
		ability += 0.3f * transform_priority[(int)priority - 1] / 100;
		ability *= completence;
		
		return ability;
	}
	
	/**
	 * 计算计划的困难程度
	 * 取平均值
	 * */
	public float computeDifficulty(float[] difficultyOfTasks) {
		float sum = 0;
		if(difficultyOfTasks.length == 0)
			return 0;
		for(int i = 0; i < difficultyOfTasks.length; i ++) {
			sum += difficultyOfTasks[i];
		}
		return sum / (difficultyOfTasks.length);
	}
	
	/**
	 * 计算计划的优先级，
	 * 取平均值
	 * */
	public float computePriority(float[] priorityOfTasks) {
		float sum = 0;
		if(priorityOfTasks.length == 0)
			return 0;
		for(int i = 0; i < priorityOfTasks.length; i ++) {
			sum += priorityOfTasks[i];
		}
		return sum / (priorityOfTasks.length);
	}
	
	/**
	 * 计算计划的完成度
	 * 取平均值
	 * */
	public float computeCompletence(float[] completenceOfTasks) {
		float sum = 0;
		if(completenceOfTasks.length == 0)
			return 0;
		for(int i = 0; i < completenceOfTasks.length; i ++) {
			sum += completenceOfTasks[i];
		}
		return sum / (completenceOfTasks.length);
	}
	
	/**
	 * 计算某条计划的困难度
	 * */
	public float computeDifficulty(List<MyTask> tasks) {
		if(tasks == null)
			return 0;
		
		//计算计划的难度
		int size = tasks.size();
		float[] difficultyOfTasks = new float[size];

		for(int i = 0; i < size; i ++) {
			difficultyOfTasks[i] = tasks.get(i).getDifficulty();
		}
		
		float difficulty = 0;
		difficulty = computeDifficulty(difficultyOfTasks);
		
		return difficulty;
	}
	
	/**
	 * 计算某条计划的优先级
	 * */
	public float computePriority(List<MyTask> tasks) {
		if(tasks == null)
			return 0;
		
		//计算计划的优先级
		int size = tasks.size();
		float[] priorityOfTasks = new float[size];

		for(int i = 0; i < size; i ++) {
			priorityOfTasks[i] = tasks.get(i).getTaskPriority();
		}
		
		float priority = 0;
		priority = computePriority(priorityOfTasks);
		
		return priority;
	}
	

	/**
	 * 计算某条计划的执行能力
	 * */
	public float computeCompletence(List<MyTask> tasks) {
		if(tasks == null)
			return 0;
		
		//计算计划的完成度
		int size = tasks.size();
		float[] completenceOfTasks = new float[size];
		for(int i = 0; i < size; i ++) {
			completenceOfTasks[i] = tasks.get(i).getCompletence();
		}
		
		float completence = 0;
		completence = computeCompletence(completenceOfTasks);

		//计算计划的执行能力并返回
		return completence;
	}
	
	/**
	 * 计算某条计划的执行能力
	 * */
	public float computeAbility(List<MyTask> tasks) {
//		if(tasks.size() == 0)
//			return 0;
		if(tasks == null)
			return 0;
		if(tasks.size() == 0)
			return 0;
		
		//计算计划的难度、优先级和完成度
		float difficulty = computeDifficulty(tasks);
		float priority = computePriority(tasks);
		float completence = computeCompletence(tasks);

		//计算计划的执行能力并返回
		return computeAbility(difficulty, priority, completence);
	}
	
	/**
	 * 计算综合执行能力
	 * */
	public float computeTotalAbility(List<MyPlan> plans, List<MyTask> singleTasks) {
		if(plans.size() == 0 && singleTasks.size() == 0)
			return 0;
		//取出状态字段为4的计划和独立任务
		int sizeOfPlans = plans.size();
		int sizeOfSingleTasks = singleTasks.size();
		List<MyPlan> finishedPlans = new ArrayList<MyPlan>();
		List<MyTask> finishedTasks = new ArrayList<MyTask>();
		for(int i = 0; i < sizeOfPlans; i ++)
			if(plans.get(i).getState() == 4)
				finishedPlans.add(plans.get(i));

		for(int i = 0; i < sizeOfSingleTasks; i ++)
			if(singleTasks.get(i).getState() == 4)
				finishedTasks.add(singleTasks.get(i));
		
		//根据筛选出来的状态为4的计划集合和独立任务集合，计算综合执行能力
		int sizeOfFinishedPlans = finishedPlans.size();
		int sizeOfFinishedTasks = finishedTasks.size();
		float[] abilityOfPlans = new float[sizeOfFinishedPlans];
		float[] abilityOfSingleTasks = new float[sizeOfFinishedTasks];
		for(int i = 0; i < sizeOfFinishedPlans; i ++)
			abilityOfPlans[i] = finishedPlans.get(i).getAbility();
		for(int i = 0; i < sizeOfFinishedTasks; i ++)
			abilityOfSingleTasks[i] = finishedTasks.get(i).getAbility();
		return computeTotalAbility(abilityOfPlans, abilityOfSingleTasks);
		
	}
	
	/**
	 * 计算综合执行能力
	 * */
	public float computeTotalAbility(float[] abilityOfPlans, float[] abilityOfSingleTasks) {
		if(abilityOfPlans.length == 0 && abilityOfSingleTasks.length == 0)
			return 0;
		float totalAbility = 0;
		for(int i = 0; i < abilityOfPlans.length; i ++)
			totalAbility += abilityOfPlans[i];
		for(int i = 0; i < abilityOfSingleTasks.length; i ++)
			totalAbility += abilityOfSingleTasks[i];
		totalAbility = totalAbility/(abilityOfPlans.length + abilityOfSingleTasks.length);
		return totalAbility;
	}
	
	
}
