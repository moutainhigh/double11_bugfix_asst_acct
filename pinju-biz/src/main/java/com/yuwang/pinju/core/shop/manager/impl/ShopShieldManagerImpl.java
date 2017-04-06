/**
 * 
 */
package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.ao.ShopShieldAO;
import com.yuwang.pinju.core.shop.dao.ShopShieldDAO;
import com.yuwang.pinju.core.shop.manager.ShopShieldManager;
import com.yuwang.pinju.domain.shop.ShopShieldDO;

/**
 * 店铺屏蔽信息
 * 
 * @author caiwei
 *
 */
public class ShopShieldManagerImpl extends BaseManager implements
	ShopShieldManager {

    @Autowired
    private ShopShieldDAO shopShieldDAO;
    @Autowired
    private ShopShieldAO shopShieldAO;
    
    /**
     * 返回一个店铺是被屏蔽
     * 
     * @return [true:false]
     * 		true代表在屏蔽中、false代表不在屏蔽中
     */
    @Override
    public Boolean getShieldInfoByUserId(Long memberId) {
	try {
	    Boolean shieldFlag = shopShieldDAO.isShiledShop(new ShopShieldDO(memberId));
	    //如果不在屏蔽中则更新店铺状态
	    if (!shieldFlag) {
		shopShieldAO.updateShopStatusForUnShieldStatus(memberId);
	    }
	    return shieldFlag;
	} catch (Exception e) {
	    log.error("Event=[ShopShieldManager#getShieldInfoByUserId] 获取店铺屏蔽信息失败", e);
	}
	return false;
    }

    /**
     * 返回所有店铺是否被屏蔽信息
     * 
     * @param param param.memberId是用户ID
     * 		参数
     * @return
     * 		ShopShieldDO.isSuccess()
     * 		[true:false]
     * 		true代表在屏蔽中、false代表不在屏蔽中
     */
    @Override
    public List<ShopShieldDO> getShieldInfoByUserId(List<ShopShieldDO> param) {
	try {
	    for (ShopShieldDO shopShieldDO : param) {
		shopShieldDO.setSuccess(shopShieldDAO.isShiledShop(shopShieldDO));
	    }
        } catch (DaoException e) {
            log.error("Event=[ShopShieldManager#getShieldInfoByUserId] 获取多个店铺屏蔽信息失败", e);
        }
	return param;
    }

}
