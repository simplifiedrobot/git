package receiver;



import utils.mToast;
import dao.dao_adress;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class outCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String number=getResultData();
		String address =dao_adress.query(number);
		mToast.show(context, address);
		
	}

}
