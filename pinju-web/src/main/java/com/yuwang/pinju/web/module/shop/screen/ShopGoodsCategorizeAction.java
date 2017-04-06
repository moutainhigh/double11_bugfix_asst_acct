package com.yuwang.pinju.web.module.shop.screen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.search.manager.SearchManager;
import com.yuwang.pinju.core.shop.ao.ShopCategoryAO;
import com.yuwang.pinju.domain.search.SearchShopItem;
import com.yuwang.pinju.domain.search.result.ItemResult;
import com.yuwang.pinju.domain.search.result.SearchResult;
import com.yuwang.pinju.domain.shop.ItemCategorizeDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * 商品归类
 * 
 * @author liyouguo
 * 
 * @since 2011-7-15
 */
public class ShopGoodsCategorizeAction extends BaseWithUserAction {
	/**
	 * 店铺商品分类AO
	 */
	private ShopCategoryAO shopCategoryAO;

	/**
	 * 商品接口
	 */
	private ItemManager itemManager;

	/**
	 * 搜索引擎接口
	 */
	private SearchManager searchManager;

	/**
	 * 店铺商品分类列表
	 */
	private Map<String, ShopCategoryListDO> shopCategoryList;

	/**
	 * 对应分类下的商品列表
	 */
	private List<ItemCategorizeDO> itemCategorizeList;

	/**
	 * 分页的当前页码
	 */
	private Integer pageId = 1;

	/**
	 * 总条数（商品列表）
	 */
	private Integer allCount;

	/**
	 * 总的页数
	 */
	private Integer allPages;

	/**
	 * 用户选中的当前分类
	 */
	private String categoryId = "0";

	/**
	 * 用户选择移动还是增加 0：移动 1：增加 2：删除
	 */
	private Short moveOrAdd;

	/**
	 * 当前商品列表归类（分类编号）
	 */
	private String itemCategorizeId;

	/**
	 * 归类商品列表
	 */
	private String[] saveItemList;

	/**
	 * 结果
	 */
	private String result = "保存成功。";

	public Integer getAllPages() {
		return allPages;
	}

	public void setAllPages(Integer allPages) {
		this.allPages = allPages;
	}

	public Integer getAllCount() {
		return allCount;
	}

