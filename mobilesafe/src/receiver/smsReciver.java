package receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.example.mobilesafe.R;

public class smsReciver extends BroadcastReceiver {

	//���ض���
	@Override
	public void onReceive(Context context, Intent intent) {
		Object []objects=(Object[]) intent.getExtras().get("pdus");
		
		for (Object object : objects) {//�������140�ֽڣ������Ļ�����ֳɶ������ŷ��ͣ�������һ������
			                                        //��Ϊ���ǵĶ��ẓ́ܶ�forֻѭ����һ��
			SmsMessage message=SmsMessage.createFromPdu((byte[] )object);
			String originatingAddress = message.getOriginatingAddress();//������Դ����
			String body = message.getMessageBody();//��������
			
			System.out.println(originatingAddress+":"+body);
			if("#*alarm*#".equals(body)){
				//���ű�������
			MediaPlayer player=	MediaPlayer.create(context, R.raw.ylzs);
			player.setVolume(1f, 1f);
			player.setLooping(true);
			player.start();
			abortBroadcast();//�ж϶��ŵĴ���
			}else if("#*location*#".equals(body)){
				System.out.println("��ȡ��γ��");
				//��ȡ��γ������
			/*	context.startActivity(new Intent(context,locationService.class));
				SharedPreferences mPref=context.getSharedPreferences("config", context.MODE_PRIVATE);
				String location=mPref.getString("location", "getting location����");
				System.out.println("location:"+location);
				abortBroadcast();*/
			}else if ("#*wipedata*#".equals(body)) {
				System.out.println("Զ���������");
				abortBroadcast();
			} else if ("#*lockscreen*#".equals(body)) {
				System.out.println("Զ������");
				abortBroadcast();
			}
		}
		
		

	}

}
