package bean;

import android.graphics.drawable.Drawable;

public class AppInfo {

	
	    private Drawable icon;
	    public Drawable getIcon() {
			return icon;
		}
		public void setIcon(Drawable icon) {
			this.icon = icon;
		}
		public String getApkName() {
			return apkName;
		}
		public void setApkName(String apkName) {
			this.apkName = apkName;
		}
		public long getApkSize() {
			return apkSize;
		}
		public void setApkSize(long apkSize) {
			this.apkSize = apkSize;
		}
		public boolean isUserApp() {
			return userApp;
		}
		public void setUserApp(boolean userApp) {
			this.userApp = userApp;
		}
		public boolean isRom() {
			return isRom;
		}
		public void setRom(boolean isRom) {
			this.isRom = isRom;
		}
		public String getApkPackageName() {
			return apkPackageName;
		}
		public void setApkPackageName(String apkPackageName) {
			this.apkPackageName = apkPackageName;
		}
		private String apkName;
	    private long apkSize;
	    private boolean userApp;
	    private boolean isRom;
	    private String apkPackageName;
	
}
