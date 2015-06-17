package com.bzdnmc.postwall_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Contacts.Groups;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.SimpleExpandableListAdapter;

import com.example.photowallfallsdemo.ImageDetailsActivity;

public class postDetails extends Activity {
	private String TITLE = "";
	private String AUTHOR = "";
	private String TIME = "";
	private String CONTENT = "";
	
	private List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
	private List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();

	private Bitmap bitmap;
	private String imagePath;
	SimpleExpandableListAdapter adapter;
	
	Handler postHandler = new Handler() {  
        public void handleMessage(Message msg) {   
             switch (msg.what) {   
                  case 12:
                	    adapter.notifyDataSetChanged();
              			break;   
             }   
             super.handleMessage(msg);   
        }   
   };
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.post_details);
		gruops = new ArrayList<Map<String, String>>();
		childs = new ArrayList<List<Map<String, String>>>();
		final String id = getIntent().getStringExtra("id");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Socket socket;
					socket = new Socket(netThread.ip, 9999);
					String result = "";
					PrintWriter out = new PrintWriter(socket.getOutputStream()); // 输出到服务端
					BufferedReader dis = null;
					out.println("getInfo");
					out.flush();
					out.println(id);
					out.flush();
					try {
						dis = new BufferedReader(new InputStreamReader(socket
								.getInputStream()));
						TITLE = dis.readLine();
						AUTHOR = dis.readLine();
						TIME = dis.readLine();
						CONTENT = dis.readLine();
						setData();
						
						Message msg = postHandler.obtainMessage();
						msg.what = 12;
						postHandler.sendMessage(msg);
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
		
		imagePath = getIntent().getStringExtra("image_path");
		bitmap = BitmapFactory.decodeFile(imagePath);
		ImageButton imagebtn = (ImageButton) findViewById(R.id.imageButton);
		imagebtn.setImageBitmap(bitmap);
		imagebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(postDetails.this,
						ImageDetailsActivity.class);
				intent.putExtra("image_path", imagePath);
				startActivity(intent);
			}
		});
		setData();
		ExpandableListView mlistView = (ExpandableListView) findViewById(R.id.expandableListView);
		adapter = new SimpleExpandableListAdapter(
				this, gruops, R.layout.expandablelistview_groups,
				new String[] { "group" }, new int[] { R.id.textGroup }, childs,
				R.layout.expandablelistview_child, new String[] { "child" },
				new int[] { R.id.textChild });
		mlistView.setAdapter(adapter);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 记得将Bitmap对象回收掉
		if (bitmap != null) {
			bitmap.recycle();
		}
	}

	public void setData() {
		gruops.clear();
		childs.clear();
		Map<String, String> title_1 = new HashMap<String, String>();
		Map<String, String> title_2 = new HashMap<String, String>();
		Map<String, String> title_3 = new HashMap<String, String>();
		Map<String, String> title_4 = new HashMap<String, String>();

		title_1.put("group", "海报内容");
		title_2.put("group", "作者信息");
		title_3.put("group", "外部链接");
		title_4.put("group", "备注");

		// 创建一级条目容器
		

		gruops.add(title_1);
		gruops.add(title_2);
		gruops.add(title_3);
		gruops.add(title_4);

		// 创建二级条目内容

		// 内容一
		Map<String, String> content_1 = new HashMap<String, String>();
		Map<String, String> content_2 = new HashMap<String, String>();
		Map<String, String> content_3 = new HashMap<String, String>();
		
		content_1.put("child", "海报标题: "+ TITLE);
		content_2.put("child", "海报简介: "+ CONTENT);
		content_3.put("child", "发布时间: "+ TIME);

		List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
		childs_1.add(content_1);
		childs_1.add(content_2);
		childs_1.add(content_3);
		
		// 内容二
		Map<String, String> content_4 = new HashMap<String, String>();
		Map<String, String> content_5 = new HashMap<String, String>();

		content_4.put("child", "作者id: " + AUTHOR);
		content_5.put("child", "认证信息:");
		
		List<Map<String, String>> childs_2 = new ArrayList<Map<String, String>>();
		childs_2.add(content_4);
		childs_2.add(content_5);
		
		Map<String, String> content_6 = new HashMap<String, String>();
		content_6.put("child", "URL: ");
		
		List<Map<String, String>> childs_3 = new ArrayList<Map<String, String>>();
		childs_3.add(content_6);

		Map<String, String> content_7 = new HashMap<String, String>();
		content_7.put("child", "备注: ");
		
		List<Map<String, String>> childs_4 = new ArrayList<Map<String, String>>();
		childs_4.add(content_7);
		
		// 存放两个内容, 以便显示在列表中
		
		childs.add(childs_1);
		childs.add(childs_2);
		childs.add(childs_3);
		childs.add(childs_4);
	}

}
