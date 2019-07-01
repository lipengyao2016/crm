package com.by.crm.zuul;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;

import java.security.KeyPair;

import java.security.KeyPairGenerator;

import java.security.interfaces.RSAPrivateKey;

import java.security.interfaces.RSAPublicKey;

import java.util.HashMap;

import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jettison.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;

import sun.misc.BASE64Decoder;

import sun.misc.BASE64Encoder;

@SuppressWarnings({"unused", "restriction"})
public class Keys {

	public static final String KEY_ALGORITHM = "RSA";

	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String PRIVATE_KEY = "RSAPrivateKey";

//	public static void main(String[] args) {
//		Map<String, Object> keyMap;
//		try {
//			keyMap = initKey();
//			Key publicKey = getPublicKey(keyMap);
//			System.out.println("公钥："+publicKey);
//			Key privateKey = getPrivateKey(keyMap);
//			System.out.println("私钥："+privateKey);
//
//			Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//			// 加密
//			String sendInfo = "我的明文";
//			byte[] results = cipher.doFinal(sendInfo.getBytes());
//
//			cipher.init(Cipher.DECRYPT_MODE, publicKey);
//			// 解密
//			byte[] deciphered = cipher.doFinal(results);
//			// 得到明文
//			String recvInfo = new String(deciphered);
//			System.out.println(recvInfo);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static Key getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
//		byte[] publicKey = key.getEncoded();
//		return encryptBASE64(key.getEncoded());
		return key;
	}

	public static Key getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
//		byte[] privateKey = key.getEncoded();
//		return encryptBASE64(key.getEncoded());
		return key;
	}

	
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	public static Map<String, Object> initKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}
	

}