package activity;

import java.util.ArrayList;
import java.util.List;

import utils.mToast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import bean.taskInfo;

import com.example.mobilesafe.R;

import dao.SharedPreferencesUtils;
import dao.SystemInfoUtils;
import dao.TaskInfoparser;

public class activity_TaskManager extends Activity {

	private List<taskInfo> taskinfo;
	private List<taskInfo> userappinfo;
	private List<taskInfo> systemappinfo;
	private Mydapter mydapter;
	private SharedPreferences mPref;
	private ListView list_view;
	private View inflate;
	private View view;
	private taskInfo infos;
	private ViewHolder holder;
	Context context;
	private TextView tv_task_process_count;
	private TextView tv_task_memory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_manager);
		list_view = (ListView) findViewById(R.id.list_view);
		initDate();
		initUI();
	}

	private void initDate() {

		// 在子线程中进行耗时操作，比如获取数据
		new Thread() {
			@Override
			public void run() {

				taskinfo = TaskInfoparser
						.getTaskInfo(activity_TaskManager.this);
				userappinfo = new ArrayList<taskInfo>();
				systemappinfo = new ArrayList<taskInfo>();
				for (taskInfo Info : taskinfo) {
					if (Info.isUserTask()) {
						userappinfo.add(Info);
					} else {
						systemappinfo.add(Info);
					}
				}
				// 子线程中刷新UI的方法
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mydapter = new Mydapter();
						list_view.setAdapter(mydapter);
					}
				});
			}
		}.start();
		list_view.setOnItemClickListener(new OnItemClickListener() {

			private taskInfo task_info;

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object atPosition = list_view.getItemAtPosition(position);
				if (atPosition != null & atPosition instanceof taskInfo) {
					task_info = (taskInfo) atPosition;
					ViewHolder holder = (ViewHolder) view.getTag();
				}
				if (task_info.getPackageName().equals(getPackageName())) {
					return;
				}
				if (task_info.isChecked()) {
					task_info.setChecked(false);
					holder.tv_app_status.setChecked(false);
				}
			}
		});
	}

	private void initUI() {
		tv_task_process_count = (TextView) findViewById(R.id.tv_task_process_count);
		tv_task_memory = (TextView) findViewById(R.id.tv_task_memory);
		tv_task_process_count.setText("运行中的进程："
				+ SystemInfoUtils.getRunningProcessCount(this) + "个");
		tv_task_memory.setText("可用内存/总内存："
				+ Formatter.formatFileSize(this,
						SystemInfoUtils.getAvailRam(this))
				+ "/"
				+ Formatter.formatFileSize(this,
						SystemInfoUtils.getTotalRam(this)));

	}

	class Mydapter extends BaseAdapter {

		boolean b = SharedPreferencesUtils.getBoolean(
				activity_TaskManager.this, "cb_status");

		@Override
		public int getCount() {
			if (b) {
				return taskinfo.size() + 2;
			} else {
				return userappinfo.size() + 1;
			}

		}

		@Override
		public Object getItem(int position) {
			if (position == 0 || position == userappinfo.size() + 1) {
				return null;
			} else if (position < userappinfo.size() + 1) {
				return userappinfo.get(position - 1);
			} else {

				return systemappinfo.get(position - userappinfo.size() - 2);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			inflate = View.inflate(activity_TaskManager.this,
					R.layout.item_task_manager, null);

			holder = null;
			if (convertView == null) {
				holder.iv_app_icon = (ImageView) inflate
						.findViewById(R.id.iv_app_icon);
				holder.tv_app_memory_size = (TextView) inflate
						.findViewById(R.id.tv_app_memory_size);
				holder.tv_app_name = (TextView) inflate
						.findViewById(R.id.tv_app_name);
				holder.tv_app_status = (CheckBox) inflate
						.findViewById(R.id.tv_app_status);
				inflate.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
				inflate = convertView;
			}
			if (position == 0) {
				TextView textView = new TextView(activity_TaskManager.this);
				textView.setText("用户进程：" + userappinfo.size() + "个");
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.BLACK);
				return textView;
			} else if (position == userappinfo.size() + 1) {
				TextView textView = new TextView(activity_TaskManager.this);
				textView.setText("系统进程：" + systemappinfo.size() + "个");
				textView.setBackgroundColor(Color.GRAY);
				textView.setTextColor(Color.BLACK);
				return textView;
			} else if (position < userappinfo.size() + 1) {
				infos = userappinfo.get(position - 1);
			} else {
				infos = systemappinfo.get(position - userappinfo.size() - 2);
			}
			holder.iv_app_icon.setImageDrawable(infos.getIcon());
			holder.tv_app_memory_size.setText(Formatter.formatFileSize(
					activity_TaskManager.this, infos.getMemsize()));
			holder.tv_app_name.setText(infos.getName());

			if (infos.isChecked()) {
				holder.tv_app_status.setChecked(true);
			} else {
				holder.tv_app_status.setChecked(false);
			}
			if (infos.getPackageName().equals(getPackageName())) {
				holder.tv_app_status.setVisibility(View.INVISIBLE);
			} else {
				holder.tv_app_status.setVisibility(View.VISIBLE);
			}
			return inflate;
		}

	}

	static class ViewHolder {
		ImageView iv_app_icon;
		TextView tv_app_name;
		CheckBox tv_app_status;
		TextView tv_app_memory_size;
	}

	public void selectAll(View v) {

		for (taskInfo info : userappinfo) {
			//判断是否为自己的程序
			if (info.getPackageName().equals(getPackageName())) {
				continue;
			}
			info.setChecked(true);
		}
		for (taskInfo info : systemappinfo) {
			info.setChecked(true);

		}
		mydapter.notifyDataSetChanged();

	}

	public void selectOppsite(View v) {

		for (taskInfo info : userappinfo) {
			if (info.getPackageName().equals(getPackageName())) {
				continue;
			}
			if (!info.isChecked()) {
				info.setChecked(true);
			} else {
				info.setChecked(false);
			}

		}
		for (taskInfo info : systemappinfo) {
			if (!info.isChecked()) {
				info.setChecked(true);
			} else {
				info.setChecked(false);
			}
		}
		mydapter.notifyDataSetChanged();
	}

	public void killProcess(View v) {
		List<taskInfo> kill_list = new ArrayList<taskInfo>();
		int totalCount=0;//清理程序的个数
		int killMen=0;//清理程序的大小
		for (taskInfo info : systemappinfo) {
			if (info.isChecked()) {
				kill_list.add(info);
				totalCount+=1;
				killMen+=info.getMemsize();
			}
		}
		for (taskInfo info : userappinfo) {
			if (info.isChecked()) {
				kill_list.add(info);
				totalCount+=1;
				killMen+=info.getMemsize();
			}
		}
		for (taskInfo info : kill_list) {
			if(info.isUserTask()){
				userappinfo.remove(info);
				SystemInfoUtils.killProcess(this, info);
			}else{
				systemappinfo.remove(info);
				SystemInfoUtils.killProcess(this, info);
			}
			mToast.show(this, "共清理了"+totalCount+"个进程,释放了"+Formatter.formatFileSize(this, killMen)+"内存");
			tv_task_process_count.setText("运行中的进程："
					+ SystemInfoUtils.getRunningProcessCount(this) + "个");
			tv_task_memory.setText("可用内存/总内存："
					+ Formatter.formatFileSize(this,
							SystemInfoUtils.getAvailRam(this)+killMen)
					+ "/"
					+ Formatter.formatFileSize(this,
							SystemInfoUtils.getTotalRam(this)-killMen));
		}
		mydapter.notifyDataSetChanged();
	}

	public void openSetting(View v) {
		Intent intent = new Intent(activity_TaskManager.this,
				activity_task_setting.class);
		startActivity(intent);
	}
}
