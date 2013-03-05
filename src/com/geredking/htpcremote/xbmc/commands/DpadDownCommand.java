package com.geredking.htpcremote.xbmc.commands;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.xbmc.XbmcCommand;

public class DpadDownCommand extends XbmcCommand
{
	@Override
	public void run(HtpcServer server)
	{
		command(server, "SendKey(271)");
	}
}
