package com.yuwang.pinju.domain.shop;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.domain.ConfigurableSupport;
import com.yuwang.pinju.filter.PropFilter;

/**
 * 店铺分类实体
 * 
 * @author mike
 *
 * @since 2011-6-8
 */
@SuppressWarnings("unchecked")
public class ShopCategoryDO extends ConfigurableSupport implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 3583497257201580598L;
	/**
	 * id号
	 */
	private Integer id;
	/**
	 * 店铺id
	 */
	private Integer shopId;
	
	/**
	 * 卖家id
	 */
	private Long userId;
	
	/**
	 * 其它分类下的商品列表
	 */
	private String otherGoods;
	/**
	 * 二级分类
	 */
	private String secondCategory;
	/**
	 * 二级分类下的商品
	 */
	private String secondGoods;
	/**
	 * 二级分类图片
	 */
	private String secondImage;
	
	/**
	 * 第一级分类
	 */
	private String firstCategory;
	/**
	 * 一级分类下的商品，以分隔符的商品列表
	 */
	private String firstGoods;
	/**
	 * 一级分类图片
	 */
	private String firstImage;
	
	/**
	 * 二级分类列表
	 */
 	private List subShopCategoryList;
    
    
    /**
     * 根据配置参数生成的配置项
     */
    private Properties categoryProperties;
    
    private Properties secondGoodsProperties;
    
    
	private Date gmtCreate;
	private Date gmtModified;
	
	
	
	 /**
     * 获取二级分类列表
     *
     * @param key 一级分类名字:衣服
     * 
     * @return 二级分类列表采用,分隔符eg:短袖,长袖
     */
	public String getCategoryConfig(String key) {
		if (categoryProperties == null
				&& StringUtils.isNotEmpty(secondCategory)) {
			try {
				secondCategory = PropFilter.doFilter(secondCategory);
				categoryProperties = new Properties();
				categoryProperties.load(new StringReader(secondCategory));
			} catch (Exception ignored) {

			}
		}
		return categoryProperties != null ? categoryProperties.getProperty(key)
				: null;
	}
    
    /**
     * 获取二级分类商品
     * @param key
     * @return
     */
	public String getSecondGoodsConfig(String key) {
		if (secondGoodsProperties == null
				&& StringUtils.isNotEmpty(secondGoods)) {
			try {
				secondGoodsProperties = new Properties();
				secondGoodsProperties.load(new StringReader(secondGoods));
			} catch (Exception ignored) {

			}
		}
		return secondGoodsProperties != null ? secondGoodsProperties
				.getProperty(key) : null;
	}
	
    /**
     * 二级分类商品中是否有这个分类
     * @param key
     * @return
     */
	public boolean hasSecondGoodsKey(String key) {
		if (secondGoodsProperties == null
				&& StringUtils.isNotEmpty(secondGoods)) {
			try {
				secondGoodsProperties = new Properties();
				secondGoodsProperties.load(new StringReader(secondGoods));
			} catch (Exception ignored) {

			}
		}
		return secondGoodsProperties != null ? secondGoodsProperties
				.containsKey(key) : false;
	}
    
    /**
     * 删除二级分类商品中这个分类
     * @param key
     * @return
     */
	public void removeSecondGoods(String key) {
		if (secondGoodsProperties == null
				&& StringUtils.isNotEmpty(secondGoods)) {
			try {
				secondGoodsProperties = new Properties();
				secondGoodsProperties.load(new StringReader(secondGoods));
			} catch (Exception ignored) {

			}
		}
		if(secondGoodsProperties != null){
			secondGoodsProperties.remove(key);
			secondGoods = getSecondGoodsStr();
		}
		
	}
    
    /**
     * 添加一个二级分类商品
     * @param key
     * @param value
     */
	public void addSecondGoods(String key, String value) {
		if (secondGoodsProperties == null) {
			try {
				secondGoodsProperties = new Properties();
				secondGoodsProperties.load(new StringReader(secondGoods==null?"":secondGoods));
			} catch (Exception ignored) {

			}
		}
		secondGoodsProperties.put(key, value==null?"":value);
		secondGoods = getSecondGoodsStr();
	}
    
    
	/**
	 * 获取一个二级分类的所有商品
	 * @return
	 */
	public String getSecondGoodsValues(){
		if (secondGoodsProperties == null) {
			try {
				secondGoodsProperties = new Properties();
				secondGoodsProperties.load(new StringReader(secondGoods==null?"":secondGoods));
			} catch (Exception ignored) {

			}
		}
		StringBuffer sb = new StringBuffer();
		Set set = secondGoodsProperties.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			String value = secondGoodsProperties.getProperty(key);
			sb.append(",").append(value);
		}
		return sb.toString().length()>0?sb.toString().substring(1):"";
	}
    
	/**
	 * 获取一个二级分类商品的字符串
	 * @return
	 */
	public String getSecondGoodsStr(){
		if (secondGoodsProperties == null) {
			try {
				secondGoodsProperties = new Properties();
				secondGoodsProperties.load(new StringReader(secondGoods==null?"":secondGoods));
			} catch (Exception ignored) {

			}
		}
		StringBuffer sb = new StringBuffer();
		Set set = secondGoodsProperties.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			String value = secondGoodsProperties.getProperty(key);
			sb.append(key).append("=").append(value).append(ShopConstant.SHOP_NEWLINE);
		}
		return sb.toString();
	}

	public void clearDefaultGoods(){
		if(secondGoodsProperties==null) return;
		StringBuffer sb = new StringBuffer();
		String defaultGoods = secondGoodsProperties.getProperty(ShopConstant.SHOP_DEFAULT_CATEGORYNAME+ShopConstant.SHOP_CATEGORY_SPLIT+ShopConstant.SHOP_DEFAULT_CATEGORYNAME);
		if(defaultGoods.length()>0){
			int i=0;
			String[] goodss = defaultGoods.split(ShopConstant.SHOP_VALUE_SPLIT);
			Set set = secondGoodsProperties.keySet();
			for(String goods:goodss){
				Iterator it = set.iterator();
				while(it.hasNext()){
					String key = it.next().toString();
					if(key.equals(ShopConstant.SHOP_DEFAULT_CATEGORYNAME+ShopConstant.SHOP_CATEGORY_SPLIT+ShopConstant.SHOP_DEFAULT_CATEGORYNAME)) continue;
					String values = secondGoodsProperties.getProperty(key);
					if(values!=null){
						String[] valueStr = values.split(ShopConstant.SHOP_VALUE_SPLIT);
						for(String value:valueStr){
							if(goods.equals(value)) {
								i=1;
								break;
							}
						}
					}
					if(i==1) break;
				}
				if(i==0){
					if(sb.toString().indexOf(ShopConstant.SHOP_VALUE_SPLIT+goods)<0){
						sb.append(ShopConstant.SHOP_VALUE_SPLIT).append(goods);
					}
				}
				i=0;
			}
			secondGoodsProperties.put(ShopConstant.SHOP_DEFAULT_CATEGORYNAME+ShopConstant.SHOP_CATEGORY_SPLIT+ShopConstant.SHOP_DEFAULT_CATEGORYNAME, sb.toString().length()>0?sb.toString().substring(1):"");
			secondGoods = getSecondGoodsStr();
		}
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getShopId() {
		return shopId;
	}
	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	public String getOtherGoods() {
		return otherGoods;
	}
	public void setOtherGoods(String otherGoods) {
		this.otherGoods = otherGoods;
	}
	public String getSecondCategory() {
		return secondCategory;
	}
	public void setSecondCategory(String secondCategory) {
		this.secondCategory = secondCategory;
	}
	public String getSecondGoods() {
		return secondGoods;
	}
	public void setSecondGoods(String secondGoods) {
		this.secondGoods = secondGoods;
	}
	public String getSecondImage() {
		return secondImage;
	}
	public void setSecondImage(String secondImage) {
		this.secondImage = secondImage;
	}

	public String getFirstCategory() {
		return firstCategory;
	}
	public void setFirstCategory(String firstCategory) {
		this.firstCategory = firstCategory;
	}
	public String getFirstGoods() {
		return firstGoods;
	}
	public void setFirstGoods(String firstGoods) {
		this.firstGoods = firstGoods;
	}
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public List getSubShopCategoryList() {
		return subShopCategoryList;
	}
	public void setSubShopCategoryList(List subShopCategoryList) {
		this.subShopCategoryList = subShopCategoryList;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getUserId() {
		return userId;
	}
}