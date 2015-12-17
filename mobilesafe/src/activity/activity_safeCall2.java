package activity;

import java.util.List;

import utils.mToast;
import Adapter.MyBaseAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import bean.blackNumberInfo;

import com.example.mobilesafe.R;

import dao.BlackNumberDao;

public class activity_safeCall2 extends Activity {

	private BlackNumberDao numberDao;
	private List<blackNumberInfo> parNumber;
	private View view;
	private ListView list_view;
	private LinearLayout l_pb;
	private int startIndex;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safecall);
		InitDate();
		InitUI();
		startIndex = 0;
		list_view = (ListView) findViewById(R.id.list_view);
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			if (adapter == null) {
				adapter = new MyAdapter(activity_safeCall2.this, parNumber);
				list_view.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
			super.handleMessage(msg);
			l_pb.setVisibility(View.INVISIBLE);
		}
	};
	private int maxCount;
	private int totalPage;
	private int getTotalPage;
	private String mode;
	private AlertDialog dialog;

	public void InitDate() {

		numberDao = new BlackNumberDao(this);
		maxCount = 15;
		getTotalPage = numberDao.getTotalPage();
		new Thread() {
			public void run() {
				if (parNumber == null) {
					parNumber = numberDao.findParNumber2(startIndex, maxCount);
				} else {
					parNumber.addAll(numberDao.findParNumber2(startIndex,
							maxCount));
				}
				SystemClock.sleep(200);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}

	private void InitUI() {

		l_pb = (LinearLayout) findViewById(R.id.pb);
		l_pb.setVisibility(View.VISIBLE);
	}

	public void add(View v) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		dialog = builder.create();
		
		dialog.setTitle("添加黑名单号码");
		final View view1 = View.inflate(activity_safeCall2.this, R.layout.dailog_add_blacknum, null);
		final EditText et_addBlackPhone = (EditText) view1
				.findViewById(R.id.et_addBlackPhone);
		final blackNumberInfo info=new blackNumberInfo();
		final CheckBox cb_sms = (CheckBox) view1.findViewById(R.id.cb_sms);
		final CheckBox cb_phone = (CheckBox) view1.findViewById(R.id.cb_phone);
		Button bt_ok_add = (Button) view1.findViewById(R.id.bt_ok_add);
		Button bt_cancle_add = (Button) view1.findViewById(R.id.bt_cancle_add);
		bt_ok_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String addBlackNum = et_addBlackPhone.getText().toString();
				info.setNumber(addBlackNum);
				 if(cb_phone.isChecked()&& cb_sms.isChecked()){
					mode = "1";
					 info.setMode("1");
		         }else if(cb_phone.isChecked()){
		        	mode="2";
		        	 info.setMode("2");
		         }else if(cb_sms.isChecked()){
		         mode="3";
		        	 info.setMode("3");
		         }else{
		            mToast.show(activity_safeCall2.this, "请选择拦截方式");
		         }
				 numberDao.add(addBlackNum, mode);
				 parNumber.add(0, info);
				 dialog.dismiss();
				 if(adapter == null){
	                    adapter = new MyAdapter(activity_safeCall2.this, parNumber);
	                    list_view.setAdapter(adapter);
	                }else{
	                    adapter.notifyDataSetChanged();
	                }
			}
		});
		bt_cancle_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.setView(view1, 0, 0, 0, 0);;
		dialog.show();
	}

	class MyAdapter extends MyBaseAdapter<blackNumberInfo> {
		public MyAdapter() {
			super();
		}

		public MyAdapter(Context mcontex, List<blackNumberInfo> list) {
			super(mcontex, list);
		}

		private ViewHolder holder;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(activity_safeCall2.this,
						R.layout.listview_safecall_layout, null);
				holder = new ViewHolder();
				holder.tv_blacknum = (TextView) convertView
						.findViewById(R.id.tv_blacknum);
				holder.tv_mode = (TextView) convertView
						.findViewById(R.id.tv_mode);
				holder.iv_delete = (ImageView) convertView
						.findViewById(R.id.iv_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_blacknum.setText(list.get(position).getNumber());
			String mode = list.get(position).getMode();
			switch (Integer.parseInt(mode)) {
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
			final blackNumberInfo blackNum = list.get(position);
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String num = blackNum.getNumber();
					Boolean b = numberDao.delete(num);
					if (b) {
						mToast.show(activity_safeCall2.this, "删除成功");
						list.remove(blackNum);
						// 刷新界面
						adapter.notifyDataSetChanged();
					} else {
						mToast.show(activity_safeCall2.this, "删除失败");
					}
				}
			});

			list_view.setOnScrollListener(new OnScrollListener() {
				// 滚动状态改变
				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
					switch (scrollState) {
					case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
						int lastItem = list_view.getLastVisiblePosition();
						if (lastItem == parNumber.size() - 1) {
							startIndex = startIndex + maxCount;
							InitDate();
							System.out.println("SCROLL_STATE_IDLE");
						} else if (lastItem >= getTotalPage) {
							mToast.show(activity_safeCall2.this, "已经到最后一条");
						}
						break;
					case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
						System.out.println("SCROLL_STATE_FLING");
						break;
					case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
						System.out.println("SCROLL_STATE_TOUCH_SCROLL");

						break;

					default:
						break;
					}
				}

				// 滚动
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {

				}
			});
			return convertView;
		}
	}

	static class ViewHolder {
		TextView tv_blacknum;
		TextView tv_mode;
		ImageView iv_delete;
	}

}
