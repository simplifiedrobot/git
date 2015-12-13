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
		//�ҵ���ǰ������
		if(siv){
			sivUpdate.setChecked(true);
		}else{
			sivUpdate.setChecked(false);
		}
		//�Ե��������Ӧ
		sivUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	        //�жϵ�ǰ�Ĺ�ѡ״̬ ,��ǰҪ�ǹ�ѡ�����Ϊ����ѡ
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
					System.out.println("����ɹ�");
					if(siv_address.isChecked()){
						System.out.println("��ֹ����");
						siv_address.setChecked(false);
						stopService(new Intent(settingActivity.this, addressService.class));//ֹͣ�����ط���
					}else{
						System.out.println("��������");
						siv_address.setChecked(true);
						startService(new Intent(settingActivity.this, addressService.class));//���������ط���
					}
					
				}
	
	    	});
}
}