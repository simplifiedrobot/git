package activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
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
	private int height;
	private int widht;
	private int lastX;
	private int lastY;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag);
        mPref= getSharedPreferences("config", MODE_PRIVATE);
		tvTop = (TextView) findViewById(R.id.tv_top);
		tvBottom = (TextView) findViewById(R.id.tv_bottom);
	    iv_drag = (ImageView) findViewById(R.id.iv_drag);
	    WindowManager wm=getWindowManager();
	    height = wm.getDefaultDisplay().getHeight();
	    widht = wm.getDefaultDisplay().getWidth();
	    iv_drag.setOnClickListener(new OnClickListener() {
			//数组长度表示点击次数
	    	long[] item=new long [2];
			public void onClick(View v) {
				item[item.length-1]=SystemClock.uptimeMillis();//开机后开始计算时间
				System.arraycopy(item, 1, item, 0, item.length-1);
				
				if(SystemClock.uptimeMillis()-item[0]<=500){
					//将图片居中
					iv_drag.layout(widht/2 - iv_drag.getWidth()/2,
							iv_drag.getTop(), widht/2 + iv_drag.getWidth()/2, iv_drag.getBottom());
				}
			}
		});
	    lastX = mPref.getInt("lastX", 0);
	    lastY = mPref.getInt("lastY", 0);
	  
	    if(lastY>height/2){
	    	tvTop.setVisibility(View.VISIBLE);
	    	tvBottom.setVisibility(View.INVISIBLE);
	    }else{
	    	tvTop.setVisibility(View.INVISIBLE);
	    	tvBottom.setVisibility(View.VISIBLE);
	    }
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
	                  //判断边界，不让其走出边界
	                  //更新界面
	                  if(l<0||r>widht||t<0||b>height-20){
	                	  break;
	                  }
	                  if(t>height/2){
	          	    	tvTop.setVisibility(View.VISIBLE);
	          	    	tvBottom.setVisibility(View.INVISIBLE);
	          	    }else{
	          	    	tvTop.setVisibility(View.INVISIBLE);
	          	    	tvBottom.setVisibility(View.VISIBLE);
	          	    }
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
	
				return false;//事件要向下传递,让onclick(双击事件)可以响应
			}
		});
	}

	
}
