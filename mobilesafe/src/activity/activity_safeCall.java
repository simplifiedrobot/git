package activity;

import java.util.List;

import utils.mToast;
import Adapter.MyBaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	private int currentPage;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safecall2);
		InitDate();
		InitUI();
		currentPage = 1;

		list_view = (ListView) findViewById(R.id.list_view);
	}

	Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			System.out.println("���ܵ�����Ϣ");
			TextView tv_pageNum = (TextView) findViewById(R.id.tv_pageNum);
			tv_pageNum.setText(currentPage + "/" + totalPage);
			l_pb.setVisibility(View.INVISIBLE);
			adapter = new MyAdapter(activity_safeCall.this, AllNumber);
			list_view.setAdapter(adapter);
		}
	};
	private int pageSize;
	private int totalPage;

	public void InitDate() {
		numberDao = new BlackNumberDao(this);
		pageSize = 15;
		int getTotalPage = numberDao.getTotalPage();
		if (getTotalPage % pageSize == 0) {
			totalPage = getTotalPage / pageSize;
		} else {
			totalPage =(getTotalPage / pageSize)+1;
		}
		new Thread() {
			public void run() {
				AllNumber = numberDao.findParNumber(pageSize, currentPage);
				SystemClock.sleep(200);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}

	public void prePage(View v) {
		if (currentPage <= 1) {
			mToast.show(this, "�Ѿ��ǵ�һҳ");
			return;
		} else {
			currentPage = currentPage - 1;
		}
		InitDate();
	}

	public void nextPage(View v) {

		if (currentPage >= (totalPage)) {
			mToast.show(this, "�Ѿ������һҳ");
			return;
		} else {
			currentPage = currentPage + 1;
		}
		InitDate();
	}

	public void jumpPage(View v) {
		EditText et_pageNum = (EditText) findViewById(R.id.et_pageNum);
		String page = et_pageNum.getText().toString();
		if (!TextUtils.isEmpty(page)) {
			currentPage = Integer.parseInt(page);
			if (currentPage < 1 || currentPage > totalPage) {
				mToast.show(this, "��������ȷ��ҳ��");
				return;
			} else {
				InitDate();
			}
		}

	}

	private void InitUI() {
	
		l_pb = (LinearLayout) findViewById(R.id.pb);
		l_pb.setVisibility(View.VISIBLE);
	}

	class MyAdapter extends MyBaseAdapter<blackNumberInfo> {
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
			if (convertView == null) {
				convertView = View.inflate(activity_safeCall.this,
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
				holder.tv_mode.setText("��������+����");
				break;
			case 2:
				holder.tv_mode.setText("�绰����");
				break;
			case 3:
				holder.tv_mode.setText("��������");
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
						mToast.show(activity_safeCall.this, "ɾ���ɹ�");
						list.remove(blackNum);
						// ˢ�½���
						adapter.notifyDataSetChanged();
					} else {
						mToast.show(activity_safeCall.this, "ɾ��ʧ��");
					}
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
