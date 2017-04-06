package com.yuwang.pinju.web.module.order.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.order.ao.ShoppingCartDetailAO;
import com.yuwang.pinju.core.refund.ao.ItemPropertyAO;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.order.query.SellerVO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.web.cookie.CookieShoppingCart;
import com.yuwang.pinju.web.cookie.manager.ShoppingCarCookieManager;

/**
 * 购物车详细
 * 
 * @author shihongbo
 * @date 2011-6-8
 * @version 1.0
 */
public class ShoppingCartDetailAction implements Action {
	
	private static Log log = LogFactory.getLog(ShoppingCartDetailAction.class);
	
	private ShoppingCartDetailAO shoppingCartDetailAO;
	private ShopShowInfoAO shopShowInfoAO;

	private ItemPropertyAO itemPropertyAO;
	
	//是否需要提示
	private boolean needtip = false;
	
	//商品列表
	private List<ItemVO> itemList;
	
	//卖家id列表
	private List<SellerVO> sellerList;
	
	//总价格
	private String totalPrice;
	
	//总数量
	private Integer totalCount;
	
	//删除的商品id
	private String delItemId;

	//sku
	private Map<String, SkuVO> skuMap;
	
	//商品属性
	private Map<String, List<ItemPropertyVO>> itemPropertyMap;

	//店铺
	private Map<String, ShopInfoDO> shopMap;
	
	private String callBack;
	
