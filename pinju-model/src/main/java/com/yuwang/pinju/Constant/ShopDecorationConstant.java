package com.yuwang.pinju.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopDecorationConstant {
	public static List<String> PICTURE_ROTATION_HEGHT_SIZE_LIST = new ArrayList<String>(); 
	static{
		PICTURE_ROTATION_HEGHT_SIZE_LIST.add("120px");
		PICTURE_ROTATION_HEGHT_SIZE_LIST.add("140px");
		PICTURE_ROTATION_HEGHT_SIZE_LIST.add("200px");
		PICTURE_ROTATION_HEGHT_SIZE_LIST.add("300px");
		PICTURE_ROTATION_HEGHT_SIZE_LIST.add("400px");
		PICTURE_ROTATION_HEGHT_SIZE_LIST.add("500px");
		PICTURE_ROTATION_HEGHT_SIZE_LIST.add("自定义");
	}
	
	public static List<String> PICTURE_ROTATION_EFFECT_LIST = new ArrayList<String>();
	static{
		PICTURE_ROTATION_EFFECT_LIST.add("左右滚动");
		PICTURE_ROTATION_EFFECT_LIST.add("渐变滚动");
	}
	
	public static final Integer INDEX_PAGE = 2;
	
	public static final Integer TYPE_HEAD = 0;
	
	public static final Integer TYPE_LEFT = 1;
	
	public static final Integer TYPE_RIGHT = 2;
	
	public static final Integer TYPE_FOOTER = 3;
	
	public static final Integer MODULE_PICTURE = 12;
	
	
	public static final String SAVE_PATH = "d:/pic/";
	
	public static final String[] CUSTOM_PAGES = new String[]{"","","","","","","","","","","","","","TopHtml1","TopHtml2","TopHtml3","LeftHtml1","LeftHtml2","LeftHtml3","RightHtml1","RightHtml2","RightHtml3","FooterHtml"};
	
	public static List<String> picSize = new ArrayList<String>();
	static{
//		picSize.add("120px");
		picSize.add("160px");
//		picSize.add("220px");
	}
	
	public static List<String> itemShowCount = new ArrayList<String>();
	static{
		itemShowCount.add("3");
		itemShowCount.add("4");
		itemShowCount.add("6");
		itemShowCount.add("8");
		itemShowCount.add("10");
		itemShowCount.add("12");
		itemShowCount.add("16");
	}
	
	public static List<String> sorttype = new ArrayList<String>();
	static{
		sorttype.add("最新上架在前");
		sorttype.add("最新上架在后");
		sorttype.add("价格从低到高");
		sorttype.add("价格从高到低");
	}
	
	public static final int PROMOTE_PAGE_SHOW_COUNT = 5;
	
	public static Map<String, String> KEEPER_PROMOTE_SORT = new HashMap<String, String>();
	static{
		KEEPER_PROMOTE_SORT.put("最新上架在前", "GMT_CREATE DESC");
		KEEPER_PROMOTE_SORT.put("最新上架在后", "GMT_CREATE ASC");
		KEEPER_PROMOTE_SORT.put("价格从低到高", "PRICE ASC");
		KEEPER_PROMOTE_SORT.put("价格从高到低", "PRICE DESC");
	}
	
	public static Map<String,String> SORT_NAME_LIST;
	static {
		SORT_NAME_LIST = new HashMap<String,String>();
		SORT_NAME_LIST.put("最新上架在前","startTime-desc");
		SORT_NAME_LIST.put("最新上架在后","startTime-asc");
		SORT_NAME_LIST.put("价格从高到低","price-desc");
		SORT_NAME_LIST.put("价格从低到高","price-asc");
	}
	
	public static Map<String,String> SORT_NAME_LIST_ItemPromote;
	static {
		SORT_NAME_LIST_ItemPromote = new HashMap<String,String>();
		SORT_NAME_LIST_ItemPromote.put("GMT_CREATE DESC","startTime-desc");
		SORT_NAME_LIST_ItemPromote.put("PRICE DESC","price-desc");
		SORT_NAME_LIST_ItemPromote.put("PRICE ASC","price-asc");
	}
	
}
