package dao;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;


public class SystemInfoUtils {
    //获取运行的程序数量
	public static int getRunningProcessCount(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
	  List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
	  return runningAppProcesses.size();
	}
	//获取可用内存大小
	public static long getAvailRam(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		MemoryInfo outInfo=new MemoryInfo();
		am.getMemoryInfo(outInfo);
	   return	outInfo.availMem;
	}
	//获取总内存大小
	public static long getTotalRam(Context context){
		ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		MemoryInfo outInfo=new MemoryInfo();
		am.getMemoryInfo(outInfo);
	   return	outInfo.totalMem;
	}
	
	
}
