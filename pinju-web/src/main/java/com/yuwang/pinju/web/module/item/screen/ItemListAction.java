package com.yuwang.pinju.web.module.item.screen;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.common.DesCryptUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.ao.ItemListAO;
import com.yuwang.pinju.core.item.ao.ItemSalesStatAO;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.shop.manager.ShopShieldManager;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

public class ItemListAction extends ActionSupport {
	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
	private List<ItemDO> itemList;
	private ItemQuery itemQuery;
	private String listType;
	private ItemListAO itemListAO;
	private ShopShowInfoManager shopShowInfoManager;
	private ItemSalesStatAO itemSalesStatAO;
	private ShopShieldManager shopShieldManager;
	private MemberAsstLog memberAsstLog;
	
	public MemberAsstLog getMemberAsstLog() {
		return memberAsstLog;
	}

	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}

	public void setShopShieldManager(ShopShieldManager shopShieldManager) {
		this.shopShieldManager = shopShieldManager;
	}

	public void setItemSalesStatAO(ItemSalesStatAO itemSalesStatAO) {
		this.itemSalesStatAO = itemSalesStatAO;
	}

	private int itemAmount = 0;
	
	/**
	 * 返回路径
	 */
	private String returnUrl;


	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public List<ItemDO> getItemListKey(List<ItemDO> itemList) {
		if(itemList!=null&&itemList.size()>0){
			for(int i =0; i<itemList.size(); i++){
				ItemDO item = itemList.get(i);
				String base=item.getId()+":seller";
				String itemKey= DesCryptUtil.encrypt(base);
				ItemSalesStatDO itemSalesStatDO = itemSalesStatAO.getItemSalesStatById(item.getId());
				if (itemSalesStatDO != null) {
					item.setSales(itemSalesStatDO.getBuyNum());
				} else {
					item.setSales(new Long(0));
				}
				item.setItemKey(itemKey);
			}
		}
		return itemList;
	}
	
	private long getMasterUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		}
		return userId;
	}
	
	/**
	 * 显示 商品列表
	 * 
	 * @return
	 */
	public String itemList() {

		if (itemQuery == null) {
			itemQuery = new ItemQuery();
		}
		long sellerId = this.getMasterUserId();
		//判断用户是否开店
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(sellerId,null);
			if(shopInfoDO==null||shopInfoDO.getShopId()==null){
				if(itemQuery.getType()==2){
					return "NOT_OPEN_STORAGE";
				}else{
					return "NOT_OPEN_SELL";
				}
			}else{
				if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()==ShopConstant.APPROVE_STATUS_HEGUI){
					if(itemQuery.getType()==2){
						return "IS_CLOSE_STORAGE";
					}else{
						return "IS_CLOSE_SELL";
					}
					
				}else if(shopInfoDO.getShopId()!=null&&shopInfoDO.getApproveStatus()!=ShopConstant.APPROVE_STATUS_YES){
					if(itemQuery.getType()==2){
						return "NOT_EXIST_STORAGE";
					}else{
						return "NOT_EXIST_SELL";
					}
				}
			}
