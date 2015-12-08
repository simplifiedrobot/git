package activity;

import com.example.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activity_step2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_step1);
    }
    public void next(View v){
    	startActivity(new Intent(this,activity_step2.class));
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
