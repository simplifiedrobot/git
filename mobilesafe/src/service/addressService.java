package service;

import receiver.outCallReceiver;
import utils.dao_addressToast;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import dao.dao_adress;

public class addressService extends Service {

	private outCallReceiver receiver;
	private myListener listener;
	private TelephonyManager tm;
	private View view;
	private SharedPreferences mPref;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
	         tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	         listener = new myListener();
	         tm.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
	         
	         //动态注册广播
	         System.out.println("动态注册广播");
	         receiver=new outCallReceiver();
	         IntentFilter filter =new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
	         registerReceiver(receiver, filter);
	}

	  class myListener extends PhoneStateListener{

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("电话铃响");
				String  address=dao_adress.query(incomingNumber);
				dao_addressToast.showToast(addressService.this, address);
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("无电话");
				
				break;
			default:
				break;
			}
		}
	  }
	@Override
	public void onDestroy() {
		super.onDestroy();
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);//停止来电监听
		unregisterReceiver(receiver);//注销广播
	}
			



}
