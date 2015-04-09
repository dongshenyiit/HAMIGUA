package com.hamigua.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotifiService extends Service {

	private NotificationManager notifiManager;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		notifiManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
	}
}
