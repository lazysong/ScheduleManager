package com.lazysong.schedulemanagement.plan;

import java.util.Calendar;

import com.lazysong.schedulemanagement.R;
import com.lazysong.schedulemanagement.R.layout;
import com.lazysong.schedulemanagement.db.PlanDataOperation;
import com.lazysong.schedulemanagement.help.MyPlan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditPlanActivity extends Activity {

	private EditText name,des;
	private Button buttonsDate,buttonsTime,buttoneDate,buttoneTime,save,cancel;
	private String sdate,stime,edate,etime,planNo,planName;
	private int syear,smonth,sday,shour,sminute,ssecond,
	eyear,emonth,eday,ehour,eminute,esecond;
	private Calendar c;
	private PlanDataOperation oper;
	private MyPlan plan;
	private String planDes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_plan);
		
		Intent intent=getIntent();
		planNo=intent.getExtras().getInt("planNo") + "";
//		TextView text1=(TextView)findViewById(R.id.conclusion_total_completence);
//		text1.setText(planNo);
		
		oper = new PlanDataOperation(this);
		
		name=(EditText)findViewById(R.id.editText1_edit_plan);
		des=(EditText)findViewById(R.id.editText2_edit_plan);
		buttonsDate = (Button)findViewById(R.id.button1_edit_plan);
		buttonsTime = (Button)findViewById(R.id.button2_edit_plan);
		buttoneDate = (Button)findViewById(R.id.button3_edit_plan);
		buttoneTime = (Button)findViewById(R.id.button4_edit_plan);
		//获取传过来的planNo所对应的plan的属性
		plan = oper.getPlanRecord(Integer.parseInt(planNo));
		Log.v("checkPlan", "1--" + plan.getInformation());

		planName = plan.getPlanName();
//		planDes = plan.getPlanDescription();
		name.setText(plan.getPlanName());
		des.setText(plan.getPlanDescription());
		sdate = plan.getStartDate();
		stime = plan.getStartTime();
		edate = plan.getEndDate();
		etime = plan.getEndTime();

		
//		Toast.makeText(getApplicationContext(), planName,
//				Toast.LENGTH_SHORT).show();
		
		c = Calendar.getInstance();
		initDate();//更新日期
		initTime();//更新时间
		
		save = (Button)findViewById(R.id.button5_edit_plan);
		cancel = (Button)findViewById(R.id.button6_edit_plan);
		
		save.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//update(planNo);更新计划数据
				//下面测试更新函数，时间改变
				planName = name.getText().toString();
				planDes = des.getText().toString();
				oper.updatePlanRecord(Integer.parseInt(planNo), planName, planDes, sdate, stime, edate, etime);
				plan = oper.getPlanRecord(Integer.parseInt(planNo));
				Log.v("checkPlan", "更新操作执行完毕，plan表数据段为--" + plan.getInformation());
//				tag = "MyLog";
//				Log.v(tag, "MainActivity的OnCreate()函数被调用");
				Toast.makeText(getApplicationContext(), "保存成功",
						Toast.LENGTH_SHORT).show();
				
				//关闭数据库连接
				oper.close();
				
				/**通过Calendar对象cal将输入的日期、时间的具体数据赋值给
				 * year,month,day,hour,minute
				 * 这五个变量原来的值是当天的日期、时间*/
//				year = c.get(Calendar.YEAR);
//				month = c.get(Calendar.MONTH);
//				day = c.get(Calendar.DAY_OF_MONTH);
//				hour = c.get(Calendar.HOUR_OF_DAY);
//				minute = c.get(Calendar.MINUTE);
				
				//通过日期、时间值来设置闹钟
