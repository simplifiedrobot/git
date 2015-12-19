package activity;

import com.example.mobilesafe.R;

import dao.SystemInfoUtils;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

public class activity_TaskManager extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager);
		initUI();
		initDate();
	}

	private void initDate() {
		// TODO Auto-generated method stub

	}

	private void initUI() {
		TextView tv_task_process_count = (TextView) findViewById(R.id.tv_task_process_count);
		TextView tv_task_memory = (TextView) findViewById(R.id.tv_task_memory);
        tv_task_process_count.setText("运行中的进程："+SystemInfoUtils.getRunningProcessCount(this)+"个");
	    tv_task_memory.setText("可用内存/总内存："+Formatter.formatFileSize(this, SystemInfoUtils.getAvailRam(this))+"/"+Formatter.formatFileSize(this, SystemInfoUtils.getTotalRam(this)));
	}
}
