package com.yuwang.pinju.core.shop.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopShieldDO;

/**
 * 店铺屏蔽信息DAO
 * 
 * @author caiwei
 *
 */
public interface ShopShieldDAO {

    /**
     * 该店铺是否是屏蔽店铺
     * 
     * @param param
     * 		参数
     * @return
     */
    Boolean isShiledShop(ShopShieldDO param) throws DaoException ;
}
