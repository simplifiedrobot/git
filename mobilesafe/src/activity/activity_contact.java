package activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.mobilesafe.R;



public class activity_contact extends Activity {

	private ArrayList< HashMap<String,String>> contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
         contact = new ArrayList<HashMap<String, String>>();
         System.out.println("进入contact了");
		  contact =contact();
		  System.out.println("拿到了联系人列表");
          super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		System.out.println("填充了联系试图 ");
	
	ListView listView=	(ListView) findViewById(R.id.listView);

	listView.setAdapter(new SimpleAdapter(this, contact, R.layout.contact_list,new String[]{"name","phone"}, new int[]{R.id.tv_name,R.id.tv_phone}));
	listView.setOnItemClickListener(new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//读取当前item的电话号码
			String phone =contact.get(arg2).get("phone");
			//将数据放在intent中返回给上一个页面
			Intent intent=new Intent();
			intent.putExtra("phone", phone);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	});
	}
	private ArrayList<HashMap<String, String>> contact() {
		// 首先,从raw_contacts中读取联系人的id("contact_id")
		// 其次, 根据contact_id从data表中查询出相应的电话号码和联系人名称
		// 然后,根据mimetype来区分哪个是联系人,哪个是电话号码
		                                                         System.out.println(" 进入联系人数据库获取方法");
		Uri rawContactsUri=Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		//从raw_contacts中读取联系人的id("contact_id")
		                                                       System.out.println("从raw_contacts中读取联系人的id_contact_id");
		Cursor rawContactsCursor=getContentResolver().query(rawContactsUri,
			new String[]{"contact_id"}, null, null, null);
		                                                        System.out.println("拿到contact——id游标");
		if(rawContactsCursor!=null){
			while(rawContactsCursor.moveToNext()){
				String contactId=rawContactsCursor.getString(0);
				//根据contact_id从data表中查询出相应的电话号码和联系人的名字，实际上查询的是试图view_data
				Cursor dataCursor=getContentResolver().query(dataUri,
						new String[]{"date1","mimetype"},"contact_id=?",
						new String[]{contactId}, null);
				                                                    System.out.println("拿到姓名和号码游标");
				if(dataCursor!=null){
					 HashMap<String,String> map=new HashMap<String, String>();
					 while(dataCursor.moveToNext()){
						 String data=dataCursor.getString(0);
						 String mimetype=dataCursor.getString(1);
						 if(mimetype.equals("vnd.android.cursor.item/phone_v2")){
							map.put("phone", data);
						 }
						 else if ("vnd.android.cursor.item/name".equals(mimetype)) {
								map.put("name", data);
					     }
				}
				list.add(map);
				System.out.println("添加至链表");
				dataCursor.close();
			}
		}
			rawContactsCursor.close();
		}
		return list;
	}
}
	
	
