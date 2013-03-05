package com.geredking.htpcremote.remotes;

import java.util.HashMap;
import java.util.Map;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.commands.HtpcServerCommand;

import android.content.Context;
import android.util.Log;

public abstract class RemoteControl
{
	private HtpcServer mServer;
	private Map<String, HtpcServerCommand> mCommandMap;
	
	public RemoteControl(HtpcServer server, Context context)
	{
		mServer = server;
		mCommandMap = new HashMap<String, HtpcServerCommand>();
	}

	public void doCommand(String name)
	{
		final HtpcServerCommand command = mCommandMap.get(name);
		if (command != null)
		{
			Log.d("RemoteControl", String.format("doCommand: %s", name));
			Thread t = new Thread(new Runnable() {
				public void run() {
					command.run(mServer);
				}
			});
			t.start();
		}
		else
			Log.e("RemoteControl", String.format("INVALID doCommand: %s", name));
	}
	
	protected void registerCommand(String name, HtpcServerCommand command)
	{
		mCommandMap.put(name, command);
	}
}
