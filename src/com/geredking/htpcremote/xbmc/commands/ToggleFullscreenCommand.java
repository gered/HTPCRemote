package com.geredking.htpcremote.xbmc.commands;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.xbmc.XbmcCommand;

public class ToggleFullscreenCommand extends XbmcCommand
{
	@Override
	public void run(HtpcServer server)
	{
		// 0xF009 corresponds to XBMC's "tab" keyboard mapping
		// ref: http://groups.google.com/group/commandfusion/browse_thread/thread/e07d37e903cbe486
		command(server, "SendKey(0xF009)");
	}
}
