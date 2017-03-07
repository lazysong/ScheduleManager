package com.lazysong.schedulemanagement;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class ExitApplication extends Application {

    private static List<Activity> list = new ArrayList<Activity>();
 
    private static ExitApplication ea;

    private ExitApplication() {

    }

    public static ExitApplication getInstance() {
        if (null == ea) {
            ea = new ExitApplication();
        }
        return ea;
    }

   
    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public static void exit(Context context) {
        for (int i = 0; i < list.size(); i ++) {
            list.get(i).finish();
            Toast.makeText(context, i + "is finished", Toast.LENGTH_SHORT).show();
        }
        System.exit(0);
    }
}