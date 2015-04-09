package com.hamigua.service;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.hamigua.EnterHMGActivity;
import com.hamigua.dao.ApplockDao;

/**
 * 监视当前用户操作的应用程序，如果这个应用程序需要保护，看门狗就蹦出来，让用户输入密码
 * 
 * @author Administrator
 * 
 */
public class PassWordService extends Service {
	private ActivityManager am;
	private boolean flag;
	// 程序锁的数据库dao
	private ApplockDao dao;
	private InnerReceiver receiver;

	/**
	 * 临时停止保护的包名集合
	 */
	private List<String> tempStopPacknames;

	private List<RunningTaskInfo> taskinfos;
	private String packname;

	private List<String> lockPacknames;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private class InnerReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
		 if (Intent.ACTION_SCREEN_OFF.equals(action)) {
				tempStopPacknames.clear();
				// 临时停止看门狗
				flag = false;
			} else if (Intent.ACTION_SCREEN_ON.equals(action)) {
				// 开启看门狗
				if (!flag) {
					startDogThread();
				}
			}
		}
	}

	// 开启输入密码界面的意图
	private Intent intent;

	@Override
	public void onCreate() {
		
		intent = new Intent(PassWordService.this,
				EnterHMGActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		registerReceiver(receiver, filter);
		super.onCreate();
	}

	private void startDogThread() {
		flag = true;
		new Thread() {
			public void run() {
				while (flag) {
						
							startActivity(intent);
				
					// long endTime = System.currentTimeMillis();
					// long dTime = endTime -startTime;
					// System.out.println("dtime="+dTime);
					try {
						Thread.sleep(30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	@Override
	public void onDestroy() {
		flag = false;
		unregisterReceiver(receiver);
		receiver = null;
		super.onDestroy();
	}
}
