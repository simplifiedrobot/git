package dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AntivirusDao {
	
/*
 * ��鵱ǰ��md5�Ƿ�������ݿ���
 */
	public static String checkFileVirus(String md5) {
		String md = null;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				"/data/data/com.example.mobilesafe/files/antivirus.db", null,
				SQLiteDatabase.OPEN_READONLY);
		// ��ѯ�����Ƿ������ݿ���
		Cursor cursor = db.rawQuery("select desc from datable  where md5=?",
				new String[] { md5 });
		// �жϵ�ǰ�α��Ƿ�����ƶ�
		while (cursor.moveToNext()) {
		md = cursor.getString(0);
		}
		cursor.close();
		return md;
	}
	/*
	 * ��Ӳ������ݿ�
	 */
	
}
