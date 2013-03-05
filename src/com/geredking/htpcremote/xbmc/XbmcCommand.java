package com.geredking.htpcremote.xbmc;

import android.net.Uri;
import android.util.Log;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.HtpcServerConfig;
import com.geredking.htpcremote.commands.HtpcServerCommand;

public abstract class XbmcCommand extends HtpcServerCommand
{
	protected void command(HtpcServer server, String command)
	{
		HtpcServerConfig config = server.getConfig();
		
		String path = String.format(config.getResource(), Uri.encode(command));
		
		try
		{
			if (config.isAuthRequired())
				server.getClient().sendHttpGet(config.getHost(), config.getPort(), path, null, config.getUsername(), config.getPassword());
			else
				server.getClient().sendHttpGet(config.getHost(), config.getPort(), path);
		}
		catch (Exception e)
		{
			Log.e("XbmcCommand", String.format("Exception occurred during command: %s", command), e);
		}
	}
}
