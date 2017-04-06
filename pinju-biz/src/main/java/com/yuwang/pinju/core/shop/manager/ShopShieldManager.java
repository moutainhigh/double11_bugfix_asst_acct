package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.domain.shop.ShopShieldDO;

/**
 * 屏蔽店铺信息
 * 
 * @author caiwei
 *
 */
public interface ShopShieldManager {
    /**
     * 返回一个店铺是被屏蔽
     * 
     * @return [true:false]
     * 		true代表在屏蔽中、false代表不在屏蔽中
     */
    Boolean getShieldInfoByUserId(Long memberId);
    
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
    List<ShopShieldDO> getShieldInfoByUserId(List<ShopShieldDO> param);
}
