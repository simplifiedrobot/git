package activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.mobilesafe.R;

public class activity_safeCall extends Activity {

	  @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safecall);
		Init();
	}

	private void Init() {

		ListView list_view=(ListView) findViewById(R.id.list_view);
	}
}
