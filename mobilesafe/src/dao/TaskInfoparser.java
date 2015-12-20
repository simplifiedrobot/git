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

	// �õ�ϵͳ�������еĽ�����Ϣ
	public static List<taskInfo> getTaskInfo(Context context) {
		List<taskInfo> infos = new ArrayList<taskInfo>();
		ActivityManager am = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = am
				.getRunningAppProcesses();
		// �õ���������
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
				// �õ�Ӧ�ó���ͼƬ
				Drawable loadIcon = packageInfo.applicationInfo
						.loadIcon(packageManager);
				taskinfo.setIcon(loadIcon);
				// �õ����������
				String loadLabel = packageInfo.applicationInfo.loadLabel(
						packageManager).toString();
				taskinfo.setName(loadLabel);
				// �жϳ����Ƿ�Ϊ�û�����
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
