package com.hamigua.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceIsRunning {

	public static boolean isRunning(Context context,String classname){
		//��ȡ��ActivityManager
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		//��ȡ���ں�̨���еķ���
		//������ʾ�����ȡ�����ٷ���
		List<RunningServiceInfo> runningServices = am.getRunningServices(100);
		//�����������еķ�����ոմ������ķ���Աȡ������ǲ���ͬһ������
		for(RunningServiceInfo info : runningServices){
			String className = info.service.getClassName();
			if(className.equals(classname)){
				return true;
			}
		}
		return false;
	}
}
