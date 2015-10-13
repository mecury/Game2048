package com.mecury.game2048;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView cvscore;
	private TextView bestscore;
	private TextView textView;
	public Button button;
	private static MainActivity MainActivity = null;
	private int score = 0;
	
	public MainActivity() {
		MainActivity = this;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cvscore = (TextView) findViewById(R.id.cvscore);
		bestscore = (TextView)findViewById(R.id.bestscore);
		textView = (TextView) findViewById(R.id.textview);
		button = (Button) findViewById(R.id.button);
		
		bestscore.setText(load());
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog();
			}
		});
	}
	
	public void clearScore(){
		score = 0;
		showScore(score);
	}
	public void addScore(int a){
		score+=a;
		showScore(score);
	}
	public void showScore(int score){
		cvscore.setText(score+"");
	}
	public int getcvscore(){
		return Integer.parseInt(cvscore.getText().toString());
	}
	public int getbestscore(){
		return Integer.parseInt(bestscore.getText().toString());
	}
	public void showBestScore(int best){
		bestscore.setText(best+"");
	}
	public static MainActivity getMainActivity(){
		return MainActivity;
	}
	
	//点击重新开始弹出的对话框
	private void dialog(){
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示：");
		builder.setMessage("确定重新开始吗？");
		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent();
				intent.setClass(MainActivity, MainActivity.class);
				startActivity(intent);
			}
		});
		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

//	//使用文件存储bestscore
		public void save(String data){
			FileOutputStream out = null;
			BufferedWriter writer = null;
			try {
				out = openFileOutput("data",Context.MODE_PRIVATE);
				writer=new BufferedWriter(new OutputStreamWriter(out));
				writer.write(data);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if (writer != null) {
						writer.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	//读取存储的数据
		public  String load() {
			FileInputStream in = null;
			BufferedReader reader = null;
			StringBuilder content = new StringBuilder();
			try {
				in = openFileInput("data");
				reader = new BufferedReader(new InputStreamReader(in));
				String line = "";
				while((line = reader.readLine())!=null){
					content.append(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				
				if (reader != null) {
					
					try {
					reader.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					  }
				}
				
			}
			return content.toString();
		}
}
