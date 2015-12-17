package activity;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import bean.AppInfo;

import com.example.mobilesafe.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import engine.AppInfos;

public class appManeger extends Activity {

	@ViewInject(R.id.list_view)
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appmaneger);
		initUI();
		initDate();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			adapter = new myAdapter();
			listView.setAdapter(adapter);
		}
	};
	private List<AppInfo> appInfos;
	private ArrayList<AppInfo> systemApp;
	private ArrayList<AppInfo> userApp;
	private myAdapter adapter;

	private void initDate() {
		new Thread() {
			private List<AppInfo> appInfos;

			public void run() {
				appInfos = AppInfos.getAppInfos(appManeger.this);
				systemApp = new ArrayList<AppInfo>();
				userApp = new ArrayList<AppInfo>();
				for (AppInfo appInfo : appInfos) {
					if (appInfo.isUserApp()) {
						userApp.add(appInfo);
					} else {
						systemApp.add(appInfo);
					}
				}
				handler.sendEmptyMessage(0);
			};
		}.start();
	}

	private void initUI() {

	}

	class myAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return userApp.size() + systemApp.size() + 2;
		}

		@Override
		public Object getItem(int position) {

			if (position == 0) {
				return null;
			} else if (position == userApp.size() + 1) {
				return null;
			}
			AppInfo appInfo;

			if (position < userApp.size() + 1) {
				// 把多出来的特殊的条目减掉
				appInfo = userApp.get(position - 1);

			} else {

				int location = userApp.size() + 2;

				appInfo = systemApp.get(position - location);
			}

			return appInfo;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			AppInfo appInfo;
			if (position == 0) {
				TextView tv_installApp = new TextView(appManeger.this);
				tv_installApp.setText("安装应用：" + userApp.size());
				tv_installApp.setBackgroundColor(color.background_light);
				tv_installApp.setTextColor(color.black);
				tv_installApp.setHeight(15);
				return tv_installApp;
			} else if (position <= (userApp.size() + 1)) {
				appInfo = appInfos.get(position - 1);

			} else if (position == (userApp.size() + 1)) {
				TextView tv_systemAppNum = new TextView(appManeger.this);
				tv_systemAppNum.setText("系统应用：" + systemApp.size());
				tv_systemAppNum.setBackgroundColor(color.background_light);
				tv_systemAppNum.setTextColor(color.black);
				tv_systemAppNum.setHeight(15);
				return tv_systemAppNum;
			} else {
				int location = position - 2;
				appInfo = appInfos.get(location);
			}
			View view = null;
			ViewHodler hodler = null;
			if (convertView != null && convertView instanceof LinearLayout) {

				view = (View) convertView.getTag();
			} else {

				view = View.inflate(appManeger.this,
						R.layout.listview_appmaneger, null);
				hodler = new ViewHodler();
				hodler.tv_location = (TextView) findViewById(R.id.tv_location);
				hodler.iv_icon = (ImageView) findViewById(R.id.iv_icon);
				hodler.tv_name = (TextView) findViewById(R.id.tv_name);
				hodler.tv_apk_size = (TextView) findViewById(R.id.tv_apk_size);
				view.setTag(hodler);
			}


			hodler.iv_icon.setImageDrawable(appInfo.getIcon());
			hodler.tv_apk_size.setText(Formatter.formatFileSize(appManeger.this, appInfo.getApkSize()));

			hodler.tv_name.setText(appInfo.getApkName());

            if (appInfo.isRom()) {
            	hodler.tv_location.setText("手机内存");
            } else {
            	hodler.tv_location.setText("外部存储");
            }
			return view;
		}
	}

	static class ViewHodler {
		ImageView iv_icon;
		TextView tv_location;
		TextView tv_name;
		TextView tv_apk_size;
	}

}
