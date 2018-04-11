package com.tianren.methane.jniutils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.apache.http.conn.ConnectTimeoutException;

import android.util.Log;

public class DataDownLoader {
	private static final String TAG="DataDownLoader";
	
	public static String DownLoad(String urldir) throws Exception  {
		Log.i(TAG, urldir);
		BufferedReader buffer=null;
		StringBuffer sbuffer = new StringBuffer("");
		String line = "";
		URL url = new URL(urldir);	
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();	  
		try {
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(15000);
			conn.connect();		
			buffer = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); 
		} catch (SocketTimeoutException e) {
			Log.e(TAG, "SocketTimeoutException");
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			Log.e(TAG, "ConnectTimeoutException");
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, "IOException");
			e.printStackTrace();
		}
		
		if (buffer != null) {
			try {
				while((line=buffer.readLine())!=null) {
					sbuffer.append(line);
				}   
			} catch (IOException e) {
		          e.printStackTrace();
		    }finally{
		    	try {
		    		buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
			    }
			}
		}
			
		Log.d(TAG, "http result = " + sbuffer.toString());
		return sbuffer.toString();						
	}	
}
