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
		 * ��һ�δ�����ʱ��Ż���õ�ǰ���������ڷ���
		 * ��ǰ�Ĺ㲥������ֻ��10��
		 *��������ʱ����
		 */
		
  //��������
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
