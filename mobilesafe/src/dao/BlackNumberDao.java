package dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import bean.blackNumberInfo;

public class BlackNumberDao {

	public BlackNumberOpenHelper helper;

	public BlackNumberDao(Context context) {
		helper = new BlackNumberOpenHelper(context);
	}

	// 增加号码和拦截模式
	public boolean add(String number, String mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("mode", mode);
		long i = db.insert("blacknumber", null, values);
		if (i == -1) {
			return false;
		} else {
			return true;
		}
	}

	// 按号码删除
	public boolean delete(String number) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int i = db.delete("blacknumble", "number=?", new String[] { number });
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	// 通过号码修改拦截模式
	public boolean changNumberMode(String number, String mode) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("mode", mode);
		int i = db.update("blacknumber", values, "number=?",
				new String[] { number });
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	// 返回一个黑名单号码的拦截模式
	public String findNumber(String number) {

		SQLiteDatabase db = helper.getWritableDatabase();
		String mode = "";
		Cursor cursor = db.query("blacknumber", new String[] { "mode" },
				"number=?", new String[] { number }, null, null, null);
		if (cursor.moveToNext()) {
			mode = cursor.getString(0);
		}
		cursor.close();
		db.close();
		return mode;
	}

	// 查询所有的黑名单
	public List<blackNumberInfo> findAllNumber() {
		SQLiteDatabase db = helper.getWritableDatabase();
		List<blackNumberInfo> numberList = new ArrayList<blackNumberInfo>();
		Cursor cursor = db
				.query("blacknumber", new String[] { "number", "mode" }, null,
						null, null, null, null);
		while (cursor.moveToNext()) {
			blackNumberInfo numberInfo = new blackNumberInfo();
			numberInfo.setNumber(cursor.getString(0));
			numberInfo.setMode(cursor.getString(1));
			numberList.add(numberInfo);
		}

		cursor.close();
		db.close();
		return numberList;
	}

	// 分页加载数据
	public List<blackNumberInfo> findParNumber(int PageSize, int page) {
		SQLiteDatabase db = helper.getWritableDatabase();
		List<blackNumberInfo> numberList = new ArrayList<blackNumberInfo>();
		Cursor cursor = db.rawQuery(
				"select number,mode from blacknumber limit ? offset ?",
				new String[] { String.valueOf(PageSize),
						String.valueOf(page * (PageSize - 1)) });
		while (cursor.moveToNext()) {
			blackNumberInfo numberInfo = new blackNumberInfo();
			numberInfo.setNumber(cursor.getString(0));
			numberInfo.setMode(cursor.getString(1));
			numberList.add(numberInfo);
		}
		cursor.close();
		db.close();
		return numberList;
	}

	// 分批加载数据
	public List<blackNumberInfo> findParNumber2(int startIndex, int maxCount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		List<blackNumberInfo> numberList = new ArrayList<blackNumberInfo>();
		Cursor cursor = db.rawQuery(
				"select number,mode from blacknumber limit ? offset ?",
				new String[] { String.valueOf(maxCount),
						String.valueOf(startIndex) });
		while (cursor.moveToNext()) {
			blackNumberInfo numberInfo = new blackNumberInfo();
			numberInfo.setNumber(cursor.getString(0));
			numberInfo.setMode(cursor.getString(1));
			numberList.add(numberInfo);
		}
		cursor.close();
		db.close();
		return numberList;
	}

	public int getTotalPage() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from blacknumber", null);
		cursor.moveToNext();
		int total = cursor.getInt(0);
		cursor.close();
		db.close();
		return total;
	}

}
