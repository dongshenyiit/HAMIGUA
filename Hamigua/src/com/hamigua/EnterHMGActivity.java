package com.hamigua;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;

public class EnterHMGActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_hmg);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_hmg, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
