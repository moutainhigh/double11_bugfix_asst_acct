package com.yuwang.pinju.core.servicefee.manager;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.servicefee.ServiceFeeResultDO;

/**
 * 平台服务费
 * 
 * @project pinju-biz
 * @author liuweiguo liuweiguo@zba.com
 * @date 2011-8-29下午2:11:36
 * @version V1.0
 * 
 */
public interface ServiceFeeManager {

	/**
	 * 根据商品店铺Id计算平台服务费
	 * 
	 * @return
	 * @throws ManagerException
	 * @return ServicefeeResultDO
	 */
	public ServiceFeeResultDO queryServiceFeeByItem(ItemDO itemDO, Long shopId)
			throws ManagerException;

	/**
	 * 根据商品查询平台服务费
	 * 
	 * @param itemDO
	 * @return
	 * @throws ManagerException
	 */
	public ServiceFeeResultDO queryServiceFeeByItem(ItemDO itemDO)
			throws ManagerException;

	/**
	 * 根据bi返回的费率以及店铺倍率查询平台服务费
	 * 
	 * @param price
	 * 
	 * @param cateRate
	 * @param shopRate
	 * @return 返回结果单位为分
	 * @throws ManagerException
	 */
	public Long calcServiceFee(long price, long cateRate, long shopRate)
			throws ManagerException;
}
