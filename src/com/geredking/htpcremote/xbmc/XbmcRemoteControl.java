package com.geredking.htpcremote.xbmc;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.commands.WakeOnLanPowerCommand;
import com.geredking.htpcremote.remotes.RemoteButton;
import com.geredking.htpcremote.remotes.RemoteControl;
import com.geredking.htpcremote.xbmc.commands.*;

import android.content.Context;
import android.util.Log;

public class XbmcRemoteControl extends RemoteControl
{
	public XbmcRemoteControl(HtpcServer server, Context context)
	{
		super(server, context);
		
		Log.d("XbmcRemoteControl", "Register commands for XBMC remote control");
		
		registerCommand(RemoteButton.POWER, new WakeOnLanPowerCommand());
		registerCommand(RemoteButton.HOME, new HomeCommand());
		registerCommand(RemoteButton.TOGGLEFULLSCREEN, new ToggleFullscreenCommand());
		registerCommand(RemoteButton.MENU, new MenuCommand());
		registerCommand(RemoteButton.INFO, new InfoCommand());
		registerCommand(RemoteButton.OSD, new OsdCommand());
		registerCommand(RemoteButton.BACK, new BackCommand());
		registerCommand(RemoteButton.OK, new OkCommand());
		registerCommand(RemoteButton.PLAY, new PlayCommand());
		registerCommand(RemoteButton.STOP, new StopCommand());
		registerCommand(RemoteButton.FASTFORWARD, new FastforwardCommand());
		registerCommand(RemoteButton.REWIND, new RewindCommand());
		registerCommand(RemoteButton.SKIP_NEXT, new SkipNextCommand());
		registerCommand(RemoteButton.SKIP_PREVIOUS, new SkipPreviousCommand());
		registerCommand(RemoteButton.DPAD_UP, new DpadUpCommand());
		registerCommand(RemoteButton.DPAD_DOWN, new DpadDownCommand());
		registerCommand(RemoteButton.DPAD_LEFT, new DpadLeftCommand());
		registerCommand(RemoteButton.DPAD_RIGHT, new DpadRightCommand());
	}

}
