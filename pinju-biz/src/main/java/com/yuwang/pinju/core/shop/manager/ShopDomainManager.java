package com.yuwang.pinju.core.shop.manager;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public interface ShopDomainManager {
	/**
	 * 根据domian店铺域名查询店铺信息
	 * @author XueQi
	 * @return shopbaseinfo对象
	 * @since 2011-10-9
	 */
	 ShopInfoDO getShopDomainObject(Long userId, String domain)throws ManagerException;
	 /**
	  * 根据userId修改店铺域名
	  * @author XueQi
	  * @param userId
	  * @param domain
	  * @return 受影响行数
	  * @throws DaoException 
	  * @since 2011-10-9
	  */
	 Object updateShopDomain(ShopInfoDO shopInfoDO)throws ManagerException;
}
