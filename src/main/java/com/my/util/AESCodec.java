package com.my.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *  AES �ӽ���
 *  15λ���ܳ�24λ����16λ���ܳ�20λ,��16λ���ܳ�20λ,��16λ���ܳ�26λ
 */
public class AESCodec {
	
	private static String key = "ewtdV^*JjmI((Ikl";
	
	/**
	 * ����
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String str){
		if(str == null || str.equals("")){
			return "";
		}
		return aesEncrypt(str, key);
	}

	/**
	 * ����
	 * @param str 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesEncrypt(String str, String key) {
		if (str == null || key == null)
			return null;
		try{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
			byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));
			return new BASE64Encoder().encode(bytes);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ����
	 * @param str
	 * @return
	 */
	public static String aesDecrypt(String str){
		if(Check.IsStringNULL(str)){
			return str;
		}
		return aesDecrypt(str, key);
	}

	/**
	 * ����
	 * @param str
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String aesDecrypt(String str, String key){
		if (str == null || key == null)
			return null;
		try{
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key.getBytes("utf-8"), "AES"));
			byte[] bytes = new BASE64Decoder().decodeBuffer(str);
			bytes = cipher.doFinal(bytes);
			return new String(bytes, "utf-8");
		}catch (Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String args[]) {
		System.out.println(AESCodec.aesEncrypt("shca!123456"));
		System.out.println(AESCodec.aesDecrypt("n0D6jKkkFS7AkM1iQijFiQ=="));
	}
}
