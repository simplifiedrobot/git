package activity;

import view.settingItemView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.mobilesafe.R;

public class aToolsActivity extends Activity {
     

	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.activity_atools);
    	
    }
      public void numberAddress(View v){
    	startActivity(new Intent(this,activity_address.class));
      }
}
