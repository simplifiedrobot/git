package activity;

import view.step4_setting;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class activity_lost extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		final SharedPreferences mPref=getSharedPreferences("config", MODE_PRIVATE);
        boolean  step4_check=mPref.getBoolean("step4_check", false);
        if(step4_check){
        	setContentView(R.layout.activity_lost);
        }else{
        	startActivity(new Intent(this, activity_step1.class));
        	finish();
        }
	}
}
