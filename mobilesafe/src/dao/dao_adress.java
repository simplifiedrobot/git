package dao;

import java.sql.SQLData;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class dao_adress {
	private static final String PATH="data/data/com.example.mobilesafe/files/address.db";
	private static Cursor cursor;
	
	private static String address;
	public static String query(String s){
		address = "δ֪����";
		System.out.println(s);
		//��ȡ���ݿ����
		SQLiteDatabase database=SQLiteDatabase.openDatabase(PATH, null,SQLiteDatabase.OPEN_READONLY);
	    System.out.println("�õ����ݿ�");
		if(s.matches("^1[3-8]\\d{9}$")){//ƥ���ֻ�����
			Cursor cursor=database.rawQuery("select location from data2"
					+ " where id=(select  outkey from data1 where id=?)",
					new String[]{s.substring(0, 7)});
			System.out.println("�õ��α�");
			if(cursor.moveToNext()){
				address=cursor.getString(0);
			}
			cursor.close();
		}else if(s.matches("^\\d+$")){//ƥ������
			switch (s.length()) {
			case 3:
				address = "�����绰";
				break;
			case 4:
				address = "ģ����";
				break;
			case 5:
				address = "�ͷ��绰";
				break;
			case 7:
			case 8:
				address = "���ص绰";
				break;
			default:
			if(s.startsWith("0")&&s.length()>10){//�����ǳ�;����
				//���ſ�����3λҲ������4λ
				//�Ȳ�ѯ4λ����
				cursor = database.rawQuery("select location from data2 where area=?", 
						new String[]{s.substring(1, 4)});
				if(cursor.moveToNext()){
					address=cursor.getString(0);
				}else {
					cursor.close();
					//��ѯ3λ����
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
		database.close();//�ر����ݿ�
		return address;
	}

}
