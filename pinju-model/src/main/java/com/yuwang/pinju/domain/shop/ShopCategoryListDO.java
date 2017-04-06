package com.yuwang.pinju.domain.shop;

import java.io.StringReader;
import java.util.*;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.domain.BaseDO;

@SuppressWarnings("unchecked")
public class ShopCategoryListDO extends BaseDO {
	
	public ShopCategoryListDO(){
		subCategoryList=new ArrayList();
		subCategoryMap = new HashMap<String, String>();
	}
	
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -4991904670096091190L;

	private String id;
	/**
	 * 一级店铺分类
	 */
	private  String  categoryName;
	
	/**
	 * 一级店铺分类图片
	 */
	private  String	 categoryImage;
	
	/**
	 * 一级店铺商品列表
	 */
	private  List<Long>  categoryGoods;
	
	private List  subCategoryList;
	private Map<String, String> subCategoryMap;

	public Map<String, String> getSubCategoryMap() {
		return subCategoryMap;
	}

	public void setSubCategoryMap(Map<String, String> subCategoryMap) {
		this.subCategoryMap = subCategoryMap;
	}

	private Map<String, List<Long>> secondeGoodsList; 
	
	public Map<String, List<Long>> getSecondeGoodsList() {
		return secondeGoodsList;
	}

	public void setSecondeGoodsList(Map<String, List<Long>> secondeGoodsList) {
		this.secondeGoodsList = secondeGoodsList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 所有店铺别的分类商品列表
	 */
	private String otherGoods;
	
    
	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public String getCategoryImage() {
		return categoryImage;
	}


	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}


	public List<Long> getCategoryGoods() {
		return categoryGoods;
	}


	public void setCategoryGoods(List<Long> categoryGoods) {
		this.categoryGoods = categoryGoods;
	}


	public String getOtherGoods() {
		return otherGoods;
	}


	public void setOtherGoods(String otherGoods) {
		this.otherGoods = otherGoods;
	}
	
	public List getSubCategoryList() {
		return subCategoryList;
	}


	public void setSubCategoryList(List subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	
	/**
	 * 取子类的所有ID,没有则取本身的ID
	 * @return
	 */
	public String getCategoryId(){
		StringBuffer sb = new StringBuffer();
		if(subCategoryList!= null && subCategoryList.size()>0){
			for(Object s:subCategoryList){
				sb.append(",").append((String)s);
			}
		}
		if(sb.toString().length()>0)
			return sb.toString().substring(1);
		return id;
	}
	
	public List<Long> getGoodsList(String goodsList, String key) {
		if(goodsList == null || goodsList.trim().length() == 0)
			return null;
		String myGoods = getConfig(goodsList, key);
		if(myGoods==null||myGoods.length()==0){
			return null;
		}
		String[] ids = myGoods.split(",");
		List<Long> itemIds = new ArrayList<Long>();
		for(String id : ids) {
			itemIds.add(new Long(id.trim()));
		}
		return itemIds;
	}

    /**
     * 获取配置参数值
     *
     * @param key key
     * @return 配置参数值
     */
    public String getConfig(String content, String key) {
    	Properties prop = new Properties();
        if (StringUtils.isNotEmpty(content)) {
            try {
            	prop.load(new StringReader(content));
            } catch (Exception ignored) {

            }
        }
        return prop != null ? prop.getProperty(key) : null;
    }

}
