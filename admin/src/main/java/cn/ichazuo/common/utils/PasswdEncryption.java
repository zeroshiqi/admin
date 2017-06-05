package cn.ichazuo.common.utils;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;


/**
 * ClassName: PasswdEncryption 
 * Description: (加密类) 
 * @author ZhaoXu
 * @date 2015年6月28日 下午4:21:35 
 * @version V1.0
 */
public class PasswdEncryption {
	
	/**
	 * 
	 * Title: generate 
	 * Description: (生成含有随机盐的密码) 
	 * @param password 未加密的数据
	 * @return
	 */
	public static String generate(String password) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		password = MD5(password + salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = password.charAt(i / 3 * 2);
			char c = salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	/**
	 * 
	 * Title: verify 
	 * Description: (校验密码是否正确) 
	 * @param password 未加密的数据
	 * @param md5Password 加密后的数据
	 * @return
	 */
	public static boolean verify(String password, String md5Password) {
		try{
			char[] cs1 = new char[32];
			char[] cs2 = new char[16];
			for (int i = 0; i < 48; i += 3) {
				cs1[i / 3 * 2] = md5Password.charAt(i);
				cs1[i / 3 * 2 + 1] = md5Password.charAt(i + 2);
				cs2[i / 3] = md5Password.charAt(i + 1);
			}
			String pass=PasswdEncryption.generate(password.trim());
			String salt = new String(cs2);
			String c = MD5(password + salt);
			return MD5(password + salt).equals(new String(cs1));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Title: md5Hex 
	 * Description: (MD5加密) 
	 * @param str 待加密的字符串 
	 * @return
	 */
	public static String MD5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(str.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			return "";
		}
	}
}
