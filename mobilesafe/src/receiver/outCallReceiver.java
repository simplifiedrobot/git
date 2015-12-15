package receiver;



import utils.mToast;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import dao.dao_adress;

public class outCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("启动去点接听1");
		String number=getResultData();
		String address =dao_adress.query(number);
		 mToast.show(context, address);
	}
 
	
}
