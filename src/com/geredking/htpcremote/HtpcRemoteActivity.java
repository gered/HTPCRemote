package com.geredking.htpcremote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HtpcRemoteActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remote_control);
	}
	
	public void onRemoteButtonClick(View v)
	{
		if (v.getTag() != null)
		{
			Intent intent = new Intent(this, HtpcRemoteService.class);
			intent.setAction(v.getTag().toString());
			intent.addCategory(HtpcRemoteService.INTENT_CATEGORY_BUTTON_PRESS);
			startService(intent);
		}
	}
}