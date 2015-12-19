package activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import bean.AppInfo;

import com.example.mobilesafe.R;

import engine.AppInfos;

public class appManeger extends Activity implements OnClickListener {

	private List<AppInfo> appInfos;
	private ArrayList<AppInfo> systemApp;
	private ArrayList<AppInfo> userApp;
	private myAdapter adapter;
	private ListView listview_appmaneger;
	private TextView tv_apptop;
	private AppInfo clickappinfo;
	private PopupWindow popupWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appmaneger);
		initUI();
		initDate();

		tv_apptop = (TextView) findViewById(R.id.tv_apptop);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			adapter = new myAdapter();
			listview_appmaneger.setAdapter(adapter);
		}
	};

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
		TextView tv_rom = (TextView) findViewById(R.id.tv_rom);
		TextView tv_sd = (TextView) findViewById(R.id.tv_sd);
		// 获取到rom内存的运行的剩余空间
		long rom_freeSpace = Environment.getDataDirectory().getFreeSpace();
		// 获取到SD卡的剩余空间
		long sd_freeSpace = Environment.getExternalStorageDirectory()
				.getFreeSpace();

		// 格式化大小
		tv_rom.setText("内存可用:" + Formatter.formatFileSize(this, rom_freeSpace));
		tv_sd.setText("sd卡可用" + Formatter.formatFileSize(this, sd_freeSpace));
		listview_appmaneger = (ListView) findViewById(R.id.listview_appmaneger);
		listview_appmaneger.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (userApp != null && systemApp != null) {
					// 当显示用户应用时
					if (firstVisibleItem < userApp.size() + 1) {
						tv_apptop.setText("用户应用：" + userApp.size());
					} else {
						tv_apptop.setText("系统应用：" + systemApp.size());
					}
				}
			}
		});
		listview_appmaneger.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取当前点击的Item对象
				Object ItematPosition = listview_appmaneger
						.getItemAtPosition(position);
				if (ItematPosition != null && ItematPosition instanceof AppInfo) {
					clickappinfo = (AppInfo) ItematPosition;
					View contentView = View.inflate(appManeger.this,
							R.layout.itempopup, null);

					LinearLayout ll_uninstall = (LinearLayout) contentView
							.findViewById(R.id.ll_uninstall);

					LinearLayout ll_share = (LinearLayout) contentView
							.findViewById(R.id.ll_share);

					LinearLayout ll_start = (LinearLayout) contentView
							.findViewById(R.id.ll_start);

					LinearLayout ll_detail = (LinearLayout) contentView
							.findViewById(R.id.ll_detail);

					ll_uninstall.setOnClickListener(appManeger.this);

					ll_share.setOnClickListener(appManeger.this);

					ll_start.setOnClickListener(appManeger.this);

					ll_detail.setOnClickListener(appManeger.this);

					popupWindowDismiss();
					// -2表示包裹内容
					popupWindow = new PopupWindow(contentView, -2, -2);
					// 要想使popupwindow有动画，必须得给其设置背景
					popupWindow.setBackgroundDrawable(new ColorDrawable(
							Color.TRANSPARENT));
					int[] location = new int[2];
					view.getLocationInWindow(location);

					popupWindow.showAtLocation(parent, Gravity.LEFT
							+ Gravity.TOP, 150, location[1]);
					ScaleAnimation sa = new ScaleAnimation(0.5f, 1f, 0.5f, 1f,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					sa.setDuration(500);
					contentView.setAnimation(sa);

				}

			}
		});
	}

	protected void popupWindowDismiss() {
		if (popupWindow != null && popupWindow.isShowing()) {
			 popupWindow.dismiss();
	            popupWindow = null;
		}
	}

	static class ViewHodler {
		ImageView iv_icon;
		TextView tv_location;
		TextView tv_name;
		TextView tv_apk_size;
	}

	class myAdapter extends BaseAdapter {

		private View view;

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

			if (position == 0) {
				TextView tv_installApp = new TextView(appManeger.this);
				tv_installApp.setText("安装应用：" + userApp.size());
				tv_installApp.setBackgroundColor(Color.GRAY);
				tv_installApp.setTextColor(Color.WHITE);
				return tv_installApp;
			} else if (position == (userApp.size() + 1)) {
				TextView tv_systemAppNum = new TextView(appManeger.this);
				tv_systemAppNum.setText("系统应用：" + systemApp.size());
				tv_systemAppNum.setBackgroundColor(Color.GRAY);
				tv_systemAppNum.setTextColor(Color.WHITE);
				return tv_systemAppNum;
			}
			AppInfo appInfo;
			if (position < userApp.size() + 1) {
				appInfo = userApp.get(position - 1);
			} else {
				int location = userApp.size() + 2;
				appInfo = systemApp.get(position - location);
			}
			view = null;
			ViewHodler hodler = null;
			if (convertView != null && convertView instanceof LinearLayout) {
				view = convertView;
				hodler = (ViewHodler) convertView.getTag();
			} else {

				view = View.inflate(appManeger.this,
						R.layout.listview_appmaneger, null);
				hodler = new ViewHodler();
				hodler.tv_location = (TextView) view
						.findViewById(R.id.tv_location);
				hodler.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				hodler.tv_name = (TextView) view.findViewById(R.id.tv_name);
				hodler.tv_apk_size = (TextView) view
						.findViewById(R.id.tv_apk_size);
				view.setTag(hodler);
			}

			hodler.iv_icon.setImageDrawable(appInfo.getIcon());
			hodler.tv_apk_size.setText(Formatter.formatFileSize(
					appManeger.this, appInfo.getApkSize()));

			hodler.tv_name.setText(appInfo.getApkName());

			if (appInfo.isRom()) {
				hodler.tv_location.setText("手机内存");
			} else {
				hodler.tv_location.setText("外部存储");
			}

			return view;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        //分享
        case R.id.ll_share:

            Intent share_localIntent = new Intent("android.intent.action.SEND");
            share_localIntent.setType("text/plain");
            share_localIntent.putExtra("android.intent.extra.SUBJECT", "分享");
            share_localIntent.putExtra("android.intent.extra.TEXT",
                    "Hi！推荐您使用软件：" + clickappinfo.getApkName()+"下载地址:"+"https://play.google.com/store/apps/details?id="+clickappinfo.getApkPackageName());
            this.startActivity(Intent.createChooser(share_localIntent, "分享"));
            popupWindowDismiss();

            break;

        //运行
        case R.id.ll_start:

            Intent start_localIntent = this.getPackageManager().getLaunchIntentForPackage(clickappinfo.getApkPackageName());
            this.startActivity(start_localIntent);
            popupWindowDismiss();
            break;
        //卸载
        case R.id.ll_uninstall:

            Intent uninstall_localIntent = new Intent("android.intent.action.DELETE", Uri.parse("package:" + clickappinfo.getApkPackageName()));
            startActivity(uninstall_localIntent);
            popupWindowDismiss();
            break;

        case R.id.ll_detail:
            Intent detail_intent = new Intent();
            detail_intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            detail_intent.addCategory(Intent.CATEGORY_DEFAULT);
            detail_intent.setData(Uri.parse("package:" + clickappinfo.getApkPackageName()));
            startActivity(detail_intent);
            break;
    }

	}

}
