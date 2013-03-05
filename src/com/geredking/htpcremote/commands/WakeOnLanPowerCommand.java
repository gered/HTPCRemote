package com.geredking.htpcremote.commands;

import java.net.InetAddress;

import android.util.Log;

import com.geredking.htpcremote.HtpcServer;
import com.geredking.htpcremote.HtpcServerConfig;

public class WakeOnLanPowerCommand extends HtpcServerCommand
{
	@Override
	public void run(HtpcServer server)
	{
		HtpcServerConfig config = server.getConfig();
		
		if (config.isWakeOnLanSet())
		{
			try
			{
				InetAddress broadcastAddr = InetAddress.getByName(config.getWakeOnLanBroadcast());
				String macAddr = config.getWakeOnLanMacAddr();
				server.getClient().sendWakeOnLanPacket(broadcastAddr, macAddr);
			}
			catch (Exception e)
			{
				Log.e("WakeOnLanPowerCommand", e.getMessage(), e);
			}
		}
	}
}
