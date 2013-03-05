package com.geredking.htpcremote.xbmc.commands;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.xbmc.XbmcCommand;

public class OsdCommand extends XbmcCommand
{
	@Override
	public void run(HtpcServer server)
	{
		// 0xF04D corresponds to XBMC's "m" keyboard mapping
		// ref: http://groups.google.com/group/commandfusion/browse_thread/thread/e07d37e903cbe486
		command(server, "SendKey(0xF04D)");
	}
}