//				Toast.makeText(AddPlanActivity.this, "已添加了编号为" + CalendarDataOperation.recordCount + "的闹钟", Toast.LENGTH_SHORT).show();
//				AlarmManage manager = new AlarmManage(CalendarAddActivity.this);
//				manager.setAlarm(CalendarDataOperation.recordCount ++, repetition, advanceTime, year, month, day, hour, minute);
				EditPlanActivity.this.finish();
			}
		});
		
        cancel.setOnClickListener(new View.OnClickListener(){
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		//关闭数据库连接
        		oper.close();
				EditPlanActivity.this.finish();
			}
        });
		
		//下面测试插入函数
//		try{
//			oper.insertPlan("plnaName1", "planDescription1", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "2" + plan.getInformation());
//		
//		
		//下面测试设置执行能力的函数
//		oper.setAbility(1, 90);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "设置执行能力操作执行完毕，plan表数据段为--" + plan.getInformation());
		
		//下面测试更新函数，时间未改变
//		oper.updatePlanRecord(1, "planName1_new", "planDescription1_new", "2012-10-09", "12:23:09", "2016-02-02", "13:00:00");
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "更新操作执行完毕，plan表数据段为--" + plan.getInformation());
//		
//		
//		//下面测试更新函数，时间改变
//		oper.updatePlanRecord(1, "planName1_new", "planDescription1_new", "2012-01-10", "12:23:19", "2013-02-02", "13:00:00");
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "更新操作执行完毕，plan表数据段为--" + plan.getInformation());
////		tag = "MyLog";
////		Log.v(tag, "MainActivity的OnCreate()函数被调用");
		
//		//下面测试删除函数
//		oper.delete(1);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "删除操作执行完毕，plan表数据段为--" + plan.getInformation());*/
//		
		//下面测试更新难度、优先级、完成度三个字段的函数
//		oper.setEvidence(1, 2, 3, 90);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "设置凭据字段操作执行完毕，plan表数据段为--" + plan.getInformation());
		
		//下面测试得到通过计划得到所属的所有任务的函数
//		List<MyTask> tasks = oper.getTasksOfPlan(1);
//		for(int i = 0; i < tasks.size(); i ++) {
//			Log.v("checkPlan", "检索出第" + i + "条记录：" + tasks.get(i).getInformation() + "\n");
//		}
		
		//下面测试计算并设置执行能力的函数
//		oper.updateEvidenceAndAbility(1);
//		plan = oper.getPlanRecord(1);
//		Log.v("checkPlan", "计算并设置执行能力字段操作执行完毕，plan表数据段为--" + plan.getInformation());
//		
//		//下面测试返回全部计划的函数
//		List<MyPlan> allPlans = new ArrayList<MyPlan>();
//		allPlans = oper.getAllRecords();
//		for(int i = 0; i < allPlans.size(); i ++) {
//			Log.v("checkPlan", "检索出第" + i + "条记录：" + allPlans.get(i).getInformation() + "\n");
//		}
//		
//
		//下面测试返回近期执行中计划的函数
//		List<MyPlan> allPlans = new ArrayList<MyPlan>();
//		allPlans = oper.getRecentPlans();
//		for(int i = 0; i < allPlans.size(); i ++) {
//			Log.v("checkPlan", "检索出第" + i + "条记录：" + allPlans.get(i).getInformation() + "\n");
//		}
		
		//关闭数据库连接
//		oper.close();
		
//		TaskDataOperation oper = new TaskDataOperation(this);
//		MyTask task = new MyTask();
//		
		//现在开始测试插入任务的函数
//		try {
//			oper.insertTask(1, "taskName1", "taskDescription1", 3, 1, "2014-09-08", "02:09:00", "2014-12-09", "13:21:00");
//			oper.setCompletence(i + 14, 80);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//现在开始测试查询某条任务记录的函数
//		task = oper.getTaskRecord(1);
//		Log.v("checkTask", "插入操作完成，第一号任务为：" + task.getInformation());
		
		//下面测试返回近期执行中独立任务的函数
