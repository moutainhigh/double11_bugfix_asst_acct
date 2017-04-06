package com.yuwang.pinju.core.shop.ao;


import com.yuwang.pinju.domain.shop.ShopInfoDO;

public interface ShopInfoMemcacheAO {
	/**
	 * 获得店铺信息缓存数据
	 * @author 杨昭
	 * @param sellerId
	 * @return ShopInfoDO
	 * @since 2011-12-12
	 */
	public ShopInfoDO getShopInfoDO(Long sellerId);
}
