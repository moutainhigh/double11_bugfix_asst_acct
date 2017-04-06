/**
 * 
 */
package com.yuwang.pinju.core.shop.ao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.ao.ShopShieldAO;
import com.yuwang.pinju.core.shop.manager.ShopOpenManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * 店铺屏蔽信息AO
 * 
 * @author caiwei
 *
 */
public class ShopShieldAOImpl extends BaseAO implements ShopShieldAO {

    @Autowired
    private ShopOpenManager shopOpenManager;
    
    private ShopShowInfoManager shopShowInfoManager;
    
    /**
     * 将店铺的屏蔽状态改为正常状态
     * 
     * @param memberId
     * 		会员名称
     */
    @Override
    public void updateShopStatusForUnShieldStatus(Long memberId) {
	try {
    	    ShopInfoDO shopInfoDO = shopShowInfoManager
    		    .queryShopBaseInfoByUser(memberId, null);
    	    if (shopInfoDO != null && shopInfoDO.getShopId() != null) {
    		shopOpenManager.updateShopApproveStatusByShopId(shopInfoDO
    			.getShopId(), ShopConstant.APPROVE_STATUS_YES);
    	    }
	} catch (ManagerException e) {
	    log.error("Event=[ShopShieldAO#updateShopStatusForUnShieldStatus] 店铺的屏蔽状态改为正常状态操作失败", e);
	   
	}

    }

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

}
