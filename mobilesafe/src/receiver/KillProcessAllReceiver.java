package receiver;

import java.util.List;

import utils.mToast;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class KillProcessAllReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		ActivityManager AM = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = AM.getRunningServices(100);
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			AM.killBackgroundProcesses(runningServiceInfo.process);
		}
		mToast.show(context, "清理完毕，共清理了"+runningServices.size()+"个进程");
	}

}
