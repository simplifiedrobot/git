package receiver;

import service.KillProcesWidgetService;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class MyAppWidget extends AppWidgetProvider {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}

	@Override
	public void onEnabled(Context context) {
		/*
		 * 第一次创建的时候才会调用当前的生命周期方法
		 * 当前的广播的生命只有10秒
		 *不能做耗时操作
		 */
		
  //启动服务
		Intent intent = new Intent(context,KillProcesWidgetService.class);
		context.startService(intent);
		super.onEnabled(context);
	}
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(context,KillProcesWidgetService.class);
		context.stopService(intent);
		super.onDisabled(context);
	}


}
