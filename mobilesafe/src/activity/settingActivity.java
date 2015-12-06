package activity;



import view.settingItemView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.R;

public class settingActivity extends Activity {
         
	private SharedPreferences mPref;
	private settingItemView sivUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mPref= getSharedPreferences("config", MODE_PRIVATE);
		
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
}
