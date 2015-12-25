package activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;

import fragment.lock_fragment;
import fragment.unlock_fragment;

public class activity_app_lock extends FragmentActivity implements
		OnClickListener {

	private TextView tv_unlock;
	private TextView tv_lock;
	private FrameLayout ap_frame;

	private FragmentManager fragmentManager;
	private android.support.v4.app.FragmentTransaction beginTransaction;
	private lock_fragment lock_fragment;
	private unlock_fragment unlock_fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applock);
		initUI();
	}
	private void initUI() {
		tv_lock = (TextView) findViewById(R.id.tv_lock);
		tv_unlock = (TextView) findViewById(R.id.tv_unlock);
		ap_frame = (FrameLayout) findViewById(R.id.ap_frame);
		tv_lock.setOnClickListener(this);
		tv_unlock.setOnClickListener(this);
		fragmentManager = getSupportFragmentManager();
		beginTransaction = fragmentManager.beginTransaction();
		lock_fragment = new lock_fragment();
		unlock_fragment = new unlock_fragment();
		beginTransaction.replace(R.id.ap_frame, unlock_fragment).commit();
	}
	@Override
	public void onClick(View v) {
		android.support.v4.app.FragmentTransaction ft = fragmentManager
				.beginTransaction();
		switch (v.getId()) {
		case R.id.tv_lock:
			tv_lock.setBackgroundResource(R.drawable.tab_left_pressed);
			tv_unlock.setBackgroundResource(R.drawable.tab_right_default);
			ft.replace(R.id.ap_frame, lock_fragment);
			System.out.println("lock");
			break;
		case R.id.tv_unlock:
			tv_lock.setBackgroundResource(R.drawable.tab_left_default);
			tv_unlock.setBackgroundResource(R.drawable.tab_right_pressed);
			ft.replace(R.id.ap_frame, unlock_fragment);
			System.out.println("unlock");
			break;
		}
		ft.commit();
	}

}
