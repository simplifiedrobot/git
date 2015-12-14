package activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.example.mobilesafe.R;

public class activity_drag extends Activity{
	private TextView tvTop;
	private TextView tvBottom;
	private ImageView iv_drag;
	private SharedPreferences mPref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag);
        mPref= getSharedPreferences("config", MODE_PRIVATE);
		tvTop = (TextView) findViewById(R.id.tv_top);
		tvBottom = (TextView) findViewById(R.id.tv_bottom);
	    iv_drag = (ImageView) findViewById(R.id.iv_drag);
	    int  lastX=mPref.getInt("lastX", 0);
	    int  lastY=mPref.getInt("lastY", 0);
	    //获取布局对象
	    RelativeLayout.LayoutParams params=(LayoutParams) iv_drag.getLayoutParams();
	    //设置左边距
	    params.leftMargin=lastX;
	    params.topMargin=lastY;
	    //重新设置位置
	    iv_drag.setLayoutParams(params);
	    //设置触摸监听
	    iv_drag.setOnTouchListener(new OnTouchListener() {
			private int startX;
			private int startY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					int endX=(int) event.getRawX();
					int endY=(int) event.getRawY();	
					//计算移动偏移量
	                  int dx=endX-startX;
	                  int dy=endY-startY;
                    //更新上下左右距离
	                  int l=iv_drag.getLeft()+dx;
	                  int r=iv_drag.getRight()+dx;
	                  int t=iv_drag.getTop()+dy;
	                  int b=iv_drag.getBottom()+dy;
	                  //更新界面
	                  iv_drag.layout(l, t, r, b);
                      //重新初始化起点坐标
	                  startX = (int) event.getRawX();
					  startY = (int) event.getRawY();
					  break;
				case MotionEvent.ACTION_UP:
					//记录坐标
					Editor editer=mPref.edit();
					editer.putInt("lastX",iv_drag.getLeft());
					editer.putInt("lastY",iv_drag.getTop());
					editer.commit();
					break;
					default:
						break;
				}
				return true;
			}
		});
	}
	
}
