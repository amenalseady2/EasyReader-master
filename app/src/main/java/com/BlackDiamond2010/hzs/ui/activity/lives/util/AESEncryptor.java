package com.BlackDiamond2010.hzs.ui.activity.lives.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author xiawei
 * 20170808
 * AES加密器
 */ 
public class AESEncryptor {

	/**
	 * AES加密
	 * @author mslan
	 * @date 2015-5-28 上午11:42:09 
	 * @param seed 密钥，加密解密需相同
	 * @param data 需加密的数据
	 * @return null,加密失败。
	 */
	public static String encrypt(String seed, String data) {
		try {
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] result = encrypt(rawKey, data.getBytes());
			return toHex(result);
		} catch (Exception e) {
			return null;
		}
	}
      
	/**
	 * AES解密
	 * @author mslan
	 * @date 2015-5-28 上午11:43:22 
	 * @param seed 密钥，加密解密需相同
	 * @param encrypted 经过加密的数据
	 * @return null，解密失败
	 */
	public static String decrypt(String seed, String encrypted) {
		try {
			byte[] rawKey = getRawKey(seed.getBytes());
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(rawKey, enc);
			return new String(result);
		} catch (Exception e) {
			return null;
		}
	}   
 
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = null;
		// SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
		if (android.os.Build.VERSION.SDK_INT >= 17) {
			sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
		} else {
			sr = SecureRandom.getInstance("SHA1PRNG");
		}
		sr.setSeed(seed);
		kgen.init(128, sr); // 192 and 256 bits may not be available
		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();
		return raw;
	}   
 
      
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}   
 
	private static byte[] decrypt(byte[] raw, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}   
 
   public static String toHex(String txt) {   
       return toHex(txt.getBytes());   
   }   
   
   public static String fromHex(String hex) {   
       return new String(toByte(hex));   
   }   
      
	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++){
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),16).byteValue();
		}
		return result;
	}   
 
   public static String toHex(byte[] buf) {   
       if (buf == null)   
           return "";   
       StringBuffer result = new StringBuffer(2*buf.length);   
       for (int i = 0; i < buf.length; i++) {   
           appendHex(result, buf[i]);   
       }   
       return result.toString();   
   }   
   
   private final static String HEX = "0123456789ABCDEF";  
   
   private static void appendHex(StringBuffer sb, byte b) {   
       sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));   
   }   
   
}
