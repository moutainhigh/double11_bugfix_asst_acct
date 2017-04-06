/**
 * 
 */
package com.yuwang.pinju.web.module.item.screen;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.Constant.ItemKeyConstants;
import com.yuwang.pinju.common.DesCryptUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.item.ao.ItemDetailAO;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.domain.active.ActivityDiscountDO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemDetailResult;
import com.yuwang.pinju.domain.item.ItemTagMetaInfo;
import com.yuwang.pinju.domain.item.SpuDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.singleton.ConfigManagerInfo;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.module.PinjuControl;
import com.yuwang.pinju.web.module.member.control.MemberSellerQualityControl;

/**
 * @Project: pinju-web
 * @Title: ItemDetail.java
 * @Package com.yuwang.pinju.web.module.item.screen
 * @Description: 商品详情展示页
 * @author liuboen liuboen@zba.com
 * @date 2011-6-4 下午02:14:22
 * @version V1.0
 */

public class ItemDetail extends BaseAction {

	private static final long serialVersionUID = 3349137656995212675L;
	private String itemId;
	private long tempItemid = 0;
	private ItemDetailAO itemDetailAO;
	private ItemDetailResult query;
	private ShopUserPageAO shopUserPageAO;
	private String errorMessage;

	public String execute() {
		/**
		 * ---------------------华丽的验证线---------------------
		 */
		if (itemId == null || itemId.equals("")) {
			errorMessage = "该商品不存在";
			return ERROR;
		}
		try {
			tempItemid = Long.parseLong(itemId);
		} catch (NumberFormatException e) {
			errorMessage = "该商品不存在";
			return ERROR;
		}
		if (tempItemid <= 0) {
			errorMessage = "该商品不存在";
			return ERROR;
		}
		ItemDO itemDO = itemDetailAO.getItemDOById(tempItemid);
		if (itemDO == null) {
			errorMessage = "该商品不存在或已经下架删除";
			return ERROR;
		}
		if (log.isDebugEnabled()) {
			log.debug("itemId = " + itemId.toString());
			log.debug(itemDO.toString());
		}
		String key = getString("key");

		// 分销代理
		setChannelId();

		long memberId = itemDO.getSellerId();
		// 判断是否该卖家
		boolean isSeller = isSeller(memberId);

		// 是否上架
		boolean isShelves = isShelves(itemDO);

		// 是否来自卖家后台
		boolean isFromSellerBack = Boolean.FALSE;
		if (isSeller) {
			isFromSellerBack = isSellerFromBack(key);
		}

		// 是否来自品聚工作环境IP
		boolean isFromPinjuIp = Boolean.FALSE;
		if (!isFromSellerBack) {
			isFromPinjuIp = isFromPinju(key);
		}

		// 校验商品是否卖家,是否上架状态,库存是否够,是否来源品聚
		if ((!isSeller || (isSeller && !isFromSellerBack)) && !isShelves && !isFromPinjuIp) {
			errorMessage = "该商品不存在或已经下架删除";
			return ERROR;
		}
	
		/**
		 * ----------------华丽的验证线----------------
		 */

		query = new ItemDetailResult();

		// 获取-[SPU]
		List<Map<String, Object>> spuProList = setSpuInfo(itemDO);

		// 获取-[类目属性]
		List<Map<String, Object>> cateProlist = itemDetailAO.getItemCategoryPro(itemDO.getPropertyValuePair());

		// 获取-[SKU信息]多个返回值
		query = itemDetailAO.getSkuByItemId(itemDO, query);

		// 获取-[店铺左侧和上部]多个返回值
		query = itemDetailAO.getShopInfoByUserId(memberId, query);

		// 获取-[店铺装修样式]
		String shopFitment = shopUserPageAO.getSkinColor(memberId, null, Boolean.TRUE);

		// 获取-[限时折扣/团购信息]
		String limitTimeDiscountID = itemDO.getFeatureByEnum(ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT);
		setActivityInfo(limitTimeDiscountID);

		// 获取-是否限购商品
		setLimitBuyItem(itemDO);

		// 获取卖家资质信息
		setSellerApInfo(memberId);

		query.setSpuProList(spuProList);
		query.setIsShelves(isShelves);
		query.setIsSeller(isSeller);
		query.setEncryptItemId(DesCryptUtil.encrypt(itemId + ":" + System.currentTimeMillis()));
		query.setItemDO(itemDO);
		query.setShopFitment(shopFitment);
		query.setCateProList(cateProlist);
		
		// 设置购买类型
		setBuyType();
		
		// 设置标签及其他信息
		setTagAndMeta();
		
		return SUCCESS;
	}
	

