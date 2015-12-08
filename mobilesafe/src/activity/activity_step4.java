package activity;


import view.step4_setting;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;

import com.example.mobilesafe.R;

public class activity_step4 extends Activity {
    private CheckBox cb;
	private step4_setting s4;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_step4);
    	s4 = (step4_setting) findViewById(R.id.step4_setting);
    	s4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean check=s4.isChecked();
		    	s4.setChecked(check);
			}
		});
    	
    }
    public void next(View v){
    	startActivity(new Intent(this,activity_lost.class));
    	finish();
    	//两个界面切换的动画
    	overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }
    public void previous(View v){
    	startActivity(new Intent(this,activity_step3.class));
    	finish();
    	//两个界面切换的动画
    	overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }
}
