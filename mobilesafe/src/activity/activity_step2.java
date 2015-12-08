package activity;

import utils.mToast;
import view.settingItemView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.R;

public class activity_step2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_step2);
    	
    	final settingItemView  set_v=(settingItemView) findViewById(R.id.siv_update);
    	final SharedPreferences mPref=getSharedPreferences("config", MODE_PRIVATE);
        boolean  step2_check=mPref.getBoolean("step2_check", false);
    		set_v.setChecked(step2_check);
    	   set_v.setOnClickListener(new OnClickListener() {  
			@Override
			public void onClick(View v) {
				boolean check = set_v.isChecked();
					set_v.setChecked(!check);
					mPref.edit().putBoolean("step2_check",(!check)).commit();
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
