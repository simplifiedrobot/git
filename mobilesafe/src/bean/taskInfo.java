package bean;

import android.graphics.drawable.Drawable;

public class taskInfo {
   Drawable icon;
   String name;
   Long memsize;
   boolean userTask;
   boolean isChecked;
   public boolean isChecked() {
	return isChecked;
}
public void setChecked(boolean isChecked) {
	this.isChecked = isChecked;
}
String packageName;
public Drawable getIcon() {
	return icon;
}
public void setIcon(Drawable icon) {
	this.icon = icon;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public Long getMemsize() {
	return memsize;
}
public void setMemsize(Long memsize) {
	this.memsize = memsize;
}
public boolean isUserTask() {
	return userTask;
}
public void setUserTask(boolean userTask) {
	this.userTask = userTask;
}
public String getPackageName() {
	return packageName;
}
public void setPackageName(String packageName) {
	this.packageName = packageName;
}

}
