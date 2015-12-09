package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class smsReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		SmsManager sms=SmsManager.getDefault();
		sms

	}

}
