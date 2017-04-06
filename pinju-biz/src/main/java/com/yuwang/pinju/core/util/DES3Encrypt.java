package com.yuwang.pinju.core.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES3Encrypt {

	private static final String ALGORITHM_KEY = "DES";
	private static final String ALGORITHM_OPTIONS = "DES/ECB/NoPadding";
	private final static String sEncoding = "UTF-8";
	protected static SecretKey secretKey1, secretKey2;
	static final char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f' };
	static final byte[] byte_key1 = { (byte) 0x11, (byte) 0x22, (byte) 0x4F,
			(byte) 0x58, (byte) 0xda, (byte) 0x9c, (byte) 0xb0, (byte) 0x98 };
	static final byte[] byte_key2 = { (byte) 0xCB, (byte) 0xDD, (byte) 0x55,
			(byte) 0x66, (byte) 0xfd, (byte) 0x32, (byte) 0x79, (byte) 0x24 };

	private static Cipher cipher;

	/**
	 * 构造函数
	 */
	private static DES3Encrypt encrypt = new DES3Encrypt();

	private DES3Encrypt() {
		init();
	}

	public static DES3Encrypt getInstance() {
		if (encrypt == null)
			encrypt = new DES3Encrypt();
		return encrypt;
	}

	private static void init() {
		try {
			DESKeySpec keyspec;
			SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM_KEY);
			keyspec = new DESKeySpec(byte_key1);
			secretKey1 = skf.generateSecret(keyspec);
			keyspec = new DESKeySpec(byte_key2);
			secretKey2 = skf.generateSecret(keyspec);
			cipher = Cipher.getInstance(ALGORITHM_OPTIONS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密函数
	 * 
	 * @param s
	 *            包含未加密数据的字符串，任意长度，程序会自动补足为8的倍数。
	 * @return 加密后的字符串，16的倍数
	 * @throws Exception
	 */
	public String encrypt(String s) throws Exception {
		byte[] b = stringToBytes(s);
		if (b.length % 8 > 0) {
			byte[] b_size = new byte[((int) (b.length / 8) + 1) * 8];
			System.arraycopy(b, 0, b_size, 0, b.length);
			for (int i = b_size.length - 1; i >= b.length; i--) {
				b_size[i] = 0;
			}
			b = b_size;
		}
		b = encrypt(b, 0, b.length);
		return byteToHexs(b, 0, b.length);
	}

	/**
	 * 加密函数
	 * 
	 * @param b
	 *            包含未加密数据的字节数组
	 * @param offset
	 *            加密的开始位置
	 * @param len
	 *            需要加密数据的长度
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	private byte[] encrypt(byte[] b, int offset, int len) throws Exception {
		byte[] encryptedText;
		if (cipher == null) {
			throw new Exception("Cipher class is null");
		}

		// Encrypting the source
		cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
		encryptedText = cipher.doFinal(b, offset, len);
		cipher.init(Cipher.DECRYPT_MODE, secretKey2);
		encryptedText = cipher.doFinal(encryptedText, 0, encryptedText.length);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
		encryptedText = cipher.doFinal(encryptedText, 0, encryptedText.length);

		return encryptedText;
	}

	/**
	 * 解密函数
	 * 
	 * @param s
	 *            包含密文的字符串
	 * @return 解密后的字符串。解密失败则返回输入的字节数组（b）
	 * @throws Exception
	 */
	public String decrypt(String s) {
		byte[] b = hexsToBytes(s);
		String ret = null;
		try {
			if (null != b) {
				b = decrypt(b, 0, b.length);
				ret = bytesToString(b, 0, b.length);
			}
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}

	/**
	 * DES解密函数。
	 * 
	 * @param b
	 *            包含密文的字节数组
	 * @param offset
	 *            密文在季节数组中的开始位置
	 * @param len
	 *            密文长度
	 * @return 解密后的字节数组。解密失败则返回输入的字节数组（b）
	 * @throws Exception
	 */
	private byte[] decrypt(byte[] b, int offset, int len) throws Exception {

		byte[] decryptBytes;

		// Decrypting...
		cipher.init(Cipher.DECRYPT_MODE, secretKey1);
		decryptBytes = cipher.doFinal(b, offset, len);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey2);
		decryptBytes = cipher.doFinal(decryptBytes, 0, decryptBytes.length);
		cipher.init(Cipher.DECRYPT_MODE, secretKey1);
		decryptBytes = cipher.doFinal(decryptBytes, 0, decryptBytes.length);

		return decryptBytes;
	}

	/**
	 * 将字节转换成字符串。
	 * 
	 * @param b
	 *            字节数组
	 * @param offset
	 *            转换的开始位置
	 * @param len
	 *            转换长度
	 * @return 字符串。返回 null，如果转换失败则返回 null。
	 */
	private String bytesToString(byte[] b, int offset, int len)
			throws Exception {
		while (b[offset + len - 1] == 0) {
			len--;
		}
		return new String(b, offset, len, DES3Encrypt.sEncoding);
	}

	/**
	 * 将字符串转换成字节数组,并补齐为8的倍数。
	 * 
	 * @param s
	 *            字符串
	 * @return 字节数组。返回 null，如果转换失败。
	 */
	private byte[] stringToBytes(String s) throws Exception {
		byte[] b = s.getBytes(DES3Encrypt.sEncoding);
		if (b.length % 8 > 0) {
			byte[] b_size = new byte[((int) (b.length / 8) + 1) * 8];
			for (int i = b_size.length - 1; i >= 0; i--) {
				b_size[i] = 0;
			}
			System.arraycopy(b, 0, b_size, 0, b.length);
			b = b_size;
		}
		return b;
	}

	/**
	 * 将字节数组转换成字符串
	 * 
	 * @param ib
	 *            字节数组
	 * @param offset
	 *            开始转换位置
	 * @param len
	 *            转换长度
	 * @return 转换后的字符串。返回 null，如果 offset + len 大于数组大小。
	 */
	private String byteToHexs(byte[] ib, int offset, int len) {
		if (null == ib || offset + len > ib.length) {
			return null;
		}

		char[] ob = new char[2 * len];
		for (int i = 0; i < len; i++) {
			ob[2 * i + 0] = Digit[(ib[offset + i] >>> 4) & 0X0F];
			ob[2 * i + 1] = Digit[ib[offset + i] & 0X0F];
		}

		String s = new String(ob);
		return s;
	}

	/**
	 * 将诸如"ebf088933ca2808a"的字符串转换成字节数组
	 * 
	 * @param str
	 *            需转换的字符串
	 * @return 字节数组。返回 null，如果输入字符串非法。
	 */
	private byte[] hexsToBytes(String str) {
		if (null == str || str.length() < 16 || (str.length() % 16) != 0) {
			return null;
		}

		byte[] b = new byte[str.length() / 2];
		for (int i = str.length() / 2 - 1; i >= 0; i--) {
			int i1, i2;
			// 取两个字符
			char c1 = str.charAt(i * 2);
			char c2 = str.charAt(i * 2 + 1);
			// 处理高位4字节数据
			if (c1 >= '0' && c1 <= '9') {
				i1 = c1 - '0';
			} else if (c1 >= 'A' && c1 <= 'F') {
				i1 = c1 - 'A' + 10;
			} else if (c1 >= 'a' && c1 <= 'f') {
				i1 = c1 - 'a' + 10;
			} else {
				return null;
			}

			// 处理低位4字节数据
			if (c2 >= '0' && c2 <= '9') {
				i2 = c2 - '0';
			} else if (c2 >= 'A' && c2 <= 'F') {
				i2 = c2 - 'A' + 10;
			} else if (c2 >= 'a' && c2 <= 'f') {
				i2 = c2 - 'a' + 10;
			} else {
				return null;
			}

			b[i] = (byte) (((i1 << 4) & 0xf0) | (i2 & 0xf));
		}

		return b;
	}

	public static void main(String[] args) throws Exception {
		String encry = DES3Encrypt.getInstance().encrypt("100000055000000");
		System.out.println(encry);
		System.out.println(DES3Encrypt.getInstance().decrypt(encry));
	}
}
