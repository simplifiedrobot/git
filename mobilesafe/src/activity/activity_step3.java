package activity;


import utils.mToast;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.mobilesafe.R;


public class activity_step3 extends Activity {
    private String safeNum;
	private EditText et_safeNum;
	private SharedPreferences mPref;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	mPref = getSharedPreferences("config", MODE_PRIVATE);
		safeNum = mPref.getString("safeNum", null);
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_step3);
    	et_safeNum = (EditText) findViewById(R.id.safeNum);
    	et_safeNum.setText(safeNum);
    }
    public void chooseContact(View v){
    	Intent intent=new Intent(this,activity_contact.class);
    	startActivityForResult(intent, 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if(requestCode==RESULT_OK){
    		 String phone = data.getStringExtra("phone");
    	    	phone=phone.replaceAll("-", " ").replaceAll(" " , "");
    	    	//将号码设置到输入框
    	    	et_safeNum.setText(phone);
    	}
      	super.onActivityResult(requestCode, resultCode, data);
    }
    public void next(View v){
    	String etPhone=et_safeNum.getText().toString().trim();
    	if(TextUtils.isEmpty(etPhone)){
    		mToast.show(this, "安全号码不能为空哦，亲");
    		return;
    	}
    	mPref.edit().putString("safeNum",etPhone).commit();
    	startActivity(new Intent(this,activity_step4.class));
    	finish();
    	//两个界面切换的动画
    	overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
    }
    public void previous(View v){
    	startActivity(new Intent(this,activity_step2.class));
    	finish();
    	//两个界面切换的动画
    	overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
    }
    
}