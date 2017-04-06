package com.yuwang.pinju.core.shop.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopDomainDAO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class ShopDomainDAOImpl extends BaseDAO  implements ShopDomainDAO {

	/**
	 * 根据User_Id查询店铺信息
	 * @author XueQi
	 * @return shopbaseinfo对象
	 * @since 2011-10-9
	 */
	public ShopInfoDO getShopDomainObject(Long userId, String domain)throws DaoException{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("domain",domain);
		map.put("userId",userId);
		return (ShopInfoDO) super.executeQueryForObject("shopBaseInfo.getShopBaseInfoObject",map);
	}
	 /**
	  * 根据userId修改店铺域名
	  * @author XueQi
	  * @param userId
	  * @param domain
	  * @return 受影响行数
	  * @throws DaoException 
	  * @since 2011-10-9
	  */
	@Override
	public Object updateShopDomain(ShopInfoDO shopInfoDO) throws DaoException {
		return executeUpdate("shopBaseInfo.updateShopBaseInfo", shopInfoDO);
	}

}
