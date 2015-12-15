package service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mobilesafe.R;

import dao.dao_adress;

public class addressService extends Service {

	private outCallReceiver receiver;
	private myListener listener;
	private TelephonyManager tm;

	private SharedPreferences mPref;
	private WindowManager mWM;
	private View view2;
	private int lastX;
	private int lastY;
	private int height;
	private int widht;
	private WindowManager.LayoutParams params;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
	         tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
	         listener = new myListener();
	         tm.listen(listener,PhoneStateListener.LISTEN_CALL_STATE);
	         mPref= getSharedPreferences("config", MODE_PRIVATE);
	         //��̬ע��㲥
	         System.out.println("��̬ע��㲥");
	         receiver=new outCallReceiver();
	         IntentFilter filter =new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
	         registerReceiver(receiver, filter);
	}
	  class myListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			super.onCallStateChanged(state, incomingNumber);
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				System.out.println("�绰����");
				String  address=dao_adress.query(incomingNumber);
			showToast(address);
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("�޵绰");
				if(mWM!=null&&view2!=null){
					mWM.removeView(view2);
					view2=null;
				}
				break;
			default:
				break;
			}
		}
	  }
	  public class outCallReceiver extends BroadcastReceiver {
			@Override
			public void onReceive(Context context, Intent intent) {
				System.out.println("����ȥ�����1");
				String number=getResultData();
				String address =dao_adress.query(number);
				 showToast(address);
			}
		}

	@Override
	public void onDestroy() {
		super.onDestroy();
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);//ֹͣ�������
		unregisterReceiver(receiver);//ע���㲥
	}
				public   void showToast(String text) {
			 mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
			
			params = new WindowManager.LayoutParams();
			params.height = WindowManager.LayoutParams.WRAP_CONTENT;
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
					| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
			params.format = PixelFormat.TRANSLUCENT;
			params.type = WindowManager.LayoutParams.TYPE_PHONE;  
			params.gravity=Gravity.LEFT+Gravity.TOP;
     	    height = mWM.getDefaultDisplay().getHeight();
		    widht = mWM.getDefaultDisplay().getWidth(); 
		    lastX = mPref.getInt("lastX", 0);
		    lastY = mPref.getInt("lastY", 0);
			params.x=lastX;
			params.y=lastY;
			
			params.setTitle("Toast");
			view2 = View.inflate(this, R.layout.toast_address, null);
			int style=mPref.getInt("style", 0);
			final int []  styleItem = new int[] { R.drawable.call_locate_white,
					R.drawable.call_locate_orange, R.drawable.call_locate_blue,
					R.drawable.call_locate_gray, R.drawable.call_locate_green };
			view2.setBackgroundResource(styleItem[style]);
			TextView tvText = (TextView) view2.findViewById(R.id.tv_number);
			tvText.setText(text);
			// ��view�������Ļ��(Window)
			mWM.addView(view2, params);
     		view2.setOnTouchListener(new OnTouchListener() {
				private int startX;
				private int startY;
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						startX = (int) event.getRawX();
						startY = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_MOVE:
						int endX = (int) event.getRawX();
						int endY = (int) event.getRawY();
						// �����ƶ�ƫ����
						int dx = endX - startX;
						int dy = endY - startY;
						// ���¸���λ��
						params.x += dx;
						params.y += dy;
						// ��ֹ����ƫ����Ļ
						if (params.x < 0) {
							params.x = 0;
						}
						if (params.y < 0) {
							params.y = 0;
						}
						// ��ֹ����ƫ����Ļ
						if (params.x > widht - view2.getWidth()) {
							params.x = widht - view2.getWidth();
						}
						if (params.y > height - view2.getHeight()) {
							params.y = height - view2.getHeight();
						}
						// System.out.println("x:" + params.x + ";y:" + params.y);
						mWM.updateViewLayout(view2, params);
						// ���³�ʼ���������
						startX = (int) event.getRawX();
						startY = (int) event.getRawY();
						break;
					case MotionEvent.ACTION_UP:
						// ��¼�����
						Editor edit = mPref.edit();
						edit.putInt("lastX", params.x);
						edit.putInt("lastY", params.y);
						edit.commit();
						break;
					default:
						break;
					}
					return false;
				}
			});
		}
			}




