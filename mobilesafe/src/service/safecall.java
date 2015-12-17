package service;

import dao.BlackNumberDao;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class safecall extends Service {

	private SmsService smss;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		smss = new SmsService();
		IntentFilter filter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE);
		registerReceiver(smss, filter);
		super.onCreate();
	}

	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smss);
	}

	class SmsService extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			BlackNumberDao dao = new BlackNumberDao(safecall.this);
			Object[] objects = (Object[]) intent.getExtras().get("pdus");
			System.out.println("��������");
			for (Object object : objects) {// �������140�ֽڣ������Ļ�����ֳɶ������ŷ��ͣ�������һ������
											// ��Ϊ���ǵĶ��ẓ́ܶ�forֻѭ����һ��
				SmsMessage message = SmsMessage.createFromPdu((byte[]) object);
				String originatingAddress = message.getOriginatingAddress();// ������Դ����
			    System.out.println(originatingAddress);
				String body = message.getMessageBody();// ��������
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
