package dao;

import utils.LockdatatbaseOpenhelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class lockdateDao {

	public static void add(Context context, String packageName) {
		LockdatatbaseOpenhelper db = new LockdatatbaseOpenhelper(context);
		SQLiteDatabase database = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("packagename", packageName);
		database.insert("lockpackage", null, values);
		database.close();
	}

	public static void delete(Context context, String packageName) {
		LockdatatbaseOpenhelper db = new LockdatatbaseOpenhelper(context);
		SQLiteDatabase database = db.getWritableDatabase();
		database.delete("lockpackage", "packagename=?",
				new String[] { packageName });
		database.close();
	}

	public static boolean query(Context context, String packageName) {
		LockdatatbaseOpenhelper db = new LockdatatbaseOpenhelper(context);
		SQLiteDatabase database = db.getReadableDatabase();
		Cursor cursor = database.query("lockpackage", null, "packagename=?",
				new String[] { packageName }, null, null, null);
		if (cursor.moveToNext()) {
			database.close();
			return true;
		} else {
			database.close();
			return false;
		}

	}

}
