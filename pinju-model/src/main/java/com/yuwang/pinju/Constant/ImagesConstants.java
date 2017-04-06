package com.yuwang.pinju.Constant;

import java.util.ArrayList;
import java.util.List;


public class ImagesConstants {
	public static final long userMaxSize = 1073741824;
	/**字节转成兆换算单位**/
	public static final long unitSize = 1048576;
	
	/****
	 * 图片显示排序
	 */
	public static  List<String[]> STORAGE_FILE_ORDER = new ArrayList<String[]>();
	static{
		STORAGE_FILE_ORDER.add(new String[]{"1","按上传时间从近到远"});
		STORAGE_FILE_ORDER.add(new String[]{"2","按上传时间从远到近"});
		STORAGE_FILE_ORDER.add(new String[]{"3","按图片从大到小"});
		STORAGE_FILE_ORDER.add(new String[]{"4","按图片从小到大"});
		STORAGE_FILE_ORDER.add(new String[]{"5","按图片名降序"});
		STORAGE_FILE_ORDER.add(new String[]{"6","按图片名升序"});
	}
}
