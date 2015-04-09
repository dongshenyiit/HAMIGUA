package com.hamigua.bean;

import android.graphics.drawable.Drawable;

public class AppInfo {
    /**
     * ͼ��
     */
	private Drawable mDrawable = null;
	/**
	 * Ӧ�ó��������
	 */
	 String appName = null;
	/**
	 * Ӧ�ó���İ���
	 */
	private String packageName = null;
	/**
	 * Ӧ�ó���Ĵ�С
	 */
	private long apkSize;
	
	/**
	 * Ӧ�ó����·��
	 */
	private String apkPath = null;
	/**
	 * ��װ������
	 */
	private boolean isInRom = false;
	/**
	 * �ж���ϵͳapp�����û�app
	 */
	private boolean isUserApp = false;
	public Drawable getmDrawable() {
		return mDrawable;
	}
	public void setmDrawable(Drawable mDrawable) {
		this.mDrawable = mDrawable;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public long getApkSize() {
		return apkSize;
	}
	public void setApkSize(long apkSize) {
		this.apkSize = apkSize;
	}
	public String getApkPath() {
		return apkPath;
	}
	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}
	public boolean isInRom() {
		return isInRom;
	}
	public void setInRom(boolean isInRom) {
		this.isInRom = isInRom;
	}
	public boolean isUserApp() {
		return isUserApp;
	}
	public void setUserApp(boolean isUserApp) {
		this.isUserApp = isUserApp;
	}
	@Override
	public String toString() {
		return "AppInfo [appName=" + appName
				+ ", packageName=" + packageName + ", apkSize=" + apkSize
				+ ", apkPath=" + apkPath + ", isInRom=" + isInRom
				+ ", isUserApp=" + isUserApp + "]";
	}
	
	
	
}
