package com.hamigua.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceIsRunning {

	public static boolean isRunning(Context context,String classname){
		//获取到ActivityManager
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		//获取后在后台运行的服务
		//参数表示你想获取到多少服务
		List<RunningServiceInfo> runningServices = am.getRunningServices(100);
		//迭代出来所有的服务跟刚刚传过来的服务对比。看看是不是同一个服务
		for(RunningServiceInfo info : runningServices){
			String className = info.service.getClassName();
			if(className.equals(classname)){
				return true;
			}
		}
		return false;
	}
}
