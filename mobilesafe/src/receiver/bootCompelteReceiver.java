package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.TextUtils;

public class bootCompelteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences mPref = context.getSharedPreferences("config", context.MODE_PRIVATE);
	   boolean protect=mPref.getBoolean("protect", false);
	   if(protect){
		   String simSerialNum=mPref.getString("simSerialNum", "");
		   if(TextUtils.isEmpty(simSerialNum)){
			   TelephonyManager phone = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
			    String currentSerial=phone.getSimSerialNumber();
			    if(!simSerialNum.equals(currentSerial)){
			    	System.out.println("sim卡变换");
			    	String safeNum=mPref.getString("safeNum", "");
			    	SmsManager  smsM=SmsManager.getDefault();
			    	smsM.sendTextMessage(safeNum, null, "sim变更", null, null);
			    }else {
			    	System.out.println("sim卡未变换");
			    }
		   }
	   }
	  
	}

}
