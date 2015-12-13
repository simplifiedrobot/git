package activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public abstract class BaseSetupActivity extends Activity {

	private GestureDetector mDectector;
	protected SharedPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mPref = getSharedPreferences("config", MODE_PRIVATE);

		mDectector = new GestureDetector(this, new SimpleOnGestureListener() {

			/**
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {

				if (Math.abs(e2.getRawY() - e1.getRawY()) > 100) {
					Toast.makeText(BaseSetupActivity.this, "涓嶈兘杩欐牱鍒掑摝!",
							Toast.LENGTH_SHORT).show();
					return true;
				}

				if (Math.abs(velocityX) < 100) {
					Toast.makeText(BaseSetupActivity.this, "婊戝姩鐨勫お鎱簡!",
							Toast.LENGTH_SHORT).show();
					return true;
				}

				// 鍚戝彸鍒�涓婁竴椤�
				if (e2.getRawX() - e1.getRawX() > 200) {
					showPreviousPage();
					return true;
				}

				if (e1.getRawX() - e2.getRawX() > 200) {
					showNextPage();
					return true;
				}

				return super.onFling(e1, e2, velocityX, velocityY);
			}
		});
	}

	/**
	 */
	public abstract void showNextPage();

	/**
	 */
	public abstract void showPreviousPage();

	public void next(View view) {
		showNextPage();
	}

	public void previous(View view) {
		showPreviousPage();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDectector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

}
