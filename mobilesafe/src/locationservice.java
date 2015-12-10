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
	//获得手机的经纬度
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	public void onCreate(){
		super.onCreate();
		mPref = getSharedPreferences("config", MODE_PRIVATE);
	    lm = (LocationManager) getSystemService(LOCATION_SERVICE);
	     criteria = new Criteria();
	     //是否允许付费
	     criteria.setCostAllowed(true);
	     criteria.setAccuracy(Criteria.ACCURACY_FINE);
	     //获取最佳
	     String bestProvider=lm.getBestProvider(criteria,true);
	
	     listener =new MylocationListener();
	lm.requestLocationUpdates(bestProvider, 0, 0, listener);
	}
	class MylocationListener implements LocationListener{
		
		//位置发生变化
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		//将获取的经纬度保存在sp中
		mPref.edit().putString("location","jindu"+ location.getLongitude()+"weidu"+location.getLatitude())
		.commit();
		//停掉service
		stopSelf();
	}
  //位置提供者状态发生改变
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		System.out.println("onStatusChanged");
	}
//用户打开gps
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		System.out.println("onProviderEnabled");
	}
	//用户关闭gps
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		System.out.println("onProviderDisabled");
	}
	}
	public void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);// 当activity销毁时,停止更新位置, 节省电量
	}
}
