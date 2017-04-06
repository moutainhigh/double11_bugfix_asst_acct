/**
 * 
 */
package com.yuwang.pinju.common;

import java.io.UnsupportedEncodingException;

/**  
 * @Project: pinju-model
 * @Title: StringUtilBYTE.java
 * @Package com.yuwang.pinju.common
 * @Description: 通过字节操作字符串
 * @author liuboen liuboen@zba.com
 * @date 2011-8-5 下午05:14:19
 * @version V1.0  
 */

public class StringUtilBYTE {

	/**
	 * 通过字节截取字符串<br>	一个中文算两个字符
	 * @param str
	 * @param start
	 * @param length
	 * @return
	 */
	 public static final String substring(String str, int startIndex, int indexLengh)
	  {
	    if (str == null) {
	      return str;
	    }
	
	    StringBuffer sb = new StringBuffer();
	    char[] c = str.toCharArray();
	    int j = 0;
	
	    for (int i = 0; i < c.length; ++i) {
	      char ch = c[i];
	
	      if ((j >= startIndex) && (j < startIndex + indexLengh)) {
	        sb.append(ch);
	      }
	
	      if (ch > '')
	        j += 2;
	      else {
	        ++j;
	      }
	
	      if (j > startIndex + indexLengh) {
	        break;
	      }
	    }
	
	    return sb.toString();
	  }
	
	 /**
	  * 获取字符串长度,默认一个中文算两个字符
	  * @param str
	  * @return
	  */
	 public static final int getLengh(String str){
		 if (str==null) {
			return 0;
		}
		 try {
			return str.getBytes("GBK").length;
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
	 }

}