//			if (shopInfoDO != null && shopInfoDO.getShopId() != null && shopInfoDO.getApproveStatus()==1) {
////				air.addMessage("image_invalid","用户已经开店，请先完成开店流程");
////				return "validateError";
//			}else{
//				return "validateError";
//			}
		} catch (ManagerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Boolean falg = shopShieldManager.getShieldInfoByUserId(sellerId);
		itemQuery.setShopStatus(falg);
		itemQuery.setSellerId(sellerId);
		itemList = itemListAO.getItemList(itemQuery);
		getItemListKey(itemList);
		return "success";
	}
	
	@AssistantPermission(target = "item", action = "delete")
	public String userDelItem() {

//		if (!checkToken()) {
//			itemQuery.setIds(null);
//		}
		if (itemQuery.getIds() != null) {
			itemListAO.userDelItem(itemQuery.getIds());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("删除商品线程休眠"+e);
		}
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		itemQuery.setSellerId(login.getMasterMemberId());
		itemList = itemListAO.getItemList(itemQuery);
		getItemListKey(itemList);
		String idsTitle = "";
		idsTitle = itemListAO.getItemTitles(itemQuery.getIds());
		memberAsstLog.log("item", "delete", "删除商品："+idsTitle);
		return SUCCESS;
	}

	@AssistantPermission(target = "item", action = "shelves")
	public String userAddShelfItem() {

//		if (!checkToken()) {
//			itemQuery.setIds(null);
//		}

		if (itemQuery.getIds() != null) {
			List<String> result = itemListAO.userAddShelfItem(itemQuery.getIds());
			itemQuery.setResultMsg(result);
		}
		//Thread th = new Thread();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("上架商品线程休眠"+e);
		}
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		itemQuery.setSellerId(login.getMasterMemberId());
		itemList = itemListAO.getItemList(itemQuery);
		getItemListKey(itemList);
		String idsTitle = itemListAO.getItemTitles(itemQuery.getIds());
		memberAsstLog.log("item", "shelves", "上架商品："+idsTitle);
		return SUCCESS;
	}

	@AssistantPermission(target = "item", action = "shelves")
	public String userDelShelfItem() {

//		if (!checkToken()) {
//			itemQuery.setIds(null);
//		}

		if (itemQuery.getIds() != null) {
			itemListAO.userDelShelfItem(itemQuery.getIds());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("下架商品线程休眠"+e);
		}
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		itemQuery.setSellerId(login.getMasterMemberId());
		itemList = itemListAO.getItemList(itemQuery);
		getItemListKey(itemList);
		String idsTitle = itemListAO.getItemTitles(itemQuery.getIds());
		memberAsstLog.log("item", "shelves", "下架商品："+idsTitle);
		return SUCCESS;
	}

	@AssistantPermission(target = "item", action = "shelves")
	public String userDelShelfItemExt() {
//		HttpServletResponse response = ServletActionContext.getResponse();
		if (itemQuery.getIds() != null) {
			itemListAO.userDelShelfItem(itemQuery.getIds());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("下架商品线程休眠"+e);
		}
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		itemQuery.setSellerId(login.getMasterMemberId());
		itemList = itemListAO.getItemList(itemQuery);
//		try {
//			response.sendRedirect(PinjuConstant.PINJU_SERVER+returnUrl);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String idsTitle = itemListAO.getItemTitles(itemQuery.getIds());
		memberAsstLog.log("item", "shelves", "下架商品："+idsTitle);
		return SUCCESS;
	}
	
	@AssistantPermission(target = "item", action = "delete")
	public String userDelItemExt() {
//		HttpServletResponse response = ServletActionContext.getResponse();
		if (itemQuery.getIds() != null) {
			itemListAO.userDelItem(itemQuery.getIds());
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("删除商品线程休眠"+e);
		}
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		itemQuery.setSellerId(login.getMasterMemberId());
		itemList = itemListAO.getItemList(itemQuery);
//		try {
//			response.sendRedirect(PinjuConstant.PINJU_SERVER+returnUrl);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String idsTitle = itemListAO.getItemTitles(itemQuery.getIds());
		memberAsstLog.log("item", "delete", "删除商品："+idsTitle);
		return SUCCESS;
	}
	
	public List<ItemDO> getItemList() {
		return itemList;
	}

	public void setItemList(List<ItemDO> itemList) {
		this.itemList = itemList;
	}

	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}

	public ItemQuery getItemQuery() {
		return itemQuery;
	}

	public void setItemQuery(ItemQuery itemQuery) {
		this.itemQuery = itemQuery;
	}

	public void setItemListAO(ItemListAO itemListAO) {
		this.itemListAO = itemListAO;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
}
