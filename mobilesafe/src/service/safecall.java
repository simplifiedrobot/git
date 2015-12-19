package service;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import dao.BlackNumberDao;

public class safecall extends Service {

	private SmsService smss;
	private PhoneStateListener listener;
	private BlackNumberDao dao;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		dao = new BlackNumberDao(safecall.this);
		smss = new SmsService();
		IntentFilter filter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(smss, filter);
		
		TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new phoneListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
		
		super.onCreate();
	}

	class phoneListener extends PhoneStateListener {

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:

				break;
			case TelephonyManager.CALL_STATE_RINGING:
				String mode = dao.findNumber(incomingNumber);
				if(mode.equals("1")||mode.equals("3")){
					endCall();
					Uri uri=Uri.parse("content://call_log/calls");
					getContentResolver().registerContentObserver(uri, true, new myContentObserver(new Handler(),incomingNumber));
				}
				//检查通讯记录，删除黑名单号码
				break;
			// 接通状态
			case TelephonyManager.CALL_STATE_OFFHOOK:
             
				break;

			default:
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}

	}
	class myContentObserver extends ContentObserver{

		private String incomingNumber;
		public myContentObserver(Handler handler,String incomingNumber) {
			super(handler);
			this.incomingNumber=incomingNumber;
			
		}@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			getContentResolver().unregisterContentObserver(this);
			deleteCalllog(incomingNumber);
		}
		
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smss);
	}

	public void deleteCalllog(String incomingNumber) {
		ContentResolver contentResolver = getContentResolver();
		Uri uri=Uri.parse("content://call_log/calls");
		contentResolver.delete(uri, "number=?", new String[] {incomingNumber});
	}

	//并未实现endcall方法
	public void endCall() {
		try {
			Class clazz = getClassLoader().loadClass("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder iBinder = (IBinder) method.invoke(null, TELEPHONY_SERVICE);
		//	ITelephony itelephony = ITelephony.Stub.asInterface(iBinder);
		//	itelephony.endCall();
			//开通呼叫转移 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SmsService extends BroadcastReceiver {
		

		@Override
		public void onReceive(Context context, Intent intent) {

		
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			System.out.println("短信来了");
			for (Object object : objects) {// 短信最多140字节，超出的话，会分成多条短信发送，所以是一个数组
											// 因为我们的短信很短，for只循环过一次
				SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
				String originatingAddress = message.getOriginatingAddress();// 短信来源号码
				System.out.println(originatingAddress);
				String body = message.getMessageBody();// 短信内容
				String mode = dao.findNumber(originatingAddress);
				System.out.println(mode);
				if (mode.equals("1")) {
					abortBroadcast();
				} else if (mode.equals("3")) {
					abortBroadcast();
				}
			}
		}
	}
}
