package utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String encode(String password){
		//��ȡMD5�㷨����
		try {
		MessageDigest instance =MessageDigest.getInstance("MD5");
		//���ַ�������,�����ֽ�����
		byte[] digest=instance.digest(password.getBytes());
		
		StringBuffer sb=new StringBuffer();
		for (byte b : digest) {
			//��ȡ�ֽڵĵͰ�λ��Чֵ
			int i=b&0xff;
			//������ת����16����
			String hexString =Integer.toHexString(i);
			if(hexString.length()<2){
				hexString="0"+hexString;
			}
			sb.append(hexString);
		}
		return sb.toString();
		
		
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getMD5(String sourceId){
		
		File file=new File(sourceId);
		try {
			MessageDigest instance =MessageDigest.getInstance("MD5");
			FileInputStream stream = new FileInputStream(file);
		   byte[] bu=new byte[1024];
		   int len=0;
		   if((len=stream.read(bu))!=-1){
			   instance.update(bu, 0, len);
		   }
		   byte[] result=instance.digest();
			
		   StringBuffer sb=new StringBuffer();
			for (byte b : result) {
				//��ȡ�ֽڵĵͰ�λ��Чֵ,����ת��Ϊ4λ��int��
				int i=b&0xff;
				//������ת����16����
				String hexString =Integer.toHexString(i);
				if(hexString.length()<2){
					hexString="0"+hexString;
				}
				sb.append(hexString);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}