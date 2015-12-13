package activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobilesafe.R;

public class activity_step1 extends BaseSetupActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.activity_step1);
        }

		@Override
		public void showNextPage() {
			startActivity(new Intent(this,activity_step2.class));
        	finish();
        	//两个界面切换的动画
        	overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			
		}
		@Override
		public void showPreviousPage() {
			
		}
	

}
