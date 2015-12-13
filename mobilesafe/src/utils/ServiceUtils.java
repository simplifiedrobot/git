package utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceUtils {
	
   protected static boolean isServiceRunning;

public  static boolean isServiceRunning(Context  context,String serviceName){
	   
	   ActivityManager am=(ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
	    List<RunningServiceInfo>  RunningServiceInfo =   am.getRunningServices(100);
	    System.out.println("进入判断");
	          for (RunningServiceInfo runningServiceInfo2 : RunningServiceInfo) {
	        	  String className=runningServiceInfo2.service.getClassName();
	        	  System.out.println(className);
				if(runningServiceInfo2.equals(serviceName)){
					System.out.println("进入判断 未启动");
					return true;
				}
			}
	   return false;
   }
}
