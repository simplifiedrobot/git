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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import bean.taskInfo;

import com.example.mobilesafe.R;

import dao.TaskInfoparser;
import dao.lockdateDao;

public class unlock_fragment extends Fragment {
	private TextView tv_text;
	private ListView lv_unlock;
	private Myadapter myadapter;
	private List<taskInfo> unlock_list;
	private View view;
	private	 ViewHolder holder;

	private List<taskInfo> taskInfo;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view1 = inflater.inflate(R.layout.fragment_lock, null);
		tv_text = (TextView) view1.findViewById(R.id.tv_text);
		lv_unlock = (ListView) view1.findViewById(R.id.lv_lock);
		return view1;
	}

	public void onStart() {
		System.out.println("onstart");
		super.onStart();
		unlock_list = new ArrayList<taskInfo>();
		System.out.println("Ω¯»Î¡Àlock");
		myadapter = new Myadapter();
		taskInfo = TaskInfoparser.getTaskInfo(getActivity());
		for (taskInfo info : taskInfo) {
			boolean b=lockdateDao.query(getActivity(), info.getPackageName());
			if(b==false){
				unlock_list.add(info);
			}else{
				continue;
			}
		}
		lv_unlock.setAdapter(myadapter);
	}
	class Myadapter extends BaseAdapter {
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return unlock_list.size();
		}
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return unlock_list.get(position);
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
				view = View.inflate(getActivity(),
						R.layout.item_lock, null);
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
			holder.tv_appname.setText(unlock_list.get(position).getName());
			System.out.println(unlock_list.get(position).getName());
			holder.iv_aapicon.setBackgroundDrawable(unlock_list.get(position).getIcon());
			lv_unlock.setOnItemClickListener(new OnItemClickListener() {
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
									lockdateDao.add(getActivity(), unlock_list.get(arg2).getPackageName());
									unlock_list.remove(arg2);
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
