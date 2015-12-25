package service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class dogWatch extends Service {

	private ActivityManager 		manager;
   private boolean flag;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		super.onCreate();
		dogWatch();
	}
	@Override
	public void onDestroy() {
		
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	public  void dogWatch(){
		new Thread(){
			public void run() {
				flag=true;
				while(flag){
					List<RunningTaskInfo> tasks = manager.getRunningTasks(1);
					//获取到最上面的进程
					RunningTaskInfo taskInfo = tasks.get(0);
					//获取到最顶端应用程序的包名
					String packageName = taskInfo.topActivity.getPackageName();
					//让狗休息一会
					SystemClock.sleep(30);
					System.out.println(packageName);
				}
			};
		}.start();
	}

}
