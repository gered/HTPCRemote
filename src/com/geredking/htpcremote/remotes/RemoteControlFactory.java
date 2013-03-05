package com.geredking.htpcremote.remotes;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.HtpcServerConfig;
import com.geredking.htpcremote.NetworkClient;
import com.geredking.htpcremote.xbmc.XbmcRemoteControl;

import android.content.Context;
import android.util.Log;

public final class RemoteControlFactory
{
	public static RemoteControl create(Context context)
	{
		// TODO: check preferences to see which subclass of RemoteControl to instantiate
		
		HtpcServer server = createServer(getCurrentServerConfig(), context);
		return new XbmcRemoteControl(server, context);
	}
	
	public static HtpcServerConfig getCurrentServerConfig()
	{
		// TODO: deserialize config object from preferences
		HtpcServerConfig config = new HtpcServerConfig();
		
		config.setHost("10.0.0.30");
		config.setPort(8080);
		config.setResource("/xbmcCmds/xbmcHttp?command=%s");
		config.setAuthRequired(true);
		config.setUsername("xbmc");
		config.setPassword("");
		config.setWakeOnLan(true);
		config.setWakeOnLanBroadcast("10.0.0.255");
		config.setWakeOnLanMacAddr("00:01:2E:2B:7C:DE");
		
		Log.d("RemoteControlFactory", String.format("Obtained configuration:\n%s", config.toString()));
		
		return config;
	}
	
	private static HtpcServer createServer(HtpcServerConfig config, Context context)
	{
		NetworkClient client = new NetworkClient(context);
		return new HtpcServer(config, client);
	}
}
