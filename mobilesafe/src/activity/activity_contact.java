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
         System.out.println("����contact��");
		  contact =contact();
		  System.out.println("�õ�����ϵ���б�");
          super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		System.out.println("�������ϵ��ͼ ");
	
	ListView listView=	(ListView) findViewById(R.id.listView);

	listView.setAdapter(new SimpleAdapter(this, contact, R.layout.contact_list,new String[]{"name","phone"}, new int[]{R.id.tv_name,R.id.tv_phone}));
	listView.setOnItemClickListener(new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//��ȡ��ǰitem�ĵ绰����
			String phone =contact.get(arg2).get("phone");
			//�����ݷ���intent�з��ظ���һ��ҳ��
			Intent intent=new Intent();
			intent.putExtra("phone", phone);
			setResult(Activity.RESULT_OK, intent);
			finish();
		}
	});
	}
	private ArrayList<HashMap<String, String>> contact() {
		// ����,��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		// ���, ����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ������
		// Ȼ��,����mimetype�������ĸ�����ϵ��,�ĸ��ǵ绰����
		                                                         System.out.println(" ������ϵ�����ݿ��ȡ����");
		Uri rawContactsUri=Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		//��raw_contacts�ж�ȡ��ϵ�˵�id("contact_id")
		                                                       System.out.println("��raw_contacts�ж�ȡ��ϵ�˵�id_contact_id");
		Cursor rawContactsCursor=getContentResolver().query(rawContactsUri,
			new String[]{"contact_id"}, null, null, null);
		                                                        System.out.println("�õ�contact����id�α�");
		if(rawContactsCursor!=null){
			while(rawContactsCursor.moveToNext()){
				String contactId=rawContactsCursor.getString(0);
				//����contact_id��data���в�ѯ����Ӧ�ĵ绰�������ϵ�˵����֣�ʵ���ϲ�ѯ������ͼview_data
				Cursor dataCursor=getContentResolver().query(dataUri,
						new String[]{"date1","mimetype"},"contact_id=?",
						new String[]{contactId}, null);
				                                                    System.out.println("�õ������ͺ����α�");
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
				System.out.println("���������");
				dataCursor.close();
			}
		}
			rawContactsCursor.close();
		}
		return list;
	}
}
	
	
