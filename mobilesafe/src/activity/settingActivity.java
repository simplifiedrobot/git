package activity;



import service.addressService;
import utils.ServiceUtils;
import view.settingItemView;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.R;

public class settingActivity extends Activity {
	private settingItemView siv_address;
	private SharedPreferences mPref;
	private settingItemView sivUpdate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mPref= getSharedPreferences("config", MODE_PRIVATE);
		initUpdate() ;
		initAddress();
	}
	
	private void initUpdate() {
		sivUpdate = (settingItemView) findViewById(R.id.siv_update);
		boolean siv=mPref.getBoolean("auto_update", true);
		//找到以前的设置
		if(siv){
			sivUpdate.setChecked(true);
		}else{
			sivUpdate.setChecked(false);
		}
		//对点击产生反应
		sivUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	        //判断当前的勾选状态 ,当前要是勾选，则变为不勾选
				if(sivUpdate.isChecked()){
					sivUpdate.setChecked(false);
					mPref.edit().putBoolean("auto_update", false).commit();
				}else{
					sivUpdate.setChecked(true);
					mPref.edit().putBoolean("auto_update", true).commit();
				}
			}
		});
		
	}
	private void initAddress() {
		
		 siv_address = (settingItemView) findViewById(R.id.siv_address);
		final boolean serviceRunning=ServiceUtils.isServiceRunning(settingActivity
				.this, "com.example.mobilesafe.service.addressService");
		if(serviceRunning){
			siv_address.setChecked(true);
		}else{
			siv_address.setChecked(false);
		}
	    	siv_address.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("点击成功");
					if(siv_address.isChecked()){
						System.out.println("终止服务");
						siv_address.setChecked(false);
						stopService(new Intent(settingActivity.this, addressService.class));//停止归属地服务
					}else{
						System.out.println("开启服务");
						siv_address.setChecked(true);
						startService(new Intent(settingActivity.this, addressService.class));//开启归属地服务
					}
					
				}
	
	    	});
}
}