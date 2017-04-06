package com.yuwang.pinju.common;

import java.util.Random;

public class ItemXianGouUtil {
	
	private static final Long SHIFT_CONSTANT=10000L;
	
	/**
	 * 加密后，显示给用户使用
	 * @Project:crm-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-1
	 * @update:2011-9-1
	 * @param code
	 * @return
	 */
	public static String encode(Long code){
		code = code + SHIFT_CONSTANT;
		String codeStr = Base64.encodeBase64(code.toString().getBytes());
		return codeStr;
	}
	/**
	 * 对限购码解密
	 * @Project:crm-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-1
	 * @update:2011-9-1
	 * @param codeStr
	 * @return
	 * @throws Base64DecodingException
	 */
	public static Long decode(String codeStr){
		if (codeStr==null ||"".equals(codeStr)) {
			return null;
		}
		byte[] bytes = Base64.decode(codeStr);
		Long code =Long.valueOf(new String(bytes));
		return code-SHIFT_CONSTANT;	
	}
	/**
	 * 生成限购活动码
	 * @Project:crm-web
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-9-1
	 * @update:2011-9-1
	 * @param itemId
	 * @return 
	 */
	public static Long createCode(Long itemId){
		Long now = System.nanoTime();
		String snow= now.toString().substring(2);
		return Long.valueOf(snow)+itemId+(new Random()).nextInt(10000);
	}
	
	private final static Random random = new Random();
	
	public static void main(String[] args) {
		Long itemId = 555L;
		Long code = createCode(itemId);
		System.out.println(code);
		
		String aa = encode(55956597336600L);
		System.out.println(aa);
		
		Long  ss = decode("NTU5NTY1OTczNDY2MDB=");
		System.out.println(ss);
	}
	
	
	
}
