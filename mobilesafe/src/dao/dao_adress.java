package dao;

import java.sql.SQLData;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class dao_adress {
	private static final String PATH="data/data/com.example.mobilesafe/files/address.db";
	private static Cursor cursor;
	
	private static String address;
	public static String query(String s){
		address = "未知号码";
		System.out.println(s);
		//获取数据库对象
		SQLiteDatabase database=SQLiteDatabase.openDatabase(PATH, null,SQLiteDatabase.OPEN_READONLY);
	    System.out.println("拿到数据库");
		if(s.matches("^1[3-8]\\d{9}$")){//匹配手机号码
			Cursor cursor=database.rawQuery("select location from data2"
					+ " where id=(select  outkey from data1 where id=?)",
					new String[]{s.substring(0, 7)});
			System.out.println("拿到游标");
			if(cursor.moveToNext()){
				address=cursor.getString(0);
			}
			cursor.close();
		}else if(s.matches("^\\d+$")){//匹配数字
			switch (s.length()) {
			case 3:
				address = "报警电话";
				break;
			case 4:
				address = "模拟器";
				break;
			case 5:
				address = "客服电话";
				break;
			case 7:
			case 8:
				address = "本地电话";
				break;
			default:
			if(s.startsWith("0")&&s.length()>10){//可能是长途号码
				//区号可能是3位也可能是4位
				//先查询4位区号
				cursor = database.rawQuery("select location from data2 where area=?", 
						new String[]{s.substring(1, 4)});
				if(cursor.moveToNext()){
					address=cursor.getString(0);
				}else {
					cursor.close();
					//查询3位区号
					Cursor cursor=database.rawQuery("select location from data2 where area=?", 
							new String[]{s.substring(1, 3)});
					if(cursor.moveToNext()){
						address=cursor.getString(0);
				}
					cursor.close();
			}
		}
			break;
			}
		}
		database.close();//关闭数据库
		return address;
	}

}
