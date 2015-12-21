package service;

import java.util.Timer;
import java.util.TimerTask;

import receiver.MyAppWidget;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.RemoteViews;

import com.example.mobilesafe.R;

import dao.SystemInfoUtils;

public class KillProcesWidgetService extends Service {

	private AppWidgetManager appWidgetManager;
	private Timer timer;
	private TimerTask timerTask;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		appWidgetManager = AppWidgetManager.getInstance(this);
		timer = new Timer();
	     
	     timerTask = new TimerTask(){
			@Override
			public void run() {
				RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.process_widget);
				remoteViews.setTextViewText(R.id.process_count, "当前运行程序"+SystemInfoUtils.getRunningProcessCount(KillProcesWidgetService.this)+"个");
				remoteViews.setTextViewText(R.id.process_memory, "当前可用内存为"+Formatter.formatFileSize(KillProcesWidgetService.this, SystemInfoUtils.getAvailRam(KillProcesWidgetService.this)));
				Intent intent = new Intent();
				intent.setAction("com.itheima.mobileguard");
				PendingIntent pendingIntent =PendingIntent.getBroadcast(KillProcesWidgetService.this, 0, intent, 0);
				remoteViews.setOnClickPendingIntent(R.id.btn_clear, pendingIntent);
				//第一个参数表示上下文
				//第二个参数表示由哪一个广播进行处理当前的桌面小控件
				ComponentName componentName = new ComponentName(getApplicationContext(),MyAppWidget.class);
				appWidgetManager.updateAppWidget(componentName, remoteViews);
			}
	     };
	     timer.schedule(timerTask, 0, 3000);
	
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//优化代码
		if(timer !=null ||timerTask !=null){
			timer.cancel();
			timerTask.cancel();
			timer=null;
			timerTask=null;
		}
	}

}
