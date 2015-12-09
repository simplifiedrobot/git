package activity;

import utils.mToast;
import view.settingItemView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.R;

public class activity_step2 extends Activity {
    private String simSerialNum;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_step2);
    	
    	final settingItemView  set_v=(settingItemView) findViewById(R.id.siv_update);
    	final SharedPreferences mPref=getSharedPreferences("config", MODE_PRIVATE);
        simSerialNum = mPref.getString("simSerialNum", "");
        if(TextUtils.isEmpty(simSerialNum)){
        	set_v.setChecked(false);
        }else{
        	set_v.setChecked(true);
        }
    	   set_v.setOnClickListener(new OnClickListener() {  
			@Override
			public void onClick(View v) {
				boolean check = set_v.isChecked();
				if(check){
					set_v.setChecked(false);
					mPref.edit().remove("simSerialNum").commit();//删除sms信息
				}else{
					set_v.setChecked(true);
					TelephonyManager phone=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
					simSerialNum = phone.getSimSerialNumber();
					mPref.edit().putString("simSerialNum", simSerialNum).commit();//保存sim序列号
				}
			}
		});
    }
    public void next(View v){
    	startActivity(new Intent(this,activity_step3.class));
    	finish();
    	//两个界面切换的动画
    	overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }
    public void previous(View v){
    	startActivity(new Intent(this,activity_step1.class));
    	finish();
    	//两个界面切换的动画
    	overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }
}
