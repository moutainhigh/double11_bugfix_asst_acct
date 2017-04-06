package com.yuwang.pinju.domain.member.auth;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.yuwang.pinju.common.RandomUUID;

public class AuthParams {

	private String param1;
	private String param2;
	private Long appKey;
	private String appSign;
	private String appTime;
	private String appName;
	private String token;
	private String success = "ERROR";

	public AuthParams() {
	}

	private AuthParams(String appName, Long appKey, String appTime) {
		this.appKey = appKey;
		this.appTime = appTime;
		this.appName = appName;
	}

	public static AuthParams parseAuthParams(String decryptData) {
		String[] str = decryptData.split("\\|");
		if (str == null || str.length != 3) {
			return null;
		}
		try {
			return new AuthParams(str[0], Long.parseLong(str[1]), str[2]);
		} catch (Exception e) {
			return null;
		}
	}

	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public Long getAppKey() {
		return appKey;
	}
	public void setAppKey(Long appKey) {
		this.appKey = appKey;
	}

	public String getAppSign() {
		return appSign;
	}
	public void setAppSign(String appSign) {
		this.appSign = appSign;
	}

	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppTime() {
		return appTime;
	}
	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getToken() {
		return token;
	}

	public String generateToken() {
		if (token == null) {
			token = RandomUUID.get(36);
		}
		return token;
	}

	public String getSuccess() {
		return success;
	}

	public void changeSuccess() {
		this.success = "SUCCESS";
	}

	public String toSignData(String key) {
		StringBuilder builder = new StringBuilder();
		builder.append(appKey).append("|").append(appTime).append("|");
		if (appName != null) {
			builder.append(appName).append("|");
		}
		builder.append(key);
		return builder.toString();
	}

	public String toEncrypt() {
		return appName + "|" + appKey + "|" + appTime;
	}

	public static void main(String[] args) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		System.out.println(Hex.encodeHex(digest.digest("12458963|1315381936500|aaMo1qHIKx1tCH3VBmcTQTq14uG1p4".getBytes())));
		System.out.println(System.currentTimeMillis());


		System.out.println(Base64.encodeBase64String(KeyGenerator.getInstance("AES").generateKey().getEncoded()));

		Key key = new SecretKeySpec(Base64.decodeBase64("OOuK2dTyA8NOkKuKDfRF5A=="), "AES");
		System.out.println(Hex.encodeHex(key.getEncoded()));

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		System.out.println(Base64.encodeBase64String(cipher.doFinal("1014477878:GYYVY9BqntYbccqBNkL7gPNJxxSmVFdvY6kp:火狐".getBytes())));
		cipher.init(Cipher.DECRYPT_MODE, key);
		System.out.println(new String(cipher.doFinal(Hex.decodeHex("adf9b76802c2134688667a994c13b7d548b9e2267c0cb91b15bd76388884c5dddaa1f60d5b0a5ea58c2a1d2962aa7f5cba37fe1dfbde11da9122424545bb4980".toCharArray()))));
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(new SecretKeySpec(Hex.decodeHex("6f7ef2f8e1e4cbc44498e153a259554b5314bb6d631573b315e912706c7f6c7048f170e7d1e736a5156cc2f4fa526e392a56ab560bb48061153ab14b22839594".toCharArray()), "HmacMD5"));
		System.out.println(Hex.encodeHex(mac.doFinal("rfm3aALCE0aIZnqZTBO31Ui54iZ8DLkbFb12OIiExd3aofYNWwpepYwqHSliqn9cujf+HfveEdqRIkJFRbtJgA==".getBytes())));
	}

	@Override
	public String toString() {
		return "AuthParams [param1=" + param1 + ", param2=" + param2 + ", appKey=" + appKey + ", appSign=" + appSign
				+ ", appTime=" + appTime + ", appName=" + appName + ", token=" + token + ", success=" + success + "]";
	}
}
