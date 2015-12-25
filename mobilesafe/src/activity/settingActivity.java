package activity;

import service.addressService;
import service.dogWatch;
import service.safecall;
import utils.ServiceUtils;
import view.settingItemView;
import view.settingclickView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.R;

public class settingActivity extends Activity {
	private settingItemView siv_address;
	private SharedPreferences mPref;
	private settingItemView sivUpdate;
	private settingItemView siv_safecall;
	private settingItemView siv_dogwatch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("���settingView");
		setContentView(R.layout.activity_setting);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		initUpdate();
		initAddress();
		initAddressStyle();
		initAddressLocation();
		initSafeCall();
		initDogWatch();

	}

	private void initDogWatch() {
		siv_dogwatch = (settingItemView) findViewById(R.id.siv_dogwatch);
		boolean b = ServiceUtils.isServiceRunning(this, "service.dogWatch");
		// �ҵ���ǰ������
		if (b) {
			siv_dogwatch.setChecked(true);
		} else {
			siv_dogwatch.setChecked(false);
		}
		// �Ե��������Ӧ
		siv_dogwatch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �жϵ�ǰ�Ĺ�ѡ״̬ ,��ǰҪ�ǹ�ѡ�����Ϊ����ѡ
				if (siv_dogwatch.isChecked()) {
					siv_dogwatch.setChecked(false);
					stopService(new Intent(settingActivity.this, dogWatch.class));
				} else {
					siv_dogwatch.setChecked(true);
					startService(new Intent(settingActivity.this,
							dogWatch.class));
				}
			}
		});
	}

	private void initAddressLocation() {
		settingclickView siv_ad_location = (settingclickView) findViewById(R.id.siv_ad_location);
		siv_ad_location.setTitle("��������������ʾλ��");
		siv_ad_location.setDesc("���Ĺ�������������ʾλ��");
		siv_ad_location.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(settingActivity.this,
						activity_drag.class));
			}
		});
	}

	private void initUpdate() {
		sivUpdate = (settingItemView) findViewById(R.id.siv_update);
		boolean siv = mPref.getBoolean("auto_update", true);
		// �ҵ���ǰ������
		if (siv) {
			sivUpdate.setChecked(true);
		} else {
			sivUpdate.setChecked(false);
		}
		// �Ե��������Ӧ
		sivUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �жϵ�ǰ�Ĺ�ѡ״̬ ,��ǰҪ�ǹ�ѡ�����Ϊ����ѡ
				if (sivUpdate.isChecked()) {
					sivUpdate.setChecked(false);
					mPref.edit().putBoolean("auto_update", false).commit();
				} else {
					sivUpdate.setChecked(true);
					mPref.edit().putBoolean("auto_update", true).commit();
				}
			}
		});

	}

	private void initSafeCall() {
		siv_safecall = (settingItemView) findViewById(R.id.siv_safecall);
		boolean siv = mPref.getBoolean("siv_safecall", true);
		// �ҵ���ǰ������
		if (siv) {
			siv_safecall.setChecked(true);
		} else {
			siv_safecall.setChecked(false);
		}
		// �Ե��������Ӧ
		siv_safecall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �жϵ�ǰ�Ĺ�ѡ״̬ ,��ǰҪ�ǹ�ѡ�����Ϊ����ѡ
				if (siv_safecall.isChecked()) {
					siv_safecall.setChecked(false);
					mPref.edit().putBoolean("siv_safecall", false).commit();
					stopService(new Intent(settingActivity.this, safecall.class));
				} else {
					siv_safecall.setChecked(true);
					mPref.edit().putBoolean("siv_safecall", true).commit();
					startService(new Intent(settingActivity.this,
							safecall.class));
				}
			}
		});

	}

	private void initAddress() {
		siv_address = (settingItemView) findViewById(R.id.siv_address);
		final boolean serviceRunning = ServiceUtils.isServiceRunning(
				settingActivity.this, "service.addressService");
		if (serviceRunning) {
			siv_address.setChecked(true);
		} else {
			siv_address.setChecked(false);
		}
		siv_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("����ɹ�");
				if (siv_address.isChecked()) {
					System.out.println("��ֹ����");
					siv_address.setChecked(false);
					stopService(new Intent(settingActivity.this,
							addressService.class));// ֹͣ�����ط���
				} else {
					System.out.println("��������");
					siv_address.setChecked(true);
					startService(new Intent(settingActivity.this,
							addressService.class));// ���������ط���
				}
			}
		});
	}

	final String[] items = new String[] { "��͸��", "������", "��ʿ��", "������", "ƻ����" };
	private int styleItem;
	private settingclickView siv_style;

	public void initAddressStyle() {
		System.out.println("�õ�clickView");
		siv_style = (settingclickView) findViewById(R.id.siv_addressStyle);
		System.out.println("�õ�clickView�ɹ�");
		siv_style.setTitle("��������ʾ����");

		styleItem = mPref.getInt("style", 0);
		siv_style.setDesc(items[styleItem]);
		siv_style.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showSingleChooseDailog();
			}
		});
	}

	protected void showSingleChooseDailog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				settingActivity.this);
		builder.setTitle("��������ʽѡ��");

		builder.setSingleChoiceItems(items, styleItem,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mPref.edit().putInt("style", which).commit();
						dialog.dismiss();
						siv_style.setDesc(items[which]);
					}
				});
		builder.setNegativeButton("ȡ��", null);
		builder.show();
	}
}