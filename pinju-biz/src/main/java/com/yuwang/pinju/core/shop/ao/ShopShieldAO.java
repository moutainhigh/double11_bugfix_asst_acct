package com.yuwang.pinju.core.shop.ao;

/**
 * 店铺屏蔽信息AO
 * 
 * @author caiwei
 *
 */
public interface ShopShieldAO {

    /**
     * 将店铺的屏蔽状态改为正常状态‘
     * 
     * @param memberId
     * 		会员名称
     */
    void updateShopStatusForUnShieldStatus(Long memberId);
}
