package com.geredking.htpcremote.xbmc.commands;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.xbmc.XbmcCommand;

public class PlayCommand extends XbmcCommand
{
	@Override
	public void run(HtpcServer server)
	{
		command(server, "Action(12)");
	}
}
