package dao;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
  static public  SharedPreferences getmPre(Context context){
	  SharedPreferences mPre=  context.getSharedPreferences("config", 0);
	  return mPre;
  }
  static public  void putBoolean(Context context,String key,Boolean b){
	  SharedPreferences mPre=  context.getSharedPreferences("config", 0);
	  mPre.edit().putBoolean(key, b).commit();
  }
  static public  boolean getBoolean(Context context,String key){
	  SharedPreferences mPre=  context.getSharedPreferences("config", 0);
	 return mPre.getBoolean(key, false);
  }
}