	private InputStream inputStream;
	
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}
	
	public String getDelItemId() {
		return delItemId;
	}

	public boolean isNeedtip() {
		return needtip;
	}

	public void setDelItemId(String delItemId) {
		this.delItemId = delItemId;
	}
	
	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public Map<String, ShopInfoDO> getShopMap() {
		return shopMap;
	}

	public Map<String, SkuVO> getSkuMap() {
		return skuMap;
	}

	public void setItemPropertyAO(ItemPropertyAO itemPropertyAO) {
		this.itemPropertyAO = itemPropertyAO;
	}

	public Map<String, List<ItemPropertyVO>> getItemPropertyMap() {
		return this.itemPropertyMap;
	}

	public void setShoppingCartDetailAO(ShoppingCartDetailAO shoppingCartDetailAO) {
		this.shoppingCartDetailAO = shoppingCartDetailAO;
	}

	public List<ItemVO> getItemList() {
		return itemList;
	}

	public List<SellerVO> getSellerList() {
		return sellerList;
	}
	
	public InputStream getInputStream() {
		return inputStream;
	}
	
	//设置所有商品相关的店铺信息
	private void setShopMap(List<ItemDO> itemDOList){
		shopMap = new HashMap<String, ShopInfoDO>();
		List<Long> userIdList = new LinkedList<Long>();
		for(ItemDO itemDO:itemDOList){
			userIdList.add(itemDO.getSellerId());
		}
		
		List<ShopInfoDO> shopInfoDOList = shoppingCartDetailAO.getShopList(userIdList);
		if(shopInfoDOList != null){
			for(ShopInfoDO shopInfoDO:shopInfoDOList){
				shopMap.put("key" + shopInfoDO.getUserId(), shopInfoDO);
			}
		}
		
	}
	
	//设置显示信息
	private void setShoppingCartView(List<ItemDO> itemDOList){
		itemList = new LinkedList<ItemVO>();
		itemPropertyMap = new HashMap<String, List<ItemPropertyVO>>();
		skuMap = new HashMap<String, SkuVO>();
		
		//设置店铺信息
		setShopMap(itemDOList);

		
		Long longTotalPrice = 0L;
		totalCount = 0;
		
		CookieShoppingCart[] a = ShoppingCarCookieManager.getCookieShoppingCarts();
		
		if(a == null)
			return;
		
		for(ItemDO itemDO:itemDOList){

			Long itemId = itemDO.getId();

			if(itemDO.getStatus() !=0 && itemDO.getStatus()!=1){
				needtip = true;
			}
			
			Integer shopId = shopShowInfoAO.queryShopIdByUserId(itemDO.getSellerId());
			
			for(CookieShoppingCart cookieShoppingCart:a){
				if(!cookieShoppingCart.getItemId().equals("" + itemId)){
					continue;
				}
			
				ItemVO itemVO = new ItemVO(itemDO);

				itemVO.setFavCount(cookieShoppingCart.getItemCount());
				
				//channelId
				String channelId = cookieShoppingCart.getChannelId();
				if(channelId == null || "".equals(channelId))
					channelId = "0";
				itemVO.setChannelId(channelId);
				
				//ad 广告id
				String ad = cookieShoppingCart.getAd();
				if(ad == null || "".equals(ad))
					ad = "0";
				itemVO.setAd(ad);
				

				//skuid
				String s_skuid = cookieShoppingCart.getSkuid();
				if(s_skuid == null || "".equals(s_skuid))
					s_skuid = "0";

				itemVO.setSkuid(s_skuid);
				
				//skudesc
				String s_skudesc = StringUtil.null2String(cookieShoppingCart.getSkuDesc());
				if("".equals(s_skudesc))
					s_skudesc = "0"; 
				itemVO.setSkuDesc(s_skudesc);
				

				//如果有skuid, 设置商品sku属性
				if(! "0".equals(s_skuid)){
					try{
						Long skuid = Long.parseLong(s_skuid);
						SkuDO sku = shoppingCartDetailAO.getItemSkuById(skuid);
						
						itemVO.setPrice(sku.getPrice());
						itemVO.setCurStock(sku.getCurrentStock());

						List<ItemPropertyVO> itemPropertylist = itemPropertyAO.getItemPropertyBySku(sku);

						itemPropertyMap.put("key" + itemId + s_skuid, itemPropertylist);

					}catch(Exception e){
						log.error(e);
					}
				}

				
				
				//收藏时候的价格
				Long priceAtFav = (new Money(cookieShoppingCart.getItemPrice())).getCent();
				if(priceAtFav == null)
					priceAtFav = 0L;
				
				//商品实时价格
				Long priceRealTime = itemVO.getPrice();
				
				//判断价格是否涨价或降价
				if(priceAtFav!=null && priceAtFav.compareTo(priceRealTime) > 0){
					itemVO.setLowerPrices(new Integer(1));
				}else if(priceAtFav!=null && priceAtFav.compareTo(priceRealTime)< 0){
					itemVO.setLowerPrices(new Integer(2));
				}else{
					itemVO.setLowerPrices(new Integer(0));
				}
				//end Lower Prices added
				//商品升或降 的价格数.
				//降价
				if(priceAtFav!=null && priceAtFav.compareTo(priceRealTime) > 0){
					Long leftPrices=priceAtFav.longValue()-priceRealTime.longValue();
					itemVO.setLeftPrices(leftPrices);
				}else if(priceAtFav!=null && priceAtFav.compareTo(priceRealTime)< 0){
					Long leftPrices=priceRealTime.longValue()-priceAtFav.longValue();
					itemVO.setLeftPrices(leftPrices);
				}
				itemVO.setMinStocks(itemVO.getCurStock());
				
				Long sortDate= Long.parseLong(cookieShoppingCart.getTime());
				itemVO.setSortDate(sortDate);

				itemVO.setItemCount(cookieShoppingCart.getItemCount());
				itemVO.setFavPrice(cookieShoppingCart.getItemPrice());
				
				if(priceAtFav.compareTo(itemVO.getPrice())!=0){
					itemVO.setFavPrice(MoneyUtil.getCentToDollar(priceAtFav));
					//priceDownMap.put("key" + itemId, "1");
					needtip = true;
				}

				int favCount = Integer.parseInt(cookieShoppingCart.getItemCount());
				longTotalPrice += priceRealTime*favCount;
				
				itemVO.setSingleTotal(MoneyUtil.getCentToDollar(priceRealTime*favCount));
				totalCount += Integer.parseInt(cookieShoppingCart.getItemCount());
				
				
				
				//加入显示列表
				itemList.add(itemVO);

			}
		}
		totalPrice =  (new Money(longTotalPrice)).toString();
		if(itemList!=null && itemList.size()!=0){
			Collections.sort(itemList);
		}
		
	}

	//根据商品列表得到卖家列表，用于显示
	private void createSellerList(List<ItemDO> itemList){
		Set<SellerVO> set = new HashSet<SellerVO>();
		for(ItemDO itemDO:itemList){
			SellerVO seller = new SellerVO();
			seller.setSellerId(itemDO.getSellerId());
			seller.setSellerNick(itemDO.getSellerNick());
			set.add(seller);
		}

		sellerList = new LinkedList<SellerVO>(set);
		Collections.sort(sellerList);
	}
	
	private List<Long> getItemIds(CookieShoppingCart[] a){
		if(a == null)
			return new LinkedList<Long>();
		
		Set<Long> set = new HashSet<Long>();
		for(CookieShoppingCart cookieShoppingCart:a){
			Long id = Long.parseLong(cookieShoppingCart.getItemId());
			set.add(id);
		}
		return new LinkedList<Long>(set);
	}
	
	/**
	 * <p>查询购物车信息</p>
	 */
	public String execute() {
		CookieShoppingCart[] a = ShoppingCarCookieManager.getCookieShoppingCarts();
		
		//空购物车页面
		if(a == null || a.length==0){
            return "empty";
		}
		
		List<Long> itemIds = getItemIds(a);
		

		List<ItemDO> itemDOList = shoppingCartDetailAO.getItemList(itemIds);
		
		createSellerList(itemDOList);
		
		setShoppingCartView(itemDOList);
		
		return SUCCESS;
	}
	
	/**
	 * <p>查询购物车信息，以jsonp格式返回</p>
	 */
	public String loadByStream(){
		CookieShoppingCart[] a = ShoppingCarCookieManager.getCookieShoppingCarts();
		
		//空购物车页面
		if(a == null || a.length==0){
            //return "empty";
			return getCartDataByJsonp();
		}
		
		List<Long> itemIds = getItemIds(a);
		
		List<ItemDO> itemDOList = shoppingCartDetailAO.getItemList(itemIds);
		
		createSellerList(itemDOList);
		
		setShoppingCartView(itemDOList);
		
		
		return getCartDataByJsonp();
	}
	
	private String getCartDataByJsonp(){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("itemList", itemList);
		data.put("itemPropertyMap", itemPropertyMap);
		
		JSONArray array = JSONArray.fromObject(data);
		String result = callBack + "(" + array.toString() + ")";
		return response(result);
	}
	
	private String response(String responseData) {
		try {
			inputStream = new ByteArrayInputStream(responseData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}
	
	/**
	 * 购物车删除商品
	 * @author shihongbo
	 * @since  2011-6-10
	 */
	public String deleteItemFromShoppingCart(){
		ShoppingCarCookieManager.deleteItemFromShoppingCart(delItemId);
		
		CookieShoppingCart[] a = ShoppingCarCookieManager.getCookieShoppingCarts();
		
		if(a == null || a.length==0){
            return "empty";
		}
		
		List<Long> itemIds = getItemIds(a);
		
		List<ItemDO> itemDOList = shoppingCartDetailAO.getItemList(itemIds);
		
		createSellerList(itemDOList);
		
		setShoppingCartView(itemDOList);
		
		return SUCCESS;
	}
	
}