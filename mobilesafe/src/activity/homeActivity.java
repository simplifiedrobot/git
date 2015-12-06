package activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
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

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_home);
    	
    	mPref= getSharedPreferences("config", MODE_PRIVATE);
    	   gv=(GridView) findViewById(R.id.gv_home);
    	   gv.setAdapter(new myAdapter());
    	gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 8://设置中心
			        startActivity(new Intent(homeActivity.this, settingActivity.class));
					break;

				default:
					break;
				}
			}
		});
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
			View view=View.inflate(homeActivity.this,  R.layout.home_list_item,   null);
			ImageView  iv=(ImageView) view.findViewById(R.id.iv_item);
			TextView  tv=(TextView) view.findViewById(R.id.tv_item);
			iv.setImageResource(mPics[position]);

			return view;
		}
	}
}
