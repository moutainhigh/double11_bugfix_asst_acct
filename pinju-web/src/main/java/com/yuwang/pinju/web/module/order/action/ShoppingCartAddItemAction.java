package com.yuwang.pinju.web.module.order.action;

import java.util.Date;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.order.ao.ShoppingCartDetailAO;
import com.yuwang.pinju.core.util.StringUtil;
import com.yuwang.pinju.web.cookie.CookieShoppingCart;
import com.yuwang.pinju.web.cookie.manager.ShoppingCarCookieManager;

/**
 * 购物车添加商品
 * @author shihongbo
 * @date   2011-6-7
 * @version 1.0
 */
public class ShoppingCartAddItemAction implements Action {

	//商品id
	private transient String itemId;
	
	//商品数量
	private transient String itemCount;
	
	//商品价格
	private transient String itemPrice;
	
	//skuid
	private transient String skuid;
	
	//skudesc
	private transient String skuDesc;
	
	//分销商id
	private transient String channelId;

	//广告id
	private transient String ad;

	//商品总价
	private String totalPrice;

	//商品总数
	private Integer totalCount;
	
	//商品种类数量
	private Integer catCount;
	
	//添加成功
	private Boolean addSuccess = false;
	
	//(值为true)表示商品库存不足
	private Boolean stockCountLower = false;
	
	protected final static Integer MAX_ITEM_COUNT=20;
	
	private ShoppingCartDetailAO shoppingCartDetailAO;
	
	public void setShoppingCartDetailAO(ShoppingCartDetailAO shoppingCartDetailAO) {
		this.shoppingCartDetailAO = shoppingCartDetailAO;
	}

	public Boolean getStockCountLower() {
		return stockCountLower;
	}

	public Boolean getAddSuccess() {
		return addSuccess;
	}

	public void setAddSuccess(Boolean addSuccess) {
		this.addSuccess = addSuccess;
	}

	public Integer getCatCount() {
		return catCount;
	}

