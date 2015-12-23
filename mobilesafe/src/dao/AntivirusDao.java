package dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntivirusDao {
	
/*
 * 检查当前的md5是否存在数据库中
 */
	public static String checkFileVirus(String md5) {
		String md = null;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				"/data/data/com.example.mobilesafe/files/antivirus.db", null,
				SQLiteDatabase.OPEN_READONLY);
		// 查询病毒是否在数据库中
		Cursor cursor = db.rawQuery("select desc from datable  where md5=?",
				new String[] { md5 });
		// 判断当前游标是否可以移动
		while (cursor.moveToNext()) {
		md = cursor.getString(0);
		}
		cursor.close();
		return md;
	}
	/*
	 * 添加病毒数据库
	 */
	
}
