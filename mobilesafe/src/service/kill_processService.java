package service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class kill_processService extends Service {

	private LockReciver receiver;
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public void onCreate() {
		 receiver = new LockReciver();
		 //ËøÆÁµÄ¹ýÂËÆ÷
		 IntentFilter filter=new IntentFilter(Intent.ACTION_SCREEN_OFF);
		 registerReceiver(receiver, filter);
		 
		super.onCreate();
	}

	@Override
	public void onDestroy() {
	unregisterReceiver(receiver);
	receiver=null;
		super.onDestroy();
	}
	class LockReciver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
		ActivityManager am=	(ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
			List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
			for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
				am.killBackgroundProcesses(runningAppProcessInfo.processName);
			}
		}
		
	}

}
