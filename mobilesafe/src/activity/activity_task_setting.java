package activity;

import service.kill_processService;
import utils.ServiceUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
		  boolean b=	SharedPreferencesUtils.getBoolean(this, "cb_status");
		  
			if(b){
				cb_status.setChecked(true);
			}else {
				cb_status.setChecked(false);
			}
			cb_status.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(!cb_status.isChecked()){
						cb_status.setChecked(true);
						SharedPreferencesUtils.putBoolean(activity_task_setting.this, "cb_status", true);
					}else {
						cb_status.setChecked(false);
						SharedPreferencesUtils.putBoolean(activity_task_setting.this, "cb_status", false);
					}
				}
			});
			//定时清理进程
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
		if(ServiceUtils.isServiceRunning(this,"com.example.mobilesafe.service.kill_processService")){
			cb_status_kill_process.setChecked(true);
		}else{
			cb_status_kill_process.setChecked(false);
		}
	}
	}

