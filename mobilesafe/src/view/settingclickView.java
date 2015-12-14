

package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;


public class settingclickView extends RelativeLayout {
	private TextView tv_title;
	private TextView tv_desc;
	private String mtitle;                                                                     
	public settingclickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
		// TODO Auto-generated constructor stub
	}
	public settingclickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		// TODO Auto-generated constructor stub
	}
	public settingclickView(Context context) {
		super(context);
		initView();
	}
	//初始化布局
	public void  initView(){
		//将自定义好的布局文件设置给当前的SettingItemView
		System.out.println("填充clickView");
		View.inflate(getContext(), R.layout.list_click_item, this);//root A view group that will be the parent. 
		System.out.println("填充clickView成功");
		tv_title = (TextView) findViewById(R.id.tv_title);    //传入this,RelativeLayout也是viewgroup，表示将 R.layout.list_item
		tv_desc = (TextView)findViewById(R.id.tv_desc);    //塞给settingItemView
		setTitle(mtitle);//设置标题
	}
	public void setDesc(String desc){
		tv_desc.setText(desc);
	}
	public void setTitle(String mtitle2) {
		tv_title.setText(mtitle2);
	}


}


