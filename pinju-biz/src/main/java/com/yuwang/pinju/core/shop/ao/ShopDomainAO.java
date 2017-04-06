package com.yuwang.pinju.core.shop.ao;

import com.yuwang.pinju.domain.shop.ShopInfoDO;

public interface ShopDomainAO {
	/**
	 * 根据domian店铺域名查询店铺信息
	 * @author XueQi
	 * @return shopbaseinfo对象
	 * @since 2011-10-9
	 */
	 ShopInfoDO getShopDomainObject(Long userId, String domain);
	 /**
	  * 根据userId修改店铺域名
	  * @author XueQi
	  * @param userId
	  * @param domain
	  * @return boolean
	  * @since 2011-10-9
	  */
	 boolean updateShopDomain(ShopInfoDO shopInfoDO);
}
