package com.geredking.htpcremote;

import java.util.Formatter;

public class HtpcServerConfig
{
	private String mHost;
	private int mPort;
	private String mResource;
	private boolean mAuthRequired;
	private String mUsername;
	private String mPassword;
	private boolean mWakeOnLanSet;
	private String mWakeOnLanBroadcast;
	private String mWakeOnLanMacAddr;
	
	public String getHost()
	{
		return mHost;
	}
	
	public void setHost(String host)
	{
		mHost = host;
	}
	
	public int getPort()
	{
		return mPort;
	}
	
	public void setPort(int port)
	{
		mPort = port;
	}
	
	public String getResource()
	{
		return mResource;
	}
	
	public void setResource(String resource)
	{
		mResource = resource;
	}
	
	public boolean isAuthRequired()
	{
		return mAuthRequired;
	}
	
	public void setAuthRequired(boolean required)
	{
		mAuthRequired = required;
	}
	
	public String getUsername()
	{
		return mUsername;
	}
	
	public void setUsername(String username)
	{
		mUsername = username;
	}
	
	public String getPassword()
	{
		return mPassword;
	}
	
	public void setPassword(String password)
	{
		mPassword = password;
	}
	
	public boolean isWakeOnLanSet()
	{
		return mWakeOnLanSet;
	}
	
	public void setWakeOnLan(boolean set)
	{
		mWakeOnLanSet = set;
	}
	
	public String getWakeOnLanBroadcast()
	{
		return mWakeOnLanBroadcast;
	}
	
	public void setWakeOnLanBroadcast(String addr)
	{
		mWakeOnLanBroadcast = addr;
	}
	
	public String getWakeOnLanMacAddr()
	{
		return mWakeOnLanMacAddr;
	}
	
	public void setWakeOnLanMacAddr(String addr)
	{
		mWakeOnLanMacAddr = addr;
	}
	
	@Override
	public String toString()
	{
		Formatter f = new Formatter();
		f.format("Host: %s\n", mHost);
		f.format("Port: %d\n", mPort);
		f.format("Resource: %s\n", mResource);
		f.format("Auth Required: %b\n", mAuthRequired);
		f.format("Username: %s\n", mUsername);
		f.format("Password: %s\n", mPassword);
		f.format("Wake On Lan Set: %b\n", mWakeOnLanSet);
		f.format("Wake On Lan Broadcast Address: %s\n", mWakeOnLanBroadcast);
		f.format("Wake On Lan MAC Address: %s\n", mWakeOnLanMacAddr);
		
		return f.toString();
	}
}
