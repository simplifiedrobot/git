package Adapter;

import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	
    public 	Context mcontex;
    public List<T> list;
	public MyBaseAdapter(Context mcontex, List<T> list) {
		super();
		this.mcontex = mcontex;
		this.list = list;
	}
	public MyBaseAdapter() {
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public  Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

}
