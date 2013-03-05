package com.geredking.htpcremote.xbmc.commands;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.xbmc.XbmcCommand;

public class InfoCommand extends XbmcCommand
{
	@Override
	public void run(HtpcServer server)
	{
		// 0xF049 corresponds to XBMC's "i" keyboard mapping
		// ref: http://groups.google.com/group/commandfusion/browse_thread/thread/e07d37e903cbe486
		command(server, "SendKey(0xF049)");
	}
}
