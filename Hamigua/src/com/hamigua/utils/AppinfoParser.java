package com.hamigua.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.hamigua.R;
import com.hamigua.bean.AppInfo;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;



public class AppinfoParser {

	private static PackageManager pm;

	public static List<AppInfo> getAppInfo(Context context) {
		// ��ȡ����װ���Ĺ�����
		pm = context.getPackageManager();
		// ��ȡ����װ�������л����������Ϣ
//		List<PackageInfo> infos = pm.getInstalledPackages(0);

		List<AppInfo> lists = new ArrayList<AppInfo>();

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);  
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
		List<ResolveInfo> infos = pm.queryIntentActivities(mainIntent, 0);  
		for (ResolveInfo info : infos) {
			AppInfo appInfo = new AppInfo();
			// ��ȡ��app�İ���
//			if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)<=0){  
			String packageName = info.activityInfo.packageName;

			
			appInfo.setPackageName(packageName);
			// ��ȡ��iconͼ��
			Drawable icon = info.activityInfo.loadIcon(pm);

			appInfo.setmDrawable(icon);
			// ��ȡ��Ӧ�ó��������
			String appname = (String) info.activityInfo.loadLabel(pm);
	
		
			appInfo.setAppName(appname);

			lists.add(appInfo);
			
			}
		return lists;
		}

	
	private static AppInfo makeAppInfo(PackageManager pManager, ResolveInfo info,Context context) {  
	    //����ComponentName���Ϳ��Ա���ÿ��Ӧ�ó����������Ϣ  
	    //�´����ǿ���ֱ��������  
	    ComponentName component = new ComponentName(info.activityInfo.applicationInfo.packageName,  
	            info.activityInfo.name);  
	    //������Լ������һ��������װ������Ҫ��Ӧ�ó������Ϣ  
	    AppInfo app = new AppInfo();  
	      
	    //��ȡӦ�õ�����  
	    CharSequence appName = info.loadLabel(pManager);  
	    if(appName == null){  
	        //���û�гɹ���ȡ������������Activity��������ΪӦ������  
	        appName = info.activityInfo.name;  
	    }  
	    app.setAppName(appName.toString());  
	      
	    //��ȡӦ�ó����ͼ��  
	    Drawable draw = info.activityInfo.loadIcon(pManager);  
	      
	    //Ӧ�ó���ͼƬ���Դ�С��һ��������Ҫ�����ǵ�size�޶���һ���Ĵ�С  
	  
	      
	    //��������Ϣ������AppInfo��,�����ǵ�Ӧ����������һ��Ӧ�ó���ʹ��FLAG_ACTIVITY_NEW_TASK  
	
	      
	    return app;  
	}  
	

}
