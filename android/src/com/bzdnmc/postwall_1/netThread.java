package com.bzdnmc.postwall_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Message;
import android.util.Log;

public class netThread {
	public static String ip = "172.27.23.4";
	public static void getMax() throws Exception {
		
		 new Thread(new Runnable() {
			@Override
			public void run() {
				try {
		
					Socket socket;
					socket = new Socket(ip, 9999);
					String result = "";
					PrintWriter out = new PrintWriter(socket.getOutputStream()); // 输出到服务端
					BufferedReader dis = null;
					out.println("getNum");
					out.flush();
					try {
						dis = new BufferedReader(new InputStreamReader(socket
								.getInputStream()));
						result = dis.readLine();
					} finally {
						if (dis != null)
							;
					}
					out.println("bye");
					out.flush();
					out.close();
					dis.close();
					socket.close();

					int num = Integer.parseInt(result);
					MainActivity.count = num;
					com.example.photowallfallsdemo.Images.imageUrls.clear();
					for (int i = num; i > 0; i--) {
						com.example.photowallfallsdemo.Images.imageUrls.add(String.valueOf(i));
					}
					Message msg = MainActivity.myHandler.obtainMessage();
					msg.what = 11;
					MainActivity.myHandler.sendMessage(msg);
					
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
 
	public static void getList(final String tag) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Socket socket;
					socket = new Socket(ip, 9999);
					String result = "";
					PrintWriter out = new PrintWriter(socket.getOutputStream()); // 输出到服务端
					BufferedReader dis = null;
					out.println("getList");
					out.flush();
					out.println(tag);
					out.flush();
					try {
						dis = new BufferedReader(new InputStreamReader(socket
								.getInputStream()));
						com.example.photowallfallsdemo.Images.imageUrls.clear();
						while ((result = dis.readLine()) != null) {
							com.example.photowallfallsdemo.Images.imageUrls.add(result);
						}
						MainActivity.count = com.example.photowallfallsdemo.Images.imageUrls.size();
						Log.v("条目个数:", String.valueOf(MainActivity.count));
						Message msg = MainActivity.myHandler.obtainMessage();
						msg.what = 11;
						MainActivity.myHandler.sendMessage(msg);
					} finally {
						if (dis != null)
							;
					}
					out.println("bye");
					out.flush();
					out.close();
					dis.close();
					socket.close();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

}