	public void setAllCount(Integer allCount) {
		this.allCount = allCount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getItemCategorizeId() {
		return itemCategorizeId;
	}

	public void setItemCategorizeId(String itemCategorizeId) {
		this.itemCategorizeId = itemCategorizeId;
	}

	public String[] getSaveItemList() {
		return saveItemList;
	}

	public void setSaveItemList(String[] saveItemList) {
		this.saveItemList = saveItemList;
	}

	public Short getMoveOrAdd() {
		return moveOrAdd;
	}

	public void setMoveOrAdd(Short moveOrAdd) {
		this.moveOrAdd = moveOrAdd;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public List<ItemCategorizeDO> getItemCategorizeList() {
		return itemCategorizeList;
	}

	public void setItemCategorizeList(List<ItemCategorizeDO> itemCategorizeList) {
		this.itemCategorizeList = itemCategorizeList;
	}

	public Map<String, ShopCategoryListDO> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(Map<String, ShopCategoryListDO> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public ShopCategoryAO getShopCategoryAO() {
		return shopCategoryAO;
	}

	public void setShopCategoryAO(ShopCategoryAO shopCategoryAO) {
		this.shopCategoryAO = shopCategoryAO;
	}

	public SearchManager getSearchManager() {
		return searchManager;
	}

	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		Integer shopId = getUserShopId();
		if (shopId == null) {
			errorMessage = "用户未开店";
			return ERROR;
		}

		/**
		 * 获取所有分类列表
		 */
		shopCategoryList = shopCategoryAO.queryShopCategoryList(shopId);

		/**
		 * 获取指定分类下的商品列表
		 */
		SearchShopItem searchShopItem = new SearchShopItem();
		searchShopItem.setCount(String.valueOf(20));
		searchShopItem.setStart(String.valueOf(pageId));
		searchShopItem.setSellerId(getUserId());
		List status = new ArrayList();
		status.add("0");
		status.add("1");
		searchShopItem.setStatus(status);
		searchShopItem.setShopCategory(categoryId);
		SearchResult searchResult = searchManager.searchItemByShopFromDB(searchShopItem);
		allCount = searchResult.getItems();
		allPages = searchResult.getPages() == 0 ? 1 : searchResult.getPages();

		List<Object> list = searchResult.getResultList();
		itemCategorizeList = new ArrayList<ItemCategorizeDO>();
		for (Object o : list) {
			ItemResult item = (ItemResult) o;
			ItemCategorizeDO itemCate = new ItemCategorizeDO();
			itemCate.setId(item.getId());
			itemCate.setTitle(item.getTitle());
			itemCate.setPicUrl(item.getPicUrl());
			itemCate.setItemCates(item.getId() + "_"
					+ (item.getStoreCategories() == null ? "" : item.getStoreCategories()));
			try {
				itemCate.setCategoryNameList(createCateNameList(item.getStoreCategories().split(",")));
			} catch (Exception ignore) {
				itemCate.setCategoryNameList(new HashMap<String, String>());
			}
			itemCategorizeList.add(itemCate);
		}
		return SUCCESS;
	}

	/**
	 * 保存用户提交的数据（包括移动、增加、删除）
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveItemCategorize() throws Exception {
		if (saveItemList == null || saveItemList.length == 0)
			return execute();
		if (itemCategorizeId == null || "".equals(itemCategorizeId.trim()))
			return execute();
		Map<Long, String> updateItemMap = new HashMap<Long, String>();
		for (String item : saveItemList) {
			try {
				String[] itemArray = item.split("_");
				StringBuffer cateStr = new StringBuffer("");
				switch (moveOrAdd) {
				case 0:// 移动
					cateStr.append(itemCategorizeId);
					break;
				case 1:// 增加
					if (itemArray.length == 1 || itemArray[1] == null || "".equals(itemArray[1].trim()))
						cateStr.append(itemCategorizeId);
					else {
						String[] cateIds = itemArray[1].split(",");
						if (inArray(cateIds, itemCategorizeId))
							cateStr.append(itemArray[1].trim());
						else
							cateStr.append(itemArray[1].trim()).append(",").append(itemCategorizeId);
					}
					break;
				case 2:// 删除
					int i = 0;
					String[] cateIds = itemArray[1].split(",");
					for (String cateId : cateIds) {
						if (itemCategorizeId.equals(cateId))
							continue;
						if (i > 0)
							cateStr.append(",").append(cateId);
						else {
							cateStr.append(cateId);
							i++;
						}
					}
					if (i == 0)
						cateStr.append(" ");
					break;
				}
				updateItemMap.put(new Long(itemArray[0]), cateStr.toString());
			} catch (Exception ignore) {
			}
		}
		try {
			itemManager.updateItemStoreCategories(updateItemMap);
		} catch (Exception e) {
			log.error("保存归类失败", e);
			return ERROR;
		}
		return execute();
	}

	private boolean inArray(String[] strs, String str) {
		if (strs == null || strs.length == 0)
			return false;
		for (String s : strs) {
			String sTrim = s == null ? "" : s.trim();
			if (sTrim.equals(str))
				return true;
		}
		return false;
	}

	/**
	 * 生成商品当前归类列表（名称 一级分类-->二级分类）
	 * 
	 * @param cateIds
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> createCateNameList(String[] cateIds) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		for (String cateId : cateIds) {
			if (cateId == null || "".equals(cateId.trim()))
				continue;
			for (String key : shopCategoryList.keySet()) {
				ShopCategoryListDO category = shopCategoryList.get(key);
				if (category.getSubCategoryMap().size() == 0 && cateId.equals(category.getId())) {// 只有一级目录
					map.put(cateId, category.getCategoryName());
					break;
				} else if (category.getSubCategoryMap().containsKey(cateId)) {
					map.put(cateId, category.getCategoryName() + "</span><span class=\"aro\"></span><span>"
							+ category.getSubCategoryMap().get(cateId));
					break;
				}
			}
		}
		return map;
	}
}
