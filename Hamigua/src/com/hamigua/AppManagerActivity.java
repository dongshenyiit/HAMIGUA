package com.hamigua;

import java.util.ArrayList;
import java.util.List;

import com.hamigua.bean.AppInfo;
import com.hamigua.dao.ApplockDao;
import com.hamigua.utils.AppinfoParser;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppManagerActivity extends Activity implements OnClickListener {
	private TextView tv_unlock;
	private TextView tv_locked;
	
	private LinearLayout ll_unlock;
	private LinearLayout ll_locked;
	
	//����listviewδ�������Ѽ���
	private ListView lv_unlock;
	private ListView lv_locked;
	
	private TextView tv_locked_count;
	private TextView tv_unlock_count;
	
	
	/**
	 * ����ֻ���������е�Ӧ�ó�����Ϣ
	 */
	private List<AppInfo> allAppInfos;
	
	/**
	 * δ����Ӧ�ó�����Ϣ
	 */
	private List<AppInfo> unlockAppInfos;
	
	/**
	 * �Ѽ���Ӧ�ó�����Ϣ
	 */
	private List<AppInfo> lockedAppInfos;
 	
	private ApplockDao dao;
	private InnerAdapter unlockAdapter;
	private InnerAdapter lockedAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_applock);
		dao = new ApplockDao(this);
		tv_unlock = (TextView) findViewById(R.id.tv_unlock);
		tv_locked = (TextView) findViewById(R.id.tv_locked);
		ll_unlock = (LinearLayout) findViewById(R.id.ll_unlock);
		ll_locked = (LinearLayout) findViewById(R.id.ll_locked);
		lv_unlock = (ListView) findViewById(R.id.lv_unlock);
		lv_locked = (ListView) findViewById(R.id.lv_locked);
		tv_locked_count = (TextView) findViewById(R.id.tv_locked_count);
		tv_unlock_count = (TextView) findViewById(R.id.tv_unlock_count);
		
		
		//��ȡȫ����Ӧ�ó�����Ϣ��  TODO ���д�����߳�
		allAppInfos = AppinfoParser.getAppInfo(this);
		unlockAppInfos = new ArrayList<AppInfo>();
		lockedAppInfos = new ArrayList<AppInfo>();
		for(AppInfo info: allAppInfos){
			if(dao.find(info.getPackageName())){
				lockedAppInfos.add(info);
			}else{
				unlockAppInfos.add(info);
			}
		}
		
		tv_locked.setOnClickListener(this);
		tv_unlock.setOnClickListener(this);
		unlockAdapter = new InnerAdapter(true);
		lockedAdapter = new InnerAdapter(false);
		
		lv_unlock.setAdapter(unlockAdapter);
		lv_locked.setAdapter(lockedAdapter);
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_unlock:
			tv_locked.setBackgroundResource(R.drawable.tab_right_default);
			tv_unlock.setBackgroundResource(R.drawable.tab_left_pressed);
			ll_unlock.setVisibility(View.VISIBLE);
			ll_locked.setVisibility(View.GONE);
			break;
		case R.id.tv_locked:
			tv_locked.setBackgroundResource(R.drawable.tab_right_pressed);
			tv_unlock.setBackgroundResource(R.drawable.tab_left_default);
			ll_unlock.setVisibility(View.GONE);
			ll_locked.setVisibility(View.VISIBLE);
			break;
		}

	}
	
	private class InnerAdapter extends BaseAdapter{
		/**
		 * ���嵱ǰ�����������ͣ�
		 * true δ����
		 * false �Ѽ���
		 */
		private boolean showUnlock;
		
		public InnerAdapter(boolean showUnlock) {
			this.showUnlock = showUnlock;
		}
		@Override
		public int getCount() {
			if(showUnlock){
				//δ����Ӧ��
				tv_unlock_count.setText("δ����Ӧ�ã�"+unlockAppInfos.size()+"��");
				return unlockAppInfos.size();
			}else{
				//�Ѽ���Ӧ��
				tv_locked_count.setText("�Ѽ���Ӧ�ã�"+lockedAppInfos.size()+"��");
				return lockedAppInfos.size();
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final View view;
			ViewHolder holder;
			if(convertView!=null && convertView instanceof RelativeLayout){
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}else{
				holder = new ViewHolder();
				view = View.inflate(getApplicationContext(), R.layout.item_applock, null);
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_applock_icon);
				holder.iv_change = (ImageView) view.findViewById(R.id.iv_applock_change);
				holder.tv_name = (TextView) view.findViewById(R.id.tv_applock_name);
				view.setTag(holder);
			}
			final AppInfo appinfo;
			if(showUnlock){
				//δ��������
				appinfo = unlockAppInfos.get(position);
				holder.iv_change.setImageResource(R.drawable.list_button_lock_pressed);
				holder.iv_change.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
								Animation.RELATIVE_TO_SELF, 1.0f,
								Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
						ta.setDuration(200);
						view.startAnimation(ta);//֪ͨ������ʼ�����ˡ�
						ta.setAnimationListener(new AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) {
								
							}
							@Override
							public void onAnimationRepeat(Animation animation) {
								
							}
							@Override
							public void onAnimationEnd(Animation animation) {
								//������������뵽���������ݿ�
								dao.addlock(appinfo.getPackageName());
								unlockAppInfos.remove(appinfo);
								lockedAppInfos.add(appinfo);
								//֪ͨ�������
								unlockAdapter.notifyDataSetChanged();
								lockedAdapter.notifyDataSetChanged();
							}
						});					
						
						
					}
				});
			}else{
				appinfo = lockedAppInfos.get(position);
				//�Ѽ�������
				holder.iv_change.setImageResource(R.drawable.list_button_unlock_pressed);
				holder.iv_change.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						TranslateAnimation ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
								Animation.RELATIVE_TO_SELF, -1.0f,
								Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
						ta.setDuration(200);
						view.startAnimation(ta);//֪ͨ������ʼ�����ˡ�
						ta.setAnimationListener(new AnimationListener() {
							
							@Override
							public void onAnimationStart(Animation animation) {
								
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								//����������ӳ��������ݿ�ɾ��
								dao.delete(appinfo.getPackageName());
								lockedAppInfos.remove(appinfo);
								unlockAppInfos.add(appinfo);
								//֪ͨ�������
								unlockAdapter.notifyDataSetChanged();
								lockedAdapter.notifyDataSetChanged();
								
							}
						});
					}
				});
			}
			holder.iv_icon.setImageDrawable(appinfo.getmDrawable());
			holder.tv_name.setText(appinfo.getAppName());
			return view;
		}
		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}
	}
	static class ViewHolder{
		ImageView iv_icon;
		ImageView iv_change;
		TextView tv_name;
	}
	
}
