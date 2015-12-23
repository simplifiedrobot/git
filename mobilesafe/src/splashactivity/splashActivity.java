package splashactivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import utils.mToast;
import utils.streamUtils;
import activity.homeActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilesafe.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class splashActivity extends Activity {

	protected static final int CODE_UPDATE_DIALOG= 0;
	protected static final int CODE_ENTER_HOME= 1;
	protected static final int CODE_URL_ERROR= 2;
	protected static final int CODE_NET_ERROR = 3;
	protected static final int CODE_JSON_ERROR = 4;
	private TextView et;
	
    private String mVersionName;
	private int mVersionCode ;
	private String desc;
	private String downUrl;
	private TextView te_progress;
	private Handler mHandler=new Handler(){
	    public void handleMessage(Message msg) {
	    	switch (msg.what) {
			case 0:
				showUpdateDailog();
				break;
			case 1:
				enterHome();
				   System.out.println("取得消息成功");
				break;
			case 2:
				Toast.makeText(splashActivity.this, "网络地址错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case 3:
				mToast.show(splashActivity.this, "网络错误");
				enterHome();
				break;
			case 4:
				Toast.makeText(splashActivity.this, "数据解析错误", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			default:
				break;
			}
	    };
    };
	private SharedPreferences mPref;
	private RelativeLayout rl_root;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		et = (TextView) findViewById(R.id.tv);
		te_progress = (TextView) findViewById(R.id.textView1);
		et.setText("版本号"+getVersonName());
		copyDB("address.db");
		copyDB("antivirus.db");
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		boolean b = mPref.getBoolean("auto_update", true);
		if(b){
			checkVersoin();
		}else{
			mHandler.sendEmptyMessageDelayed(CODE_ENTER_HOME, 200);//延迟2秒发送消息
		}
	AlphaAnimation anim=new AlphaAnimation(0.3f, 1f);
	anim.setDuration(200);
	rl_root.startAnimation(anim);
	
	}

	private void copyDB(String dbName) {
		File  destFile=new File(getFilesDir(), dbName);
		if(destFile.exists()){
			System.out.println("数据库已存在");
			return;
		}else{
			System.out.println("复制数据库");
			InputStream in=null;
			FileOutputStream out=null;
			try {
				in=getAssets().open(dbName);
			    out =new FileOutputStream(destFile);
			    byte [] b=new byte[1024];
			    int len=0;
			    while((len=in.read(b))!=-1){
			    	out.write(b, 0, len);
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					in.close();
					out.close();
					System.out.println("复制数据库成功");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getVersonName(){//得到版本名和号
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			int versionCode = packageInfo.versionCode;
			String versionName = packageInfo.versionName;
			System.out.println("versionName"+versionName+";versioncode="+versionCode);
			return versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}
	private void checkVersoin(){//检查版本号，判断是否更新
	      final long startTime=System.currentTimeMillis();
		new Thread(){
			public void run() {
			     HttpURLConnection conn=null;
				Message msg = Message.obtain();
				try {
					URL url=new URL("http://10.0.2.2:8080/update.json");
					conn = (HttpURLConnection)url.openConnection();
				     conn.setRequestMethod("GET");
				     conn.setConnectTimeout(5000);
				     conn.setReadTimeout(5000);
					conn.connect();
				     int rsc=conn.getResponseCode();
		            if(rsc==200){
		            	System.out.println("连接成功");
		            	InputStream is =conn.getInputStream();
		                String result=streamUtils.readFromStream(is);
		                System.out.println("网络返回"+result);
		                //解析json
		                JSONObject ob=new JSONObject(result);
		             mVersionName = ob.getString("versionName");
		             mVersionCode = ob.getInt("versionCode");
		             desc = ob.getString("description");
		             downUrl = ob.getString("downloadUrl");
		             System.out.println("解析json成功");
		             if(mVersionCode>getVersion()){
		            	 msg.what=CODE_UPDATE_DIALOG;
		             }else{
		            	 msg.what=CODE_ENTER_HOME;
		            	   System.out.println("发消息成功");
		             }
		            }
				} catch (MalformedURLException e) {
					msg.what=CODE_URL_ERROR;
					e.printStackTrace();
				} catch (IOException e) {
					msg.what = CODE_NET_ERROR;
					e.printStackTrace();
				} catch (JSONException e) {
					msg.what = CODE_JSON_ERROR;
					e.printStackTrace();
				}finally{
					long endTime=System.currentTimeMillis();
					long time=endTime-startTime;
					if(time<500){
						long t=500-time;
						try {
							Thread.sleep(t);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				mHandler.sendMessage(msg);
				if(conn!=null){
					conn.disconnect();
				}
				}
			}
		}.start();
	}
		private int getVersion() {
		PackageManager packageManager = getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
			int versionCode1=packageInfo.versionCode;
			return versionCode1;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
		};
		 private void enterHome() {
			Intent intent=new Intent(this,homeActivity.class);
			startActivity(intent);
			finish();
		}
		protected void showUpdateDailog() {
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("最新版本"+mVersionName);
			builder.setMessage(desc);
			builder.setPositiveButton("更新", new OnClickListener(){
				public void onClick(DialogInterface arg0, int arg1) {
					down();
				}
			});
			builder.setNegativeButton("取消", new OnClickListener(){
				public void onClick(DialogInterface arg0, int arg1) {
					enterHome();
				}
			});
			builder.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					enterHome();
				}
			});
			builder.show();
		}                                           
		protected void down() { //下载apk                          
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				te_progress.setVisibility(View.VISIBLE);//显示进度
				String target=Environment.getExternalStorageDirectory()+"/update.apk";
				
				HttpUtils utils = new HttpUtils();
			     utils.download(downUrl, target,
						true, 
						true,
						new RequestCallBack<File>() {
							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {
								super.onLoading(total, current, isUploading);
								te_progress.setText("下载进度"+current*100/total+"%");
							}
							@Override
							public void onSuccess(ResponseInfo<File> arg0) {
								Intent intent=new Intent(Intent.ACTION_VIEW);
								intent.addCategory("android.intent.category.DEFAULT");
								intent.setDataAndType(Uri.fromFile(arg0.result), 
										"application/vnd.android.package-archive");
								startActivityForResult(intent, 0);//如果用户取消安装的话,会返回结果，或者第二个activity调用finish()方法时，回调方法onActivityResult;
							}
							@Override
							public void onFailure(HttpException arg0, String arg1) {
								Toast.makeText(splashActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
							}
						});
			}else {
				Toast.makeText(this, "没有找到sdcard", Toast.LENGTH_SHORT).show();
			}
		}
		
	   	protected void onActivityResult(int requestCode, int resultCode, Intent data){//取消安装回调此方法
	   		super.onActivityResult(requestCode, resultCode, data);
		}
}