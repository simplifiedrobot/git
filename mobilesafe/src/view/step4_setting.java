package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mobilesafe.R;
public class step4_setting extends RelativeLayout {
	  static final String NAMESPACE="http://schemas.android.com/apk/res/com.example.mobilesafe";
	private String on_t;
	private String off_t;
	private CheckBox button;
	private TextView text;
	public step4_setting(Context context, AttributeSet attrs) {
		super(context, attrs);
		on_t = attrs.getAttributeValue(NAMESPACE, "check_on");
		off_t = attrs.getAttributeValue(NAMESPACE, "check_off");
		initView();
	}
	private void initView() {
		View.inflate(getContext(), R.layout.step4_setting, this);
		button = (CheckBox) findViewById(R.id.checkBox1);
		text = (TextView) findViewById(R.id.textView1);
	}
	public boolean isChecked(){
		return button.isChecked();
	}
	public void setChecked(boolean check){
	    button.setChecked(check);
		if(check){
			text.setText(on_t);
		}else{
			text.setText(off_t);
		}
	}
}
