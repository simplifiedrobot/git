package activity;
import utils.MD5Utils;
import utils.mToast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesafe.R;





public class homeActivity extends Activity {
    private String [] mItems = new String[]{"手机防盗" ,"通讯卫士" ,"软件管理",
			"进程管理" ,"流量统计" ,"手机杀毒" ,"缓存清理" ,"高级工具" ,"设置中心"};
	private int [] mPics= new int[]{R.drawable.home_safe,   R.drawable.home_callmsgsafe,  R.drawable.home_apps,
			R.drawable.home_taskmanager,   R.drawable.home_netmanager,   R.drawable.home_trojan,
			R.drawable.home_sysoptimize,   R.drawable.home_tools,   R.drawable.home_settings};
	private GridView gv;
	private SharedPreferences mPref;
	private String set_password;
	private String set_password_confirm;
	private String in_pass;
	private String password;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_home);
	   gv=(GridView) findViewById(R.id.gv_home);
	   gv.setAdapter(new myAdapter());
	   mPref= getSharedPreferences("config", MODE_PRIVATE);
    	gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 8://设置中心
					startActivity(new Intent(homeActivity.this, settingActivity.class));
					break;
				case 7://设置中心
					startActivity(new Intent(homeActivity.this, aToolsActivity.class));
					break;
				case 5://手机杀毒
					startActivity(new Intent(homeActivity.this, activity_virus.class));
					break;
				case 2://设置中心
					startActivity(new Intent(homeActivity.this, appManeger.class));
					break;
				case 0://防盗中心
					showPasswordDialog();
					break;
					
				case 3://进程管理
					startActivity(new Intent(homeActivity.this, activity_TaskManager.class));
					break;
				case 1://防盗中心
					startActivity(new Intent(homeActivity.this, activity_safeCall2.class));
					break;
				default:
					break;
				}
			}
		});
    }
	protected void showPasswordDialog() {
	//判断是否有密码
		System.out.println("进入show对话框");
		String savePassword=mPref.getString("password", null);
		System.out.println("拿过密码");
		if(!TextUtils.isEmpty(savePassword)){
			//如果不为空，输入密码框
			showPasswordInputDialog();
		}else{
			System.out.println("密码为空");
			//如果为空，弹出设置密码框
			showPasswordSetDialog();
		}
	}
	private void showPasswordSetDialog() {
		// 设置密码框
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		final AlertDialog dialog =builder.create();
		//final AlertDialog dialog = new AlertDialog.Builder(homeActivity.this).create();
		System.out.println("拿到了了对话框");
		View view=View.inflate(this,R.layout.dailog_set_password, null);
		dialog.setView(view, 0, 0, 0, 0);
		System.out.println("填充了对话框");
		 final EditText  et_password=(EditText) view.findViewById(R.id.et_pass);
	 final EditText  et_passwordconfirm=(EditText) view.findViewById(R.id.et_passconfirm);
	    Button bt_ok = (Button) view.findViewById(R.id.bt_ok);
	    Button bt_cancle= (Button) view.findViewById(R.id.bt_cancle);
		System.out.println("找到了按钮");
		bt_ok.setOnClickListener(new  OnClickListener() {
			@Override//确定按钮
			public void onClick(View v) {
				System.out.println("进入了确定点击");
				set_password =et_password.getText().toString();
				set_password_confirm = et_passwordconfirm.getText().toString();
				if(!TextUtils.isEmpty(set_password)&&!set_password_confirm.isEmpty()){
					if(set_password.equals(set_password_confirm)){
						mPref.edit().putString("password", MD5Utils.encode(set_password)).commit();
						startActivity(new Intent(homeActivity.this,activity_step1.class));
						dialog.dismiss();
					}else{
						mToast.show(homeActivity.this, "输入密码不一致");
					}
				}else{
					mToast.show(homeActivity.this, "输入不能为空");
				}
			}
		});
		bt_cancle.setOnClickListener(new OnClickListener() {
		//	取消
			@Override
			public void onClick(View v) {
				dialog.dismiss();//隐藏dialog
			}
		});
		System.out.println("对话显示前");
		dialog.show();
		System.out.println("对话显示后");
	}
	private void showPasswordInputDialog() {
		// 输入密码框
		final AlertDialog dialog2=new AlertDialog.Builder(homeActivity.this).create();
		View view=View.inflate(homeActivity.this, R.layout.dailog_input_password, null);
		dialog2.setView(view, 0, 0, 0, 0);
		final EditText et_pass=(EditText) view.findViewById(R.id.et_pass1);
		Button bt_3=(Button) view.findViewById(R.id.button3);
		Button bt_4=(Button) view.findViewById(R.id.button4);
		bt_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				in_pass= et_pass.getText().toString();
				if(!TextUtils.isEmpty(in_pass)){
					password = mPref.getString("password", null);
					if(password.equals(MD5Utils.encode(in_pass))){
						dialog2.dismiss();
						startActivity(new Intent(homeActivity.this, activity_lost.class));
					}else{
						mToast.show(homeActivity.this,"密码错误");
					}
				}else{
					mToast.show(homeActivity.this, "密码不能为空");
				}
			}
		});
		bt_4.setOnClickListener(new OnClickListener() {
			//	取消
				@Override
				public void onClick(View v) {
					dialog2.dismiss();
				}
			});
		dialog2.show();
	}
	class myAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return mItems.length;
		}
		@Override
		public Object getItem(int arg0) {
			return mItems[arg0];
		}
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view=View.inflate(homeActivity.this,  R.layout.home_list_item,  null);
			ImageView  iv=(ImageView) view.findViewById(R.id.iv_item);
			TextView  tv=(TextView) view.findViewById(R.id.tv_item);
			tv.setText(mItems[position]);
			iv.setImageResource(mPics[position]);
			return view;
		}
	}
}
