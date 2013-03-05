package com.geredking.htpcremote;

public class HtpcServer
{
	private HtpcServerConfig mConfig;
	private NetworkClient mClient;
	
	public HtpcServer(HtpcServerConfig config, NetworkClient client)
	{
		mConfig = config;
		mClient = client;
	}
	
	public HtpcServerConfig getConfig()
	{
		return mConfig;
	}
	
	public NetworkClient getClient()
	{
		return mClient;
	}
}
