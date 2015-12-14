package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mobilesafe.R;


public class dao_addressToast{
  
					public static  void showToast(Context contex,String text) {
					SharedPreferences 	mPref= contex.getSharedPreferences("config", contex.MODE_PRIVATE);
				        //���WindowManager�ǹ̶����̣�ֱ�ӿ�����
				WindowManager mWM = (WindowManager) contex.getSystemService(Context.WINDOW_SERVICE);
				
				WindowManager.LayoutParams params = new WindowManager.LayoutParams();
				params.height = WindowManager.LayoutParams.WRAP_CONTENT;
				params.width = WindowManager.LayoutParams.WRAP_CONTENT;
				params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
				params.format = PixelFormat.TRANSLUCENT;
				params.type = WindowManager.LayoutParams.TYPE_TOAST;
				params.setTitle("Toast");
				//������������ʾ����ʽ
			View	view = View.inflate(contex, R.layout.toast_address, null);
				int style=mPref.getInt("style", 0);
				final int []  styleItem = new int[] { R.drawable.call_locate_white,
						R.drawable.call_locate_orange, R.drawable.call_locate_blue,
						R.drawable.call_locate_gray, R.drawable.call_locate_green };
				view.setBackgroundResource(styleItem[style]);
				TextView tvText = (TextView) view.findViewById(R.id.tv_number);
				tvText.setText(text);
				// ��view�������Ļ��(Window)
				mWM.addView(view, params);
				}
}
