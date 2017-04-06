package com.yuwang.pinju.web.module.shop.screen;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.ShopDecorationConstant;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.item.ItemConstant;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.shop.dao.ShopUserModuleParamDao;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQueryEx;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public class ShopQueryPromoteItemAction extends ShopOpenBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ShopBaseDesignerManager shopDesignkeeperPromoteManager;

	private ShopUserModuleParamDao shopUserModuleParamDao;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private Integer pageId;

	private Integer moduleId;

	private ItemManager itemManager;

	private Integer page;

	public String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public ShopUserModuleParamDao getShopUserModuleParamDao() {
		return shopUserModuleParamDao;
	}

	public void setShopUserModuleParamDao(
			ShopUserModuleParamDao shopUserModuleParamDao) {
		this.shopUserModuleParamDao = shopUserModuleParamDao;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public ShopBaseDesignerManager getShopDesignkeeperPromoteManager() {
		return shopDesignkeeperPromoteManager;
	}

	public void setShopDesignkeeperPromoteManager(
			ShopBaseDesignerManager shopDesignkeeperPromoteManager) {
		this.shopDesignkeeperPromoteManager = shopDesignkeeperPromoteManager;
	}

	private JSONObject queryPromotePageShow(long userId) {
		ItemQueryEx itemQuery = new ItemQueryEx();
		try {
			itemQuery.setSellerId(userId);
			itemQuery.setPage(page);
			itemQuery.setItemsPerPage(ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT);
			List<Long> status = new ArrayList<Long>();
			status.add(ItemConstant.STATUS_TYPE_0);
			status.add(ItemConstant.STATUS_TYPE_1);
			itemQuery.setStatus(status);
			int count = itemManager.getItemListCount(itemQuery);
			itemQuery.setItems((page) * ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT);
			int totalPage = count % ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT > 0 ? count / ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT + 1 : count / ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT;
			List<ItemDO> itemsList = itemManager.queryItemListEx(itemQuery);
			//List<ItemDO> itemsList = itemManager.getItemList(itemQuery);

			ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
			shopUserModuleParamDO.setModuleId(new Long(moduleId));
			shopUserModuleParamDO.setUserPageId(new Long(pageId));
			List<ShopUserModuleParamDO> paramList = shopUserModuleParamDao
					.queryShopUserModuleParam(shopUserModuleParamDO);
			String promoteStrings = "";
			if (paramList != null && paramList.size() > 0) {
				shopUserModuleParamDO = paramList.get(0);
				promoteStrings = shopUserModuleParamDO.getConfig(
						"ITEMIDS", false);
			}
//				if (promoteStrings != null && promoteStrings.length() > 0) {
					if (itemsList != null && itemsList.size() > 0) {
						List<ItemDO> items = new ArrayList<ItemDO>();
						String[] isPromoted = new String[itemsList.size()];
						if(promoteStrings!=null){
							for (int i = 0; i < itemsList.size(); i++) {
								ItemDO item = itemsList.get(i);
								if (promoteStrings.indexOf(String.valueOf(item
										.getId())) != -1) {
									items.add(item);
									isPromoted[i] = "1";
								} else {
									isPromoted[i] = "0";
								}
							}
						}

//						List<ItemDO> promoteShowItemList = new ArrayList<ItemDO>();
//						int totalPage = itemManager.getItemListCount(itemQuery);

//						int cycleInt = page
//								* ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT;
//						if (page == totalPage) {
//							cycleInt = itemsList.size();
//						}

//						for (int i = page
//								* ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT
//								- ShopDecorationConstant.PROMOTE_PAGE_SHOW_COUNT; i < cycleInt; i++) {
//							promoteShowItemList.add(itemsList.get(i));
//						}
						JSONObject json = new JSONObject();
						json.put("currentPage", page);
						json.put("totalPage",totalPage);
						json.put("nextPage", page + 1);
						json.put("prevPage", page - 1);

						json.put("isPromoted", isPromoted);
						json.put("ITEMLIST", itemsList);

						log.info("json = " + json.toString());
						return json;
					}
//				}
//			}

		} catch (ManagerException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
		return null;
	}

	public String queryPromoteItem() {
		long userId = queryUserId();
		JSONObject json = this.queryPromotePageShow(userId);
		result = json.toString();
		return "success";
	}
}
