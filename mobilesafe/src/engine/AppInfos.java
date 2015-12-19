package engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import bean.AppInfo;

public class AppInfos {

	private static List<AppInfo> packageAppInfos;
	private static PackageManager packageManager;
	private static List<PackageInfo> installedPackages;
	private static AppInfo appInfo;

	public static List<AppInfo> getAppInfos(Context context) {
		packageAppInfos = new ArrayList<AppInfo>();
		// 获取包的管理者
		packageManager = context.getPackageManager();
		// 获取到安装包
		installedPackages = packageManager.getInstalledPackages(0);
		for (PackageInfo installedPackage : installedPackages) {
			appInfo = new AppInfo();
			// 获取到应用程序的图标
			Drawable icon = installedPackage.applicationInfo
					.loadIcon(packageManager);
			appInfo.setIcon(icon);
			// 获取应用程序的名字
			String label = installedPackage.applicationInfo.loadLabel(
					packageManager).toString();
			appInfo.setApkName(label);
			// 获取应用程序的包名
			String packageName = installedPackage.packageName;
			appInfo.setApkPackageName(packageName);
			// 获取应用的apk路径
			String sourceDir = installedPackage.applicationInfo.sourceDir;
			File file = new File(sourceDir);
			// apk的长度
			long length = file.length();
			appInfo.setApkSize(length);
			// 获取到安装应用的标记
			int flags = installedPackage.applicationInfo.flags;
			if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				// 表示系统app
				appInfo.setUserApp(false);
			} else {
				// 表示用户app
				appInfo.setUserApp(true);
			}

			if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
				// 表示在sd卡
				appInfo.setRom(false);
				
			} else {
				// 表示内存
				appInfo.setRom(true);
			}
			packageAppInfos.add(appInfo);
		}
		return packageAppInfos;
	}
}
