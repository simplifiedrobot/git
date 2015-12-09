package activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesafe.R;

public class activity_lost extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final SharedPreferences mPref=getSharedPreferences("config", MODE_PRIVATE);
        boolean  configed=mPref.getBoolean("configed", false);
        String safeNum=mPref.getString("safeNum", "");
       
        if(configed){
        	setContentView(R.layout.activity_lost);
        	TextView tv_phone=(TextView) findViewById(R.id.tv_phone);
        	ImageView iv_lock=(ImageView) findViewById(R.id.iv_lock);
        	tv_phone.setText(safeNum);
        	boolean protect=mPref.getBoolean("protect", false);
        	if(protect){
        		iv_lock.setBackgroundResource(R.drawable.lock);
        	}else{
        		iv_lock.setBackgroundResource(R.drawable.unlock);
        	}
        }else{
        	startActivity(new Intent(this, activity_step1.class));
        	finish();
        }
	}
	public void reEnter(View v){
		startActivity(new Intent(this, activity_step1.class));
    	finish();
	}
}
