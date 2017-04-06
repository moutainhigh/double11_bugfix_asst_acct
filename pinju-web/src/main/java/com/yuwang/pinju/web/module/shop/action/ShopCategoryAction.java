package com.yuwang.pinju.web.module.shop.action;

import java.util.Date;

import com.yuwang.pinju.core.shop.manager.ShopCategoryManager;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;
/**
 * 店铺商品分类所有操作
 * 
 * @author mike
 *
 * @since 2011-6-4
 */
public class ShopCategoryAction extends BaseWithUserAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5090516839046206927L;

	/**
	 * 店铺商品分类管理
	 */
	private ShopCategoryManager  shopCategoryManager;
	
	private String firstCategory;
	
	private String categoryList;

	private Integer sequenceId;

	private Integer shopId;
	public String getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(String categoryList) {
		this.categoryList = categoryList;
	}

	/**
   * 构建一个商品分类
   * 
   * @return 商品分类
   */
  public ShopCategoryDO constructShopCategory(Integer shopId,String firstCategory,String categoryList){
  	ShopCategoryDO  shopDO=new ShopCategoryDO();
  	shopDO.setFirstCategory(firstCategory);
  	shopDO.setSecondCategory(categoryList);
  	shopDO.setShopId(shopId);
  	shopDO.setGmtModified(new Date());
  	return shopDO;
  }
  
	/**
	 * 插入新的商品分类
	 * 
	 * @return 商品分类
	 *
	 * @throws Exception
	 */
	public String insertShopCategory()throws Exception{
		shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		ShopCategoryDO categoryDO=constructShopCategory(shopId,firstCategory,categoryList);
		categoryDO.setUserId(getUserId());
		if(categoryDO.getFirstCategory().length()>3000){
			errorMessage = "一级分类超出长度限制";
			return "error";
		}
		if(categoryDO.getSecondCategory().length()>3000){
			errorMessage = "二级分类超出长度限制";
			return "error";
		}
		if(sequenceId==0){
			categoryDO.setGmtCreate(new Date());
			shopCategoryManager.insertShopCategroy(categoryDO);
		}else{
			categoryDO.setId(sequenceId);
			shopCategoryManager.updateShopCategroy(categoryDO);
		}
		return "success";
	}


	public void setShopCategoryManager(ShopCategoryManager shopCategoryManager) {
		this.shopCategoryManager = shopCategoryManager;
	}


	public void setFirstCategory(String firstCategory) {
		this.firstCategory = firstCategory;
	}

	public String getFirstCategory() {
		return firstCategory;
	}

	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
	}

	public Integer getSequenceId() {
		return sequenceId;
	}

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
}