//		List<MyTask> recentSingleTasks = new ArrayList<MyTask>();
//		recentSingleTasks = oper.getRecentSingleTasks();
//		for(int i = 0; i < recentSingleTasks.size(); i ++) {
//			Log.v("checkTask", "检索出第" + i + "条记录：" + recentSingleTasks.get(i).getInformation() + "\n");
//		}
		
		//下面测试返回全部独立任务的函数
//		List<MyTask> allSingleTasks = new ArrayList<MyTask>();
//		allSingleTasks = oper.getAllSingleTasks();
//		for(int i = 0; i < allSingleTasks.size(); i ++) {
//			Log.v("checkTask", "检索出第" + i + "条记录：" + allSingleTasks.get(i).getInformation() + "\n");
//		}
		
		//下面测试设置执行能力字段的函数
//		oper.setAbility(4, 80);
		
		//下面测试更新任务或者独立任务的函数，日期变动了
//		oper.updateTaskRecord(4, "taskName_new", "taskDescription_new", 3, 3, "2014-09-08", "02:09:00", "2015-12-09", "13:21:00");
		//下面测试更新任务或者独立任务的函数，日期变动了
//		oper.updateTaskRecord(5, "taskName_new", "taskDescription_new", 3, 3, "2014-09-08", "02:09:00", "2014-12-09", "13:21:00");
		
		//下面测试设置任务的完成度函数
//		oper.setCompletence(8, 80);
		
		//下面测试删除任务的函数
//		oper.delete(7);
		
		//下面测试得到某条计划的所有任务的函数
//		List<MyTask> allTasks = new ArrayList<MyTask>();
//		allTasks = oper.getTasksOfPlan(1);
//		for(int i = 0; i < allTasks.size(); i ++) {
//			Log.v("checkTask", "检索出第" + i + "条记录：" + allTasks.get(i).getInformation() + "\n");
//		}
		
		//下面测试计算并设置执行能力字段的函数
//		oper.updateSingleAbility(8);
		
		//关闭数据库连接
//		oper.close();
		
		//计算执行能力
//		Evaluate evaluate = new Evaluate();
//		Log.v("evaluate", "执行能力为：" + evaluate.computeAbility(5, 1, 100));

		
	}
    public void initDate() {
//		//获得当前日期，存入date字段并显示在日期按钮上
//		year = c.get(Calendar.YEAR);
//		month = c.get(Calendar.MONTH) + 1;
//		day = c.get(Calendar.DAY_OF_MONTH);
		//拆分字符串
		String[] sd =sdate.split("-");
		syear = Integer.parseInt(sd[0]);
		smonth = Integer.parseInt(sd[1]);
		sday = Integer.parseInt(sd[2]);
		
		//拆分字符串
		String[] ed =edate.split("-");
		eyear = Integer.parseInt(ed[0]);
		emonth = Integer.parseInt(ed[1]);
		eday = Integer.parseInt(ed[2]);
		
		if(smonth < 10)
			sdate = "" + syear + "-0" + smonth;
		else
			sdate = "" + syear + "-" + smonth;
		if (sday < 10)
			sdate += "-" + "0" + sday;
		else
			sdate += "-" + sday;
		
		if(emonth < 10)
			edate = "" + eyear + "-0" + emonth;
		else
			edate = "" + eyear + "-" + emonth;
		if (eday < 10)
			edate += "-" + "0" + eday;
		else
			edate += "-" + eday;
		
		
		buttonsDate.setText("" + syear + "年" + smonth + "月" + sday + "日");
		buttoneDate.setText("" + eyear + "年" + emonth + "月" + eday + "日");
		
		buttonsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EditPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if(monthOfYear < 10)
                			sdate = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			sdate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			sdate += "-" + "0" + dayOfMonth;
                		else
                			sdate += "-" + dayOfMonth;
//                        date = year + "-" + (int)(monthOfYear + 1) + "-" + dayOfMonth;
                        buttonsDate.setText("" + year + "年" + (int)(monthOfYear + 1) + "月" + dayOfMonth + "日");
//                        Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
                    }
                }, syear,smonth,sday);
                dialog.show();
            }
        });


		buttoneDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EditPlanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        if(monthOfYear < 10)
                			edate = "" + year + "-0" + (int)(monthOfYear + 1);
                		else
                			edate = "" + year + "-" + monthOfYear;
                		if (dayOfMonth < 10)
                			edate += "-" + "0" + dayOfMonth;
                		else
                			edate += "-" + dayOfMonth;
