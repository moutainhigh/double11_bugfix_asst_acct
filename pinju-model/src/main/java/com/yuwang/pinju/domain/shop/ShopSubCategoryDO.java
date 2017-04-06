package com.yuwang.pinju.domain.shop;

import com.yuwang.pinju.domain.BaseDO;

/**
 * 店铺二级分类
 * 
 * @author mike
 *
 * @since 2011-6-3
 */
public class ShopSubCategoryDO  extends BaseDO{

	private static final long serialVersionUID = 5089516703661347989L;

	/**
	 * 二级分类名字
	 */
	private String categoryName;
	
	/**
	 * 二级店铺分类图片
	 */
	private  String	 categoryImage;
	
	/**
	 * 二级店铺商品列表
	 */
	private  String  categoryGoods;
	
	
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

	public String getCategoryGoods() {
		return categoryGoods;
	}

	public void setCategoryGoods(String categoryGoods) {
		this.categoryGoods = categoryGoods;
	}
	
	
}
