package test;

import java.util.Random;

import dao.BlackNumberDao;
import android.test.AndroidTestCase;

public class BlackNumberDaoTest extends AndroidTestCase {

	public  void testAdd(){
		BlackNumberDao dao= new BlackNumberDao(getContext());
		for (int i = 0; i < 60; i++) {
			String number=1000000000+i+"";
			Random random = new Random();
			dao.add(number, String.valueOf(random.nextInt(3)+1));
			
		}
	}
}
