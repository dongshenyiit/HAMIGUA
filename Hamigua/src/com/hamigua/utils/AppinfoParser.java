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
		// 获取到安装包的管理者
		pm = context.getPackageManager();
		// 获取到安装包的所有基本程序的信息
//		List<PackageInfo> infos = pm.getInstalledPackages(0);

		List<AppInfo> lists = new ArrayList<AppInfo>();

		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);  
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);  
		List<ResolveInfo> infos = pm.queryIntentActivities(mainIntent, 0);  
		for (ResolveInfo info : infos) {
			AppInfo appInfo = new AppInfo();
			// 获取到app的包名
//			if((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)<=0){  
			String packageName = info.activityInfo.packageName;

			
			appInfo.setPackageName(packageName);
			// 获取到icon图标
			Drawable icon = info.activityInfo.loadIcon(pm);

			appInfo.setmDrawable(icon);
			// 获取到应用程序的名字
			String appname = (String) info.activityInfo.loadLabel(pm);
	
		
			appInfo.setAppName(appname);

			lists.add(appInfo);
			
			}
		return lists;
		}

	
	private static AppInfo makeAppInfo(PackageManager pManager, ResolveInfo info,Context context) {  
	    //有了ComponentName，就可以保存每个应用程序的启动信息  
	    //下次我们可以直接启动它  
	    ComponentName component = new ComponentName(info.activityInfo.applicationInfo.packageName,  
	            info.activityInfo.name);  
	    //这个是自己定义的一个用来封装我们需要的应用程序的信息  
	    AppInfo app = new AppInfo();  
	      
	    //获取应用的名称  
	    CharSequence appName = info.loadLabel(pManager);  
	    if(appName == null){  
	        //如果没有成功获取到，则用其主Activity的名字作为应用名称  
	        appName = info.activityInfo.name;  
	    }  
	    app.setAppName(appName.toString());  
	      
	    //获取应用程序的图标  
	    Drawable draw = info.activityInfo.loadIcon(pManager);  
	      
	    //应用程序图片可以大小不一，这里需要将他们的size限定在一定的大小  
	  
	      
	    //将启动信息保存在AppInfo中,在我们的应用中启动另一个应用程序，使用FLAG_ACTIVITY_NEW_TASK  
	
	      
	    return app;  
	}  
	

}
