package com.geredking.htpcremote;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetworkClient
{
	private final static String URL_STRING_ENCODING = "UTF-8";
	private final static int CONNECTION_TIMEOUT = 2000;
	private final static int SOCKET_TIMEOUT = 2000;
	
	private Context mContext;
	private ClientConnectionManager mClientManager;
	private HttpClient mHttpClient;
	
	public NetworkClient(Context context)
	{
		mContext = context;
		
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(params, SOCKET_TIMEOUT);
		
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		
		mClientManager = new ThreadSafeClientConnManager(params, registry);
		mHttpClient = new DefaultHttpClient(mClientManager, params);
	}
	
	public void sendHttpGet(String host, int port, String path) throws Exception
	{
		sendHttpGet(host, port, path, null, null, null);
	}
	
	public void sendHttpGet(String host, int port, String path, List<NameValuePair> params) throws Exception
	{
		sendHttpGet(host, port, path, params, null, null);
	}
	
	public void sendHttpGet(String host, int port, String path, List<NameValuePair> params, String username, String password) throws Exception
	{
		HttpHost httpHost = new HttpHost(host, port, "http");
		Uri.Builder b = Uri.parse(httpHost.toURI()).buildUpon();
		b.encodedPath(path);
		if (params != null)
			b.query(URLEncodedUtils.format(params, URL_STRING_ENCODING));
	
		HttpGet get = new HttpGet(b.build().toString());
		if (username != null && password != null)
		{
			Credentials credentials = new UsernamePasswordCredentials(username, password);
			get.addHeader(BasicScheme.authenticate(credentials, URL_STRING_ENCODING, false));
		}
		
		Log.d("NetworkClient.sendHttpGet", String.format("Sending to: %s", get.getURI()));
		HttpResponse response = mHttpClient.execute(get);

		StatusLine responseStatus = response.getStatusLine();
		Log.d("NetworkClient.sendHttpGet", String.format("Response status: %d %s", responseStatus.getStatusCode(), responseStatus.getReasonPhrase()));
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null)
			Log.d("NetworkClient.sendHttpGet", String.format("Response body: %s",responseString));
	}
	
	public void sendHttpPost(String host, int port, String path) throws Exception
	{
		sendHttpPost(host, port, path, null, null, null);
	}
	
	public void sendHttpPost(String host, int port, String path, List<NameValuePair> params) throws Exception
	{
		sendHttpPost(host, port, path, params, null, null);
	}

	public void sendHttpPost(String host, int port, String path, List<NameValuePair> params, String username, String password) throws Exception
	{
		HttpHost httpHost = new HttpHost(host, port, "http");
		Uri.Builder b = Uri.parse(httpHost.toURI()).buildUpon();
		b.encodedPath(path);
		
		HttpPost post = new HttpPost(b.build().toString());
		if (username != null && password != null)
		{
			Credentials credentials = new UsernamePasswordCredentials(username, password);
			post.addHeader(BasicScheme.authenticate(credentials, URL_STRING_ENCODING, false));
		}
		if (params != null)
			post.setEntity(new UrlEncodedFormEntity(params));
		
		Log.d("NetworkClient.sendHttpPost", String.format("Sending to: %s", post.getURI()));
		HttpResponse response = mHttpClient.execute(post);

		StatusLine responseStatus = response.getStatusLine();
		Log.d("NetworkClient.sendHttpPost", String.format("Response status: %d %s", responseStatus.getStatusCode(), responseStatus.getReasonPhrase()));
		
		String responseString = EntityUtils.toString(response.getEntity());
		if (responseString != null)
			Log.d("NetworkClient.sendHttpPost", String.format("Response body: %s", responseString));
	}
	
	public void sendWakeOnLanPacket(InetAddress broadcastAddr, String macAddr) throws Exception
	{
		byte[] macBytes = getMacAddressBytes(macAddr);
		byte[] bytes = new byte[6 + 16 * macBytes.length];
		for (int i = 0; i < 6; i++)
			bytes[i] = (byte)0xff;

		for (int i = 6; i < bytes.length; i += macBytes.length)
			System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
		
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, broadcastAddr, 9);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		socket.close();		
	}
	
	public boolean isWifiEnabled()
	{
		WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
		return wifiManager.isWifiEnabled();
	}

	private InterfaceAddress getWifiAddress() throws Exception
	{
		WifiManager wifiManager = (WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);
		if (!wifiManager.isWifiEnabled())
			return null;

		int ip = wifiManager.getConnectionInfo().getIpAddress();
		InetAddress wifiIp = InetAddress.getByName(String.format("%d.%d.%d.%d", (ip & 0xff), (ip >> 8 & 0xff), (ip >> 16 & 0xff), (ip >> 24 & 0xff)));
		
		NetworkInterface wifiInterface = NetworkInterface.getByInetAddress(wifiIp);
		List<InterfaceAddress> addresses = wifiInterface.getInterfaceAddresses();
		for (InterfaceAddress a : addresses)
		{
			if (a.getAddress().equals(wifiIp))
				return a;
		}
		
		return null;
	}

	private byte[] getMacAddressBytes(String macAddr) throws IllegalArgumentException
	{
		byte[] bytes = new byte[6];
		String[] hex = macAddr.split("(\\:|\\-)");
		
		if (hex.length != 6)
			throw new IllegalArgumentException("Invalid MAC address.");

		try
		{
			for (int i = 0; i < 6; ++i)
				bytes[i] = (byte)Integer.parseInt(hex[i], 16);
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException("Invalid hex digit in MAC address.");
		}
		
		return bytes;
	}
}
