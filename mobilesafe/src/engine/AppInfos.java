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
		// ��ȡ���Ĺ�����
		packageManager = context.getPackageManager();
		// ��ȡ����װ��
		installedPackages = packageManager.getInstalledPackages(0);
		for (PackageInfo installedPackage : installedPackages) {
			appInfo = new AppInfo();
			// ��ȡ��Ӧ�ó����ͼ��
			Drawable icon = installedPackage.applicationInfo
					.loadIcon(packageManager);
			appInfo.setIcon(icon);
			// ��ȡӦ�ó��������
			String label = installedPackage.applicationInfo.loadLabel(
					packageManager).toString();
			appInfo.setApkName(label);
			// ��ȡӦ�ó���İ���
			String packageName = installedPackage.packageName;
			appInfo.setApkPackageName(packageName);
			// ��ȡӦ�õ�apk·��
			String sourceDir = installedPackage.applicationInfo.sourceDir;
			File file = new File(sourceDir);
			// apk�ĳ���
			long length = file.length();
			appInfo.setApkSize(length);
			// ��ȡ����װӦ�õı��
			int flags = installedPackage.applicationInfo.flags;
			if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
				// ��ʾϵͳapp
				appInfo.setUserApp(false);
			} else {
				// ��ʾ�û�app
				appInfo.setUserApp(true);
			}

			if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
				// ��ʾ��sd��
				appInfo.setRom(false);
				
			} else {
				// ��ʾ�ڴ�
				appInfo.setRom(true);
			}
			packageAppInfos.add(appInfo);
		}
		return packageAppInfos;
	}
}
