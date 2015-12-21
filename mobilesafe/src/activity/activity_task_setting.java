package activity;

import service.kill_processService;
import utils.ServiceUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.mobilesafe.R;

import dao.SharedPreferencesUtils;

public class activity_task_setting extends Activity {

	private CheckBox cb_status;
	private CheckBox cb_status_kill_process;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager_setting);
		cb_status = (CheckBox) findViewById(R.id.cb_status);
		cb_status_kill_process = (CheckBox) findViewById(R.id.cb_status_kill_process);
		intiUI();
	
	}

	private void intiUI( ){
				cb_status.setChecked(SharedPreferencesUtils.getBoolean(this, "cb_status"));
			cb_status.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					SharedPreferencesUtils.putBoolean(activity_task_setting.this, "cb_status", isChecked);
				}
			});
			
			//锁屏清理进程
			final Intent intent=new Intent(activity_task_setting.this,kill_processService.class);
			cb_status_kill_process.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						startService(intent);
					}else{
						stopService(intent);
					}
				}
				
			});
		}

	@Override
	protected void onStart() {
		
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("onstart");
		boolean b=ServiceUtils.isServiceRunning(this,"service.kill_processService");
	  System.out.println(b);
		if(b){
			cb_status_kill_process.setChecked(true);
		}else{
			cb_status_kill_process.setChecked(false);
		}
	}
	 public void addshortcut(View v){
			Intent intent = new Intent();
			intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
			/**
			 * 1  你想干什么事情
			 * 2  叫什么名字
			 * 3  长什么样子
			 */
			Intent dowhtIntent = new Intent();
			//告诉系统哥想去进程管理
			dowhtIntent.setAction("ACTION_TASK");
			//叫什么名字
			intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "进程管理");
			//长什么样子
			intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, 
					BitmapFactory.decodeResource(getResources(), R.drawable.home_taskmanager));
			intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, dowhtIntent);
			sendBroadcast(intent);
	 }
	}

