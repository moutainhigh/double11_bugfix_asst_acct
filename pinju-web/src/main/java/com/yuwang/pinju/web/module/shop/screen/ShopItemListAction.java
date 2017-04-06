/**
 * 品聚生活，创造奇迹
 */
package com.yuwang.pinju.web.module.shop.screen;

import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.core.shop.ao.ShopInfoMemcacheAO;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.item.ItemTagMetaInfo;
import com.yuwang.pinju.domain.search.SearchItemDO;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.domain.shop.SearchResultExt;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.module.PinjuControl;
import com.yuwang.pinju.web.module.member.control.MemberSellerQualityControl;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author liyouguo
 * 
 * @since 2011-8-4
 */
public class ShopItemListAction extends BaseWithUserAction {

	/**
	 * 店铺编号
	 */
	private Integer shopId;

	/**
	 * 分页的当前页码
	 */
	private Integer currentPage = 1;

	/**
	 * 搜索引擎接口
	 */
	private SearchManager searchManager;

	/**
	 * 用户选中的当前分类
	 */
	private String categoryId = "0";

	/**
	 * 分类名称
	 */
	private String categoryName;

	/**
	 * 查询结果
	 */
	private SearchResultExt query;

	/**
	 * 查询关键字
	 */
	private String searchKey;

	/**
	 * 起始价格区间
	 */
	private Float startPrice;

	/**
	 * 结束价格区间
	 */
	private Float endPrice;
	/**
	 * 店铺装修页
	 */
	private ShopUserPageManager shopUserPageManager;

	/**
	 * 店铺装修
	 */
	private ShopUserPageAO shopUserPageAO;

	/**
	 * 获取店铺信息
	 */
	private ShopShowInfoManager shopShowInfoManager;
	private ShopInfoMemcacheAO shopInfoMemcacheAO;


	public ShopInfoMemcacheAO getShopInfoMemcacheAO() {
		return shopInfoMemcacheAO;
	}

	public void setShopInfoMemcacheAO(ShopInfoMemcacheAO shopInfoMemcacheAO) {
		this.shopInfoMemcacheAO = shopInfoMemcacheAO;
	}
	private String shopName;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		/**
		 * 获取指定分类下的商品列表
		 */
		SearchItemDO searchShopItem = new SearchItemDO();
		searchShopItem.setCount("20");
		searchShopItem.setStart(String.valueOf(currentPage));
		ShopInfoDO shopInfo = shopShowInfoManager.queryShopInfoByShopId(shopId, ShopConstant.APPROVE_STATUS_YES);
		if (shopInfo == null)
			return ERROR;
		shopName = shopInfo.getName();
		searchShopItem.setSellerId(String.valueOf(shopInfo.getUserId()));
		searchShopItem.setQ(searchKey);
		searchShopItem.setStartPrice(String.valueOf(startPrice));
		searchShopItem.setEndPrice(String.valueOf(endPrice));
		searchShopItem.setIp(this.getClientIp());
		searchShopItem.setStoreCategories(categoryId);
		SearchResult result = searchManager.searchItemByShop(searchShopItem);
		query = new SearchResultExt(result);
		query.setAction("/detail/shopItemList.htm");
		query.setShopUpHtml(shopUserPageManager.getTopPageHtml(shopInfo.getUserId()));
		query.setShopLeftHtml(shopUserPageManager.getLeftPageHtml(shopInfo.getUserId()));
		query.setShopFitment(shopUserPageAO.getSkinColor(shopInfo.getUserId(), shopId, true));
		/**********************************店铺标签 start***********************************/
		try{
			query.setItemTagMetaInfo(getShopTag(shopInfo.getUserId()));
		}catch(Exception e){
			log.error("商品列表页面显示店铺标签异常",e);
		}
		/**********************************店铺标签 end***********************************/
		setSellerApInfo(shopInfo.getUserId());
		return SUCCESS;
	}

	/**
	 * 设置卖家资质信息
	 * 
	 * @param memberId
	 */
	private void setSellerApInfo(long memberId) {
		PinjuControl pc = new MemberSellerQualityControl(memberId);
		pc.doControl();
	}

	public Paginator getQuery() {
		return query;
	}

	public void setQuery(SearchResultExt t) {
		this.query = t;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public ShopUserPageAO getShopUserPageAO() {
		return shopUserPageAO;
	}

	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}

	public ShopUserPageManager getShopUserPageManager() {
		return shopUserPageManager;
	}

	public void setShopUserPageManager(ShopUserPageManager shopUserPageManager) {
		this.shopUserPageManager = shopUserPageManager;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		categoryId = null;
		this.searchKey = searchKey;
	}

	public void setStartPrice(Float startPrice) {
		this.startPrice = startPrice;
	}

	public void setEndPrice(Float endPrice) {
		this.endPrice = endPrice;
	}
	//获取店铺标签
	private ItemTagMetaInfo getShopTag(Long userId){
		ItemTagMetaInfo itemTagMetaInfo = new ItemTagMetaInfo();
		//调用缓存数据
		ShopInfoDO shopInfoDO = shopInfoMemcacheAO.getShopInfoDO(userId);
		//获取店铺标签
		if(shopInfoDO.getShopLabel()!=null){
			itemTagMetaInfo.setShopLabel(shopInfoDO.getShopLabel());
			List <String> shopLabelList = new ArrayList<String>();
			String[] strShopLabel = shopInfoDO.getShopLabel().split(" ");
			if(strShopLabel!=null){
				for(int i=0;i<strShopLabel.length;i++){
					shopLabelList.add(strShopLabel[i]);
				}
			}else{
				shopLabelList.add("品聚");
			}
			itemTagMetaInfo.setShopLabelList(shopLabelList);
		}
		return itemTagMetaInfo;
	}
}
