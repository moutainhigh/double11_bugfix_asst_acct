package com.yuwang.pinju.core.distribute.manager;

import java.util.List;

import com.yuwang.pinju.domain.distribute.DistributeChannelItemDO;
import com.yuwang.pinju.domain.distribute.DistributeChannelItemParamDO;

public interface DistributeChannelItemManager {

	/**
	 * 分销商商品上架
	 * 
	 * @param param
	 * 			分销商商品
	 * @return
	 */
	abstract Boolean addDistributeChannelItem(DistributeChannelItemDO param);

	/**
	 * 分销商商品下架
	 * 
	 * @param param
	 * 			分销商品
	 * @return
	 */
	abstract Boolean discardDistributeChannelItem(DistributeChannelItemDO param);

	/**
	 * 查询分销商品列表
	 * 
	 * @param param
	 * 			查询参数
	 * @return
	 */
	abstract List<DistributeChannelItemDO> findDistributeChannelItemByCondition(
			DistributeChannelItemParamDO param);

	/**
	 * 查询分销商品总数
	 * 
	 * @param param
	 * 			查询参数
	 * @return
	 */
	abstract Integer getCount(DistributeChannelItemParamDO param);

	/**
	 * 商品支持、反对信息更新
	 * 
	 * @param channelId
	 * 			分销商ID[加密]
	 * @param itemId
	 * 			商品ID
	 * @param type
	 * 			[1：支持、2：反对]
	 * @return
	 */
	abstract Boolean channelItemReputionUpdate(String channelId, Long itemId,
			Integer type);

}