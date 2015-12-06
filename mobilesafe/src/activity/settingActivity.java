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
		//�ҵ���ǰ������
		if(siv){
			sivUpdate.setChecked(true);
		}else{
			sivUpdate.setChecked(false);
		}
		//�Ե��������Ӧ
		sivUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
	        //�жϵ�ǰ�Ĺ�ѡ״̬ ,��ǰҪ�ǹ�ѡ�����Ϊ����ѡ
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