//                        date = year + "-" + (int)(monthOfYear + 1) + "-" + dayOfMonth;
                        buttoneDate.setText("" + year + "年" + (int)(monthOfYear + 1) + "月" + dayOfMonth + "日");
//                        Toast.makeText(CalendarAddActivity.this, date, Toast.LENGTH_LONG).show();
                    }
                }, eyear,emonth,eday);
                dialog.show();
            }
        });

		
           
	}
	
	public void initTime() {
		//拆分字符串
		String[] st =stime.split(":");
		shour = Integer.parseInt(st[0]);
		sminute = Integer.parseInt(st[1]);
		ssecond = Integer.parseInt(st[2]);
		
		String[] et =etime.split(":");
		ehour = Integer.parseInt(et[0]);
		eminute = Integer.parseInt(et[1]);
		esecond = Integer.parseInt(et[2]);
		
		//格式化时间
		if(shour < 10)
			stime = "0" + shour + ":";
		else
			stime = "" + shour + ":";
		if(sminute < 10)
			stime += "0"+ sminute + ":";
		else
			stime += sminute + ":";
		if(ssecond < 10)
			stime += "0" + ssecond;
		else
			stime += ssecond;
		
		if(ehour < 10)
			etime = "0" + ehour + ":";
		else
			etime = "" + ehour + ":";
		if(eminute < 10)
			etime += "0"+ eminute + ":";
		else
			etime += eminute + ":";
		if(esecond < 10)
			etime += "0" + esecond;
		else
			etime += esecond;
		
		//在按钮上显示时间
		if(sminute == 0)
			buttonsTime.setText("" + shour + ":" + sminute+"0");
		else
			buttonsTime.setText("" + shour + ":" + sminute);
        
		if(eminute == 0)
			buttoneTime.setText("" + ehour + ":" + eminute+"0");
		else
			buttoneTime.setText("" + ehour + ":" + eminute);
        
        //为按钮绑定监听器
		buttonsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(EditPlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//                    	c.set(hourOfDay, minute);
                    	if(hourOfDay < 10)
                    		stime = "0" + hourOfDay + ":";
                		else
                			stime = "" + hourOfDay + ":";
                		if(minute < 10)
                			stime += "0"+ minute + ":";
                		else
                			stime += minute + ":";
                		stime += "00";
//                    	time = hourOfDay + ":" + minute + ":" + 00;
                    	buttonsTime.setText("" + hourOfDay + ":" + minute);
//                      Toast.makeText(CalendarAddActivity.this, time, Toast.LENGTH_LONG).show();
        			}
                }, shour,sminute,true);
                dialog.show();
            }
        });
		
	      
	    buttoneTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	TimePickerDialog dialog = new TimePickerDialog(EditPlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        				// TODO Auto-generated method stub
//                    	c.set(hourOfDay, minute);
                    	c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    	c.set(Calendar.MINUTE, minute);
                    	if(hourOfDay < 10)
                    		etime = "0" + hourOfDay + ":";
                		else
                			etime = "" + hourOfDay + ":";
                		if(minute < 10)
                			etime += "0"+ minute + ":";
                		else
                			etime += minute + ":";
                		etime += "00";
//                    	time = hourOfDay + ":" + minute + ":" + 00;
                    	buttoneTime.setText("" + hourOfDay + ":" + minute);
//                      Toast.makeText(CalendarAddActivity.this, time, Toast.LENGTH_LONG).show();
        			}
                }, ehour,eminute,true);
                dialog.show();
            }
        });
	
	
	}
}
