package utils;

import android.content.Context;
import android.widget.Toast;
public class mToast {
	public static void show(Context context,String string){
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
	}
}
