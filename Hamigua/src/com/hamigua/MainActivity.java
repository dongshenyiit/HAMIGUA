package com.hamigua;

import com.hamigua.service.PassWordService;
import com.hamigua.utils.ServiceIsRunning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends Activity implements OnClickListener {

    private Intent intent;
	private Button start_lock;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        RelativeLayout apphide=(RelativeLayout) findViewById(R.id.setapphide);
        RelativeLayout pwdset=(RelativeLayout) findViewById(R.id.pwmanager);
        start_lock=(Button) findViewById(R.id.start_lockservice);
        if(ServiceIsRunning.isRunning(this, "com.hamigua.service.PassWordService"))
        start_lock.setBackgroundResource(R.drawable.common_toggle_on);
        apphide.setOnClickListener(this);
        pwdset.setOnClickListener(this);
        start_lock.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.setapphide:
			intent=new Intent(this,AppManagerActivity.class);
			startActivity(intent);
			break;
		case R.id.pwmanager:
			intent=new Intent(this,PwdSetting.class);
			System.out.println("-----------");
			startActivity(intent);
			break;
		case R.id.start_lockservice:
			intent=new Intent(this,PassWordService.class);
			if(ServiceIsRunning.isRunning(this, "com.hamigua.service.PassWordService")){
				start_lock.setBackgroundResource(R.drawable.common_toggle_off);
				stopService(intent);
			}
			else{
				start_lock.setBackgroundResource(R.drawable.common_toggle_on);
				startService(intent);
			}
		default:
			break;
		}
	}
    
}
