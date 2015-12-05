

package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class streamUtils {
	
         public static String readFromStream(InputStream in) throws IOException{
        	 ByteArrayOutputStream out = new ByteArrayOutputStream();
        	 int len=0;
        	 byte[] b=new byte[1024];
				while((len=in.read(b))!=-1){
					 out.write(b, 0, len);
				 }
			String s=out.toString();
			 System.out.println(s);
			
			in.close();
			out.close();
			   System.out.println("取得json成功");
        	 return s;
         }
        
}


