package com.yuwang.pinju.core.order.ao;

import java.util.List;

import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * 购物车详细相关业务操作
 * @author shihongbo
 * @date   2011-6-8
 * @version 1.0
 */
public interface ShoppingCartDetailAO {
	/**
	 * 取得店铺详细信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	public List<ShopInfoDO> getShopList(List<Long> ids);
	
	/**
	 * 取得商品详细信息
	 * 
	 * @param shopInfoDO
	 * @return
	 */
	public List<ItemDO> getItemList(List<Long> ids);
	
	/**
	 * 通过商品sku编号 获得SKU
	 * 
	 * @param skuid
	 * @return
	 */
	public SkuDO getItemSkuById(Long skuid);
	
	/**
	 * 取得商品属性
	 * 
	 * @param ItemPropertyVO
	 * @return
	 */
	public ItemPropertyVO getItemPropertyBySku(String pv);
	
	/**
	 * 取得商品库存数量
	 * 
	 * @param itemId
	 * @param skuid
	 * @return
	 */
	public Long getItemStockCount(Long itemId, String skuid);
}
