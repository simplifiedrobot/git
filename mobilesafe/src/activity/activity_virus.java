package activity;

import java.util.List;

import utils.MD5Utils;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.mobilesafe.R;

import dao.AntivirusDao;

public class activity_virus extends Activity {
	protected static final int BEGING = 1;
	protected static final int SCANING = 2;
	protected static final int FINISH = 3;
	private ImageView iv_scanning;
	private TextView tv_init_virus;
	private ProgressBar progressBar1;
	private LinearLayout ll_content;
	protected Message message;
	private ScrollView scrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_antivirus);
		initUI();
		initData();
	}

	private void initData() {
		new Thread() {
			public void run() {
				message = Message.obtain();
				message.what = BEGING;
				PackageManager packageManager = getPackageManager();
				List<ApplicationInfo> installedApplications = packageManager
						.getInstalledApplications(0);
				ScanInfo scanInfo = new ScanInfo();
				int size = installedApplications.size();
				progressBar1.setMax(size);
				int progress = 0;
				for (ApplicationInfo applicationInfo : installedApplications) {
					// applicationInfo;
					scanInfo.appName = packageManager.getApplicationLabel(
							applicationInfo).toString();
					scanInfo.packageName = applicationInfo.processName;
					String md5 = MD5Utils.getMD5(applicationInfo.sourceDir);
					String desc = AntivirusDao.checkFileVirus(md5);
					System.out.println("-------------------------");
					System.out.println(scanInfo.appName);
					System.out.println(md5);
					if (desc == null) {
						scanInfo.desc = false;
					} else {
						scanInfo.desc = true;
					}
					SystemClock.sleep(100);
					progress++;
					progressBar1.setProgress(progress);
					message = Message.obtain();

					message.what = SCANING;

					message.obj = scanInfo;
					handler.sendMessage(message);
				}

				message = Message.obtain();

				message.what = FINISH;

				handler.sendMessage(message);

			};
		}.start();

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case BEGING:
				tv_init_virus.setText("初始化八核引擎");
				break;

			case SCANING:
				// 病毒扫描中：
				TextView child = new TextView(activity_virus.this);

				ScanInfo scanInfo = (ScanInfo) msg.obj;
				// 如果为true表示有病毒
				if (scanInfo.desc) {
					child.setTextColor(Color.RED);

					child.setText(scanInfo.appName + "有病毒");
				} else {
					child.setTextColor(Color.BLACK);
					// // 为false表示没有病毒
					child.setText(scanInfo.appName + "扫描安全");
				}

				ll_content.addView(child, 0);
				// 自动滚动
				scrollView.post(new Runnable() {

					@Override
					public void run() {
						// 一直往下面进行滚动
						scrollView.fullScroll(scrollView.FOCUS_DOWN);
					}
				});

				System.out.println(scanInfo.appName + "扫描安全");
				break;
			case FINISH:
				// 当扫描结束的时候。停止动画
				iv_scanning.clearAnimation();
				break;
			}
		};
	};

	private void initUI() {

		iv_scanning = (ImageView) findViewById(R.id.iv_scanning);
		tv_init_virus = (TextView) findViewById(R.id.tv_init_virus);
		progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
		ll_content = (LinearLayout) findViewById(R.id.ll_content);
		scrollView = (ScrollView) findViewById(R.id.scrollView);

		/**
		 * 第一个参数表示开始的角度 第二个参数表示结束的角度 第三个参数表示参照自己 初始化旋转动画
		 */
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置动画的时间
		rotateAnimation.setDuration(5000);
		// 设置动画无限循环
		rotateAnimation.setRepeatCount(Animation.INFINITE);
		// 开始动画
		iv_scanning.startAnimation(rotateAnimation);

	}

	static class ScanInfo {
		boolean desc;
		String appName;
		String packageName;
	}

}