	public void setCatCount(Integer catCount) {
		this.catCount = catCount;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	public String getSkuid() {
		return skuid;
	}

	public void setSkuid(String skuid) {
		this.skuid = skuid;
	}

	public String getSkuDesc() {
		return skuDesc;
	}

	public void setSkuDesc(String skuDesc) {
		this.skuDesc = skuDesc;
	}
	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	private void setCatCount(CookieShoppingCart[] cookieShoppingCart){
		
		//购物车中没有信息
		if(cookieShoppingCart == null || cookieShoppingCart.length == 0){
			catCount = 0;
		}else{
			catCount = cookieShoppingCart.length;
		}
	}
	
	/**
	 * <p>设置购物车中商品数量总数和总价格</p>
	 *
	 * @author shihongbo
	 */
	private void setTotal(CookieShoppingCart[] cookieShoppingCart){
		Long longTotalPrice = 0L;
		
		//购物车中没有信息
		if(cookieShoppingCart == null || cookieShoppingCart.length == 0){
			
			Long itemPriceLong = (new Money(itemPrice)).getCent();
			Integer itemCountInt = Integer.parseInt(itemCount);
			totalCount = itemCountInt;
			longTotalPrice = itemPriceLong * itemCountInt;
			totalPrice =  MoneyUtil.getCentToDollar(longTotalPrice).toString();
			
			return;
		}
		
		//购物车中有信息
		totalCount = 0;
		longTotalPrice = 0L;
		for(CookieShoppingCart a:cookieShoppingCart){
			
			Long itemPriceLong = (new Money(a.getItemPrice())).getCent();
			Integer itemCountInt = Integer.parseInt(a.getItemCount());
			totalCount += itemCountInt;
			longTotalPrice += itemPriceLong * itemCountInt;
		}
		
		totalPrice =  MoneyUtil.getCentToDollar(longTotalPrice).toString();
		
		
	}
	
	/**
	 * <p>取得当前购物车中的商品数量</p>
	 *
	 * @author shihongbo
	 */
	private Integer getCurrentItemCount(CookieShoppingCart[] cookieShoppingCart){
		if(cookieShoppingCart == null || cookieShoppingCart.length == 0){
			return 0;
		}
		
		int currentItemCount = 0;
		for(CookieShoppingCart a:cookieShoppingCart){
			Integer itemCountInt = Integer.parseInt(a.getItemCount());
			currentItemCount += itemCountInt;
		}
		
		return currentItemCount;
	}
	
	/**
	 * <p>选中商品和数量加入购物车</p>
	 *
	 * @author shihongbo
	 */
	private CookieShoppingCart[] addToShoppingCart(CookieShoppingCart[] a, CookieShoppingCart b){
		//购物车中没有信息
		if(a == null){
			return new CookieShoppingCart[]{b};
		}
		
		//购物车中有信息，并且商品之前已经添加到购物车，单纯累加数量
		for(CookieShoppingCart cookieShoppingCart:a){
			
			if(cookieShoppingCart.getItemId().equalsIgnoreCase(b.getItemId())
					&&(b.getSkuid().equals(cookieShoppingCart.getSkuid()))){
				
				Integer itemCount_a = Integer.parseInt(cookieShoppingCart.getItemCount());
				Integer itemCount_b = Integer.parseInt(b.getItemCount());
				Integer itemCount_new = itemCount_a + itemCount_b;
				cookieShoppingCart.setItemCount("" + itemCount_new);
				
				return a;
			}
		}
		
		//购物车中有信息，并且商品之前没有添加到购物车
		CookieShoppingCart[] newCookieShoppingCart = new CookieShoppingCart[a.length + 1];
		System.arraycopy(a, 0, newCookieShoppingCart, 0, a.length);
		newCookieShoppingCart[a.length] = b;
		
		return newCookieShoppingCart;
	}
	
	

	public String execute() {

		skuid = StringUtil.null2String(skuid);
		skuDesc = StringUtil.null2String(skuDesc);
		channelId = StringUtil.null2String(channelId);
		ad = StringUtil.null2String(ad);

		if(skuid.equals(""))
			skuid = "0";


		CookieShoppingCart[] a = ShoppingCarCookieManager.getCookieShoppingCarts();


		//判断库存
		//商品库存
		Long stockCount = shoppingCartDetailAO.getItemStockCount(Long.parseLong(itemId), skuid);
		
		
		//收藏数量
		Long favCount = 0L;
		for(CookieShoppingCart shoppingCart:a){
			if(shoppingCart.getItemId().equals(itemId)&& shoppingCart.getSkuid().equals(skuid))
				favCount = Long.parseLong(shoppingCart.getItemCount());	
		}
		favCount += Long.parseLong(itemCount);

		//如果收藏大于库存，提示库存不足
		if(favCount.compareTo(stockCount) > 0){
			addSuccess = false;
			stockCountLower = true;
			return SUCCESS;
		}

		
		//更改购物车Max Total
		if(a!=null && a.length==MAX_ITEM_COUNT){
			boolean isExitItem=false; 
			for(CookieShoppingCart shoppingCart:a){
				if(shoppingCart.getItemId().equals(itemId)&& shoppingCart.getSkuid().equals(skuid))
					isExitItem=true;	
			}
			if(!isExitItem){
				addSuccess = false;
				return SUCCESS;
			}
		}

		String time = "" + (new Date().getTime());
		CookieShoppingCart cookieShoppingCart = 
			new CookieShoppingCart(itemId, itemCount, itemPrice, skuid, skuDesc, channelId, ad, time);


		CookieShoppingCart[] newShoppingCartData = addToShoppingCart(a, cookieShoppingCart);

		//写入购物车信息到cookie
		ShoppingCarCookieManager.writeShoppingCars(newShoppingCartData);		

		//设置页面显示的购物车中商品数量总数和总价格
		setTotal(newShoppingCartData);

		//设置页面显示的购物车中商品种类数量
		setCatCount(newShoppingCartData);

		addSuccess = true;

		return SUCCESS;

	}


	public static void main(String[] args) {

		Date now = new Date();
		System.out.println(now.getTime());
	}
}
