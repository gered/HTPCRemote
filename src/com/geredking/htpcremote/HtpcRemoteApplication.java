package com.geredking.htpcremote;

import com.geredking.htpcremote.remotes.RemoteControl;
import com.geredking.htpcremote.remotes.RemoteControlFactory;

import android.app.Application;
import android.util.Log;

public class HtpcRemoteApplication extends Application
{
	private RemoteControl mRemote = null;
	
	public RemoteControl getRemoteControl()
	{
		if (mRemote == null)
		{
			Log.d("HtpcRemoteApplication", "No existing RemoteControl instance, creating one.");
			mRemote = RemoteControlFactory.create(this);
		}
		
		return mRemote;
	}
	
	public void refreshRemoteControlConfig()
	{
		if (mRemote == null)
			Log.d("HtpcRemoteApplication", "No existing RemoteControl instance. No need to refresh config.");
		else
		{
			Log.d("HtpcRemoteApplication", "Recreating RemoteControl instance to use new configuration.");
			mRemote = RemoteControlFactory.create(this);
		}
	}
}
