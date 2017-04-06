package com.yuwang.pinju.core.servicefee.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.servicefee.dao.ServiceFeeDAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.servicefee.ServiceFeeDO;

public class ServiceFeeDAOImpl extends BaseDAO implements ServiceFeeDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceFeeDO> selectServFeeByItemDO(ItemDO itemDO)
			throws DaoException {
		return super.executeQueryForList("service_fee.selectServFeeByItemDO",
				itemDO);
	}

	@Override
	public Double selectShopServiceFeeRateById(Long shopId) throws DaoException {
		return (Double) super.executeQueryForObject(
				"service_fee.selectShopServFeeRateById", shopId);
	}

}
