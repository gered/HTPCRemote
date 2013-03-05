package com.geredking.htpcremote;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public final class NetworkUtils
{
	public static String getMacAddressOf(String host) throws Exception
	{
		// try to see if the host with the IP is accessible. this also puts the IP
		// in /proc/net/arp so we can read it's MAC address from there
		// TODO: is this the best way to accomplish this?
		InetAddress ipAddr = InetAddress.getByName(host);
		boolean reachable = isReachable(ipAddr);
		if (!reachable)
			throw new Exception("Unable to reach host.");
		
		// try to find the matching MAC address in /proc/net/arp
		BufferedReader reader = null;
		String ip = ipAddr.getHostAddress();
		try
		{
			reader = new BufferedReader(new FileReader("/proc/net/arp"));
		}
		catch (Exception e)
		{
			throw new Exception("Error accessing ARP cache.", e);
		}

		try
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] parts = line.split(" +");
				if (parts != null && parts.length >= 4 && ip.equals(parts[0]))
				{
					String macAddr = parts[3];
					if (macAddr.matches("..:..:..:..:..:.."))
						return macAddr;
				}
			}
		}
		catch (Exception e)
		{
			throw new Exception("Error parsing ARP cache information.", e);
		}
		finally
		{
			reader.close();
		}
		
		return null;
	}

	public static boolean areOnSameNetwork(InetAddress addr1, InetAddress addr2, int cidr) throws Exception
	{
		byte[] addr1Bytes = addr1.getAddress();
		byte[] addr2Bytes = addr2.getAddress();
		byte[] subnetMaskBytes = getSubnetMaskBytes(cidr);
		
		for (int i = 0; i < addr1Bytes.length; ++i)
		{
			if ((addr1Bytes[i] & subnetMaskBytes[i]) != (addr2Bytes[i] & subnetMaskBytes[i]))
				return false;
		}
		
		return true;
	}
	
	public static InetAddress getBroadcastAddress(InetAddress addr, int cidr) throws Exception
	{
		byte[] addrBytes = addr.getAddress();
		byte[] subnetMaskBytes = getSubnetMaskBytes(cidr);
		
		byte[] broadcastBytes = new byte[addrBytes.length];
		
		for (int i = 0; i < addrBytes.length; ++i)
			broadcastBytes[i] = (byte)(addrBytes[i] | ~subnetMaskBytes[i]);
		
		return InetAddress.getByAddress(broadcastBytes);
	}
	
	public static InetAddress getSubnetMask(int cidr) throws Exception
	{
		byte[] maskBytes = getSubnetMaskBytes(cidr);
		return InetAddress.getByAddress(maskBytes);
	}
	
	private static byte[] getSubnetMaskBytes(int cidr)
	{
		int mask = 0xffffffff << (32 - cidr);
		return new byte[] {
			(byte)(mask >> 24),
			(byte)(mask >> 16 & 0xff),
			(byte)(mask >> 8 & 0xff),
			(byte)(mask & 0xff)
		};
	}
	
	public static boolean isReachable(InetAddress addr)
	{
		String pingCommand = String.format("ping -c 4 %s", addr.getHostAddress());
		
		try
		{
			Process proc = Runtime.getRuntime().exec(pingCommand);
			proc.waitFor();
			int result = proc.exitValue();
			if (result == 0)
				return true;
			else
				return false;
			
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	public static boolean isPortAvailable(InetAddress addr, int port)
	{
		Socket socket = null;
		boolean reachable;
		try
		{
			socket = new Socket(addr, port);
			reachable = true;
		}
		catch (IOException e)
		{
			reachable = false;
		}
		finally
		{
			if (socket != null)
			{
				try
				{
					socket.close();
				}
				catch (IOException e)
				{
				}
			}
		}
		
		return reachable;
	}
}
