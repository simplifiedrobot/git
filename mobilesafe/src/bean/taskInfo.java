package bean;

import android.widget.ImageView;
import android.widget.TextView;

public class taskInfo {
	ImageView app_icon;
	TextView app_name;
	Long app_memory_size;
	boolean app_status;

	public ImageView getApp_icon() {
		return app_icon;
	}

	public void setApp_icon(ImageView app_icon) {
		this.app_icon = app_icon;
	}

	public TextView getApp_name() {
		return app_name;
	}

	public void setApp_name(TextView app_name) {
		this.app_name = app_name;
	}


	public Long getApp_memory_size() {
		return app_memory_size;
	}

	public void setApp_memory_size(Long app_memory_size) {
		this.app_memory_size = app_memory_size;
	}

	public boolean isApp_status() {
		return app_status;
	}

	public void setApp_status(boolean app_status) {
		this.app_status = app_status;
	}

}
