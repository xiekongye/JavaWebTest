/**
 * 
 */
package com.xiekongye.util;

import java.security.MessageDigest;
import java.util.Random;

import sun.misc.BASE64Encoder;

/**
 * @author xiekongye
 *
 */
public class TokenProcessor {
	private TokenProcessor(){}
	
	private static TokenProcessor instance;
	
	public static TokenProcessor getInstance() {
		if(instance == null){
			instance = new TokenProcessor();
		}
		return instance;
	}
	
	/**
	 * @author xiekongye
	 * @return 编码后的Token
	 * @throws RunTimeException
	 * */
	public String makeToken() {
		String tokenString = Long.toString(System.currentTimeMillis() + new Random().nextInt(999999));
		
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			byte[] md5 = md.digest(tokenString.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			return encoder.encode(md5);
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
			throw new RuntimeException();
		}
	}
}
