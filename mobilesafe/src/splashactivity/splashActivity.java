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
				   System.out.println("ȡ����Ϣ�ɹ�");
				break;
			case 2:
				Toast.makeText(splashActivity.this, "�����ַ����", Toast.LENGTH_SHORT).show();
				enterHome();
				break;
			case 3:
				mToast.show(splashActivity.this, "�������");
				enterHome();
				break;
			case 4:
				Toast.makeText(splashActivity.this, "���ݽ�������", Toast.LENGTH_SHORT).show();
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
		et.setText("�汾��"+getVersonName());
		copyDB("address.db");
		copyDB("antivirus.db");
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
		mPref = getSharedPreferences("config", MODE_PRIVATE);
		boolean b = mPref.getBoolean("auto_update", true);
		if(b){
			checkVersoin();
		}else{
			mHandler.sendEmptyMessageDelayed(CODE_ENTER_HOME, 200);//�ӳ�2�뷢����Ϣ
		}
	AlphaAnimation anim=new AlphaAnimation(0.3f, 1f);
	anim.setDuration(200);
	rl_root.startAnimation(anim);
	
	}

	private void copyDB(String dbName) {
		File  destFile=new File(getFilesDir(), dbName);
		if(destFile.exists()){
			System.out.println("���ݿ��Ѵ���");
			return;
		}else{
			System.out.println("�������ݿ�");
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
					System.out.println("�������ݿ�ɹ�");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getVersonName(){//�õ��汾���ͺ�
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
	private void checkVersoin(){//���汾�ţ��ж��Ƿ����
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
		            	System.out.println("���ӳɹ�");
		            	InputStream is =conn.getInputStream();
		                String result=streamUtils.readFromStream(is);
		                System.out.println("���緵��"+result);
		                //����json
		                JSONObject ob=new JSONObject(result);
		             mVersionName = ob.getString("versionName");
		             mVersionCode = ob.getInt("versionCode");
		             desc = ob.getString("description");
		             downUrl = ob.getString("downloadUrl");
		             System.out.println("����json�ɹ�");
		             if(mVersionCode>getVersion()){
		            	 msg.what=CODE_UPDATE_DIALOG;
		             }else{
		            	 msg.what=CODE_ENTER_HOME;
		            	   System.out.println("����Ϣ�ɹ�");
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
			builder.setTitle("���°汾"+mVersionName);
			builder.setMessage(desc);
			builder.setPositiveButton("����", new OnClickListener(){
				public void onClick(DialogInterface arg0, int arg1) {
					down();
				}
			});
			builder.setNegativeButton("ȡ��", new OnClickListener(){
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
		protected void down() { //����apk                          
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				te_progress.setVisibility(View.VISIBLE);//��ʾ����
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
								te_progress.setText("���ؽ���"+current*100/total+"%");
							}
							@Override
							public void onSuccess(ResponseInfo<File> arg0) {
								Intent intent=new Intent(Intent.ACTION_VIEW);
								intent.addCategory("android.intent.category.DEFAULT");
								intent.setDataAndType(Uri.fromFile(arg0.result), 
										"application/vnd.android.package-archive");
								startActivityForResult(intent, 0);//����û�ȡ����װ�Ļ�,�᷵�ؽ�������ߵڶ���activity����finish()����ʱ���ص�����onActivityResult;
							}
							@Override
							public void onFailure(HttpException arg0, String arg1) {
								Toast.makeText(splashActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
							}
						});
			}else {
				Toast.makeText(this, "û���ҵ�sdcard", Toast.LENGTH_SHORT).show();
			}
		}
		
	   	protected void onActivityResult(int requestCode, int resultCode, Intent data){//ȡ����װ�ص��˷���
	   		super.onActivityResult(requestCode, resultCode, data);
		}
}