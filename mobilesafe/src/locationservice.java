import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class locationservice extends Service {

	private SharedPreferences mPref;
	private LocationManager lm; 
	private Criteria criteria;
	private MylocationListener listener;
	//����ֻ��ľ�γ��
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	public void onCreate(){
		super.onCreate();
		mPref = getSharedPreferences("config", MODE_PRIVATE);
	    lm = (LocationManager) getSystemService(LOCATION_SERVICE);
	     criteria = new Criteria();
	     //�Ƿ�������
	     criteria.setCostAllowed(true);
	     criteria.setAccuracy(Criteria.ACCURACY_FINE);
	     //��ȡ���
	     String bestProvider=lm.getBestProvider(criteria,true);
	
	     listener =new MylocationListener();
	lm.requestLocationUpdates(bestProvider, 0, 0, listener);
	}
	class MylocationListener implements LocationListener{
		
		//λ�÷����仯
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//����ȡ�ľ�γ�ȱ�����sp��
		mPref.edit().putString("location","jindu"+ location.getLongitude()+"weidu"+location.getLatitude())
		.commit();
		//ͣ��service
		stopSelf();
	}
  //λ���ṩ��״̬�����ı�
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		System.out.println("onStatusChanged");
	}
//�û���gps
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		System.out.println("onProviderEnabled");
	}
	//�û��ر�gps
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		System.out.println("onProviderDisabled");
	}
	}
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);// ��activity����ʱ,ֹͣ����λ��, ��ʡ����
	}
}
