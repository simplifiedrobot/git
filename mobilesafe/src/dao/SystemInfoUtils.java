package dao;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;


public class SystemInfoUtils {
    //��ȡ���еĳ�������
	public static int getRunningProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
	  List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
	  return runningAppProcesses.size();
	}
	//��ȡ�����ڴ��С
	public static long getAvailRam(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		MemoryInfo outInfo=new MemoryInfo();
		am.getMemoryInfo(outInfo);
	   return	outInfo.availMem;
	}
	//��ȡ���ڴ��С
	public static long getTotalRam(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		MemoryInfo outInfo=new MemoryInfo();
		am.getMemoryInfo(outInfo);
	   return	outInfo.totalMem;
	}
	
	
}
