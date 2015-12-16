package activity;




import java.util.List;

import Adapter.MyBaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import bean.blackNumberInfo;

import com.example.mobilesafe.R;

import dao.BlackNumberDao;

public class activity_safeCall extends Activity {

	  private BlackNumberDao numberDao;
	private List<blackNumberInfo> AllNumber;
	private View view;
	private ListView list_view;
	private LinearLayout l_pb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safecall);
		InitDate();
		InitUI();
		list_view = (ListView) findViewById(R.id.list_view);
	}
  	Handler handler=new Handler(){
  		public void handleMessage(Message msg) {
  			super.handleMessage(msg);
  			System.out.println("接受到了消息");
  			l_pb.setVisibility(View.INVISIBLE);
  			ListAdapter adapter=new MyAdapter(activity_safeCall.this,AllNumber);
  			list_view.setAdapter(adapter);
  		}
  	};
	public void InitDate(){
		numberDao = new BlackNumberDao(this);
		new Thread(){
			public void run() {
				AllNumber =numberDao.findAllNumber();
				SystemClock.sleep(500);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
	private void InitUI() {
		l_pb = (LinearLayout) findViewById(R.id.pb);
		l_pb.setVisibility(View.VISIBLE);
	}
	class MyAdapter extends MyBaseAdapter<blackNumberInfo>{
		public MyAdapter() {
			super();
			// TODO Auto-generated constructor stub
		}
		public MyAdapter(Context mcontex, List<blackNumberInfo> list) {
			super(mcontex, list);
			// TODO Auto-generated constructor stub
		}
		private ViewHolder holder;
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("进入适配器");
			if(convertView==null){
				convertView = View.inflate(activity_safeCall.this, R.layout.listview_safecall_layout, null);
				holder = new ViewHolder();
				holder.tv_blacknum=(TextView) convertView.findViewById(R.id.tv_blacknum);
				holder.tv_mode=(TextView) convertView.findViewById(R.id.tv_mode);
				holder.iv_delete=(ImageView) convertView.findViewById(R.id.iv_delete);
			    convertView.setTag(holder);
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			System.out.println("填充成功");
			 holder.tv_blacknum.setText(list.get(position).getNumber());
	            String mode = list.get(position).getMode();
	         switch (Integer.parseInt(mode)){
			case 1:
				 holder.tv_mode.setText("来电拦截+短信");
				break;
			case 2:
				 holder.tv_mode.setText("电话拦截");
				break;
			case 3:
				 holder.tv_mode.setText("短信拦截");
				break;
			default:
				break;
			}
	            System.out.println("填充成功，返回view");
		return convertView;
		}
	}
	static class  ViewHolder{
		TextView tv_blacknum;
		TextView tv_mode;
		ImageView iv_delete;
	}
}
