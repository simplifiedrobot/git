package activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mobilesafe.R;

public class activity_step1 extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.activity_step1);
        }
        public void next(View v){
        	startActivity(new Intent(this,activity_step2.class));
        	finish();
        	//���������л��Ķ���
        	overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
        }
}
