package com.geredking.htpcremote;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class HtpcRemoteService extends IntentService
{
	public static final String SERVICE_NAME = "HtpcRemoteService";
	public static final String INTENT_CATEGORY_BUTTON_PRESS = "com.geredking.htpcremote.INTENT_CATEGORY_BUTTON_PRESS";
	
	private HtpcRemoteApplication mApplication;
	
	public HtpcRemoteService()
	{
		super(SERVICE_NAME);
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		mApplication = (HtpcRemoteApplication)getApplication();
	}
	
	@Override
	protected void onHandleIntent(Intent intent)
	{
		Log.d(SERVICE_NAME, String.format("Handling intent: %s", intent));
		String action = intent.getAction();
		if (action == null)
		{
			Log.d(SERVICE_NAME, "Nothing to handle");
			return;
		}

		if (intent.hasCategory(INTENT_CATEGORY_BUTTON_PRESS))
		{
			Log.d(SERVICE_NAME, String.format("Running RemoteControl command for action \"%s\"", action));
			mApplication.getRemoteControl().doCommand(action);
		}
	}
}
