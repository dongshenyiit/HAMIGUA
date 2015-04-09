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
 * ���ӵ�ǰ�û�������Ӧ�ó���������Ӧ�ó�����Ҫ���������Ź��ͱĳ��������û���������
 * 
 * @author Administrator
 * 
 */
public class PassWordService extends Service {
	private ActivityManager am;
	private boolean flag;
	// �����������ݿ�dao
	private ApplockDao dao;
	private InnerReceiver receiver;

	/**
	 * ��ʱֹͣ�����İ�������
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
				// ��ʱֹͣ���Ź�
				flag = false;
			} else if (Intent.ACTION_SCREEN_ON.equals(action)) {
				// �������Ź�
				if (!flag) {
					startDogThread();
				}
			}
		}
	}

	// ������������������ͼ
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
