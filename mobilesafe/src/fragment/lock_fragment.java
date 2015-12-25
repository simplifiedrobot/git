package fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import bean.taskInfo;

import com.example.mobilesafe.R;

import dao.TaskInfoparser;
import dao.lockdateDao;

public class lock_fragment extends Fragment {

	private TextView tv_text;
	private ListView lv_lock;
	private Myadapter myadapter;
	private List<taskInfo> taskInfo;
	private View view;
	private ViewHolder holder;
	private List<taskInfo> lock_list;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view1 = inflater.inflate(R.layout.fragment_lock, null);
		tv_text = (TextView) view1.findViewById(R.id.tv_text);
		lv_lock = (ListView) view1.findViewById(R.id.lv_lock);
		return view1;
	}

	public void onStart() {
		super.onStart();
		// ��������ĳ���
		lock_list = new ArrayList<taskInfo>();
		System.out.println("������lock");
		myadapter = new Myadapter();
		taskInfo = TaskInfoparser.getTaskInfo(getActivity());
		for (taskInfo info : taskInfo) {
			boolean b = lockdateDao.query(getActivity(), info.getPackageName());
			if (b == true) {
				lock_list.add(info);
			} else {
				continue;
			}
		}
		lv_lock.setAdapter(myadapter);
	}

	class Myadapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lock_list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return lock_list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				holder = new ViewHolder();
				view = View.inflate(getActivity(), R.layout.item_lock, null);
				holder.tv_appname = (TextView) view
						.findViewById(R.id.tv_appname);
				holder.iv_aapicon = (ImageView) view
						.findViewById(R.id.iv_aapicon);
				holder.iv_aaplock = (ImageView) view
						.findViewById(R.id.iv_aaplock);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.tv_appname.setText(lock_list.get(position).getName());
			System.out.println(lock_list.get(position).getName());
			holder.iv_aapicon.setBackgroundDrawable(lock_list.get(position).getIcon());
			holder.iv_aaplock
					.setBackgroundResource(R.drawable.list_button_lock_default);
			lv_lock.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int arg2, long arg3) {
					TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1.0f, 
							Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
					translateAnimation.setDuration(500);
					view.startAnimation(translateAnimation);
					new Thread(){
					public void run() {
						SystemClock.sleep(500);
						getActivity().runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								lockdateDao.delete(getActivity(), lock_list.get(arg2)
										.getPackageName());
								lock_list.remove(arg2);
								myadapter.notifyDataSetChanged();
							}
						});
					};
			    		
					}.start();
				
				}
			});
			return view;
		}
	}

	static class ViewHolder {
		TextView tv_appname;
		ImageView iv_aaplock;
		ImageView iv_aapicon;
	}

}
