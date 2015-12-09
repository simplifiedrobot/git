package receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.example.mobilesafe.R;

public class smsReciver extends BroadcastReceiver {

	//拦截短信
	@Override
	public void onReceive(Context context, Intent intent) {
		Object []objects=(Object[]) intent.getExtras().get("pdus");
		
		for (Object object : objects) {//短信最多140字节，超出的话，会分成多条短信发送，所以是一个数组
			                                        //因为我们的短信很短，for只循环过一次
			SmsMessage message=SmsMessage.createFromPdu((byte[] )object);
			String originatingAddress = message.getOriginatingAddress();//短信来源号码
			String body = message.getMessageBody();//短信内容
			
			System.out.println(originatingAddress+":"+body);
			if("#*alarm*#".equals(body)){
				//播放报警音乐
			MediaPlayer player=	MediaPlayer.create(context, R.raw.ylzs);
			player.setVolume(1f, 1f);
			player.setLooping(true);
			player.start();
			abortBroadcast();//中断短信的传递
			}else if("#*location*#".equals(body)){
				System.out.println("获取经纬度");
				//获取经纬度坐标
			/*	context.startActivity(new Intent(context,locationService.class));
				SharedPreferences mPref=context.getSharedPreferences("config", context.MODE_PRIVATE);
				String location=mPref.getString("location", "getting location……");
				System.out.println("location:"+location);
				abortBroadcast();*/
			}else if ("#*wipedata*#".equals(body)) {
				System.out.println("远程清除数据");
				abortBroadcast();
			} else if ("#*lockscreen*#".equals(body)) {
				System.out.println("远程锁屏");
				abortBroadcast();
			}
		}
		
		

	}

}