	// 查看是否来源于品聚内部
	private boolean isFromPinju(String keyWord) {
		if (isCrmEncryptKey(keyWord)) {
			// String cips[]=PinjuConstant.PINJU_COMPANY_IPS;
			String cips[] = ConfigManagerInfo.getStringValues("pinju.company.ips");
			String currentIp = ipAddr();
			if (cips != null) {
				for (String cip : cips) {
					if (cip.equals(currentIp)) {
						if (log.isDebugEnabled()) {
							log.debug("Access from the company internal,ip is:"+currentIp);
						}
						return Boolean.TRUE;
					}
				}
			}
		}
		return Boolean.FALSE;
	}

	// 是否CRM加密过来的key
	private boolean isCrmEncryptKey(String keyWord) {
		if (!StringUtil.isBlank(keyWord)) {
			String key = DesCryptUtil.decrypt(keyWord);
			String idString = StringUtil.substringBefore(key, ":");
			String type = StringUtil.substringAfter(key, ":");
			if (!StringUtil.isBlank(type)&& type.equals(ItemKeyConstants.KEY_FROM_CRM)&& !StringUtil.isBlank(idString)) {
				try {
					long iid = Long.parseLong(idString);
					if (iid == tempItemid) {
						if (log.isDebugEnabled()) {
							log.debug("is from crm,key=" + type);
						}
						return Boolean.TRUE;
					}
				} catch (NumberFormatException e) {
					return Boolean.FALSE;
				}
			}
		}
		return Boolean.FALSE;
	}

