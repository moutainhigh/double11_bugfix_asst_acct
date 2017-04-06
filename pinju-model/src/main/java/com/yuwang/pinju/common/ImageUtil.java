/**
 * 
 */
package com.yuwang.pinju.common;

/**  
 * @Project: pinju-model
 * @Title: ImageUtil.java
 * @Package com.yuwang.pinju.common
 * @Description: 图片相关处理工具(还不完全,暂只支持修改缩略图)
 * @author liuboen liuboen@zba.com
 * @date 2011-7-21 下午02:11:09
 * @version V1.0  
 */

public class ImageUtil {

	/**
	 * 替换jpg的后缀,获取目前的缩略图(临时的)
	 * 
	 * @param jpgFileName(jpg的文件全名称,非url)
	 * @return 
	 */
	public static String getJpgThumb(String jpgFileName){
		return StringUtil.replace(jpgFileName, ".jpg","_thumb.jpg");
	}
}
