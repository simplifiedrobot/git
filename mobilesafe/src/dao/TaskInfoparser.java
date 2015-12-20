package dao;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug.MemoryInfo;
import bean.taskInfo;

public class TaskInfoparser {

	// 得到系统正在运行的进程信息
	public static List<taskInfo> getTaskInfo(Context context) {
		List<taskInfo> infos = new ArrayList<taskInfo>();
		ActivityManager am = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = am
				.getRunningAppProcesses();
		// 得到包管理器
		PackageManager packageManager = context.getPackageManager();
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			taskInfo taskinfo = new taskInfo();
			MemoryInfo[] processMemoryInfo = am
					.getProcessMemoryInfo(new int[] { runningAppProcessInfo.pid });
			int totalPrivateDirty = processMemoryInfo[0].getTotalPrivateDirty();
			long memorySize = totalPrivateDirty * 1024L;
			taskinfo.setMemsize(memorySize);
			String packageName = runningAppProcessInfo.processName;
			taskinfo.setPackageName(packageName);

			try {
				PackageInfo packageInfo = packageManager.getPackageInfo(
						packageName, 0);
				// 得到应用程序图片
				Drawable loadIcon = packageInfo.applicationInfo
						.loadIcon(packageManager);
				taskinfo.setIcon(loadIcon);
				// 得到程序的名称
				String loadLabel = packageInfo.applicationInfo.loadLabel(
						packageManager).toString();
				taskinfo.setName(loadLabel);
				// 判断程序是否为用户程序
				int flags = packageInfo.applicationInfo.flags;
				if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
					taskinfo.setUserTask(false);
				} else {
					taskinfo.setUserTask(true);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			infos.add(taskinfo);

		}

		return infos;
	}

}
