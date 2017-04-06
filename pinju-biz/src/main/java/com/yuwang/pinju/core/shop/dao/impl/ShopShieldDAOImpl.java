/**
 * 
 */
package com.yuwang.pinju.core.shop.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopShieldDAO;
import com.yuwang.pinju.domain.shop.ShopShieldDO;

/**
 * @author caiwei
 *
 */
public class ShopShieldDAOImpl extends BaseDAO implements ShopShieldDAO {

    /**
     * 该店铺是否是屏蔽店铺
     * 
     * @param param
     * 		参数
     * @return
     */
    @Override
    public Boolean isShiledShop(ShopShieldDO param) throws DaoException {
	ShopShieldDO shopShieldDO = (ShopShieldDO)super.executeQueryForObject("shop_shield.selectShopShielded", param);
	if (shopShieldDO != null && shopShieldDO.getId() != null) {
	    return true;
	}
	return false;
    }
}
