package activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobilesafe.R;

import dao.dao_adress;

public class activity_address extends Activity {

	private EditText et_phone;
	private TextView tv_address;
	private String num;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address);
		et_phone = (EditText) findViewById(R.id.et_adress_phone);
		tv_address = (TextView) findViewById(R.id.tv_address);
		et_phone.addTextChangedListener(new TextWatcher() {
			//内容改变时调用
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String address=dao_adress.query(s.toString());
				tv_address.setText(address);
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}
	public void click(View v){
		num = et_phone.getText().toString().trim();
		System.out.println("点击进入搜索"+num);
		if(!TextUtils.isEmpty(num)){
			String address=dao_adress.query(num);
			tv_address.setText(address);
		}else{
			Animation shake=  AnimationUtils.loadAnimation(this, R.anim.shake);
		   et_phone.startAnimation(shake);
		   vibrator();
		}
	}
    //手机震动
	private void vibrator() {
        Vibrator vibrator=(Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{1000, 2000,3000,1000},-1);//stop,vibrate,stopm^
	}
	
}
