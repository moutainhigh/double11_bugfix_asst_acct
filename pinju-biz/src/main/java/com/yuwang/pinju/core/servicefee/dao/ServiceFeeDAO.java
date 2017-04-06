package com.yuwang.pinju.core.servicefee.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.servicefee.ServiceFeeDO;

/**
 * 平台服务费 数据持久化接口
 * 
 * @project pinju-biz
 * @author liuweiguo liuweiguo@zba.com
 * @date 2011-8-29下午2:11:13
 * @version V1.0
 * 
 */
public interface ServiceFeeDAO {


	/**
	 * 根据商品ItemDO查询商品的服务费
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<ServiceFeeDO> selectServFeeByItemDO(ItemDO itemDO) throws DaoException;
	
	/**
	 * 根据店铺Id查询店铺服务费倍率
	 * @param shopId
	 * @return
	 * @throws DaoException
	 * @return Long
	 */
	public Double selectShopServiceFeeRateById(Long shopId)throws DaoException;
}
