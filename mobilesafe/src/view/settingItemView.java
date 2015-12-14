
package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;


public class settingItemView extends RelativeLayout {

	private TextView tv_title;
	private TextView tv_desc;
	private CheckBox cbStatus;
    static final String NAMESPACE="http://schemas.android.com/apk/res/com.example.mobilesafe";
	private String mtitle;                                                                     
	private String mDesc_on;
	private String mDesc_off;
	public settingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	public settingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mtitle = attrs.getAttributeValue(NAMESPACE, "title");
		mDesc_on = attrs.getAttributeValue(NAMESPACE, "desc_on");
		mDesc_off = attrs.getAttributeValue(NAMESPACE, "desc_off");
		initView();
	}
	public settingItemView(Context context) {
		super(context);
		initView();
	}
	//��ʼ������
	public void  initView(){
		//���Զ���õĲ����ļ����ø���ǰ��SettingItemView
		View.inflate(getContext(), R.layout.list_item, this);//root A view group that will be the parent. 
		tv_title = (TextView) findViewById(R.id.tv_title);    //����this,RelativeLayoutҲ��viewgroup����ʾ�� R.layout.list_item
		tv_desc = (TextView)findViewById(R.id.tv_desc);    //����settingItemView
		cbStatus = (CheckBox) findViewById(R.id.cb_status);
		setTile(mtitle);//���ñ���
	}
	public void setDesc(String desc){
		tv_desc.setText(desc);
	}
	private void setTile(String mtitle2) {
		tv_title.setText(mtitle2);
	}
	public boolean isChecked(){
		return cbStatus.isChecked();
	}
	public void setChecked(boolean check){
		cbStatus.setChecked(check);
		//����ѡ���״̬�������ı�����
		if(check){
			tv_desc.setText(mDesc_on);
			System.out.println("ѡ��״̬");
		}else{
			tv_desc.setText(mDesc_off);
			System.out.println("ûѡ״̬");
		}
	}
}