	public String ipAddr() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (log.isDebugEnabled()) {
			log.debug("Visitor's IP address is :" + ip);
		}
		return ip;
	}


	private void setChannelId() {
		String channelId = getString("channelId");
		if (channelId != null) {
			if (log.isDebugEnabled()) {
				log.debug("shop channelId = " + channelId.toString());
			}
			// 转发店铺分销用户ID
			request.setAttribute("channelId", channelId);
		}
	}

	private List<Map<String, Object>> setSpuInfo(ItemDO itemDO) {
		// 获取-[SPU]
		if (itemDO.getSpuId() != null && itemDO.getSpuId().longValue() > 0) {
			SpuDO spuDO = itemDetailAO.getItemCategorySpubyId(itemDO.getSpuId());
			if (spuDO != null) {
				if (log.isDebugEnabled()) {
					log.debug(spuDO.toString());
				}
				// 组装SPU
				List<Map<String, Object>> spuProList = itemDetailAO.getItemCategoryPro(spuDO.getPropertyValuePair());
				return spuProList;
			}
		}
		return null;
	}

	/**
	 * 设置购买类型
	 */
	private void setBuyType() {
		String urlFrom = getString("from");
		if (!StringUtil.isEmpty(urlFrom) && urlFrom.equals(ItemKeyConstants.URL_FROM_GROUP_BUY)) {
			if (log.isDebugEnabled()) {
				log.debug("Access from = " + ItemKeyConstants.URL_FROM_GROUP_BUY);
				log.debug("set buy type :" + ItemKeyConstants.URL_FROM_GROUP_BUY);
			}
			request.setAttribute("groupbuy", Boolean.TRUE);
			request.setAttribute("bussinessType", OrderItemDO.ORDER_ITEM_TYPE_4);
		}else {
			if (query.getActivityDiscountDO() != null) {
				if (log.isDebugEnabled()) {
					log.debug("set buy type : limit discount buy");
				}
				request.setAttribute("bussinessType", OrderItemDO.ORDER_ITEM_TYPE_3);
			} else {
				if (log.isDebugEnabled()) {
					log.debug("set buy type : common");
				}
				request.setAttribute("bussinessType", OrderItemDO.ORDER_ITEM_TYPE_2);
			}
		}
		
	}

	/**
	 * 是否是卖家
	 * 
	 * @param sellerId
	 * @return
	 */
	private boolean isSeller(long sellerId) {
		CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
		if (loginInfo.isLogin()) {
			long sellerMemberId = loginInfo.getMemberId();
			if (sellerMemberId == sellerId) {
				if (log.isDebugEnabled()) {
					log.debug("is sellers own,MemberId = " + sellerMemberId + ",Nickname=" + loginInfo.getNickname());
				}
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/**
	 * 是否正常上架并且库存大于0
	 * 
	 * @param itemDO
	 * @return
	 */
	private boolean isShelves(ItemDO itemDO) {
		if ((itemDO.getStatus().longValue() == 0 || itemDO.getStatus().longValue() == 1) && itemDO.getCurStock().longValue() > 0) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * 判断是否为卖家后台
	 * 
	 * @param sellerId
	 * @return
	 */
	private boolean isSellerFromBack(String keyWord) {
		String keyEncrpt = keyWord;
		if (!StringUtil.isBlank(keyEncrpt)) {
			String key = DesCryptUtil.decrypt(keyEncrpt);
			String idString = StringUtil.substringBefore(key, ":");
			String type = StringUtil.substringAfter(key, ":");
			if (!StringUtil.isBlank(type) && type.equals(ItemKeyConstants.KEY_FROM_SELLER) && !StringUtil.isBlank(idString)) {
				try {
					long iid = Long.parseLong(idString);
					if (iid == tempItemid) {
						if (log.isDebugEnabled()) {
							log.debug("Access from the seller Back,decrypt key=" + key);
						}
						return Boolean.TRUE;
					}
				} catch (NumberFormatException e) {
					return Boolean.FALSE;
				}
			}

		}
		return Boolean.FALSE;
	}

	/**
	 * 设置活动信息
	 * @param limitTimeDiscountID
	 */
	private void setActivityInfo(String limitTimeDiscountID){
		ActivityDiscountDO activityDiscountDO=itemDetailAO.getActivityDiscountDOById(limitTimeDiscountID);
		if (activityDiscountDO!=null) {
			query.setActivityDiscountDO(activityDiscountDO);
			long countDown=activityDiscountDO.getEndTime().getTime()-System.currentTimeMillis();
			long hourTime=countDown/ONE_HOUR;
			//long minuteTime=(countDown-hourTime*1000*60*60)/(1000*60);
			long minuteTime=(countDown%ONE_HOUR)/ONE_MINUTE;
			query.setActHour(hourTime);
			query.setActMinute(minuteTime);
			String []ids=activityDiscountDO.getItemList().split(",");
			for (int i = 0; i < ids.length; i++) {
				long id=Long.parseLong(ids[i]);
				if (id==tempItemid) {
					//获取折扣
					String []actDiscounts=activityDiscountDO.getDiscountList().split(",");
					long activityDiscount=Long.parseLong(actDiscounts[i]);
					Double actDiscount=Double.valueOf(activityDiscount)/1000;
					
					//获取限购数量
					String []actLimits=activityDiscountDO.getLimitList().split(",");
					long actLimit=Long.parseLong(actLimits[i]);
					query.setActLimitCount(actLimit);
					query.setActivityDiscount(actDiscount);
					break;
				}
			}
				
		}
	}

	/**
	 * 判断是否为限购商品
	 * 
	 * @param itemDO
	 */
	private void setLimitBuyItem(ItemDO itemDO) {
		String limitBuy = itemDO.getFeatureByEnum(ItemFeaturesEnum.ITEM_LIMIT_XIANGOU);
		if (limitBuy != null) {
			if (log.isDebugEnabled()) {
				log.debug("is limit buy item = true");
			}
			query.setIsLimitBuyItem(Boolean.TRUE);
		} else {
			if (log.isDebugEnabled()) {
				log.debug("is limit buy item = false");
			}
			query.setIsLimitBuyItem(Boolean.FALSE);
		}
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

	// 获取枚举和标签信息
	private void setTagAndMeta() {
		ItemTagMetaInfo itemTagMetaInfo = itemDetailAO.getItemTagMetaInfo(query.getItemDO());
		query.setItemTagMetaInfo(itemTagMetaInfo);
	}
	/**
	 * @return the query
	 */
	public ItemDetailResult getQuery() {
		return query;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param itemDetailAO
	 *            the itemDetailAO to set
	 */
	public void setItemDetailAO(ItemDetailAO itemDetailAO) {
		this.itemDetailAO = itemDetailAO;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}

}
