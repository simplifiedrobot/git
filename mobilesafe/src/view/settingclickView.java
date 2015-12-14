

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
	//��ʼ������
	public void  initView(){
		//���Զ���õĲ����ļ����ø���ǰ��SettingItemView
		System.out.println("���clickView");
		View.inflate(getContext(), R.layout.list_click_item, this);//root A view group that will be the parent. 
		System.out.println("���clickView�ɹ�");
		tv_title = (TextView) findViewById(R.id.tv_title);    //����this,RelativeLayoutҲ��viewgroup����ʾ�� R.layout.list_item
		tv_desc = (TextView)findViewById(R.id.tv_desc);    //����settingItemView
		setTitle(mtitle);//���ñ���
	}
	public void setDesc(String desc){
		tv_desc.setText(desc);
	}
	public void setTitle(String mtitle2) {
		tv_title.setText(mtitle2);
	}


}


